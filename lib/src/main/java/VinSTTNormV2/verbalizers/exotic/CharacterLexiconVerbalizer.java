package VinSTTNormV2.verbalizers.exotic;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import VinSTTNormV2.taggers.exotic.CharacterLexiconTagger;
import VinSTTNormV2.verbalizers.ReplaceRegexVerbalizer;
import org.json.JSONObject;

public class CharacterLexiconVerbalizer extends ReplaceRegexVerbalizer {
    private static String TAG = "character_lexicon_normalizer";

    public CharacterLexiconVerbalizer(JSONObject config){
        super(config);
    }

    @Override
    public ReplaceRegexTagger getSampleExtractor(JSONObject config){
        return new CharacterLexiconTagger(config);
    }

    @Override
    public String doNorm(String spokenFormEntityString) {
        StringBuilder result = new StringBuilder();
        String[] lexiconCharacters = spokenFormEntityString.split(" ");
        for (String t: lexiconCharacters) {
            System.out.print(t + " ; ");
        }

        int idx = 0;
        while (idx < lexiconCharacters.length) {
            String allCharacterTokens = lexiconCharacters[idx];
            int endIdx = idx+1; // the token idx of the last token which belongs to a same character (Eg: vê kép => endIdx = idx+1)

            String prediction = super.doNorm(allCharacterTokens);
            if (prediction.equals(allCharacterTokens)){
                prediction = " " + prediction + " ";
                }
            while (endIdx < lexiconCharacters.length) {
                String currentString = allCharacterTokens + ' ' + lexiconCharacters[endIdx];

                String segmentPrediction = super.doNorm(currentString);

                // if next token is not in current character, break
                if (!this.cacheTokenMap.containsKey(currentString)) {
                    break;
                }
                prediction = segmentPrediction;

                allCharacterTokens = currentString;
                endIdx++;
            }

            idx = endIdx;

            result.append(prediction);
        }
        return result.toString().trim().replaceAll("( )+", " ");
    }

}

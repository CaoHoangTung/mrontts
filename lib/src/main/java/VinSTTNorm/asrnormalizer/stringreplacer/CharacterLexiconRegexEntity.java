package VinSTTNorm.asrnormalizer.stringreplacer;


import VinSTTNorm.asrnormalizer.abstractentity.ReplaceRegexEntity;
import org.json.JSONObject;

public class CharacterLexiconRegexEntity extends ReplaceRegexEntity {
    private static String TAG = "CHARACTER_LEXICON_ENTITY";

    public CharacterLexiconRegexEntity(JSONObject config) {
        super(config);
    }
    
    @Override
    public String getType() {
        return "character_lexicon";
    }

    @Override
    public String normEntity(String spokenFormEntityString) {
        StringBuilder result = new StringBuilder();
        String[] lexiconCharacters = spokenFormEntityString.split(" ");
        for (String t: lexiconCharacters) {
            System.out.print(t + " ; ");
        }

        int idx = 0;
        while (idx < lexiconCharacters.length) {
            String allCharacterTokens = lexiconCharacters[idx];
            int endIdx = idx+1; // the token idx of the last token which belongs to a same character (Eg: vê kép => endIdx = idx+1)

            String prediction = super.normEntity(allCharacterTokens);
            while (endIdx < lexiconCharacters.length) {
                String currentString = allCharacterTokens + ' ' + lexiconCharacters[endIdx];

                String segmentPrediction = super.normEntity(currentString);

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

        return result.toString();
    }
}

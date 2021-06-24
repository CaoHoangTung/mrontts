package VinSTTNormV2.taggers.propername;

import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.utilities.BinaryDictionary;
import VinSTTNormV2.utilities.ConfigUtilities;
import VinSTTNormV2.utilities.StringUtilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class BinaryProperNameTagger extends BinarySetBaseTagger {
    Set<Integer> properNameSet;

    public BinaryProperNameTagger(JSONObject config) {
        super(config);
        this.init();
    }

    private void init() {
        String[] keys = {this.getType()};
        JSONObject configObject = ConfigUtilities.getConfigItem(this.getGlobalConfig(), keys);
        try {
            JSONArray configItems = configObject.getJSONArray("word_list");
            this.properNameSet = ConfigUtilities.getSetFromJSONArray(configItems);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a list of entities
     * @param text
     * @return
     */
    @Override
    public SpanObject[] getSpans(String text){
        ArrayList<SpanObject> result = new ArrayList<>();

        String[] tokens = this.splitText(text);

        int startIdx = 0;
        int stringStartIdx = 0;

        while (startIdx <= tokens.length - this.getMinWindowSize()) {
            for (int wSize = this.getMaxWindowSize() ; wSize >= this.getMinWindowSize()  ; wSize--) {
                int endIdx = startIdx + wSize - 1;
                if (endIdx >= tokens.length) {
                    continue;
                }

                String[] candidate = Arrays.copyOfRange(tokens, startIdx, endIdx+1);


                String candidateString = StringUtilities.join(candidate, " ");

                if (BinaryDictionary.checkExist(this.properNameSet, candidateString)) {
                    int stringEndIdx = stringStartIdx + candidateString.length() - 1;

                    result.add(new SpanObject(stringStartIdx, stringEndIdx, this.getType(), candidateString, ""));

                    startIdx = endIdx;
                    stringStartIdx = stringEndIdx - candidate[candidate.length-1].length() + 1;

                    break;
                }
            }

            stringStartIdx += tokens[startIdx].length() + 1;
            startIdx++;
        }

        SpanObject[] resultStringArray = new SpanObject[result.size()];
        result.toArray(resultStringArray);

        return resultStringArray;
    }

    @Override
    public String getType() {
        return "all_proper_name";
    }

    /**
     * Use dictionary longest matching for word segmentation
     * @param text
     * @return list of string tokens
     */
    @Override
    public String[] splitText(String text) {
        String[] tokens = super.splitText(text);
        ArrayList<String> segmentedTokens = new ArrayList<>();

        int startTokenIdx = 0;
        int stringStartIdx = 0;

        while (startTokenIdx <= tokens.length - this.getMinWindowSize()) {
            for (int wSize = this.getMaxWindowSize(); wSize >= this.getMinWindowSize(); wSize--) {
                int endTokenIdx = startTokenIdx + wSize - 1;
                if (endTokenIdx >= tokens.length) {
                    continue;
                }

                String[] candidate = Arrays.copyOfRange(tokens, startTokenIdx, endTokenIdx+1);
                String candidateString = VinSTTNorm.asrnormalizer.utilities.StringUtilities.join(candidate, " ");

                if (BinaryDictionary.checkExist(binaryWordSegmentationSet, candidateString)) {
                    int stringEndIdx = stringStartIdx + candidateString.length() - 1;

                    segmentedTokens.add(candidateString);

                    startTokenIdx = endTokenIdx;
                    stringStartIdx = stringEndIdx - candidate[candidate.length-1].length() + 1;

                    break;
                } else if (wSize == 1) {
                    segmentedTokens.add(candidateString);
                }
            }

            stringStartIdx += tokens[startTokenIdx].length() + 1;
            startTokenIdx++;
        }

        String[] resultSegmentedTokens = new String[segmentedTokens.size()];
        segmentedTokens.toArray(resultSegmentedTokens);

        return resultSegmentedTokens;
    }

}

package VinSTTNorm.asrnormalizer.propername;

import VinSTTNorm.asrnormalizer.abstractentity.NameMapEntity;
import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import VinSTTNorm.asrnormalizer.utilities.StringUtilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * Combination of proper_name classes
 * Use the proper_name/full_dict option
 */
public class SameDictNameMapEntity extends NameMapEntity {
    Set<String> wordList;
    private final int MIN_WORD_SEGMENTATION_WSIZE = 1;
    private final int MAX_WORD_SEGMENTATION_WSIZE = 5;

    public SameDictNameMapEntity(JSONObject config) {
        super(config);
        this.init();
    }

    public void init() {
        try {
            String[] keys = {"word_segmentation"};
            JSONObject wordSegmentationConfig = ConfigUtilities.getConfigItem(this.getGlobalConfig(), keys);
            JSONArray wordSegmentationArray = wordSegmentationConfig.getJSONArray("word_list");
            this.wordList = ConfigUtilities.getSetFromJSONArray(wordSegmentationArray);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(TAG +  "Error init() PersonNameEntity");
        }
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
            for (int wSize = MAX_WORD_SEGMENTATION_WSIZE; wSize >= MIN_WORD_SEGMENTATION_WSIZE; wSize--) {
                int endTokenIdx = startTokenIdx + wSize - 1;
                if (endTokenIdx >= tokens.length) {
                    continue;
                }

                String[] candidate = Arrays.copyOfRange(tokens, startTokenIdx, endTokenIdx+1);
                String candidateString = StringUtilities.join(candidate, " ");

                if (this.wordList.contains(candidateString)) {
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
        // resultSegmentedTokens will be a list containing splitted tokens. Eg: {"<s>", "B???t", "??i???u", "h??a", "l??n", "hai", "s??u", "?????", "r?????i", "</s>"}

        return resultSegmentedTokens;
    }

    @Override
    protected int getMinWindowSize() {
        return 1;
    }

    @Override
    public String getType() {
        return "all_proper_name";
    }
}

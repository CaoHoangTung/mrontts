package VinSTTNormV2.spanExtractor;

import VinSTTNormV2.utilities.StringUtilities;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

abstract public class MapBaseExtractor extends BaseExtractor{
    protected final String TAG = "map_base_extractor";

    protected int getMinWindowSize(){
        return 2;
    }
    protected int getMaxWindowSize() {
        return 4;
    }

    Map<String, String> cacheEntityMap;

    /**
     * Get mapping for entity strings
     * Example: {
     *     "hoàng thành thăng long": "Hoàng Thành Thăng Long",
     *     "hà nội": "Hà Nội"
     * }
     * @return
     */
    abstract public Map<String, String> loadEntityMap();

    public MapBaseExtractor(JSONObject config) {
        super(config);
    }

    public Map<String, String> getEntityMap(){
        Map<String, String> entityMap;
        if (this.cacheEntityMap != null){
            entityMap = this.cacheEntityMap;
        } else {
            entityMap = this.loadEntityMap();
        }
        return entityMap;
    }

    public String[] splitText(String text){
        return text.split(" ");
    }

    /**
     * Get a list of entities
     * @param text
     * @return
     */

    @Override
    public SpanObject[] getSpans(String text){
        ArrayList<SpanObject> result = new ArrayList<>();

        Map<String, String> entityMap = this.getEntityMap();

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

                if (entityMap.containsKey(candidateString) || entityMap.containsKey(candidateString.toLowerCase(Locale.ROOT))) {
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
}

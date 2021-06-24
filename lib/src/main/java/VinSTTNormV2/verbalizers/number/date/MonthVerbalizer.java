package VinSTTNormV2.verbalizers.number.date;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import VinSTTNormV2.taggers.number.date.MonthTagger;
import VinSTTNormV2.verbalizers.ReplaceRegexVerbalizer;
import org.json.JSONObject;

public class MonthVerbalizer extends ReplaceRegexVerbalizer {

    public MonthVerbalizer(JSONObject config){
        super(config);
    }

    @Override
    public ReplaceRegexTagger getSampleExtractor(JSONObject config){
        return new MonthTagger(config);
    }

}

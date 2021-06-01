package VinSTTNormV2.spanNormalizer.number.time;

import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Queue;

public class TimeNormalizer extends BaseNormalizer {
    private static String TAG = "TIME_NORMALIZER";

    public TimeNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public String doNorm(String spokenFormEntityString){
        String[] tokens = spokenFormEntityString.split(" ");
        Queue<String> q = new LinkedList<>();

        boolean isLessFlag = false,
                isHalfFlag = false,
                includeHourFlag = false,
                includeMinuteFlag = false,
                includeSecondFlag = false;

        String  hour = "",
                minute = "",
                second = "";

        for (String token: tokens) {
            if (token.matches("^[0-9]+$")) { // if token is numeric
                q.add(token);

            } else {
                if (token.equals("rưỡi")) {
                    isHalfFlag = true;
                    includeMinuteFlag = true;
                } else if (token.equals("kém")) {
                    isLessFlag = true;
                }
            }
        }

        if (q.size() > 0) {
            hour = q.remove();
            includeHourFlag = true;
        }

        if (q.size() > 0) {
            minute = q.remove();
            if (minute == "không") {
                minute = "0";
            }
            includeMinuteFlag = true;
        }

        if (q.size() > 0) {
            second = q.remove();
            includeSecondFlag = true;
        }

        if (isLessFlag) {
            int numericHour;
            numericHour = ((Integer.parseInt(hour) - 1) + 24) % 24;
            int numericMinute;
            numericMinute = 60 - Integer.parseInt(minute);

            if (numericMinute == 60) {
                numericHour++;
                numericMinute = 0;
            }

            hour = String.valueOf(numericHour);
            minute = String.valueOf(numericMinute);
        }

        if (isHalfFlag) {
            minute = "30";
        }

        if (minute.length() == 1)
            minute = "0" + minute;
        if (second.length() == 1)
            second = "0" + second;

        String normText = "";
        if (includeHourFlag && !includeMinuteFlag && !includeSecondFlag)
            normText = hour + ":00";
        else if (includeHourFlag && includeMinuteFlag && !includeSecondFlag)
            normText = hour + ":" + minute;
        else
            normText = hour + ":" + minute + ":" + second;

        return normText;
    }
}

package VinSTTNormV2;

import VinSTTNorm.asrnormalizer.utilities.LogInfo;
import VinSTTNormV2.utilities.Utilities;

import java.util.Locale;

public class Benchmarker {
    OfflineNormalizer normalizer;

    public Benchmarker(OfflineNormalizer normalizer){
        this.normalizer = normalizer;
    }

    static public double calculateWER(String prediction, String groundTruth){
        String[] predictionTokens = prediction.split(" ");
        String[] groundTruthTokens = groundTruth.split(" ");

        int v0[] = new int[groundTruthTokens.length+1];
        int v1[] = new int[groundTruthTokens.length+1];

        for (int i = 0 ; i <= groundTruthTokens.length ; i++) {
            v0[i] = i;
        }

        for (int i = 0 ; i < predictionTokens.length ; i++) {
            v1[0] = i+1;

            for (int j = 0 ; j < groundTruthTokens.length ; j++) {
                int deletionCost = v0[j+1] + 1;
                int insertionCost = v1[j] + 1;
                int substitutionCost;
                if (predictionTokens[i].equals(groundTruthTokens[j])) {
                    substitutionCost = v0[j];
                } else {
                    substitutionCost = v0[j] + 1;
                }
                v1[j+1] = Math.min(deletionCost, Math.min(insertionCost, substitutionCost));
            }

            // swap array
            for (int j = 0 ; j <= groundTruthTokens.length ; j++) {
                int tmp = v1[j];
                v1[j] = v0[j];
                v0[j] = tmp;
            }
        }
        return (double) v0[groundTruthTokens.length] / predictionTokens.length;
    }

    public String evaluate(String spokenFilePath, String writtenFilePath) {
        return this.evaluate(spokenFilePath, writtenFilePath, true, true, true);
    }

    public String evaluate(String spokenFilePath, String writtenFilePath, boolean firstCharCaseSentitive, boolean doLogCsv, boolean caseSensitive){
        String[] lines = Utilities.loadLinesFromFile(spokenFilePath);
        String[] results = Utilities.loadLinesFromFile(writtenFilePath);

        int totalSample = 0;
        int correctSample = 0;
        float accuracy = 0;
        double totalWER = 0;

        long t0 = System.currentTimeMillis();
        boolean currentSampleIsCorrect = true;
        String currentInput = "";
        String currentResult = "";
        String currentPrediction = "";
        double currentMinWER = 1;

        for (int idx = 0; idx < lines.length ; idx++){
            String line = lines[idx];
            String result = results[idx];

            if (!line.equals("")){
                totalSample++;
                if (!currentSampleIsCorrect){
                    System.out.println(String.format("ERROR AT:\nLine: %s\nType: %s\nString: %s\nResult: %s\nPrediction: %s", idx+1, spokenFilePath, currentInput, currentResult, currentPrediction));
                }

                if (idx > 0){
                    totalWER += currentMinWER;
                    if (currentSampleIsCorrect) correctSample++;

                    if (doLogCsv){
                        LogInfo logInfo = new LogInfo();
                        logInfo.category = "Test final";
                        logInfo.test = null;
                        logInfo.result = null;
                        logInfo.prediction = null;
                        logInfo.wer = String.valueOf(currentMinWER);
                        logInfo.status = currentSampleIsCorrect ? "PASS" : "FAIL";

                        logInfo.category = null;
                        logInfo.test = null;
                        logInfo.result = null;
                        logInfo.prediction = null;
                        logInfo.wer = null;
                        logInfo.status = null;
                    }
                }

                currentSampleIsCorrect = false;
                currentMinWER = 1;
                currentInput = line;

                String prediction = normalizer.normText(line);
                currentPrediction = prediction;
                currentResult = "";
            }
            currentResult += result + " | ";
            if (!caseSensitive){
                currentPrediction = currentPrediction.toLowerCase(Locale.ROOT);
                result = result.toLowerCase(Locale.ROOT);
            }
            if (currentPrediction.equals(result) ||
                    currentPrediction.equals(result.replace("một", "1")) ||
                    currentPrediction.equals(result.replace(" giờ", ":00")) ||
                    currentPrediction.replace("°", "o").equals(result)){
                currentSampleIsCorrect = true;
            }
            double wer = calculateWER(currentPrediction, result);
            if (wer < currentMinWER){
                currentMinWER = wer;
            }
            if (doLogCsv) {
                LogInfo logInfo = new LogInfo();
                logInfo.category = spokenFilePath.split("/")[2];
                logInfo.test = line;
                logInfo.result = result;
                logInfo.prediction = currentPrediction;
                logInfo.wer = String.valueOf(wer);
                logInfo.status = currentPrediction.equals(result) ? "1" : "0";

//                FileLogger.getInstance().write(logInfo);
            }
            if (idx == lines.length-1){
                totalWER += currentMinWER;
                if (currentSampleIsCorrect) correctSample++;
                else {
                    System.out.println(String.format("ERROR AT:\nLine: %s\nType: %s\nString: %s\nResult: %s\nPrediction: %s", idx+1, spokenFilePath, currentInput, currentResult, currentPrediction));
                }
                if (doLogCsv){
                    LogInfo logInfo = new LogInfo();
                    logInfo.category = "Test final";
                    logInfo.test = null;
                    logInfo.result = null;
                    logInfo.prediction = null;
                    logInfo.wer = String.valueOf(currentMinWER);
                    logInfo.status = currentSampleIsCorrect ? "PASS" : "FAIL";

                    logInfo.category = null;
                    logInfo.test = null;
                    logInfo.result = null;
                    logInfo.prediction = null;
                    logInfo.wer = null;
                    logInfo.status = null;
                }
            }
        }

        long elapsedTime = System.currentTimeMillis() - t0;

        accuracy = ((float) correctSample/(float) totalSample) * 100;
        double avarageWER = totalWER / totalSample;

        return String.format("Total: %s ; Correct: %s ; Accuracy: %s%% ; Avg WER: %s ; Time taken: %sms", totalSample, correctSample, accuracy, avarageWER, elapsedTime);
    }
}

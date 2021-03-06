package VinSTTNorm.asrnormalizer;

import VinSTTNorm.asrnormalizer.utilities.LogInfo;
import VinSTTNorm.asrnormalizer.utilities.Utilities;
import VinSTTNorm.speech.asr.INormalizer;

import java.util.Locale;

public class Benchmarker {
    INormalizer normalizer;

    public Benchmarker(INormalizer normalizer) {
        this.normalizer = normalizer;
    }

    static private int levDistance(String a, String b, int pa, int pb, int[][] levDistCache) {
        if (pa == a.length()) {
            return b.length();
        } else if (pb == b.length()) {
            return a.length();
        } else {
            if (a.charAt(pa) == b.charAt(pb)) {
                return levDistance(a, b, pa+1, pb+1, levDistCache);
            } else {

            }
        }
        return 0;
    }

    static public double calculateWER(String prediction, String groundTruth) {
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
        return this.evaluate(spokenFilePath, writtenFilePath, true, false);
    }

    public String evaluate(String spokenFilePath, String writtenFilePath, boolean firstCharacterCaseSensitive, boolean doLogCsv) {
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

        for (int idx = 0 ; idx < lines.length ; idx++) {
            String line = lines[idx];
            String result = results[idx];

            // if meet a new sample
            if (!line.equals("")) {
                totalSample++;
                if (!currentSampleIsCorrect) {
                    System.out.println(String.format("ERROR AT:\nLine: %s\nType: %s\nString: %s\nResult: %s\nPrediction: %s", idx+1, spokenFilePath, currentInput, currentResult, currentPrediction));
                }

                if (idx > 0) {
                    totalWER += currentMinWER;
                    if (currentSampleIsCorrect)
                        correctSample++;

                    if (doLogCsv) {
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

            currentPrediction = currentPrediction.toLowerCase(Locale.ROOT);
            result = result.toLowerCase(Locale.ROOT);

            if (currentPrediction.equals(result)) {
                currentSampleIsCorrect = true;
            }

            double wer = calculateWER(currentPrediction, result);
            if (wer < currentMinWER) {
                currentMinWER = wer;
            }

            if (idx == lines.length-1) {
                if (!currentSampleIsCorrect) {
                    System.out.println(String.format("ERROR AT:\nLine: %s\nType: %s\nString: %s\nResult: %s\nPrediction: %s", idx+1, spokenFilePath, currentInput, currentResult, currentPrediction));
                } else {
                    correctSample++;
                }
                totalWER += currentMinWER;
            }



            if (doLogCsv) {
                LogInfo logInfo = new LogInfo();
                logInfo.category = spokenFilePath.split("/")[2];
                logInfo.test = line;
                logInfo.result = result;
                logInfo.prediction = currentPrediction;
                logInfo.wer = String.valueOf(wer);
                logInfo.status = currentPrediction.equals(result) ? "1" : "0";
            }
        }

        long elapsedTime = System.currentTimeMillis() - t0;

        accuracy = ((float) correctSample/(float) totalSample) * 100;
        double avarageWER = totalWER / totalSample;

        return String.format("Total: %s ; Correct: %s ; Accuracy: %s%% ; Avg WER: %s ; Time taken: %sms", totalSample, correctSample, accuracy, avarageWER, elapsedTime);
    }

    public static void main(String[] args) {
        Benchmarker benchmarker = new Benchmarker(new VinFastVoiceControlAsrNormalizer());

        String navigationBenchmarkResult = benchmarker.evaluate("testcase/navigation/spoken", "testcase/navigation/written");
        String navigationCustom01BenchmarkResult = benchmarker.evaluate("testcase/navigation_custom01/spoken", "testcase/navigation_custom01/written");
        String numberBenchmarkResult = benchmarker.evaluate("testcase/numbers/spoken", "testcase/numbers/written");
        String datetimeBenchmarkResult = benchmarker.evaluate("testcase/datetime/spoken", "testcase/datetime/written");
        String calculationBenchmarkResult = benchmarker.evaluate("testcase/calculations/spoken", "testcase/calculations/written");
        String appnameBenchmarkResult = benchmarker.evaluate("testcase/appname/spoken", "testcase/appname/written");
        String websitenameBenchmarkResult = benchmarker.evaluate("testcase/websitename/spoken", "testcase/websitename/written");
        String officialBenchmarkResult = benchmarker.evaluate("testcase/official_offline/spoken", "testcase/official_offline/written");
        String officialBenchmarkResultv4 = benchmarker.evaluate("testcase/official_offline_v4/spoken", "testcase/official_offline_v4/written");
        String officialBenchmarkResultvnghi = benchmarker.evaluate("testcase/official_offline_nghi/spoken", "testcase/official_offline_nghi/written");

        String result = String.format("" +
                        "Sao Official vnghi80: %s\n\n" +
                        "Sao Official v4: %s\n\n" +
                        "Sao Official: %s\n\n" +
                        "Navigation: %s\n\n" +
                        "Navigation custom 01: %s\n\n" +
                        "Number: %s\n\n" +
                        "Datetime: %s\n\n" +
                        "Calculation: %s\n\n" +
                        "Appname: %s\n\n" +
                        "Websitename: %s",
                officialBenchmarkResultvnghi,
                officialBenchmarkResultv4,
                officialBenchmarkResult,
                navigationBenchmarkResult,
                navigationCustom01BenchmarkResult,
                numberBenchmarkResult,
                datetimeBenchmarkResult,
                calculationBenchmarkResult,
                appnameBenchmarkResult,
                websitenameBenchmarkResult
        );
        
        System.out.println(String.format("TEST RESULT %s", result));
    }
}

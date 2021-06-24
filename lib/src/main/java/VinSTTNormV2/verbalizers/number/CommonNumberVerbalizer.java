package VinSTTNormV2.verbalizers.number;

import VinSTTNormV2.verbalizers.BaseVerbalizer;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CommonNumberVerbalizer extends BaseVerbalizer {
    private static class Pair {
        public final int value;
        public final int exp;

        public Pair(int value, int exp) {
            this.value = value;
            this.exp = exp;
        }

        public String toString() {
            return String.format("Pair(%s,%s)", value, exp);
        }
    }
    private final HashMap<String, Pair> stringToDigit = new HashMap<>();
    private final HashMap<String, Pair> stringToExp = new HashMap<>();

    public CommonNumberVerbalizer(JSONObject config){
        super(config);

        stringToDigit.put("linh", new Pair(0, 0));
        stringToDigit.put("lẻ", new Pair(0, 0));
        stringToDigit.put("không", new Pair(0, 0));
        stringToDigit.put("mốt", new Pair(1, 0));
        stringToDigit.put("một", new Pair(1, 0));
        stringToDigit.put("hai", new Pair(2, 0));
        stringToDigit.put("hăm", new Pair(2, 0));
        stringToDigit.put("ba", new Pair(3, 0));
        stringToDigit.put("bốn", new Pair(4, 0));
        stringToDigit.put("tư", new Pair(4, 0));
        stringToDigit.put("năm", new Pair(5, 0));
        stringToDigit.put("lăm", new Pair(5, 0));
        stringToDigit.put("nhăm", new Pair(5, 0));
        stringToDigit.put("sáu", new Pair(6, 0));
        stringToDigit.put("bảy", new Pair(7, 0));
        stringToDigit.put("bẩy", new Pair(7, 0));
        stringToDigit.put("tám", new Pair(8, 0));
        stringToDigit.put("chín", new Pair(9, 0));
        stringToDigit.put("mười", new Pair(1, 1));

        // the value here represent if the exp should be replaced(1) or multiplied(0) by the original exp from the stack
        stringToExp.put("tỉ", new Pair(1000000000, 9));
        stringToExp.put("tỷ", new Pair(1000000000, 9));
        stringToExp.put("triệu", new Pair(1000000, 6));
        stringToExp.put("nghìn", new Pair(1000, 3));
        stringToExp.put("ngàn", new Pair(1000, 3));
        stringToExp.put("trăm", new Pair(100, 2));
        stringToExp.put("chục", new Pair(10, 1));
        stringToExp.put("mươi", new Pair(10, 1));
    }

    @Override
    public String doNorm(String spokenFormEntityString){
        boolean lastCharacterIsSpace = (spokenFormEntityString.charAt(spokenFormEntityString.length()-1)==' ');

        ArrayList<Pair> decimalStack = new ArrayList<>();
        ArrayList<Pair> currentStack = decimalStack;

        String[] tokens = spokenFormEntityString.split(" +");

        // trăm mốt, triệu hai, ngàn ba...
        if (this.stringToExp.containsKey(tokens[0])) {
            ArrayList<String> newTokens = new ArrayList<>(Arrays.asList(tokens));
            newTokens.add(0, "một");
            tokens = new String[newTokens.size()];
            newTokens.toArray(tokens);
        }

        // the "locked" stack value token will not be updated as the exp flow backwards
        int stackTokenIdx = -1;
        int lockedStackTokenIdx = -1;
        int lockedHundredTokenIdx = -1;

        int minExpThousandOrMore = 10; // this value is used to handle cases like "một ngàn hai ngàn ba ngàn"
        int minExpLessThanThousand = 4;

        int countPreviousNumToken = 0;
        int startIdx = 0;
        int endIdx = tokens.length-1;

        for (int tokenIdx = startIdx; tokenIdx <= endIdx; tokenIdx++) {
            String token = tokens[tokenIdx];
            if (this.stringToDigit.containsKey(token)) { // 1 2 3...
                Pair digitAndBase = this.stringToDigit.get(token);

                if (!token.equals("mười")) {
                    countPreviousNumToken++;
                    if (countPreviousNumToken == 2) {
                        for (int i = currentStack.size() - 1; i > lockedStackTokenIdx; i--) {
                            Pair item = currentStack.get(i);
                            currentStack.set(i, new Pair(item.value, currentStack.size() - i));
                        }
                    }
                }


                if (lockedHundredTokenIdx == stackTokenIdx)
                    lockedStackTokenIdx = stackTokenIdx;


                int value = digitAndBase.value;
                int exp = (currentStack.size() == 0 || digitAndBase.exp != 0) ? digitAndBase.exp : currentStack.get(currentStack.size() - 1).exp - 1;

                if (token.equals("mười") && exp < 1) {
                    exp = 1;
                    for (int i = lockedHundredTokenIdx + 1; i < stackTokenIdx; i++) {
                        Pair item = currentStack.get(i);
                        currentStack.set(i, new Pair(item.value, item.exp - 1));
                    }
                }

                if (exp == -1)
                    exp = 0;

                currentStack.add(new Pair(value, exp));
                stackTokenIdx++;

                if (token.equals("mười"))
                    lockedHundredTokenIdx = stackTokenIdx + 1;


                for (int idx = currentStack.size() - 2; idx >= 0; idx--) {
                    if (idx <= lockedHundredTokenIdx)
                        break;

                    Pair stackItem1 = currentStack.get(idx);
                    Pair stackItem2 = currentStack.get(idx + 1);

                    if (stackItem1.exp != stackItem2.exp)
                        break;

                    currentStack.set(idx, new Pair(stackItem1.value, (stackItem1.exp + 1)));
                }

            } else if (this.stringToExp.containsKey(token)) { // trăm, nghìn, triệu,....
                countPreviousNumToken = 0;
                int unitExp = this.stringToExp.get(token).exp;

                // if need to multiply
                if (unitExp >= 3) { // >= nghìn
                    if (unitExp >= minExpThousandOrMore) { // new number
                        int lastIdx = currentStack.size() - 1;
                        Pair oldStackValue = currentStack.get(lastIdx);

                        currentStack.set(lastIdx, new Pair(oldStackValue.value, 0));
                        lockedStackTokenIdx = lastIdx - 1;
                    }

                    minExpThousandOrMore = Math.min(unitExp, minExpThousandOrMore);
                    minExpLessThanThousand = 4;

                    for (int idx = currentStack.size() - 1; idx >= 0; idx--) {
                        if (idx <= lockedStackTokenIdx)
                            break;

                        Pair oldStackValue = currentStack.get(idx);
                        currentStack.set(idx, new Pair(oldStackValue.value, unitExp + oldStackValue.exp));
                    }

                    lockedStackTokenIdx = stackTokenIdx;
                    lockedHundredTokenIdx = lockedStackTokenIdx;
                } else { // <= trăm
                    int idx = currentStack.size() - 1;
                    if (unitExp >= minExpLessThanThousand) {
                        Pair oldStackValue = currentStack.get(idx);
                        currentStack.set(idx, new Pair(oldStackValue.value, 0));
                        lockedStackTokenIdx = currentStack.size() - 1;
                    }
                    minExpLessThanThousand = Math.min(unitExp, minExpLessThanThousand);
                    minExpThousandOrMore = 10;

                    if (idx >= 0) {
                        Pair topStackValue = currentStack.get(idx);
                        if (topStackValue.exp == unitExp) {
                            continue;
                        }
                        currentStack.set(idx, new Pair(topStackValue.value, unitExp));

                        currentStack.set(stackTokenIdx, new Pair(topStackValue.value, unitExp));
                        for (int i = lockedHundredTokenIdx + 1; i < stackTokenIdx; i++) {
                            Pair item = currentStack.get(i);
                            currentStack.set(i, new Pair(item.value, item.exp - 1));
                        }
                    }
                }
                lockedHundredTokenIdx = Math.max(stackTokenIdx + unitExp, lockedHundredTokenIdx);
            }
        }

        StringBuilder writtenFormEntityStringBuilder = new StringBuilder("");

        long value = 0;
        long maxExp = 1000000001;

        for (Pair item : decimalStack) {
            if (item.value == 0)
                writtenFormEntityStringBuilder.append('0');
            else
                break;
        }

        for (Pair item : decimalStack) {
            // if new number (sep by space)
            if (maxExp <= item.exp && value > 0) {
                if (writtenFormEntityStringBuilder.length() > 0) {
                    writtenFormEntityStringBuilder.append(' ');
                }
                writtenFormEntityStringBuilder.append(value);
                value = 0;
            }
            value += item.value * Math.pow(10, item.exp);
            maxExp = item.exp;
        }

        // append last value
        if (value > 0) {
            if (writtenFormEntityStringBuilder.length() > 0 && writtenFormEntityStringBuilder.charAt(0) != '0') {
                writtenFormEntityStringBuilder.append(' ');
            }
            writtenFormEntityStringBuilder.append(value);
        }

        if (spokenFormEntityString.contains("âm") || spokenFormEntityString.contains("trừ"))
            writtenFormEntityStringBuilder.insert(0, '-');

        // handle cases where regex also capture space at end
        if (lastCharacterIsSpace)
            writtenFormEntityStringBuilder.append(' ');


        String writtenFormEntityString = smoothWrittenForm(writtenFormEntityStringBuilder.toString());

        return writtenFormEntityString;
    }

    /**
     * Smooth the written form text obtained from norm function
     * Ex: 20 19 => 2019 (year)
     * @param writtenFormText
     * @return
     */
    public String smoothWrittenForm(String writtenFormText) {
        String joinText = writtenFormText.replaceAll(" ", "");
        if (joinText.length() == 4 && (joinText.startsWith("20") || joinText.startsWith("1"))) {
            writtenFormText = joinText;
        }
        return writtenFormText;
    }


}

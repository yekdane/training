package service;
import service.common.Base;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasisTwo {
    private static List<Integer> basisTwoVal = new ArrayList<>();
    public static final Map<Integer, String> HEX_STR = new HashMap<>();
    private static int changedLen, arrayLen;

    static {
        HEX_STR.put(10, "A");
        HEX_STR.put(11, "B");
        HEX_STR.put(12, "C");
        HEX_STR.put(13, "D");
        HEX_STR.put(14, "E");
        HEX_STR.put(15, "F");
    }

    public static void setBasisTwoValNumber(String val) throws Exception {
        try {
            basisTwoVal.clear();
            if (val == "" || val == null) throw new Exception();
            for (Character c : val.toCharArray()) {
                if (c == '0' || c == '1')
                    basisTwoVal.add(0, Integer.parseInt(c.toString()));
                else throw new Exception();
            }
            arrayLen = basisTwoVal.size();
        } catch (Exception ex) {
            throw new Exception("Your number must be '0' or '1' ");
        }
    }

    public static void doActions() throws Exception {
        Base.print(8, convertToBasis8());
        Base.print(10, convertToBasis10());
        Base.print(16, convertToBasis16());
    }

    public static String getMapKey(String srchVal) {
        for (Map.Entry<Integer, String> entry : BasisTwo.HEX_STR.entrySet()) {
            if (entry.getValue().equals(srchVal))
                return entry.getKey().toString();
        }
        return null;
    }

    private static void addSize(int size) {
        changedLen = 0;
        int temp = (arrayLen % size);
        if (temp > 0) changedLen = size - temp;
        for (int i = 0; i < changedLen; i++)
            basisTwoVal.add(0);
        arrayLen = basisTwoVal.size();
    }

    private static void removeSize(int size) {

        for (int i = arrayLen - 1; i >= arrayLen - changedLen; i--)
            basisTwoVal.remove(i);
        arrayLen = basisTwoVal.size();
    }

    public static String convertToBasis8() {
        String retVal = "";

        addSize(3);

        for (int j = 0; j < arrayLen; j++) {
            retVal = convertToBasis10(j, j += 2) + retVal;
        }

        removeSize(3);

        return retVal;
    }

    private static String convertToBasis10(int from, int to) {
        int retVal = 0;

        for (int j = from; j <= to; j++) {
            retVal += basisTwoVal.get(j) * (int) Math.pow(2, j - from);
        }
        return String.valueOf(retVal);
    }

    public static String convertToBasis10() {
        return convertToBasis10(0, arrayLen - 1);
    }

    public static String convertToBasis16() {
        String retVal = "";

        addSize(4);

        Integer val;
        for (int j = 0; j < arrayLen; j++) {
            val = Integer.valueOf( convertToBasis10(j, j += 3));
            retVal = (HEX_STR.get(val) == null ? val.toString() : HEX_STR.get(val)) + retVal;
        }

        removeSize(4);

        return retVal;
    }

}

package service;
import service.common.Base;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BasisHex  {
    private static List<Character> basisHexVal = new ArrayList<>();

    public static void setBasisHexValNumber(String Val) throws Exception {
        basisHexVal.clear();
        if (!Pattern.matches("[0-7A-F]+", Val)) throw new Exception("Your number must be '0' to '9' or 'A' to 'F'");
        for (Character c : Val.toCharArray()) {
            basisHexVal.add(0, c);
        }
    }

    public static String convertToBasis2() throws Exception {
        String retVal = "";
        for (Character i : basisHexVal) {
            String val = String.valueOf(i);
            retVal = Base.replicate(
                    Base.basis2(Integer.valueOf(Pattern.matches("[0-7]+", val) ? val : BasisTwo.getMapKey(val)))
                    , 4) + retVal;
        }

        Base.prepareBasis2(retVal);
        return retVal;
    }

    public static void doActions() throws Exception {
        Base.print(2, convertToBasis2());
        Base.print(8, Base.convertToBasis8());
        Base.print(10, Base.convertToBasis10());
    }
}

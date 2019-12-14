package service;

import service.common.Base;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BasisEight {
    private static List<Integer> basisEightVal = new ArrayList<>();

    public static void setBasisEightValNumber(String Val) throws Exception {
        basisEightVal.clear();
        if (!Pattern.matches("[0-7]+", Val)) throw new Exception("Your number must be '0' to '7' ");
        for (Character c : Val.toCharArray()) {
            basisEightVal.add(0, Integer.parseInt(c.toString()));
        }
    }

    public static String convertToBasis2() throws Exception {
        String retVal = "";
        for (int i : basisEightVal) {
            retVal = Base.replicate(Base.basis2(i), 3) + retVal;
        }
        Base.prepareBasis2(retVal);
        return retVal;
    }

    public static void doActions() throws Exception {
        Base.print(2, convertToBasis2());
        Base.print(10, Base.convertToBasis10());
        Base.print(16, Base.convertToBasis16());
    }
}

package service.common;
import service.BasisTwo;

public  class Base {
    public static String basis2(int num) {
        String retVal = Integer.toString(num % 2);
        if (num / 2 == 0)
            return retVal;
        return basis2(num / 2) + retVal;
    }

    public static String replicate(String val, int len) {
        return ("0".repeat(len - val.length()) + val);
    }

    public static void prepareBasis2(String val) throws Exception {
        String retVal = val.replaceAll("^0+", "");
        BasisTwo.setBasisTwoValNumber(retVal);
    }

    public static String convertToBasis8() throws Exception {
        return BasisTwo.convertToBasis8();
    }

    public static String convertToBasis10() throws Exception {
        return BasisTwo.convertToBasis10();
    }

    public static String convertToBasis16() throws Exception {
        return BasisTwo.convertToBasis16();
    }
    public static void print(int basis,String val){
        System.out.println("(?)"+basis+" = "+ val);
    }
}

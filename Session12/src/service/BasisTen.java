package service;
import service.common.Base;

public class BasisTen  {
    private static Integer basisTenVal;

    public static void setBasisTenValNumber(Integer Val) {
        basisTenVal = Val;
    }

    public static String convertToBasis2() throws Exception {
        String retVal = Base.basis2(basisTenVal);
        Base.prepareBasis2(retVal);
        return retVal;
    }

    public static void doActions() throws Exception {
        Base.print(2, convertToBasis2());
        Base.print(8, Base.convertToBasis8());
        Base.print(16, Base.convertToBasis16());
    }
}

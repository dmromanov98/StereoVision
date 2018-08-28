package Utils;

import static java.lang.Double.valueOf;

public class Test {

    public static boolean testOnNumber(String string){
        boolean res = false;
        try {
            double d = valueOf(string);
            res = true;
        }catch (Exception e){}

        return res;
    }

    public static boolean testOnInteger(String string){
        boolean res = false;
        try {
            int d = Integer.valueOf(string);
            res = true;
        }catch (Exception e){}

        return res;
    }
}

package Utils;

import static java.lang.Double.valueOf;

public class Test {

    /***
     * test string on double value
     * @param string
     * @return
     */
    public static boolean testOnDouble(String string){
        boolean res = false;
        try {
            double d = valueOf(string);
            res = true;
        }catch (Exception e){}

        return res;
    }

    /***
     * test string on double value
     * @param string
     * @return
     */
    public static boolean testOnInteger(String string){
        boolean res = false;
        try {
            int d = Integer.valueOf(string);
            res = true;
        }catch (Exception e){}

        return res;
    }
}

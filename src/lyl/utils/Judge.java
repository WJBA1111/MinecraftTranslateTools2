package lyl.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Judge {

    // 是不是数字
    public static boolean isNum(String s){
        Pattern pattern = Pattern.compile("[0-9]*\\.?[0-9]+");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
    public static boolean isAllNum(String...s){
        Pattern pattern = Pattern.compile("[0-9]*\\.?[0-9]+");
        for (String value : s) {
            Matcher matcher = pattern.matcher(value);
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }


}

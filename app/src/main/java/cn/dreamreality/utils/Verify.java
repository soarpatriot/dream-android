package cn.dreamreality.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuhaibao on 15/5/24.
 */
public class Verify {

    public static boolean isMobileNO(String mobiles){
        boolean flag = true;
        try{
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        }catch(Exception e){

            flag = false;
        }
        return flag;
    }
}

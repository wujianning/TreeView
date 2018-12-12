package com.wjn.treeview.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wjn on 2017/11/22.
 * 布尔值工具类
 */

public class BooleanUtils {

    /**
     * 判断字符串是否为空
     * */

    public static boolean isEmpty(String content){
        if("".equals(content)||"null".equals(content)||null==content){
            return true;
        }else{
            return  false;
        }
    }

    /**
     * 判断手机号是否合法
     * */

    public static boolean isMobileNO(String mobiles) {
        Pattern p=Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher m=p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断email格式是否正确
     * */

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断字符串是否是纯数字 带小数
     * */

    public static boolean isNum(String str){
        return str.matches("^[0.0-9.0]+$");
    }

    /**
     * 判断字符串是否是纯数字
     * */

    public static boolean isNumber(String str){
        String reg="^[0-9]+?$";
        return str.matches(reg);
    }

    /**
     * 判断字符串是否全部为空串
     * */

    public static boolean isStringHaveElement(String[] string){
        boolean result=false;
        if(null!=string){
            for(String s:string){
                if(!BooleanUtils.isEmpty(s)){
                    result=true;
                    break;
                }
            }
        }
        return result;
    }

}

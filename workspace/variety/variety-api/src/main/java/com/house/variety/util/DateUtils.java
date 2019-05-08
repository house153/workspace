package com.house.variety.util;

import java.util.Calendar;

/**
 * @Auther: tanfan
 * @Date: 2019/5/6 15:27
 * @Description:
 */
public class DateUtils {
    /**
     * 获取当年已过的年月份
     * @return
     */
    public static String[] getYearMonthByOld(){
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month =cale.get( Calendar.MONTH)+1;
        String [] res = new String[12];
        for(int i=1;i<=month;i++){
            if((i+"").length()>1){
                res[i-1]= year+"-"+i+"";
            }else{
                res[i-1]= year+"-"+"0"+i;
            }
        }
        return res;
    }

    /**
     * 获取当年未过的年月份
     * @return
     */
    public static String[] getYearMonthByNew(){
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month =cale.get( Calendar.MONTH)+1;
        String [] res = new String[12];
        for(int i=month+1;i<=12;i++){
            if((i+"").length()>1){
                res[i-1]= year+"-"+i+"";
            }else{
                res[i-1]= year+"-"+"0"+i;
            }
        }
        return res;
    }

    /**
     *
     * @param str  2019-01 00:00:00
     * @return    2019/01/01 00:00:00
     */
    public static String getDateTransf(String str){
        String [] strArr = str.split(" ");
        String [] yArr =  strArr[0].split("-");

        return yArr[0]+"/"+yArr[1]+"/01 00:00:00";
    }
}

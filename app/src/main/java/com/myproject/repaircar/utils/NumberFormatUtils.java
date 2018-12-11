package com.myproject.repaircar.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by niceinkeaw on 30/3/2559.
 */
public class NumberFormatUtils {
    public static String toCurrency(Double value){
        NumberFormat formatter = new DecimalFormat("###,###,##0.00");
        String currency = formatter.format(value);
        return currency;
    }

    public static int toInt(String value){
        try{
            return Integer.parseInt(value);
        }catch (Exception e){
            return 0;
        }
    }


    public static double toDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0;
        }
    }
}

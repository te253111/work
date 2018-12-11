package com.myproject.repaircar.consts;

/**
 * Created by Semicolon07 on 2/17/2017 AD.
 */

public class ServiceUrl {
    //public static final String BASE_URL = "http://192.168.1.220:8888/pos-online";
    //public static final String BASE_URL = "http://192.168.0.104:8888/pos-online";
    public static final String BASE_URL = "http://unigainfo.homedns.org:8888/pos-online";
    public static final String BASE_API_URL = BASE_URL+"/apis/";
    public static final String LOGIN = "login.php";
    public static final String GET_PRODUCT_LIST = "get_product_list.php";
    public static final String GET_TABLE_LIST = "get_table_list.php";
    public static final String GET_ORDER_LIST = "get_order_list.php";
    public static final String ORDER_SERVICE = "order_service.php";

    public static String getDownloadLink(String exportFileName) {
        String url = BASE_URL + "/uploads/"+exportFileName;
        return url;
    }
}

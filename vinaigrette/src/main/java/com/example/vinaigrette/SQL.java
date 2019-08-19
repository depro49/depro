package com.example.vinaigrette;

public class SQL {

    public static String DB_NAME = "db_cron";

    public static String PRODUCT_ORDER = "product_order";
    public static String PRODUCT_ORDER_PARAM = "orderId,product_id,count";
    public static String PRODUCT_ORDER_INDEX_NAME = "prod_ord_ind";
    public static String PRODUCT_ORDER_INDEX_COLUMN = "orderId";
    public static String PRODUCT_ORDER_FIELDS = "prod_ord INTEGER PRIMARY KEY, orderId TEXT, product_id INTEGER, count INTEGER";

    public static String ORDER_TAB = "order_tab";
    public static String ORDER_INDEX_NAME = "order_ind";
    public static String ORDER_INDEX_COLUMN = "orderId";
    public static String ORDER_FIELDS = "ord_ind INTEGER PRIMARY KEY, orderId TEXT, orderName TEXT, status INTEGER, comment TEXT, payBonus INTEGER, date INTEGER";
    public static String ORDER_LIST = "SELECT * FROM order_tab";
}

package com.example.vinaigrette;

public class SQL {

    public static String DB_NAME = "db_cron";

    public static String PRODUCT_ORDER = "product_order";
    public static String PRODUCT_ORDER_PARAM = "order_name,product_id,product_name,count,price";
    public static String PRODUCT_ORDER_INDEX_NAME = "prod_ord_ind";
    public static String PRODUCT_ORDER_INDEX_COLUMN = "order_name";
    public static String PRODUCT_ORDER_FIELDS = "prod_ord INTEGER PRIMARY KEY, order_name TEXT, product_name TEXT, product_id INTEGER, count INTEGER, price REAL";
    public static String PRODUCT_ORDER_WHERE = "product_id = ?";
    public static String PRODUCT_IN_ORDER = "SELECT *, (price * count) AS amount " +
            "FROM product_order WHERE order_name = ? ORDER BY product_name";

    public static String ORDER_TAB = "order_tab";
    public static String ORDER_WHERE = "order_name = ?";
    public static String ORDER_FIELDS = "ord_ind INTEGER PRIMARY KEY, order_name TEXT, status INTEGER, comment TEXT, date INTEGER";
    public static String ORDER_LIST = "SELECT * FROM order_tab WHERE date >= ? AND date <= ? ORDER BY date";
    public static String ORDER_LIST_ALL = "SELECT * FROM order_tab";
}

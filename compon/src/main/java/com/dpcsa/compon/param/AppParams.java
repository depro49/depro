package com.dpcsa.compon.param;

public abstract class AppParams<T> {
    public int youtubeApiKey = 0;
    public String baseUrl;
    public ParamModel.TypeParam typeParameterTransfer = ParamModel.TypeParam.NAME;
    public int paginationPerPage = 20;
    public String paginationNameParamPerPage = "PerPage";
    public String paginationNameParamNumberPage = "NumberPage";
    public int NETWORK_TIMEOUT_LIMIT = 30000; // milliseconds
    public int RETRY_COUNT = 0;
    public int LOG_LEVEL = 3;    // 0 - not, 1 - ERROR, 2 - URL, 3 - URL + jsonResponse
    public static String NAME_LOG_NET = "SMPL_NET";
    public static String NAME_LOG_APP = "SMPL_APP";
    public static String NAME_LOG_DB = "SMPL_DB";
    public Class<T>  classProgress;
    public Class<T>  classErrorDialog;
    public int errorDialogViewId = 0,
            errorDialogLayoutId = 0,
            errorDialogPositiveId = 0,
            errorDialogNegativeId = 0;
    public int progressLayoutId = 0;
    public String nameTokenInHeader = "";

    public String nameLanguageInHeader = "",
            nameLanguageInParam = "",
            initialLanguage = "";

    public String nameVersionInHeader = "",
            valueVersionInHeader = "";

    public boolean nameLanguageInURL = false;

    public int idStringERRORINMESSAGE = 0;
    public int idStringDefaultErrorTitle = 0;
    public int idStringNOCONNECTION_TITLE = 0;
    public int idStringNOCONNECTIONERROR = 0;
    public int idStringTIMEOUT = 0;
    public int idStringSERVERERROR = 0;
    public int idStringJSONSYNTAXERROR = 0;
    public int idStringNO_AUTH = 0;
    public int defaultMethod = ParamModel.GET;

    public abstract void setParams();
    public AppParams() {
        setParams();
    }
}

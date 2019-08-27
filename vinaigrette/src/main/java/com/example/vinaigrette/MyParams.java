package com.example.vinaigrette;

import com.dpcsa.compon.param.AppParams;

public class MyParams extends AppParams {
    @Override
    public void setParams() {
        baseUrl =  "http://examples.delta.branderstudio.com/";
        nameTokenInHeader = "Auth-token";

//        youtubeApiKey = R.string.youtube_api_key;
        progressLayoutId = R.layout.dialog_progress;
        errorDialogLayoutId = R.layout.dialog_error;
        errorDialogNegativeId = R.id.cancel;
        errorDialogPositiveId = R.id.exit;

        idStringDefaultErrorTitle = R.string.er_title_def;
//        errorDialogViewId = R.id.error_dialog;
        idStringERRORINMESSAGE = R.string.er_message;
        idStringNOCONNECTION_TITLE = R.string.er_connect_title;
        idStringNOCONNECTIONERROR = R.string.er_connect;
        idStringTIMEOUT = R.string.er_timeout;
        idStringSERVERERROR = R.string.er_server_error;
        idStringJSONSYNTAXERROR = R.string.er_json_syntax;
        idStringNO_AUTH = R.string.er_no_auth;
    }
}

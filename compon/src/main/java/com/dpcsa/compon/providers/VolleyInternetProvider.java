package com.dpcsa.compon.providers;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import com.dpcsa.compon.base.BaseInternetProvider;
import com.dpcsa.compon.interfaces_classes.IVolleyListener;
import com.dpcsa.compon.volley.VolleyProvider;
import com.dpcsa.compon.volley.VolleyRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class VolleyInternetProvider extends BaseInternetProvider {
    VolleyRequest request;

    @Override
    public void setParam(int method, String url, Map<String, String> headers,
                         String data, InternetProviderListener listener) {
        super.setParam(method, url, headers, data, listener);
        byte [] dataBytes = null;
        if (data != null) {
            dataBytes = data.getBytes();
        }
        request = new VolleyRequest(method, url, listenerVolley,
                headers, dataBytes);
        VolleyProvider.getInstance().addToRequestQueue(request);
    }

    @Override
    public void cancel() {
        super.cancel();
        request.cancel();
    }

    IVolleyListener listenerVolley = new IVolleyListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            int statusCode = ERRORINMESSAGE;
            int status = 0;
            if (error.networkResponse != null) {
                status = error.networkResponse.statusCode;
                NetworkResponse response = error.networkResponse;
                String jsonSt;
                try {
                    jsonSt = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                } catch (UnsupportedEncodingException e) {
                    jsonSt = "";
                    e.printStackTrace();
                }
//                Log.d("QWERT","onErrorResponse status="+status+" jsonSt="+jsonSt);
                if (jsonSt != null && jsonSt.length() > 0) {
                    listener.error(status, jsonSt);
                    return;
                }
            }
//            Log.d(Injector.getComponGlob().appParams.NAME_LOG_NET,"VolleyInternetProvider error.toString()="+error.toString()+"< status="
//                    + status
//                    +"< mes="+error.getMessage()+"< URL="+url);
            String st = error.toString();
            if (st != null) {
                st = st.toUpperCase();
                if (st.contains("TIMEOUT")) {
                    statusCode = TIMEOUT;
                } else {
                    if (st.contains("NOCONNECTIONERROR")) {
                        statusCode = NOCONNECTIONERROR;
                    } else {
                        if (st.contains("SERVERERROR")) {
                            statusCode = SERVERERROR;
                        } else {
                            if (st.contains("AUTHFAILURE")) {
                                statusCode = AUTHFAILURE;
                            }
                        }
                    }
                }
            }
            listener.error(statusCode, error.getMessage());
        }

        @Override
        public void onResponse(String response) {
            listener.response(response);
        }
    };

}

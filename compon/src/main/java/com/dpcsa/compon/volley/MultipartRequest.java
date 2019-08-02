package com.dpcsa.compon.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.dpcsa.compon.interfaces_classes.IVolleyListener;
import com.dpcsa.compon.param.AppParams;
import com.dpcsa.compon.single.Injector;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class MultipartRequest extends Request<String> {
//    private static final String CONTENT_TYPE_IMAGE = "image/jpeg";
    private static final String CONTENT_TYPE_IMAGE = "multipart/form-data";
    private final Map<String, File> mFilePart;

    private IVolleyListener listener;
    private Map<String, String> headers;
    private String data;
    private final String boundary = "apiclient-" + System.currentTimeMillis();
    MultipartEntityBuilder entity = MultipartEntityBuilder.create();
    HttpEntity httpentity;
    private AppParams appParams;

    public MultipartRequest(String url, IVolleyListener listener, Map<String, String> headers, String data, Map<String, File> file) {
        super(Method.POST, url, listener);
        appParams = Injector.getComponGlob().appParams;
        if (appParams.LOG_LEVEL > 1) Log.d(appParams.NAME_LOG_NET, "method=" + Method.POST + " url=" + url);

        this.headers = headers;
        this.listener = listener;
        this.data = data;
        setRetryPolicy(new DefaultRetryPolicy(appParams.NETWORK_TIMEOUT_LIMIT,
                appParams.RETRY_COUNT, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mFilePart = file;
        entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        buildMultipartEntity();
    }

//    public void addStringBody(String param, String value) {
//        mStringPart.put(param, value);
//    }

    private void buildMultipartEntity() {
//        for (Map.Entry<String, File> entry : mFilePart.entrySet()) {
////            entity.addPart(entry.getKey(), new FileBody(entry.getValue(), ContentType.create("image/jpeg"), entry.getKey()));
//Log.d("QWERT","buildMultipartEntity entry.getKey()="+entry.getKey()+" NNNN="+entry.getValue().getName()+"<< EEE="+entry.getValue().exists());
//            entity.addBinaryBody(entry.getKey(), entry.getValue(), ContentType.create(CONTENT_TYPE_IMAGE), entry.getValue().getName());
////            try {
////                entity.addBinaryBody(entry.getKey(), Util.toByteArray(new FileInputStream(entry.getValue())), ContentType.create("image/jpeg"), entry.getKey() + ".JPG");
////            } catch (FileNotFoundException e) {
////                e.printStackTrace();
////            }
//        }

        Log.d("QWERT","buildMultipartEntity data="+data);
        entity.addTextBody("data", data);

//        for (Map.Entry<String, String> entry : mStringPart.entrySet()) {
//            if (entry.getKey() != null && entry.getValue() != null) {
//                entity.addTextBody(entry.getKey(), entry.getValue());
//            }
//        }
    }

//    @Override
//    public String getBodyContentType() {
//        return httpentity.getContentType().getValue();
//    }

    @Override
    public   String   getBodyContentType ( )   {
        return   "multipart/form-data;boundary="   +   boundary ;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            httpentity = entity.build();
            httpentity.writeTo(bos);
        } catch (IOException e) {
            Log.d(appParams.NAME_LOG_NET,"getBody error="+e);
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonSt = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            if (appParams.LOG_LEVEL > 2) Log.d(appParams.NAME_LOG_NET, "Respons json=" + jsonSt);
            CookieManager.checkAndSaveSessionCookie(response.headers);
            return Response.success( jsonSt, HttpHeaderParser.parseCacheHeaders(response));
//            return Response.success( (T) Html.fromHtml(jsonSt).toString(),
//                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            if (appParams.LOG_LEVEL > 0) Log.d(appParams.NAME_LOG_NET, "UnsupportedEncodingException="+e);
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (appParams.LOG_LEVEL > 2) Log.d(appParams.NAME_LOG_NET,"VolleyRequest headers="+headers);
        return headers;
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
//        if (error.networkResponse != null && error.networkResponse.data != null) {
//            Log.d(appParams.NAME_LOG_NET, "VolleyRequest deliverError error=" + error.networkResponse.data.toString());
//        }
        listener.onErrorResponse(error);
    }
}

package de.mateco.integrAMobile.Helper;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;

/**
 * Created by S Soft on 11-Jan-18.
 */

public class GZipRequest extends StringRequest {

    public GZipRequest(int method, String url, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public GZipRequest(String url, Response.Listener listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        return Response.success(unzipString(response.data), HttpHeaderParser.parseCacheHeaders(response));
    }

    public static String unzipString(byte[] zbytes) {
        String charsetName = "ISO-8859-1";
        String unzipped = null;
        try {
// Add extra byte to array when Inflater is set to true
            byte[] input = new byte[zbytes.length + 1];
            System.arraycopy(zbytes, 0, input, 0, zbytes.length);
            input[zbytes.length] = 0;
            ByteArrayInputStream bin = new ByteArrayInputStream(input);
            InflaterInputStream in = new InflaterInputStream(bin);
            ByteArrayOutputStream bout = new ByteArrayOutputStream(512);
            int b;
            while ((b = in.read()) != -1) {
                bout.write(b);
            }
            bout.close();
            unzipped = bout.toString(charsetName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return unzipped;
    }

}

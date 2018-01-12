package de.mateco.integrAMobile.Helper;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.TimeZone;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by S Soft on 10-Jan-18.
 */

public class RequestInterceptor implements Interceptor
{
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException
    {
        Request originalRequest = chain.request();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        Request newRequest = originalRequest.newBuilder()
                .header("token", URLEncoder.encode(DataHelper.getToken().trim(), "UTF-8"))
                .build();
        return chain.proceed(newRequest);
    }
}

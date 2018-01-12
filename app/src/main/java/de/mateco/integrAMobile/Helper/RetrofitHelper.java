package de.mateco.integrAMobile.Helper;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by S Soft on 10-Jan-18.
 */

public class RetrofitHelper {
    Context con;
    public RetrofitHelper(Context con) {
        this.con = con;
        //MyApplication.component(con).inject(this);
    }
    public RetrofitInterface getRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                //.connectTimeout(600, TimeUnit.SECONDS)
                //.addInterceptor(new RequestInterceptor())
                .build();

        Retrofit service = new Retrofit.Builder().baseUrl(DataHelper.URL_USER_HELPER).
                addConverterFactory(ScalarsConverterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).
                client(okHttpClient).
                build();

        return service.create(RetrofitInterface.class);
    }

}

package de.mateco.integrAMobile.Helper;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by S Soft on 10-Jan-18.
 */

public interface RetrofitInterface {

    @GET("salesservice/token={token}")
    Call<String> getSalesService(@Path("token") String token);
}


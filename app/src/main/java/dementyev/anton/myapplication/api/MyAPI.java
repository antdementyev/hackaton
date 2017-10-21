package dementyev.anton.myapplication.api;

import dementyev.anton.myapplication.CordiData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by yusi on 21/10/2017.
 */

public interface MyAPI {
    @POST("hello")
    Call<Void> postData(@Body double body);

    static String URL = "http://10.80.5.108:8080/";

    @POST("angle")
    Call<Void> postDataAngle(@Body double body);

}

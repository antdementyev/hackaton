package dementyev.anton.myapplication;

import dementyev.anton.myapplication.api.MyAPI;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by yusi on 21/10/2017.
 */

public class NetworkProvider {

    private static OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    private static Retrofit retrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(MyAPI.URL)
                .client(okHttpClient())
                .build();
    }

    static MyAPI api() {
        return retrofit().create(MyAPI.class);
    }

}

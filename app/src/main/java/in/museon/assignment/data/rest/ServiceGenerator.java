package in.museon.assignment.data.rest;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public ServiceGenerator() {
    }
    //http://museon.net/testapp/mapp/api/reg
    private  final String API_BASE_URL = "http://museon.net/testapp/mapp/api/";
    private  OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private  Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()));

    public  <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit;
        httpClient.authenticator(new Authenticator() {
            @Override
            public Request authenticate(@NonNull Route route, @NonNull Response response) throws IOException {
                return response.request().newBuilder()
                        .build();
            }
        });
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = httpClient.build();

        httpClient.addInterceptor(interceptor);
        httpClient.connectTimeout(5, TimeUnit.MINUTES);
        httpClient.readTimeout(5, TimeUnit.MINUTES);
        httpClient.writeTimeout(5, TimeUnit.MINUTES);
        retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

}

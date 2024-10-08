package com.example.historyproj;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// apilient- wazne do polaczeni tego zmieniac nie będziesz najprawdopodobniej-juleczka
public class ApiClient {
    //CODY RHODES BEDZIE MISTRZEM 7 KWIETNIA, ZDETRONIZUJE ROMANA I ZROBI FINISH THE STORY. JAK TAK NIE BEDZIE TO MOZESZ MI WYMYSLEC CHALLENGE!!!!!!!!!!!!!!!!!!!!-seler
    //zapisze sobie w kalendrzu specjalniue xd-julka
    private static final String BASE_URL = "super tajne";

    private static Retrofit retrofit = null;

    public ApiClient() throws FileNotFoundException {
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);

            // tutaj trustmanager(tenz certyfikatami jest uzywany)-juleczka
            TrustManager[] trustAllCerts = new TrustManager[]{new MyTrustManager()};
            SSLContext sslContext;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize SSLContext", e);
            }
            clientBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);

            // tutaj jak haker ogarniasz veryfikacje bez httpsa (genisu me moment fr)- juleczka
            clientBuilder.hostnameVerifier((hostname, session) -> true);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientBuilder.build())
                    .build();
        }
        return retrofit;
    }
}

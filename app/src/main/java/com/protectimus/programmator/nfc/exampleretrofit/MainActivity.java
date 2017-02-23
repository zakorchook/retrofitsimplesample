package com.protectimus.programmator.nfc.exampleretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.protectimus.programmator.nfc.exampleretrofit.rest.ApiEndpointInterface;
import com.protectimus.programmator.nfc.exampleretrofit.rest.pojo.ResponseStudents;
import com.protectimus.programmator.nfc.exampleretrofit.rest.pojo.Student;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ApiEndpointInterface apiService;
    private ProgressBar rogressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rogressBar = (ProgressBar) findViewById(R.id.progressBar);
        initRetrofit();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: click");
                doRequest();
            }
        });
    }

    private void doRequest() {
        rogressBar.setVisibility(View.VISIBLE);
        final Call<List<Student>> call = apiService.getStudents();
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                /**
                 * Вот твой список студентов :
                 * response.body()
                 * :)
                 */
                Log.d(TAG, "onResponse: response.isSuccessful() " + response.isSuccessful());
                Log.d(TAG, "onResponse: response.size() = " + response.body().size());
                Log.d(TAG, "onResponse: getFirstName "+response.body().get(0).getFirstName());
                rogressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                rogressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ddapp-sfa-api-dev.azurewebsites.net/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()).build();
        apiService = retrofit.create(ApiEndpointInterface.class);
    }
}

package com.protectimus.programmator.nfc.exampleretrofit.rest;

import com.protectimus.programmator.nfc.exampleretrofit.rest.pojo.ResponseStudents;
import com.protectimus.programmator.nfc.exampleretrofit.rest.pojo.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Eugene on 5/6/2016
 */
public interface ApiEndpointInterface {

    @GET("api/test/students")
    Call<List<Student>> getStudents();
}

package com.example.bluetooth_to_arduino;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceHolder {
    @POST("/input/data")
    Call<postPesan>postpesan(@Body postPesan postPesan);
}

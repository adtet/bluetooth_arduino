package com.example.bluetooth_to_arduino;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ImageButton btnkirim;
    EditText input_pesan;
    public static String ip = "http://156.67.221.101:";
    public String port_welcome = "3001/";
    public String url = ip+port_welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnkirim = findViewById(R.id.btnkirim);
        input_pesan = findViewById(R.id.inputtext1);

        btnkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_pesan.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"pesan kosong",Toast.LENGTH_SHORT).show();
                }
                else{
                    kirim_pesan(input_pesan.getText().toString());
                }
            }
        });

    }

    private void kirim_pesan(String pesan){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        postPesan postPesan = new postPesan(pesan);
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<postPesan>call = jsonPlaceHolder.postpesan(postPesan);
        call.enqueue(new Callback<com.example.bluetooth_to_arduino.postPesan>() {
            @Override
            public void onResponse(Call<com.example.bluetooth_to_arduino.postPesan> call, Response<com.example.bluetooth_to_arduino.postPesan> response) {
                postPesan postPesan1 = response.body();
                String m = postPesan1.getMessage();
                Toast.makeText(getApplicationContext(),m,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<com.example.bluetooth_to_arduino.postPesan> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Lost connection",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
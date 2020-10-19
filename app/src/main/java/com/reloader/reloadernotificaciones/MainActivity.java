package com.reloader.reloadernotificaciones;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button especifico, atopico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        especifico = findViewById(R.id.btn_especifico);
        atopico = findViewById(R.id.btn_atopico);

        especifico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                llamarEspecifico();
            }
        });

        atopico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                llamarAtopico();
            }
        });
    }

    private void llamarAtopico() {
    }

    private void llamarEspecifico() {
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            String token = "ct1NyBBtTf2hGiP3sFxxr8:APA91bF2luas6tsl6VXT9tB8a5P-AL3_-0_sI2ohAb7Eu48_7zDvWCRxM3I6yzwuV26svVd3vyhTCkE5QCgAgclhsdio2znGIUCoogFkS5QxL1OfJe9F_B8D-IBd4PRgDxBdeZqrRaVN";
            json.put("to", token);
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo", "desde json titulo");
            notificacion.put("detalle", "soy un detalle");
            json.put("data", notificacion);

            String URL = "https://fcm.googeapis.com/fcm/send";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, json, null, null) {

                @Override
                public Map<String, String> getHeaders() {

                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAAeBv4ics:APA91bGfaIcfBHdRtOmwkupp_Ru7GOzVNnP8MqrymBXqOEWZLrW5qOaLJxhnrZjjzgQnNTAJWM_3jqLzgf4YMYsbFZucnwK3Je9VbJaka1GTZepE6DDtJMLsNVeBz9ZPfND2sEfMIU6J");
                    return header;

                }
            };

            myrequest.add(request);

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}

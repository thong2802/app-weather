package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
   EditText search;
   ImageView imageViewstatus;
   LottieAnimationView buttonsearch;
   TextView location;
   TextView textcountry;
   TextView nhietdo;
   TextView Trangthai;
   TextView humidity;
   TextView milland;
   TextView cloud;
   TextView presenttime;
   Button skipday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();

       buttonsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String city = search.getText().toString();
                GetpresentWeatherData(city);
            }
        });
        GetpresentWeatherData("Saigon");
    }

    public void GetpresentWeatherData( String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=b93dc150e9302483ab56136e9ed4328c";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(MainActivity.this, "" + response.toString(), Toast.LENGTH_SHORT).show();
                        //Log.d("ketqua", response.toString());
                        try{
                        //    JSONObject jsonObject = new JSONObject(response);
                            String day = response.getString("dt");
                            String name = response.getString("name");
                            location.setText("Tên thành phố: " + name);
                            Long l = Long.valueOf(day);
                            Date date = new Date(l*1000L);
                            // format lại dinh dang thu
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd-HH-mm-ss");
                            String Day = simpleDateFormat.format(date);
                            presenttime.setText(Day);

                            JSONArray jsonArrayWeather = response.getJSONArray("weather");
                            JSONObject jsonObjectweather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectweather.getString("main");
                            String icon = jsonObjectweather.getString("icon");
                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/wn/"+icon+".png").into(imageViewstatus);
                            Trangthai.setText(status);

                            JSONObject jsonObjectMain = response.getJSONObject("main");
                            String nhietdo1 = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");
                            Double a = Double.valueOf(nhietdo1);
                            String NhietDo = String.valueOf(a.intValue());
                            nhietdo.setText(NhietDo+"°C");
                            humidity.setText(doam+"%");

                            JSONObject jsonObjectWind = response.getJSONObject("wind");
                            String gio = jsonObjectWind.getString("speed");
                            milland.setText(gio+"m/s");

                            JSONObject jsonObjectclouds = response.getJSONObject("clouds");
                            String may = jsonObjectclouds.getString("all");
                            cloud.setText(may+"%");

                            JSONObject jsonObjectsys = response.getJSONObject("sys");
                            String macountry = jsonObjectsys.getString("country");
                            textcountry.setText("Tên quốc gia: "+macountry);

                        }catch (JSONException e){
                           e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                        //Log.d("erro", error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);

    }

    public void Anhxa(){
        imageViewstatus = (ImageView) findViewById(R.id.imagestatus);
        search = (EditText) findViewById(R.id.editTextSearch);
        buttonsearch = (LottieAnimationView) findViewById(R.id.buttonsearch);
        location = (TextView)findViewById(R.id.textLocation);
        textcountry = (TextView)findViewById(R.id.textcountry);
        nhietdo = (TextView)findViewById(R.id.nhietdo);
        Trangthai = (TextView)findViewById(R.id.trangthai);
        humidity = (TextView)findViewById(R.id.texthumidity);
        milland = (TextView)findViewById(R.id.textmilland);
        cloud = (TextView)findViewById(R.id.textcloud);
        presenttime = (TextView)findViewById(R.id.textpresent);
        skipday = (Button)findViewById(R.id.buttonskip);
    }
}
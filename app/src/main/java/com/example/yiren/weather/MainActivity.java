package com.example.yiren.weather;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //define components
    private EditText editTextEnterCity;
    private ImageView imageViewSearch, imageViewIcon;
    private String citySearch;
    private final String APPID = "4992be43e442292c9c576c39f5dac69a";

    private TextView textViewCity, textViewWeather, textViewTemperature,
            textViewDate, textViewDescription, textViewMin, textViewMax,
            textViewHumidity, textViewClouds;

    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEnterCity = findViewById(R.id.et_search);
        imageViewSearch = findViewById(R.id.iv_search);
        imageViewIcon = findViewById(R.id.iv_icon);

        textViewCity = findViewById(R.id.tv_city);
        textViewTemperature = findViewById(R.id.tv_temporature);
        textViewDate = findViewById(R.id.tv_date);
        textViewDescription = findViewById(R.id.tv_description);
        textViewWeather = findViewById(R.id.tv_weather);
        textViewMin = findViewById(R.id.tv_min);
        textViewMax = findViewById(R.id.tv_max);
        textViewHumidity = findViewById(R.id.tv_humidity);
        textViewClouds = findViewById(R.id.tv_clouds);



        getCurrentWeather();

        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get the name of city for search
                citySearch = editTextEnterCity.getText().toString();

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        getCurrentWeather();
                    }
                };

                //retrieve data on separate thread
                Thread thread = new Thread(null, runnable, "weather");
                thread.start();

                //close the soft keyboard
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });

    }

    private void getCurrentWeather() {

        final String url = "https://api.openweathermap.org/data/2.5/weather?q=";
        String urlWithCity = url.concat(TextUtils.isEmpty(citySearch) ?
                "Halifax&appid=" + APPID :
                citySearch + "&appid=" + APPID);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlWithCity, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonMainObject = response.getJSONObject("main");
                    JSONArray jsonArray = response.getJSONArray("weather");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    JSONObject jsonObjectClouds = response.getJSONObject("clouds");


                    //city belongs to the main branch
                    String city = response.getString("name");
                    //description belongs to weather/description
                    String description = jsonObject.getString("description");
                    //temperature belongs to main/temp
                    Double temperature = jsonMainObject.getDouble("temp");
                    //temp_min belongs to main/temp_min
                    Double temp_min = jsonMainObject.getDouble("temp_min");
                    //temp_max belongs to main/temp_max
                    Double temp_max = jsonMainObject.getDouble("temp_max");
                    //humidity belongs to main/humidity
                    int humidity = jsonMainObject.getInt("humidity");
                    //clouds belongs to clouds/all
                    int clouds = jsonObjectClouds.getInt("all");
                    String weather = jsonObject.getString("main");
                    String uriIcon = jsonObject.getString("icon");

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE MM dd");
                    String date = simpleDateFormat.format(calendar.getTime());

                    textViewCity.setText(city);
                    textViewTemperature.setText(getCelsius(temperature));
                    textViewDate.setText(date);
                    textViewDescription.setText(description);
                    textViewMin.setText(getCelsius(temp_min));
                    textViewMax.setText(getCelsius(temp_max));
                    textViewHumidity.setText(String.valueOf(humidity));
                    textViewClouds.setText(String.valueOf(clouds));
                    textViewWeather.setText(String.valueOf(weather));

                    uriIcon = "http://openweathermap.org/img/w/" + uriIcon + ".png";

//                    RequestOptions requestOptions = new RequestOptions();
//                    requestOptions.placeholder(R.drawable.ic_cloud_queue_black_24dp);
                    Glide.with(getApplicationContext())
                            //.setDefaultRequestOptions(requestOptions)
                            .load(uriIcon)
                            .into(imageViewIcon);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Re-enter a city", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    //convert Fahrenheit temperature scale to Celsius
    private String getCelsius(double temp) {

        double celsius = temp - 273.15;
        int roundCelsius = (int) Math.round(celsius);

        return String.valueOf(roundCelsius);
    }
}

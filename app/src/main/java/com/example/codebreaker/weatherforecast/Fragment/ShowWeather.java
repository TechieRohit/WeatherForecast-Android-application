package com.example.codebreaker.weatherforecast.Fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.codebreaker.weatherforecast.R;
import com.example.codebreaker.weatherforecast.RecyclerView.Adapter;
import com.example.codebreaker.weatherforecast.RecyclerView.DATA;
import com.example.codebreaker.weatherforecast.Tracker.LocationTracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class ShowWeather extends android.support.v4.app.Fragment {


    RequestQueue requestQueue;

    Double longitude ;
    Double latitude ;

    private static DecimalFormat Up_to_2 = new DecimalFormat(".##");
    private RecyclerView recyclerView;
    public Adapter adapter;

    public String city;
    double temp;
    double temp_actual;
    int pressure;
    int humidity;
    double min_temp;
    double min_temp_actual;
    double max_temp;
    double max_temp_actual;
    public int Visibility;

    LocationTracker gpsTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =inflater.inflate(R.layout.display_weather,container,false);

        final TextView Temp = (TextView)view.findViewById(R.id.temperature);
        final TextView Place = (TextView)view.findViewById(R.id.place);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

         gpsTracker = new LocationTracker(getActivity());

        if (gpsTracker.canFetchLocation())
        {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        }else {
            gpsTracker.SettingsAlert();
        }

        String url ="http://api.openweathermap.org/data/2.5/weather?lat=" + String.valueOf(latitude) + "&lon=" + String.valueOf(longitude) +"&appid=6017c97fa5c5a4d0990df091155f5e1e" ;
        requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    city = response.getString("name");
                    Place.setText("" + city);

                    Visibility = response.getInt("visibility");

                    JSONObject data = response.getJSONObject("main");
                    temp = data.getDouble("temp");
                    //double temp_actual = (temp-32) * 5/9;
                    temp_actual = temp - 273;

                    Temp.setText("" + Up_to_2.format(temp_actual) + " Celsius");

                    pressure = data.getInt("pressure");

                    humidity = data.getInt("humidity");

                    min_temp = data.getDouble("temp_min");
                    //double min_temp_actual = (min_temp - 32) * 5/9;
                    min_temp_actual = min_temp - 273;

                    max_temp = data.getDouble("temp_max");
                    //double max_temp_actual = (max_temp - 32) * 5/9;
                    max_temp_actual = max_temp - 273;

                    String sendData[] = {String.valueOf(pressure),String.valueOf(humidity),String.valueOf(Up_to_2.format(min_temp_actual)),String.valueOf(Up_to_2.format(max_temp_actual)),String.valueOf(Visibility)};

                    adapter = new Adapter(getActivity(),getData(sendData));
                    recyclerView.setAdapter(adapter);



                } catch (JSONException e) {

                    Toast.makeText(getActivity(),"JSONException e",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(),"onErrorResponse",Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

        return view;
    }


    public List<DATA> getData(String receivedData[])
    {

        List<DATA> mydata = new ArrayList<>();

        int[] icons = {R.drawable.ic_fitness_center_white_36dp,R.drawable.ic_hot_tub_white_36dp,R.drawable.ic_whatshot_white_24dp,R.drawable.ic_visibility_white_24dp};
        String[] string_1 = {"Pressure","Humidity","Temp min/max","Visibility"};

        String string_2[] = receivedData;

        for (int i =0 ; i<icons.length ; i++)
        {
            DATA current = new DATA();
            current.ItemId = icons[i];
            current.string_1 = string_1[i];
            current.string_2 = string_2[i];
            current.max_temp = string_2[3];
            current.visibility = string_2[4];
            mydata.add(current);
        }
        return mydata;
    }
}

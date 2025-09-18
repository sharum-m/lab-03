package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private CityArrayAdapter cityAdapter;
    private ListView cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initial cities
        String[] cities    = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        // Setup ListView + Adapter
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // Setup FloatingActionButton
        FloatingActionButton addButton = findViewById(R.id.button_add_city);
        addButton.setOnClickListener(v -> {
            AddCityFragment fragment = new AddCityFragment();
            fragment.show(getSupportFragmentManager(), "AddCityFragment");
        });
    }

    @Override
    public void addCity(City city) {
        // Add new city from fragment input
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }
}

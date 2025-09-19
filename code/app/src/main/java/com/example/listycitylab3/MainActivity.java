package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AddCityFragment.AddCityDialogListener,
        EditCityFragment.EditCityDialogListener {

    private ArrayList<City> dataList;
    private CityArrayAdapter cityAdapter;
    private ListView cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dataList = new ArrayList<>();
        dataList.add(new City("Edmonton", "AB"));
        dataList.add(new City("Vancouver", "BC"));
        dataList.add(new City("Toronto", "ON"));

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        if (fab != null) {
            fab.setOnClickListener(v ->
                    new AddCityFragment().show(getSupportFragmentManager(), "Add City")
            );
        }

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City selected = dataList.get(position);
            EditCityFragment.newInstance(position, selected)
                    .show(getSupportFragmentManager(), "Edit City");
        });
    }

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCity(int position, City updated) {
        dataList.get(position).setName(updated.getName());
        dataList.get(position).setProvince(updated.getProvince());
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteCity(int position) {
        dataList.remove(position);
        cityAdapter.notifyDataSetChanged();
    }
}
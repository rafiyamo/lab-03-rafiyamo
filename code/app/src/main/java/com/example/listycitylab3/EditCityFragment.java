package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    public interface EditCityDialogListener {
        void updateCity(int position, City updated);
        void deleteCity(int position);
    }

    private static final String ARG_POSITION = "position";
    private static final String ARG_CITY = "city";

    private EditCityDialogListener listener;

    public static EditCityFragment newInstance(int position, City city) {
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putSerializable(ARG_CITY, city); // City implements Serializable
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_edit_city, null);

        EditText cityName = view.findViewById(R.id.edit_city_name);
        EditText provinceName = view.findViewById(R.id.edit_province_name);

        Bundle args = requireArguments();
        int position = args.getInt(ARG_POSITION);
        City city = (City) args.getSerializable(ARG_CITY);

        if (city != null) {
            cityName.setText(city.getName());
            provinceName.setText(city.getProvince());
        }

        return new AlertDialog.Builder(requireContext())
                .setTitle("Edit city")
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setNeutralButton("Delete", (d, w) -> {
                    if (listener != null) {
                        listener.deleteCity(position);
                    }
                })
                .setPositiveButton("Save", (d, w) -> {
                    String newName = cityName.getText().toString().trim();
                    String newProv = provinceName.getText().toString().trim();

                    if (newName.isEmpty() || newProv.isEmpty()) return;

                    City updated = new City(newName, newProv);
                    if (listener != null) {
                        listener.updateCity(position, updated);
                    }
                })
                .create();
    }
}

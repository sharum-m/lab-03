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

import java.io.Serializable;

public class AddCityFragment extends DialogFragment {

    public interface AddCityDialogListener {
        void addCity(City city);
        void onCityEdited();
    }

    private static final String ARG_CITY = "arg_city";
    private AddCityDialogListener listener;

    public static AddCityFragment newInstance(@Nullable City city) {
        AddCityFragment fragment = new AddCityFragment();
        if (city != null) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_CITY, (Serializable) city);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_add_city, null);

        EditText nameEt = view.findViewById(R.id.edit_text_city_text);
        EditText provEt = view.findViewById(R.id.edit_text_province_text);

        City editing = null;
        Bundle args = getArguments();
        if (args != null) {
            editing = (City) args.getSerializable(ARG_CITY);
            if (editing != null) {
                nameEt.setText(editing.getName());
                provEt.setText(editing.getProvince());
            }
        }

        City finalEditing = editing;

        return new AlertDialog.Builder(requireContext())
                .setTitle("Add/edit city")
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    String name = nameEt.getText().toString().trim();
                    String prov = provEt.getText().toString().trim();
                    if (name.isEmpty() || prov.isEmpty()) return;

                    if (finalEditing != null) {
                        finalEditing.setName(name);
                        finalEditing.setProvince(prov);
                        listener.onCityEdited();
                    } else {
                        listener.addCity(new City(name, prov));
                    }
                })
                .create();
    }
}

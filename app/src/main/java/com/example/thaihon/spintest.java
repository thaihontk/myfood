package com.example.thaihon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class spintest extends AppCompatActivity {
    Spinner spinner;
    private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spintest);
        spinner = (Spinner)findViewById(R.id.spin);

        list = new ArrayList<>();
        list.add("Lập Trình Android");
        list.add("Lập Trình Java");
        list.add("Lập Trình JavaFX");
        list.add("Lập Trình Web");
        list.add("Lập Trình Ruby");
        list.add("Lập Trình C++");
        list.add("Lập Trình PHP");
        list.add("Lập Trình WordPress");

        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, list);

        spinner.setAdapter(spinnerAdapter);

        //Bắt sự kiện cho Spinner, khi chọn phần tử nào thì hiển thị lên Toast
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //đối số postion là vị trí phần tử trong list Data
                String msg = "position :" + position + " value :" + list.get(position);
                Toast.makeText(spintest.this, msg, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(spintest.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

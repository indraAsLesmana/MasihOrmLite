package com.tutor93.tugasfrensky.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tutor93.tugasfrensky.R;

/**
 * Created by indraaguslesmana on 10/6/16.
 */

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    Button toEmployee, toProductview, toEmloyeeSalesinfo, toEmployeeProject, logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_dashboard);

        toEmployee = (Button) findViewById(R.id.toemployeeview);
        toProductview = (Button) findViewById(R.id.toproductview);
        toEmloyeeSalesinfo = (Button) findViewById(R.id.toemployeesalesinformation);
        toEmployeeProject = (Button) findViewById(R.id.toemployeeprojectinfomation);
        logout = (Button) findViewById(R.id.logout);

        toEmployee.setOnClickListener(this);
        toProductview.setOnClickListener(this);
        toEmloyeeSalesinfo.setOnClickListener(this);
        toEmployeeProject.setOnClickListener(this);
        logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v == toEmployee) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v == toProductview) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v == toEmloyeeSalesinfo) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v == toEmloyeeSalesinfo) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v == toEmloyeeSalesinfo) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}

package com.example.evaluationpereaualban;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViewById(R.id.imgMap).setOnClickListener(this);
        findViewById(R.id.imgMeteo).setOnClickListener(this);
        findViewById(R.id.imgSoleil).setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgMap) {
            Toast.makeText(this, "Map", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MapsActivity.class));
        } else if (v.getId() == R.id.imgMeteo) {
            Toast.makeText(this, "Meteo", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MeteoActivity.class));
        } else if (v.getId() == R.id.imgSoleil) {
            Toast.makeText(this, "Soleil", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, AstronomieActivity.class));
        }
    }
}



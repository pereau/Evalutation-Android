package com.example.evaluationpereaualban;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MeteoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        findViewById(R.id.accueil2).setOnClickListener(this);
        findViewById(R.id.selection).setOnClickListener(this);

    }


    public void setNomVille(String ville) {

        try {
            Toast.makeText(this, ville, Toast.LENGTH_LONG).show();
            URL url = new URL("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22%27"+ville+"%27%2C%20France%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys") ;
            new TacheAsynchrone(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void notifieResultat(String reponse) {
        JSONObject objet;
        try {
            objet = new JSONObject(reponse);
            String localite = objet.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("location").getString("city");
            String dateEtHeure = objet.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getString("date");
            String conditionMeteo = objet.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getString("text");
            String sunrise = objet.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("astronomy").getString("sunrise");
            String sunset = objet.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("astronomy").getString("sunset");
            TextView lieu = findViewById(R.id.lieu);
            TextView date = findViewById(R.id.date);
            TextView condition = findViewById(R.id.condition);
            TextView leverSoleil = findViewById(R.id.leverSoleil);
            TextView coucherSoleil = findViewById(R.id.coucherSoleil);
            lieu.setText(localite);
            date.setText(dateEtHeure);
            condition.setText(conditionMeteo);
            leverSoleil.setText(sunrise);
            coucherSoleil.setText(sunset);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    @Override
    public void onClick(View v) {
        EditText ville = (EditText) findViewById(R.id.ville);
        String nom_ville = ville.getText().toString().toLowerCase().trim();
        if (v.getId() == R.id.selection) {
            this.setNomVille(nom_ville);
        }
        if (v.getId() == R.id.accueil2) {
            startActivity(new Intent(this, MenuActivity.class));
        }

    }
}

package com.example.evaluationpereaualban;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class AstronomieActivity extends AppCompatActivity implements  View.OnClickListener {

    Double latitude;
    Double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astronomie);
        findViewById(R.id.accueil3).setOnClickListener(this);
        findViewById(R.id.astronomie2).setOnClickListener(this);

        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, locationListener);
    }


    public void notifieResultat(String reponse) {
        JSONObject objet;
        try {
            objet = new JSONObject(reponse);

            String leverSoleil = objet.getJSONObject("results").getString("sunrise");
            Toast.makeText(this, leverSoleil, Toast.LENGTH_LONG).show();
            String coucherSoleil = objet.getJSONObject("results").getString("sunset");
            String zenith = objet.getJSONObject("results").getString("solar_noon");
            String crepusculeCivil = objet.getJSONObject("results").getString("civil_twilight_begin");
            String crepusculeNautique = objet.getJSONObject("results").getString("nautical_twilight_begin");
            String crepusculeAstronomique = objet.getJSONObject("results").getString("astronomical_twilight_begin");

            TextView leverSol = findViewById(R.id.leverSol);
            TextView coucherSol = findViewById(R.id.coucherSol);
            TextView zenithSol = findViewById(R.id.zenithSol);
            TextView civil = findViewById(R.id.civil);
            TextView nautique = findViewById(R.id.nautique);
            TextView astronomique = findViewById(R.id.astronomique);

            leverSol.setText("lever du soleil : " +leverSoleil);
            coucherSol.setText("coucher du soleil : " +coucherSoleil);
            zenithSol.setText("zenith :" + zenith);
            civil.setText("crepuscule civil :"+crepusculeCivil);
            nautique.setText("crepuscule nautique :"+crepusculeNautique);
            astronomique.setText("crepuscule astronomique :"+crepusculeAstronomique);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.accueil3) {
            startActivity(new Intent(this, MenuActivity.class));
        }

        if (v.getId() == R.id.astronomie2) {
            try {
                Toast.makeText(this, "Si vous êtes en France, ajouter 1h en hiver et 2h en été ! \n" +"latitude :" + latitude.toString() + " , longitude :" + longitude.toString(), Toast.LENGTH_LONG).show();
                URL url = new URL("https://api.sunrise-sunset.org/json?lat="+this.latitude.toString()+"&lng="+this.longitude.toString()+"&callback") ;
                new TacheAsynchroneAstronomie(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

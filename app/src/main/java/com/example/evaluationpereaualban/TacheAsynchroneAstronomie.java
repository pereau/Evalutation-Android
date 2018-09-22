package com.example.evaluationpereaualban;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Artignan on 11/09/2018.
 */

public class TacheAsynchroneAstronomie extends AsyncTask<URL,Integer,String> {

    private AstronomieActivity context;

    public TacheAsynchroneAstronomie(AstronomieActivity context){
        this.context = context;
    }



    @Override
    protected String doInBackground(URL... urls) {
        URL u = urls[0];
        String result = "";
        try {
            URLConnection connection = u.openConnection();
            BufferedReader buf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = buf.readLine();
            while (line!=null){
                result+=line+"\n";
                line = buf.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected void onPostExecute(String resultat){
            this.context.notifieResultat(resultat);
    }
}
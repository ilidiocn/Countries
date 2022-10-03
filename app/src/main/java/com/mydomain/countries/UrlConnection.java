package com.mydomain.countries;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlConnection {
    private Activity activity;

    public UrlConnection(Activity activity) {
        this.activity = activity;
    }

    public String allCountries(){
        InputStream inputStream=null;
        String line= null;
        String result= null;
        String INPUT_URL="https://restcountries.com/v3.1/all";

        try {
            URL url= new URL(INPUT_URL);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");



            inputStream= new BufferedInputStream(httpURLConnection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder= new StringBuilder();
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line).append("\n");
            }
            inputStream.close();
            result= stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Log.e("Return ",result+"");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public void test(){

        StringBuilder data= new StringBuilder();
        JSONArray arrayCountries=new JSONArray();
        for(int i=0; i<arrayCountries.length(); i++){


        }
    }

}

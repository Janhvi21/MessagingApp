package com.example.midterm_2018;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AsyncTaskForDelete extends AsyncTask<String, Void, Integer> {
    int position;
    String token;
    idata Idata;

    public AsyncTaskForDelete(int position, String token,idata idata) {
        this.position = position;
        this.token = token;
        this.Idata=idata;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer s) {
       Idata.handleDeleteData(s);
    }

    @Override
    protected Integer doInBackground(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        LoggedUser loggedUser = new LoggedUser();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result = null;
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            Log.d("resulttoken", token);
            connection.setRequestProperty("Authorization", "BEARER " + token);
            connection.connect();
            Log.d("result", url + " " + connection.getResponseCode());
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return position;
            } else {
                return -1;
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public interface idata{
        public void handleDeleteData(Integer result);
    }
}
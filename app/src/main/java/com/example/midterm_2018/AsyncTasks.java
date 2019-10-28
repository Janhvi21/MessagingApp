package com.example.midterm_2018;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class AsyncTasks extends AsyncTask<String, Void, LoggedUser> {
    idata Idata;
    LoggedUser userResult = new LoggedUser();
    requestParams mParams;

    // public AsyncTasks(requestParams mParams) {
    //  this.mParams = mParams;
    //}
    public AsyncTasks(idata Idata, requestParams mParams) {
        this.Idata = Idata;
        this.mParams = mParams;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(LoggedUser user) {
        Idata.handleData(user);
    }

    @Override
    protected LoggedUser doInBackground(String... params) {
        StringBuilder stringBuilder = new StringBuilder();
        LoggedUser loggedUser = new LoggedUser();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result = null;
        try {
            URL url = new URL(mParams.getEncodedURL(params[0]));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            mParams.encodePostParameters(connection);
            connection.connect();
            Log.d("result", url + " " + connection.getResponseCode());
            if (connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                Log.d("result", "suceesssss");
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                result = stringBuilder.toString();
                Log.d("result", result);
                JSONObject jsonResult = new JSONObject(result);

                String result_status = jsonResult.getString("status");
                Log.d("result", result_status);
                if (result_status.equals("ok")) {
                    loggedUser.fname = jsonResult.getString("user_fname");
                    loggedUser.lname = jsonResult.getString("user_lname");
                    loggedUser.userID = jsonResult.getInt("user_id");
                    loggedUser.role = jsonResult.getString("user_role");
                    loggedUser.token = jsonResult.getString("token");
                    loggedUser.User_email = jsonResult.getString("user_email");
                    Log.d("result", loggedUser + "");

                }
                else{
                    loggedUser.User_email="";
                    Idata.handleData(loggedUser);
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return loggedUser;
    }

    public interface idata {
        public void handleData(LoggedUser user);
    }
}

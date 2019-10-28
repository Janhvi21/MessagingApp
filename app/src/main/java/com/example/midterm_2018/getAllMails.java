package com.example.midterm_2018;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class getAllMails extends AsyncTask<String, Void, ArrayList<Messages>> {
    message Message;
    requestParams mParams=new requestParams();
    ArrayList<Messages> allMessages=new ArrayList<>();
    String Token;

    public getAllMails(message message, String token) {
        Message = message;
        Token = token;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Messages> s) {
       Message.handleMessage(s);
    }

    @Override
    protected ArrayList<Messages> doInBackground(String... params) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Messages> messages = new ArrayList<Messages>();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


            Log.d("resulttoken",Token);
            connection.setRequestProperty("Authorization", "BEARER "+Token);
            connection.connect();
            Log.d("result", url + " " + connection.getResponseCode());
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.d("result", "suceesssss");
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                result = stringBuilder.toString();
                JSONObject jsonResult = new JSONObject(result);
                JSONArray jsonArray=jsonResult.getJSONArray("messages");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject sourceJSON = jsonArray.getJSONObject(i);
                    Messages messages1=new Messages();
                    messages1.message = sourceJSON.getString("message");
                    messages1.id = sourceJSON.getString("id");
                    messages1.created_at = sourceJSON.getString("created_at");
                    messages1.receiver_id = sourceJSON.getString("receiver_id");
                    messages1.sender_fname = sourceJSON.getString("sender_fname");
                    messages1.sender_id = sourceJSON.getString("sender_id");
                    messages1.sender_lname = sourceJSON.getString("sender_lname");
                    messages1.subject = sourceJSON.getString("subject");
                    messages1.updated_at = sourceJSON.getString("updated_at");
                    allMessages.add(messages1);
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(Messages message:allMessages){
            Log.d("resultmsg", message.toString());
        }

        return allMessages;
    }
    public interface message{
        public void handleMessage(ArrayList<Messages> allmessage);
    }
}

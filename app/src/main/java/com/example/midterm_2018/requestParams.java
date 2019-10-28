package com.example.midterm_2018;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

public class requestParams {
    private HashMap<String, String> params;
    private StringBuilder stringBuilder;

    public requestParams() {
        params = new HashMap<>();
        stringBuilder = new StringBuilder();
    }

    public requestParams addParameter(String key, String value) {

        try {
            params.put(key, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getEncodedParameter() {
        for (String key : params.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");

            }
            stringBuilder.append(key + "=" + params.get(key));
        }
        return stringBuilder.toString();
    }

    public String getEncodedURL(String url) {
        return url + "?" + getEncodedParameter();
    }

    public void encodePostParameters(HttpURLConnection conn) throws IOException {
        conn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        writer.write(getEncodedParameter());
        writer.flush();
    }
}

package com.example.eklass;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler
{

    // this method will send the post request to server
    // in the hash ma we have data in key value form

    public String sendPostRequest(String requestURL, HashMap<String, String> params) throws MalformedURLException {
        URL url;

        StringBuilder sb = new StringBuilder();

        url = new URL(requestURL);

        try
        {
            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();

            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        return "";

    }

}

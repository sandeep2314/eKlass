package com.example.eklass;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler
{

    // this method will send the post request to server
    // in the hash ma we have data in key value form

    public String sendPostRequest(String requestURL, HashMap<String, String> postDataparams) throws MalformedURLException {
        URL url;

        StringBuilder sb = new StringBuilder();

        url = new URL(requestURL);

        try
        {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();

            BufferedWriter writer  = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));


            writer.write(getPostDataString(postDataparams));
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if(responseCode == HttpsURLConnection.HTTP_OK)
            {
                BufferedReader br = new BufferedReader
                                (new InputStreamReader(conn.getInputStream()));

                sb = new StringBuilder();

                String response;

                while ((response = br.readLine()) != null )
                {
                    sb.append(response);
                }
            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        return sb.toString();

    }

    // this method converts HashMaps into query string
    public String getPostDataString(HashMap<String, String> params)
    {
        StringBuilder result = new StringBuilder();

        boolean first = true;

        for(HashMap.Entry<String, String> entry : params.entrySet())
        {
            if(first)
                first=false;
            else
                result.append("&");

            try {

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));

                }
            catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }


        }


        return result.toString();
    }

}

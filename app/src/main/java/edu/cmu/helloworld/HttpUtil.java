package edu.cmu.helloworld;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpUtil {

    public String httpGet(String urlPara) {
        HttpURLConnection con = null;
        String result = null;
        try {
            URL url = new URL(urlPara);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(1000);
            con.setDoOutput(false);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "text/plain");
            con.connect();
            int code = con.getResponseCode();
            if (code == 200) {
                // read the content
                StringBuffer buffer = new StringBuffer();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        con.getInputStream(), "UTF-8"));
                String temp;
                while ((temp = br.readLine()) != null) {
                    buffer.append(temp);
                    buffer.append("\n");
                }
                result = buffer.toString().trim();
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        con.getErrorStream(), "UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String temp;
                while ((temp = br.readLine()) != null) {
                    buffer.append(temp);
                    buffer.append("\n");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            con.disconnect();
        }
        return result;
    }



}
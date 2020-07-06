package com.qiu.base.lib.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    public static String submitPostData(String strData, String strUrlPath) {
        try {
            URL url = new URL(strUrlPath);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");

            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection
                    .setRequestProperty("Content-Length", String.valueOf(strData.length()));
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(strData.getBytes());
            outputStream.close();
            int response = httpURLConnection.getResponseCode();
            if (response == HttpURLConnection.HTTP_CREATED) {
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);
            }
        } catch (IOException e) {
            //e.printStackTrace();
            return "err: " + e.getMessage();
        }
        return "-1";
    }

    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "err: " + e.getMessage();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

}

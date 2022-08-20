package lyl.utils;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

// 发送http
public class HttpRequest {
    // 发送get请求
    public static String sendGet(String url, Map<String, String> urlParams) {
        String urlStr;
        if (urlParams != null && urlParams.size() > 0) {
            // 拼接完整的get请求url
            StringBuilder url_path = new StringBuilder(url + "?");
            for (String str : urlParams.keySet()) {
                url_path.append(str).append("=").append(urlParams.get(str)).append("&");
            }
            urlStr = url_path.deleteCharAt(url_path.length() - 1).toString();
        } else {
            urlStr = url;
        }

        System.out.println("完整地址是" + urlStr);




        try {

            HttpURLConnection httpURIConnection = (HttpURLConnection)new URL(urlStr).openConnection();
            httpURIConnection.setRequestProperty("accept", "*/*");
            httpURIConnection.setRequestProperty("connection", "Keep-Alive");
            httpURIConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36");

            // 连接
            httpURIConnection.connect();

            // 发送出去，并得到响应，接收到字节流
            InputStream inputStream = httpURIConnection.getInputStream();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i;
            while ((i = inputStream.read()) != -1) {
                baos.write(i);
            }
            return String.valueOf(baos);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static HttpURLConnection getPostConnection(String url){
        HttpURLConnection httpURIConnection = null;
        try {

            httpURIConnection = (HttpURLConnection)new URL(url).openConnection();
            httpURIConnection.setRequestProperty("accept", "*/*");
            httpURIConnection.setRequestProperty("connection", "Keep-Alive");
            httpURIConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36");
            httpURIConnection.setRequestMethod("POST");
            httpURIConnection.setDoInput(true);
            httpURIConnection.setDoOutput(true);




        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpURIConnection;
    }


}
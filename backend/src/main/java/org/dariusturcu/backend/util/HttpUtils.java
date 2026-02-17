package org.dariusturcu.backend.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

public class HttpUtils {
    public static HttpURLConnection createConnection(String url) throws IOException {
        return (HttpURLConnection) URI.create(url).toURL().openConnection();
    }

    public static HttpURLConnection createGetConnection(String url) throws IOException {
        HttpURLConnection connection = createConnection(url);
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        return connection;
    }
}

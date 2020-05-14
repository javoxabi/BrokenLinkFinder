package controller;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.Callable;

public class HttpRequest implements Callable<Response> {

    public HttpRequest(String url) {
        this.url = url;
    }

    public Response call() throws IOException {
        Pair<Integer, String> status = getStatus();
        return new Response(url, status.getKey(), status.getValue());
    }

    private final String url;

    private Pair<Integer, String> getStatus() throws IOException {
        FileInputStream fis;
        Properties property = new Properties();
        fis = new FileInputStream("src/main/java/resources/config.properties");
        property.load(fis);
        int connectionTimeout = Integer.parseInt(property.getProperty("connectionTimeout"));

        try {
            Connection.Response response = Jsoup.connect(this.url)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .timeout(connectionTimeout)
                    .execute();
            return new MutablePair<>(response.statusCode(), response.statusMessage());
        } catch (SocketTimeoutException exception) {
            System.out.println("522, Connection time out!");
            return new MutablePair<>(522, "Connection time out!");
        } catch (Exception exception) {
            return new MutablePair<>(400, "Bad request");
        }
    }
}
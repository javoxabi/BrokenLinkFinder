package handler;

import controller.HttpRequest;
import controller.Response;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class FindBrokenLinks {
    public FindBrokenLinks(List<String> links) {
        this.links = links;
    }

    //List<String> getLinks() {
    //    return this.links;
   // }

    public List<Response> getBrokenLinks() throws InterruptedException, ExecutionException, IOException {
        FileInputStream fis;
        Properties property = new Properties();
        fis = new FileInputStream("src/main/java/resources/config.properties");
        property.load(fis);
        int threadsNumber = Integer.parseInt(property.getProperty("threadsNumber"));
        final ExecutorService service = Executors.newFixedThreadPool(threadsNumber);

        List<Future<Response>> httpCallsResult = service.invokeAll(addHttpCallsToList());
        service.shutdown();

        return addBrokenLinksToList(httpCallsResult);
    }

    private final List<String> links;

    private List<Response> addBrokenLinksToList(List<Future<Response>> httpCallsResult) throws InterruptedException, ExecutionException {
        List<Response> brokenLinks = new ArrayList<>();
        for (final Future<Response> callResult : httpCallsResult) {
            Response httpResult = callResult.get();
            if (isLinkBroken(httpResult.getStatusCode())) {
                brokenLinks.add(httpResult);
            }
        }

        return brokenLinks;
    }

    private List<HttpRequest> addHttpCallsToList() {
        List<HttpRequest> httpCalls = new ArrayList<>();

        for (String link : links) {
            httpCalls.add(new HttpRequest(link));
        }

        return httpCalls;
    }

    private Boolean isLinkBroken(Integer code) {
        String str = code.toString();
        String codeNumber = str.substring(0,1);
        String STATUS_SUCCESS = "2";
        String STATUS_REDIRECT = "3";
        return ((!codeNumber.equals(STATUS_SUCCESS)) && (!codeNumber.equals(STATUS_REDIRECT)));
    }
}
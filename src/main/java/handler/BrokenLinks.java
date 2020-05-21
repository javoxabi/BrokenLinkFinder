package handler;

import model.LinkModel;
import controller.RequestHttp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class BrokenLinks {
    public BrokenLinks(List<String> links)
    {
        this.links = links;
    }

    public List<LinkModel> getBrokenLinks() throws InterruptedException, ExecutionException, IOException
    {
        FileInputStream fis;
        Properties property = new Properties();
        fis = new FileInputStream("resources/config.properties");
        property.load(fis);
        int threadsNumber = Integer.parseInt(property.getProperty("threadsCount"));
        final ExecutorService service = Executors.newFixedThreadPool(threadsNumber);

        var httpCallsResult = service.invokeAll(addHttpCallsToList());
        service.shutdown();

        return addBrokenLinksToList(httpCallsResult);
    }

    private final List<String> links;

    private List<LinkModel> addBrokenLinksToList(List<Future<LinkModel>> httpCallsResult) throws InterruptedException, ExecutionException
    {
        List<LinkModel> brokenLinks = new ArrayList<>();
        for (var callResult : httpCallsResult) {
            LinkModel httpResult = callResult.get();
            if (isLinkBroken(httpResult.getStatusCode())) {
                brokenLinks.add(httpResult);
            }
        }

        return brokenLinks;
    }

    private List<RequestHttp> addHttpCallsToList()
    {

        return links.stream().map(RequestHttp::new).collect(Collectors.toList());
    }

    private Boolean isLinkBroken(Integer code)
    {
        String str = code.toString();
        String codeNumber = str.substring(0,1);
        String STATUS_SUCCESS = "2";
        String STATUS_REDIRECT = "3";
        return ((!codeNumber.equals(STATUS_SUCCESS)) && (!codeNumber.equals(STATUS_REDIRECT)));
    }
}

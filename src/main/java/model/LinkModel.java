package model;

public class LinkModel {
    public LinkModel(String url, Integer statusCode, String statusMessage)
    {
        this.url = url;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public Integer getStatusCode()
    {
        return statusCode;
    }

    public String getStatusMessage()
    {
        return statusMessage;
    }

    public String getUrl()
    {
        return url;
    }

    private final String url;
    private final String statusMessage;
    private final Integer statusCode;
}

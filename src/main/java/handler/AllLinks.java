package handler;

import commandLineCommands.States;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class AllLinks {
    public List<String> getLinks(String htmlFileName, States state) throws IOException
    {
        List<String> links = new ArrayList<>();

        switch (state)
        {
            case FILES:
                openDocument(htmlFileName);
                break;
            case LINKS:
                connect(htmlFileName);
                break;
            case ERROR:
                throw new FileNotFoundException();
        }

        Map<String, Elements> mapTags = getTags();
        for (Map.Entry<String, Elements> entry : mapTags.entrySet())
        {
            for (Element tag : entry.getValue())
            {
                links.add(tag.attr("abs:" + ATTRIBUTES.get(entry.getKey())));
            }
        }

        return links;
    }

    private Document document;

    public static final String HREF = "HREF";
    public static final String SRC = "SRC";

    private final Map<String, String> ATTRIBUTES = new LinkedHashMap<>()
    {
        {
            put(HREF, "href");
            put(SRC, "src");
        }
    };

    private void connect(String link) throws IOException
    {
        FileInputStream fis;
        Properties property = new Properties();
        fis = new FileInputStream("resources/config.properties");
        property.load(fis);
        int connectionTimeout = Integer.parseInt(property.getProperty("connectTimeout"));
        Connection connection = Jsoup.connect(link).timeout(connectionTimeout);
        document = connection.get();
    }

    private void openDocument(String link) throws IOException
    {
        document = Jsoup.parse(new File(link), null);
    }

    private Map<String, Elements> getTags() {
        Map<String, Elements> mapTags = new LinkedHashMap<>();
        for (Map.Entry<String, String> attribute : ATTRIBUTES.entrySet())
        {
            mapTags.put(attribute.getKey(), document.getElementsByAttribute(attribute.getValue()));
        }

        return mapTags;
    }
}

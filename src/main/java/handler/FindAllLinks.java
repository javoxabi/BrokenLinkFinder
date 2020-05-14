package handler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import read.ReadLinksStates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class FindAllLinks
{
    public List<String> getLinks(String htmlFileName, ReadLinksStates state) throws IOException
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

        Map<Attribute, Elements> mapTags = getTags();
        for (Map.Entry<Attribute, Elements> entry : mapTags.entrySet())
        {
            for (Element tag : entry.getValue())
            {
                links.add(tag.attr("abs:" + ATTRIBUTES.get(entry.getKey())));
            }
        }

        return links;
    }

    private Document document;

    private final Map<Attribute, String> ATTRIBUTES = new LinkedHashMap<>()
    {
        {
            put(Attribute.HREF, "href");
            put(Attribute.SRC, "src");
        }
    };

    private void connect(String link) throws IOException
    {
        FileInputStream fis;
        Properties property = new Properties();
        fis = new FileInputStream("resources/config.properties");
        property.load(fis);
        int connectionTimeout = Integer.parseInt(property.getProperty("connectionTimeout"));
        Connection connection = Jsoup.connect(link)
                .timeout(connectionTimeout)
                .userAgent(property.getProperty("userAgent"));
        document = connection.get();
    }

    private void openDocument(String link) throws IOException
    {
        document = Jsoup.parse(new File(link), null);
    }

    private Map<Attribute, Elements> getTags() {
        Map<Attribute, Elements> mapTags = new LinkedHashMap<>();
        for (Map.Entry<Attribute, String> attribute : ATTRIBUTES.entrySet())
        {
            mapTags.put(attribute.getKey(), document.getElementsByAttribute(attribute.getValue()));
        }

        return mapTags;
    }
}
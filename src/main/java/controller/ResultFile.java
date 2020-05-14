package controller;

import handler.FindAllLinks;
import handler.FindBrokenLinks;
import read.ReadLinksStates;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ResultFile
{
    public ResultFile(String filename)
    {
        this.filename = filename;
    }

    public void append(List<Response> brokenLinks)
    {
        try (PrintWriter writer = new PrintWriter(new File(filename)))
        {
            if (filename.isEmpty())
            {
                return;
            }
            for (Response brokenLink : brokenLinks)
            {
                String builder = brokenLink.getUrl() +
                        ';' +
                        brokenLink.getStatusCode() +
                        ';' +
                        brokenLink.getStatusMessage() +
                        '\n';
                writer.write(builder);
                writer.flush();
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static List<Response> getBrokenLinks(List<String> pages, ReadLinksStates state) throws Exception
    {
        List<Response> brokenLinks = new ArrayList<>();
        FindAllLinks linksFinder = new FindAllLinks();
        for (String page : pages)
        {
            List<String> links = linksFinder.getLinks(page, state);
            FindBrokenLinks brokenLinksFinder = new FindBrokenLinks(links);
            brokenLinks.addAll(brokenLinksFinder.getBrokenLinks());
        }
        return brokenLinks;
    }

    public static void printBrokenLinks(List<Response> brokenLinks, String outputFile)
    {
        ResultFile writer = new ResultFile(outputFile);
        writer.append(brokenLinks);
    }

    private final String filename;
}
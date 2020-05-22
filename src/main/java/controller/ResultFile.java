package controller;

import model.LinkModel;
import commandLineCommands.States;
import handler.AllLinks;
import handler.BrokenLinks;

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

    public void append(List<LinkModel> brokenLinks) {
        try (PrintWriter writer = new PrintWriter(new File(filename)))
        {
            if (filename.isEmpty())
            {
                return;
            }

            writer.write(String.format("%-5s%-50s%-15s%-50s\n", "№", "Link", "Status Code", "Status Message"));
            for (int i = 0; i < brokenLinks.size(); i++) {
                LinkModel brokenLink = brokenLinks.get(i);
                writer.write(String.format("%-5s%-50s%-15s%-50s\n", i+1, brokenLink.getUrl(), brokenLink.getStatusCode(), brokenLink.getStatusMessage()));
                writer.flush();
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static List<LinkModel> getBrokenLinks(List<String> pages, States state) throws Exception
    {
        List<LinkModel> brokenLinks = new ArrayList<>();
        AllLinks linksFinder = new AllLinks();
        for (String page : pages) {
            List<String> links = linksFinder.getLinks(page, state);
            BrokenLinks brokenLinksFinder = new BrokenLinks(links);
            brokenLinks.addAll(brokenLinksFinder.getBrokenLinks());
        }
        return brokenLinks;
    }

    public static void printBrokenLinks(List<LinkModel> brokenLinks, String outputFile) {
        ResultFile writer = new ResultFile(outputFile);
        writer.append(brokenLinks);
    }

    private final String filename;
}
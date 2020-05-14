import controller.Response;
import controller.ResultFile;
import handler.FindBrokenLinks;
import read.ReadLinks;

import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        try {
            ReadLinks reader = new ReadLinks(args);
            List<Response> brokenLinks = ResultFile.getBrokenLinks(reader.getPages(), reader.getReaderState());
            ResultFile.printBrokenLinks(brokenLinks, reader.getOutFile());
            System.out.println("Found " + brokenLinks.size() + " broken links, for details check file “out.csv”");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

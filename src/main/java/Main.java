import controller.Response;
import controller.ResultFile;
import read.ReadLinks;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            ReadLinks reader = new ReadLinks(args);
            List<Response> brokenLinks = ResultFile.getBrokenLinks(reader.getPages(), reader.getReaderState());
            ResultFile.printBrokenLinks(brokenLinks, reader.getOutFile());
        } catch (Exception ex) {
            System.out.println("Please, enter input and output");
        }
    }
}

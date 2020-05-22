package controller;

import commandLineCommands.States;
import model.LinkModel;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ResultFileTest {

    @org.junit.Test
    public void NotFoundStatusMessage() throws Exception {
        List<String> links = new ArrayList<>();
        links.add("http://www.testingstandards.co.uk/testing_links.htm");
        List<LinkModel> broken = ResultFile.getBrokenLinks(links, States.LINKS);
        List<LinkModel> expected = new ArrayList<>();
        expected.add(new LinkModel("http://hissa.ncsl.nist.gov/risq/", 404, "Not Found"));
        assertEquals(expected.get(0).getStatusMessage(), broken.get(1).getStatusMessage());
        assertEquals(expected.get(0).getStatusCode(), broken.get(1).getStatusCode());
        assertEquals(expected.get(0).getUrl(), broken.get(1).getUrl());
    }

    @org.junit.Test
    public void GoneStatusMessage() throws Exception {
        List<String> links = new ArrayList<>();
        links.add("http://www.testingstandards.co.uk/testing_links.htm");
        List<LinkModel> broken = ResultFile.getBrokenLinks(links, States.LINKS);
        List<LinkModel> expected = new ArrayList<>();
        expected.add(new LinkModel("http://members.tripod.com/~bazman/", 410, "Gone"));
        assertEquals(expected.get(0).getStatusMessage(), broken.get(0).getStatusMessage());
        assertEquals(expected.get(0).getStatusCode(), broken.get(0).getStatusCode());
        assertEquals(expected.get(0).getUrl(), broken.get(0).getUrl());
    }

    @org.junit.Test
    public void BadRequestStatusMessage() throws Exception {
        List<String> links = new ArrayList<>();
        links.add("http://www.testingstandards.co.uk/testing_links.htm");
        List<LinkModel> broken = ResultFile.getBrokenLinks(links, States.LINKS);
        List<LinkModel> expected = new ArrayList<>();
        expected.add(new LinkModel("http://www.ietesting.co.uk/", 400, "Bad request"));
        assertEquals(expected.get(0).getStatusMessage(), broken.get(2).getStatusMessage());
        assertEquals(expected.get(0).getStatusCode(), broken.get(2).getStatusCode());
        assertEquals(expected.get(0).getUrl(), broken.get(2).getUrl());
    }

    @org.junit.Test
    public void ServiceTemporarilyUnavailable() throws Exception {
        List<String> links = new ArrayList<>();
        links.add("http://www.testingstandards.co.uk/testing_links.htm");
        List<LinkModel> broken = ResultFile.getBrokenLinks(links, States.LINKS);
        List<LinkModel> expected = new ArrayList<>();
        expected.add(new LinkModel("http://www.bcs.org/BCS/Products/Qualifications/ISEB/", 503, "Service Temporarily Unavailable"));
        assertEquals(expected.get(0).getStatusMessage(), broken.get(3).getStatusMessage());
        assertEquals(expected.get(0).getStatusCode(), broken.get(3).getStatusCode());
        assertEquals(expected.get(0).getUrl(), broken.get(3).getUrl());
    }
}
package handler;

import commandLineCommands.States;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class AllLinksTest {

    @org.junit.Test
    public void checkNotEmptyLinksValue() throws IOException {
        AllLinks links = new AllLinks();
        List<String> returnValue = links.getLinks("http://www.testingstandards.co.uk/testing_links.htm", States.LINKS);
        assertFalse(returnValue.isEmpty());
    }

    @org.junit.Test
    public void checkLinksArray() throws IOException {
        AllLinks links = new AllLinks();
        List<String> returnValue = links.getLinks("http://www.testingstandards.co.uk/testing_links.htm", States.LINKS);
        assertEquals(returnValue.get(14), "http://www.sqa-test.com");
        assertEquals(returnValue.get(15), "http://members.tripod.com/~bazman/");
        assertEquals(returnValue.get(18), "http://www.compendiumdev.co.uk");
        assertEquals(returnValue.get(20), "http://www.fortest.org.uk/");
    }
}
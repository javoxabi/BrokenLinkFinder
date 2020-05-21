package handler;

import model.LinkModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


import static org.junit.Assert.*;
@RunWith(value = BlockJUnit4ClassRunner.class)
public class BrokenLinksTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final PrintStream old = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(output));
    }

    @After
    public void tearDown() {
        System.out.flush();
        System.setOut(old);
    }

    @org.junit.Test
    public void checkBrokenLinksWithEmptyList() throws InterruptedException, ExecutionException, IOException {
        List<String> links = new ArrayList<>();
        BrokenLinks finder = new BrokenLinks(links);
        assertTrue(finder.getBrokenLinks().isEmpty());
    }

    @org.junit.Test
    public void checkSuccessLinks() throws InterruptedException, ExecutionException, IOException {
        List<String> links = new ArrayList<>();
        links.add("https://httpstat.us/200");
        BrokenLinks finder = new BrokenLinks(links);
        List<LinkModel> expected = new ArrayList<>();
        assertEquals(finder.getBrokenLinks(), expected );
    }

    @org.junit.Test
    public void checkRedirectionLinks() throws InterruptedException, ExecutionException, IOException {
        List<String> links = new ArrayList<String>();
        links.add("https://httpstat.us/301");
        BrokenLinks finder = new BrokenLinks(links);
        List<LinkModel> expected = new ArrayList<LinkModel>();
        assertEquals(finder.getBrokenLinks(), expected );
    }


}
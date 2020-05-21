package commandLineCommands;

import static org.junit.Assert.*;

public class CommandLineAnalyzerTest {

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void InputIsEmpty() {
        String[] input = new String[0];
        CommandLineAnalyzer reader = new CommandLineAnalyzer(input);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void InputIsWithoutOutput() {
        String[] input = new String[2];
        input[0] = "--files";
        input[1] = "https://www.scottseverance.us/mailto.html";
        CommandLineAnalyzer reader = new CommandLineAnalyzer(input);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void InputIsWrong() {
        String[] input = new String[1];
        input[0] = "https://www.scottseverance.us/mailto.html";
        CommandLineAnalyzer reader = new CommandLineAnalyzer(input);
    }

    @org.junit.Test
    public void InputIsRight() {
        String[] input = new String[4];
        input[0] = "--files";
        input[1] = "https://www.scottseverance.us/mailto.html";
        input[2] = "--out";
        input[3] = "out.csv";
        CommandLineAnalyzer reader = new CommandLineAnalyzer(input);
    }

}
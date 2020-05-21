package commandLineCommands;

import java.util.ArrayList;
import java.util.List;

public class CommandLineAnalyzer {
    public CommandLineAnalyzer(String[] args)
    {
        States state = States.ERROR;
        boolean isOutFile = false;
        for (String argument : args)
        {
            if (switchState(argument) != States.ERROR)
            {
                state = switchState(argument);
                continue;
            }
            if (state == States.LINKS) {
                this.pages.add(argument);
                this.states = States.LINKS;
            } else if (state == States.FILES) {
                this.pages.add(argument);
                this.states = States.FILES;
            } else if (state == States.OUT) {
                this.outputFile = argument;
                isOutFile = true;
            } else if (state == States.ERROR) {
                throw new IllegalArgumentException("You entered the wrong input");
            }
        }
        if (!isOutFile)
        {
            throw new IllegalArgumentException("The output file is required");
        }
    }

    private States switchState(String element)
    {
        switch (element) {
            case "--files":
                return States.FILES;
            case "--links":
                return States.LINKS;
            case "--out":
                return States.OUT;
        }
        return States.ERROR;
    }

    public List<String> getPages()
    {
        return this.pages;
    }
    public String getOutFile()
    {
        return outputFile;
    }
    public States getReaderState()
    {
        return this.states;
    }

    private final List<String> pages = new ArrayList<>();

    private String outputFile;

    private States states = States.ERROR;
}
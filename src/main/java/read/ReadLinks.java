package read;

import java.util.ArrayList;
import java.util.List;

public class ReadLinks {
    public ReadLinks(String[] args)
    {
        ReadLinksStates state = ReadLinksStates.ERROR;
        boolean isOutFile = false;
        for (String argument : args)
        {
            if (changeState(argument) != ReadLinksStates.ERROR)
            {
                state = changeState(argument);
                continue;
            }
            switch (state)
            {
                case LINKS:
                    {
                    this.pages.add(argument);
                    this.states = ReadLinksStates.LINKS;
                    continue;
                }
                case FILES:
                    {
                    this.pages.add(argument);
                    this.states = ReadLinksStates.FILES;
                    continue;
                }
                case OUT:
                    {
                    this.outputFile = argument;
                    isOutFile = true;
                    continue;
                }
                case ERROR:
                    {
                    throw new IllegalArgumentException("You entered the wrong input");
                }
            }
        }
        if (!isOutFile)
        {
            throw new IllegalArgumentException("The output file is required");
        }
    }

    public List<String> getPages()
    {
        return this.pages;
    }
    public String getOutFile()
    {
        return outputFile;
    }
    public ReadLinksStates getReaderState()
    {
        return this.states;
    }
    private final List<String> pages = new ArrayList<>();

    private String outputFile;

    private ReadLinksStates states = ReadLinksStates.ERROR;

    private ReadLinksStates changeState(String element)
    {
        switch (element)
        {
            case "--files":
                return ReadLinksStates.FILES;
            case "--links":
                return ReadLinksStates.LINKS;
            case "--out":
                return ReadLinksStates.OUT;
        }
        return ReadLinksStates.ERROR;
    }
}
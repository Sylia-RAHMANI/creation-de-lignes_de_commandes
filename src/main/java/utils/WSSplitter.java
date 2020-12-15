package utils;

import com.beust.jcommander.converters.IParameterSplitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Splits command-line arguments on single whitespace (instead of commas).
 */
public final class WSSplitter implements IParameterSplitter {

    @Override
    public List<String> split(String value) {
        return new ArrayList<>(Arrays.asList(value.split(" ")));
    }
}

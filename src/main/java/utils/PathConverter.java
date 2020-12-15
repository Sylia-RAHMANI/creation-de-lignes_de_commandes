package utils;

import com.beust.jcommander.IStringConverter;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Converts a String, from the command line, into a Path.
 */
public final class PathConverter implements IStringConverter<Path> {

    @Override
    public Path convert(String value) {
        return Paths.get(value);
    }
}

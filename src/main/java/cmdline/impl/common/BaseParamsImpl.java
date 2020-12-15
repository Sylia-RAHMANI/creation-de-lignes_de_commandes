package cmdline.impl.common;

import cmdline.api.BaseParams;
import com.beust.jcommander.Parameter;
import utils.PathConverter;
import utils.WSSplitter;

import java.nio.file.Path;
import java.util.List;

/**
 * Defines the most basic parameters for command lines.
 * These definitions may be reused in other command lines.
 * Relies on JCommander to parse the command, its arguments, and options.
 */
public final class BaseParamsImpl implements BaseParams {

    @Parameter(names = {"-f", "--files"}, required = true, description = "space-separated list of file or directory paths",
            converter = PathConverter.class,
            splitter = WSSplitter.class)
    private List<Path> paths;

    @Parameter(names = {"-h", "--help"}, description = "display this help", help = true)
    private boolean help;

    @Override
    public List<Path> getPaths() {
        return paths;
    }

    @Override
    public boolean isHelp() {
        return help;
    }
}

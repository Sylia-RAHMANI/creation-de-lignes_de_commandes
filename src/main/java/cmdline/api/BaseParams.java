package cmdline.api;

import java.nio.file.Path;
import java.util.List;

/**
 * Common definition for any command line basic parameters.
 * Any file system-based command line, takes at least paths as arguments.
 */
public interface BaseParams {
    List<Path> getPaths();
    boolean isHelp();
}

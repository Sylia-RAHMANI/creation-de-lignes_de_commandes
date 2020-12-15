package cmdline.impl.size;

import cmdline.api.CommandLine;
import cmdline.api.CommandLineProvider;

/**
 * A provider for the size command line.
 * @see CommandSize
 */
public final class SizeProvider implements CommandLineProvider {
    @Override
    public CommandLine create() {
        return new CommandSize();
    }
}

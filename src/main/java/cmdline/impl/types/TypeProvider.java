package cmdline.impl.types;

import cmdline.api.CommandLine;
import cmdline.api.CommandLineProvider;
    /**
     * A provider for the size command line.
     * @see CommandTypes
     */
    public final class TypeProvider implements CommandLineProvider {
        @Override
        public CommandLine create() {
            return new CommandTypes();
        }
    }



package cmdline.impl.zip;


import cmdline.api.CommandLine;
import cmdline.api.CommandLineProvider;

public final class ZipProvider implements CommandLineProvider {
    @Override
    public CommandLine create() {
        return new CommandZip();
    }
}

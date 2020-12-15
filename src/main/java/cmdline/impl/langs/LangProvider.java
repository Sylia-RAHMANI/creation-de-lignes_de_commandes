package cmdline.impl.langs;

import cmdline.api.CommandLine;
import cmdline.api.CommandLineProvider;

public class LangProvider implements CommandLineProvider {
    @Override
    public CommandLine create() {
        return new CommandLangs();
    }
}

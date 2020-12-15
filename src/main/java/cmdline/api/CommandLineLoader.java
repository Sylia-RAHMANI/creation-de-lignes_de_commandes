package cmdline.api;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Service loader for command line providers.
 * @see {@link CommandLineProvider}
 */
public final class CommandLineLoader {
    private static final ServiceLoader<CommandLineProvider> LOADER = ServiceLoader.load(CommandLineProvider.class);

    private CommandLineLoader(){}

    public static Iterator<CommandLineProvider> providers(boolean refresh) {
        if(refresh)
            LOADER.reload();
        return LOADER.iterator();
    }
}

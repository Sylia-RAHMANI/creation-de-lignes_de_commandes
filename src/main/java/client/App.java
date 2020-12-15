package client;

import cmdline.api.CommandLine;
import cmdline.api.CommandLineLoader;
import cmdline.api.CommandLineProvider;
import com.beust.jcommander.JCommander;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Main class to launch this application.
 * This class was designed using service loader to fetch available command line providers.
 * Each provided command line implementation is invoked with the parsed command line.
 * It is the responsibility of each implementing class (of CommandLine) to carry out the command, or decline it.
 */
public final class App {

    public static void main(String[] args) {
        long start = System.nanoTime();
        List<CommandLine> commandLines = new ArrayList<>();
        JCommander.Builder builder = JCommander.newBuilder();

        Iterator<CommandLineProvider> providers = CommandLineLoader.providers(false);
        providers.forEachRemaining(p -> {
            CommandLine cmdline = p.create();
            commandLines.add(cmdline);
            builder.addCommand(cmdline);
        });

        JCommander jc = builder.build();
        jc.setProgramName(App.class.getName());
        jc.parse(args);
        Optional<CommandLine> commandLine = commandLines.stream().parallel()
                .filter(cmd -> cmd.getName().equals(jc.getParsedCommand()))
                .findAny();
        commandLine.ifPresent(cmdline -> cmdline.execute(jc));
        long end = System.nanoTime();
        System.out.println("The program ran in " + (end - start) / 1.0e9 +  " seconds.");
    }
}

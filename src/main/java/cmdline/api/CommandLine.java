package cmdline.api;

import com.beust.jcommander.JCommander;

/**
 * Definition for command lines.
 */
public interface CommandLine {
    String getName();
    void execute(JCommander jc);
}

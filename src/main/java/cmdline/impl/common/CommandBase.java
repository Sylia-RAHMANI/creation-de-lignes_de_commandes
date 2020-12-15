package cmdline.impl.common;

import cmdline.api.CommandLine;
import com.beust.jcommander.JCommander;
import filesystem.api.Component;
import fsmount.FSMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines the most common properties and behavior that all
 * command lines may share (to allow reuse and extension).
 */
public abstract class CommandBase implements CommandLine {

    protected final List<Component> components;

    public CommandBase() {
        components = new ArrayList<>();
    }

    public boolean isDisplayHelp(boolean isHelp, JCommander jc){
        if (isHelp) {
            jc.usage();
            return true;
        }
        return false;
    }

    public void buildComponentTree(List<Path> paths) {
        paths.forEach(p -> {
            FSMapper mapper = new FSMapper(p);
            try {
                components.add(mapper.mountFS());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

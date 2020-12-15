package cmdline.impl.size;

import cmdline.api.SizeParams;
import cmdline.impl.common.CommandBase;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.google.inject.Guice;
import com.google.inject.Injector;
import visitors.api.Visitor;
import visitors.impl.size.SizeVisitorBuilderModule;

/**
 * Files and directories size command line implementation. The files or directories are provided as arguments.
 * The size is about:
 * - each file
 * - each directory
 * - the total of (only) all files sizes, which is the default behavior.
 * <p>
 * An alternative behavior is to display each directory size as the sum of contained files or subdirectories.
 * As a consequence, the size of an empty directory is zero.
 * <p>
 * Another alternative behavior is to include the size of directories in the total, while displaying
 * each directory size as in the default behavior.
 */
@Parameters(commandNames = {"size"}, commandDescription = "Shows size of files or directories. " +
        "Default behavior includes only files sizes in the total, while showing each directory's own size.")
public final class CommandSize extends CommandBase {

    private static final String NAME = "size";

    @ParametersDelegate
    private final SizeParams params = new SizeParamsImpl();

    public CommandSize() {
        super();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void execute(JCommander jc) {
        if (isDisplayHelp(params.isHelp(), jc))
            return;

        if (bothOptionsFilesNDirectories()) {
            System.err.println("Parameters -f and -d are mutually exclusive. " +
                    "Use only one of them at each invocation.");
            return;
        }

        buildComponentTree(params.getPaths());

        Injector injector = Guice.createInjector(new SizeVisitorBuilderModule());
        SizeVisitorBuilderDirector director = injector.getInstance(SizeVisitorBuilderDirector.class);
        Visitor visitor = director.buildVisitor(params).getVisitor();
        components.forEach(c -> c.accept(visitor));
        visitor.print();

    }

    private boolean bothOptionsFilesNDirectories() {
        return params.isFilesOnly() && params.isIncludeDirectories();
    }
}

package cmdline.impl.zip;

import cmdline.api.VisitorBuilderDirector;
import cmdline.impl.common.CommandBase;
import cmdline.impl.langs.LangsValidator;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.google.inject.Guice;
import com.google.inject.Injector;
import visitors.api.Visitor;
import visitors.impl.zip.ZipVisitorBuilderModule;



@Parameters(commandNames = {"zip"}, commandDescription = "cette commande permet de zipper un repertoire et metre le fichier.zip dans le repertoire spécifier en argument  " )
 public final class CommandZip extends CommandBase {

    private static final String NAME = "zip";

    @ParametersDelegate
    private final ZipParamsImpl params = new ZipParamsImpl();

    public CommandZip() {super();}

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void execute(JCommander jc) {
        if (isDisplayHelp(params.isHelp(), jc)) {
            System.out.println("Supported languages: " + LangsValidator.mediaTypeGetKeys());
            return;
        }

        buildComponentTree(params.getPaths());

        Injector injector = Guice.createInjector(new ZipVisitorBuilderModule());
        VisitorBuilderDirector director = injector.getInstance(ZipVisitorBuilderDirector.class);
        Visitor visitor = director.buildVisitor(params).getVisitor();
        components.forEach(c -> c.accept(visitor));
        visitor.print();
    }
}

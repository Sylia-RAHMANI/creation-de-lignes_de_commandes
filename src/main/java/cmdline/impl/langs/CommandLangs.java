package cmdline.impl.langs;

import cmdline.api.VisitorBuilderDirector;
import cmdline.impl.common.CommandBase;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.google.inject.Guice;
import com.google.inject.Injector;
import visitors.api.Visitor;
import visitors.impl.langs.LangsVisitorBuilderModule;

import static java.lang.System.*;


@Parameters(commandNames = {"langs"}, commandDescription = "cette commande indique par type  de fichier spécifier :" +
        "            \"le nombre de fichiers de ce type,\" +\n" +
        "            \"la taille totale des fichiers de ce type,\" +\n"+
        "            \"la taille totale des fichiers de ce type,\" +\n"+
        "            \"la taille totale des fichiers de ce type,\" +\n")

    public final class CommandLangs extends CommandBase {

        private static final String NAME = "langs";

        @ParametersDelegate
        private final LangsParamImpl langsParam = new LangsParamImpl();

        public CommandLangs() {
            super();
        }

        @Override
        public String getName() {
            return NAME;
        }

        @Override
        public void execute(JCommander jc) {
            if (isDisplayHelp(langsParam.isHelp(), jc))
                out.println("les langages pris en charge sont: " + LangsValidator.mediaTypeGetKeys());

            buildComponentTree(langsParam.getPaths());

            Injector injector = Guice.createInjector(new LangsVisitorBuilderModule());
            VisitorBuilderDirector director;
            director = injector.getInstance(LangsVisitorBuilderDirector.class);
            Visitor visitor = director.buildVisitor(langsParam).getVisitor();
            components.forEach(c -> c.accept(visitor));
            visitor.print();
        }
    }



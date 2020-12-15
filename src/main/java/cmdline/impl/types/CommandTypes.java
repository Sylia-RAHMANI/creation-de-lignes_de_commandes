package cmdline.impl.types;

import cmdline.impl.common.CommandBase;
import cmdline.impl.common.CommonParamsImpl;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.google.inject.Guice;
import com.google.inject.Injector;
import visitors.api.Visitor;
import visitors.impl.types.typesVisitorBuilderModule;


@Parameters(commandNames = {"types"}, commandDescription = "cette commande indique par type T de fichier rencontré dans l'arborescence :" +
        "            \"le nombre de fichiers et la taille totale pour chaque type T de fichier;\" +\n" +
        "            \"puis la somme pour les nombres de fichiers et les tailles de chaque type.\" +\n" )
    public final class CommandTypes extends CommandBase {

        private static final String NAME = "types";

        @ParametersDelegate
        private final CommonParamsImpl commonParams = new CommonParamsImpl();

        public CommandTypes() {super();}

        @Override
        public String getName() {
            return this.NAME;
        }

        @Override
        public void execute(JCommander jc) {
            if (isDisplayHelp(commonParams.isHelp(), jc))
                return;

            buildComponentTree(commonParams.getPaths());

            Injector injector = Guice.createInjector(new typesVisitorBuilderModule());
            TypeVisitorBuilderDirector director = injector.getInstance(TypeVisitorBuilderDirector.class);
            Visitor visitor = director.buildVisitor(commonParams).getVisitor();
            components.forEach(c -> c.accept(visitor));
            visitor.print();
        }
}

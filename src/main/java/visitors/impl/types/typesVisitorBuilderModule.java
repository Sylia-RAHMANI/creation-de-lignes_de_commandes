package visitors.impl.types;

import com.google.inject.AbstractModule;
import visitors.api.TypesVisitorBuilder;


public class typesVisitorBuilderModule extends AbstractModule {
    public typesVisitorBuilderModule() {
        super();
    }

    @Override
    protected void configure() {
        bind(TypesVisitorBuilder.class)
                .annotatedWith(TypesVisitorBuilder.types.class)
                .to(typesVisitorBuilderImpl.class);
    }
}

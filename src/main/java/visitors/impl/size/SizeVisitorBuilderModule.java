package visitors.impl.size;

import com.google.inject.AbstractModule;
import visitors.api.SizeVisitorBuilder;

/**
 * Binds annotated {@link SizeVisitorBuilder} interface to its implementation, for dependency injection.
 * @see <a href="https://github.com/google/guice/wiki/GettingStarted">Guice documentation</a>.
 */
public final class SizeVisitorBuilderModule extends AbstractModule {

    public SizeVisitorBuilderModule() {
        super();
    }

    @Override
    protected void configure() {
       bind(SizeVisitorBuilder.class)
               .annotatedWith(SizeVisitorBuilder.FileSize.class)
               .to(SizeVisitorBuilderImpl.class);
    }
}

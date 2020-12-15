package visitors.impl.langs;

import com.google.inject.AbstractModule;
import visitors.api.LangsVisitorBuilder;

public final class LangsVisitorBuilderModule extends AbstractModule {

    public LangsVisitorBuilderModule() {
        super();
    }

    @Override
    protected void configure() {
        bind(LangsVisitorBuilder.class)
                .annotatedWith(LangsVisitorBuilder.Lags.class)
                .to(LangsVisitorBuilderImpl.class);
    }
}


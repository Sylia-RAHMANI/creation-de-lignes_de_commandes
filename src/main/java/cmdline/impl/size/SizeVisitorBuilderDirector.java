package cmdline.impl.size;

import cmdline.api.SizeParams;
import cmdline.api.VisitorBuilderDirector;
import com.google.inject.Inject;
import visitors.api.SizeVisitorBuilder;
import visitors.api.Visitor;

/**
 * A director for the builder which creates the visitor for the size command line.
 */
final class SizeVisitorBuilderDirector implements VisitorBuilderDirector<SizeParams> {
    @Inject
    @SizeVisitorBuilder.FileSize
    private SizeVisitorBuilder builder;

    @Override
    public SizeVisitorBuilderDirector buildVisitor(SizeParams params) {
        builder.setIncludeFilesOnly(params.isFilesOnly())
                .setIncludeDirectories(params.isIncludeDirectories())
                .setSizeUnit(params.getSizeUnit());
        return this;
    }

    @Override
    public Visitor getVisitor() {
        return builder.build();
    }
}

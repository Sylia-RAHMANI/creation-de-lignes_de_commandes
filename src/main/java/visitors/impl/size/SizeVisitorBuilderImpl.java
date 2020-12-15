package visitors.impl.size;

import cmdline.impl.common.SizeUnit;
import visitors.api.SizeVisitorBuilder;
import visitors.api.Visitor;
import visitors.api.VisitorBuilder;

/**
 * Concrete builder of {@link SizeVisitor} implementing {@link SizeVisitorBuilder}
 */
final class SizeVisitorBuilderImpl implements SizeVisitorBuilder {

    private final SizeVisitor visitor;

    SizeVisitorBuilderImpl() {
        visitor = new SizeVisitor();
    }

    @Override
    public VisitorBuilder setSizeUnit(SizeUnit sizeUnit) {
        visitor.setSizeUnit(sizeUnit);
        return this;
    }

    @Override
    public SizeVisitorBuilder setIncludeFilesOnly(boolean option) {
        visitor.setIncludeFilesOnly(option);
        return this;
    }

    @Override
    public SizeVisitorBuilder setIncludeDirectories(boolean option) {
        visitor.setIncludeDirectories(option);
        return this;
    }

    @Override
    public Visitor build() {
        return visitor;
    }
}

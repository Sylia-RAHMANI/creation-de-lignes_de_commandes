package visitors.impl.types;

import cmdline.impl.common.SizeUnit;
import visitors.api.TypesVisitorBuilder;
import visitors.api.Visitor;
import visitors.api.VisitorBuilder;

public class typesVisitorBuilderImpl implements TypesVisitorBuilder {
    private final typesVisitor typesVisitor;

    typesVisitorBuilderImpl() {
        typesVisitor = new typesVisitor();
    }

    @Override
    public VisitorBuilder setSizeUnit(SizeUnit sizeUnit) {
        typesVisitor.setSizeUnit(sizeUnit);
        return this;
    }

    @Override
    public Visitor build() {
        return typesVisitor;
    }
}

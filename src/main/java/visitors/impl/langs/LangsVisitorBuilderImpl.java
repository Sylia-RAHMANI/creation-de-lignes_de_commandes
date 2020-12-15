package visitors.impl.langs;

import cmdline.impl.common.SizeUnit;
import org.apache.tika.mime.MediaType;
import visitors.api.LangsVisitorBuilder;
import visitors.api.Visitor;
import visitors.api.VisitorBuilder;

import java.util.List;

final class LangsVisitorBuilderImpl implements LangsVisitorBuilder {

    private final LangsVisitor langsVisitor;

    public LangsVisitorBuilderImpl(){
        this.langsVisitor = new LangsVisitor();
    }

    @Override
    public VisitorBuilder setSizeUnit(SizeUnit sizeUnit) {
        langsVisitor.setSizeUnit(sizeUnit);
        return this;
    }

    @Override
    public Visitor build() {
        return langsVisitor;
    }

    @Override
    public LangsVisitorBuilder setMediaTypes(List<MediaType> mediaTypes) {
        langsVisitor.setMediaTypes(mediaTypes);
        return this;
    }
}

package visitors.impl.zip;

import cmdline.impl.common.SizeUnit;
import org.apache.tika.mime.MediaType;
import visitors.api.Visitor;
import visitors.api.VisitorBuilder;
import visitors.api.ZipVisitorBuilder;

import java.nio.file.Path;
import java.util.List;


final class ZipVisitorBuilderImpl implements ZipVisitorBuilder {

    private final ZipVisitor zipVisitor;

    public ZipVisitorBuilderImpl() {this.zipVisitor = new ZipVisitor();}

    @Override
    public VisitorBuilder setSizeUnit(SizeUnit sizeUnit) {
        return this;
    }

    @Override
    public Visitor build() {
        return zipVisitor;
    }

    @Override
    public ZipVisitorBuilder setMediaTypes(List<MediaType> mediaTypes) {
        zipVisitor.setMediaTypes(mediaTypes);
        return this;
    }

    @Override
    public ZipVisitorBuilder repertoirDestination(Path path) {
        return null;//to do
    }


    @Override
    public ZipVisitorBuilder overwriteDestination(boolean overwrite) {
        zipVisitor.setOverwrite(overwrite);
        return this;
    }
}

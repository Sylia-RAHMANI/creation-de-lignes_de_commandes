package visitors.impl.zip;

import filesystem.api.Component;
import filesystem.api.Composite;
import iterators.IteratorFactory;
import org.apache.tika.mime.MediaType;
import visitors.impl.common.VisitorImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


final class ZipVisitor extends VisitorImpl {


    private List<MediaType> mediaTypes;
    private ZipOutputStream zipOutputStream;
    private boolean overwrite;

    ZipVisitor(){
        super();

    }

    @Override
    public void visit(Component visitable) {
        Iterator<Component> iterator = IteratorFactory.getInstance().getIterator(visitable, mediaTypes);
        if (!iterator.hasNext()) {
            return;
        }
        addFile(visitable, visitable.getPath().getParent());
    }

    @Override
    public void visit(Composite<Component> visitable) {
        Component component;
        Iterator<Component> iterator = IteratorFactory.getInstance().getIterator(visitable, mediaTypes);
        if (iterator.hasNext()) {
            do {
                component = iterator.next();
                addFile(component, visitable.getPath());
            } while (iterator.hasNext());
        }
    }

    @Override
    public void print() {
        try {
            zipOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setMediaTypes(List<MediaType> mediaTypes) {
        this.mediaTypes = mediaTypes;
    }

    void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }


    private void addFile(Component visitable, Path parent) {
        ZipEntry zipEntry = new ZipEntry(visitable.getPath().relativize(parent).toString());
        try {
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(Files.readAllBytes(visitable.getPath()));
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

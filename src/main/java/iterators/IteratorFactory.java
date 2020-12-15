package iterators;

import filesystem.api.Component;
import org.apache.tika.mime.MediaType;

import java.util.Iterator;
import java.util.List;

public final class IteratorFactory {

    private static final IteratorFactory INSTANCE = new IteratorFactory();

    private IteratorFactory(){}

    public static IteratorFactory getInstance() {
        return INSTANCE;
    }

    public Iterator<Component> getIterator(Component component, List<MediaType> mediaTypes){
        return new IteratorByMediaTypeGenerator(component, mediaTypes).iterator();
    }
}

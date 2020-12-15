package iterators;

import com.zoominfo.util.yieldreturn.Generator;
import filesystem.api.Component;
import filesystem.api.Composite;
import org.apache.tika.mime.MediaType;
import visitors.api.Visitor;

import java.util.List;

/**
 * Provides a generator of {@link Component} for a given criterion, for example only Java source files.
 * The generator provides a lazy, on-demand fetching service.
 * @see <a href="https://github.com/domlachowicz/java-generators>Java Generator</a>
 */
public final class IteratorByMediaTypeGenerator extends Generator<Component> implements Visitor {

    private final Component root;
    private final List<MediaType> mediaTypes;

    /**
     * Returns an iterator on components of the specified media types
     * @param root the root component of the component tree
     * @param mediaTypes the list of media types (some languages have several media types, e.g. C (.h, .c))
     */
     IteratorByMediaTypeGenerator(Component root, List<MediaType> mediaTypes) {
        this.root = root;
        this.mediaTypes = mediaTypes;
    }

    @Override
    protected void run() {
        root.accept(this);
    }

    @Override
    public void visit(Component visitable) {
        if (mediaTypes.contains(visitable.getContentType()))
            this.yield(visitable);
    }

    @Override
    public void visit(Composite<Component> visitable) {
        visitable.getChildren().forEach(c -> c.accept(this));
    }

    @Override
    public void print() {
        System.out.println("Iterator generator for content type: " + mediaTypes);
    }
}

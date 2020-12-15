package filesystem.api;

import org.apache.tika.mime.MediaType;

import java.io.IOException;
import java.util.List;

public interface Component extends Visitable, PathComponent {

    String getName();

    /**
     * Returns the size of this component, in bytes.
     * @return the size of this component
     * @throws IOException if an I/O error occurred during this operation
     */
    long getSize() throws IOException;
    List<String> getContent();
    void setContent(List<String> content);
    boolean isDirectory();
    boolean isFile();
    MediaType getContentType();
    Composite<Component> asComposite();
}

package filesystem.impl;

import filesystem.api.Component;
import org.apache.tika.mime.MediaType;
import utils.FileContentTypeDetector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public abstract class ComponentImpl implements Component {

    protected final File wrapped;

    public ComponentImpl(File wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getName() {
        return this.wrapped.getName();
    }

    @Override
    public long getSize() throws IOException {
        return Files.size(this.wrapped.toPath());
    }

    @Override
    public MediaType getContentType()  {
        FileContentTypeDetector contentExtractor = FileContentTypeDetector.getInstance();
        try {
            return contentExtractor.getContentType(this.wrapped);
        } catch (IOException e) {
            e.printStackTrace();
            return MediaType.EMPTY;
        }
    }

    @Override
    public boolean isDirectory() {
        return this.wrapped.isDirectory();
    }

    @Override
    public boolean isFile() {
        return this.wrapped.isFile();
    }

    @Override
    public Path getPath() {
        return this.wrapped.toPath();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentImpl component = (ComponentImpl) o;
        try {
            return this.getSize() == component.getSize() &&
                    Objects.equals(this.getName(), component.getName());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int hashCode() {
        try {
            return Objects.hash(this.getName(), this.getSize());
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

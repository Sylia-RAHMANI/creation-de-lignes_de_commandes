package filesystem.impl;

import filesystem.api.Component;
import filesystem.api.Composite;
import visitors.api.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class File extends ComponentImpl implements Component {

    private final List<String> content;

    protected File(java.io.File wrapped) {
        super(wrapped);
        content = new ArrayList<>();
    }

    @Override
    public List<String> getContent() {
        return this.content;
    }

    @Override
    public void setContent(List<String> content) {
        this.content.addAll(content);
    }

    @Override
    public Composite<Component> asComposite() {
        return null;
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        File file = (File) o;
        return Objects.equals(content, file.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), content);
    }

    @Override
    public String toString() {
        return this.content.toString();
    }
}

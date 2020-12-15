package filesystem.impl;

import filesystem.api.Component;
import filesystem.api.Composite;
import visitors.api.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Directory extends ComponentImpl implements Component, Composite<Component> {
    private final List<Component> children;

    protected Directory(java.io.File wrapped) {
        super(wrapped);
        children = new ArrayList<>();
    }

    @Override
    public Composite<Component> asComposite() {
        return this;
    }

    @Override
    public List<Component> getChildren() {
        return this.children;
    }

    @Override
    public void addChild(Component component) {
        this.children.add(component);
    }

    @Override
    public boolean removeChild(Component component) {
        return this.children.remove(component);
    }

    @Override
    public boolean removeChildren(List<Component> t) {
        return this.children.removeAll(t);
    }

    @Override
    public List<String> getContent() {
        return this.children.stream().map(Component::getName).collect(Collectors.toList());
    }

    @Override
    public void setContent(List<String> content) {
        throw new UnsupportedOperationException("Setting the content of a directory is an unsupported feature.");
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
        Directory directory = (Directory) o;
        return Objects.equals(children, directory.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), children);
    }

    @Override
    public String toString() {
        return String.join(";", getContent());
    }
}

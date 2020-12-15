package filesystem.api;

import visitors.api.Visitor;

public interface Visitable {
    void accept(Visitor visitor);
}

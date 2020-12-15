package visitors.api;

import filesystem.api.Component;
import filesystem.api.Composite;

public interface Visitor {
   void visit(Component visitable);
   void visit(Composite<Component> visitable);
   void print();
}

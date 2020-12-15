package cmdline.api;

import visitors.api.Visitor;

/**
 * Common definition for directors of visitor builders.
 */
public interface VisitorBuilderDirector<T extends BaseParams > {

    VisitorBuilderDirector<T> buildVisitor(T params);

    Visitor getVisitor();
}

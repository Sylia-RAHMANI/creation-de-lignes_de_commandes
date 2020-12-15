package visitors.api;

import cmdline.impl.common.SizeUnit;

/**
 * Basic visitor builder interface.
 */
public interface VisitorBuilder {
    VisitorBuilder setSizeUnit(SizeUnit sizeUnit);
    Visitor build();
}

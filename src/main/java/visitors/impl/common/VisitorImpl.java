package visitors.impl.common;

import cmdline.impl.common.SizeUnit;
import visitors.api.Visitor;

/**
 * Defines common properties and behavior that most visitors may share
 * (reuse and extension).
 */
public abstract class VisitorImpl implements Visitor {

    public static final String WS = " ";
    public static final String LEFT_PAR = "(";
    public static final String RIGHT_PAR = ")";
    public static final String TOTAL = "TOTAL";
    public static final String NB_FILES = "Files";
    public static final String TOTAL_SIZE = "Size";
    protected String unitStr;

    protected SizeUnit sizeUnit;

    public VisitorImpl() {
        this.sizeUnit = SizeUnit.BYTES;
    }

    public void setSizeUnit(SizeUnit sizeUnit) {
        this.sizeUnit = sizeUnit;
        this.unitStr = LEFT_PAR + sizeUnit + RIGHT_PAR;
    }
}

package utils;

import cmdline.impl.common.SizeUnit;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Provides translation of sizes into the requested unit, and formatting for display.
 */
public final class SizeFormatter {
    public static final double TO_KB = 1024.0d;
    public static final double TO_MB = Math.pow(TO_KB, 2.0d);
    public static final double TO_GB = Math.pow(TO_KB, 3.0d);

    private SizeFormatter(){}

    public static double translateSizeInUnit(long sizeInByte, SizeUnit sizeUnit) {
        switch (sizeUnit) {
            case KB:
                return sizeInByte / SizeFormatter.TO_KB;
            case MB:
                return sizeInByte / SizeFormatter.TO_MB;
            case GB:
                return sizeInByte / SizeFormatter.TO_GB;
            default:
                return sizeInByte;
        }
    }

    public static String formatSize(double size) {
        DecimalFormat dc = new DecimalFormat("###,###.###", new DecimalFormatSymbols(Locale.ENGLISH));
        return dc.format(size);
    }
}

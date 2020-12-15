package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Prints a formatted table on the command line.
 * Source : https://www.logicbig.com/how-to/code-snippets/jcode-java-cmd-command-line-table.html
 */
public final class CommandLineTable {
    private static final String HORIZONTAL_SEP = "-";
    private static final String HORIZONTAL_MID_SEP = "=";
    private String verticalSep;
    private String joinSep;
    private String[] headers;
    private final List<String[]> rows;
    private final List<String[]> finalRows;
    private boolean rightAlign;

    public CommandLineTable() {
        setShowVerticalLines(false);
        rows = new ArrayList<>();
        finalRows = new ArrayList<>();
    }

    public void setRightAlign(boolean rightAlign) {
        this.rightAlign = rightAlign;
    }

    public void setShowVerticalLines(boolean showVerticalLines) {
        verticalSep = showVerticalLines ? "|" : "";
        joinSep = showVerticalLines ? "+" : " ";
    }

    public void setHeaders(String... headers) {
        this.headers = headers;
    }

    public void addRow(String... cells) {
        rows.add(cells);
    }

    public void addFinalRow(String... cells){
        finalRows.add(cells);
    }

    public void reset(){
        this.headers =  null;
        this.rows.clear();
        this.finalRows.clear();
    }

    public void print() {
        int[] maxWidths = headers != null ?
                Arrays.stream(headers).mapToInt(String::length).toArray() : null;

        for (String[] cells : rows) {
            if (maxWidths == null) {
                maxWidths = new int[cells.length];
            }
            if (cells.length != maxWidths.length) {
                throw new IllegalArgumentException("Number of row-cells and headers should be consistent");
            }
            for (int i = 0; i < cells.length; i++) {
                maxWidths[i] = Math.max(maxWidths[i], cells[i].length());
            }
        }

        if (headers != null) {
            printLine(maxWidths, HORIZONTAL_MID_SEP);
            printRow(headers, maxWidths);
            printLine(maxWidths, HORIZONTAL_MID_SEP);
        }
        for (String[] cells : rows) {
            printRow(cells, maxWidths);
        }
        if (headers != null) {
            printLine(maxWidths, HORIZONTAL_SEP);
        }
        if (!finalRows.isEmpty()) {
            if (headers == null)
                printLine(maxWidths, HORIZONTAL_SEP);
            for (String[] cells : finalRows) {
                printRow(cells, maxWidths);
            }
            printLine(maxWidths, HORIZONTAL_SEP);
        }
    }

    private void printLine(int[] columnWidths, String hSep) {
        for (int i = 0; i < columnWidths.length; i++) {
            String line = String.join("", Collections.nCopies(columnWidths[i] +
                    verticalSep.length() + 1, hSep));
            System.out.print(joinSep + line + (i == columnWidths.length - 1 ? joinSep : ""));
        }
        System.out.println();
    }

    private void printRow(String[] cells, int[] maxWidths) {
        for (int i = 0; i < cells.length; i++) {
            String s = cells[i];
            String verStrTemp = i == cells.length - 1 ? verticalSep : "";
            if (rightAlign) {
                System.out.printf("%s %" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
            } else {
                System.out.printf("%s %-" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        //test code
        CommandLineTable st = new CommandLineTable();
        //st.setRightAlign(true);//if true then cell text is right aligned
        st.setShowVerticalLines(true);//if false (default) then no vertical lines are shown
        st.setHeaders("one", "two", "three");//optional - if not used then there will be no header and horizontal lines
        st.addRow("super/subA/sub2/sub3", "broccoli", "flexible");
        st.addRow("assumption", "announcement", "reflection");
        st.addRow("logic", "pleasant", "wild");
        st.addFinalRow("SUM: ", "sum 1", "sum 2");
        st.print();
    }
}

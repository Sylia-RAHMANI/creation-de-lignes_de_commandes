package visitors.impl.size;

import filesystem.api.Component;
import filesystem.api.Composite;
import utils.CommandLineTable;
import visitors.impl.common.VisitorImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static utils.SizeFormatter.formatSize;
import static utils.SizeFormatter.translateSizeInUnit;

final class SizeVisitor extends VisitorImpl {

    public static final String SIZE = "Size";
    public static final String NAME = "Name";

    private final CommandLineTable cmdLineTable;
    private final Map<Path, Long> dirSize;
    private long totalSize;
    private boolean includeFilesOnly;
    private boolean includeDirectories;

    SizeVisitor() {
        super();
        cmdLineTable = new CommandLineTable();
        dirSize = new HashMap<>();
    }

    @Override
    public void visit(Component visitable) {
        try {
            long size = visitable.getSize();
            totalSize += size;
            checkIncludeFileSizeOnly(visitable);
            cmdLineTable.addRow(visitable.getPath().toString(),
                    formatSize(translateSizeInUnit(size, sizeUnit)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(Composite<Component> visitable) {
        try {
            long size = visitable.getSize();
            checkIncludeDirSize(size);
            checkIncludeFileSizeOnly(visitable);
            visitable.getChildren().forEach(c -> c.accept(this));
            updateParentSize(visitable);
            size = getDirSizeToPrint(visitable);
            cmdLineTable.addRow(visitable.getPath().toString(),
                    formatSize(translateSizeInUnit(size, sizeUnit)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {
        initCmdLineTableHeaders();
        cmdLineTable.setShowVerticalLines(true);
        cmdLineTable.addFinalRow(TOTAL + WS + unitStr,
                formatSize(translateSizeInUnit(totalSize, sizeUnit)));
        cmdLineTable.print();
    }

    void setIncludeFilesOnly(boolean includeFilesOnly) {
        this.includeFilesOnly = includeFilesOnly;
    }

    void setIncludeDirectories(boolean includeDirectories) {
        this.includeDirectories = includeDirectories;
    }

    private void initCmdLineTableHeaders() {
        cmdLineTable.setHeaders(NAME, SIZE + WS + unitStr);
    }

    private void checkIncludeDirSize(long size) {
        if (includeDirectories)
            totalSize += size;
    }

    private void checkIncludeFileSizeOnly(Composite<Component> composite) {
        if (includeFilesOnly)
            dirSize.put(composite.getPath(), 0L);
    }

    private void checkIncludeFileSizeOnly(Component component) throws IOException {
        if (includeFilesOnly) {
            long size = component.getSize();
            dirSize.computeIfPresent(component.getPath().getParent(), (k, v) -> v + size);
        }
    }

    private void updateParentSize(Composite<Component> composite) {
        if (includeFilesOnly) {
            long mySize = dirSize.get(composite.getPath());
            dirSize.computeIfPresent(composite.getPath().getParent(), (k, v) -> v + mySize);
        }
    }

    private long getDirSizeToPrint(Composite<Component> composite) throws IOException {
        if (includeFilesOnly)
            return dirSize.get(composite.getPath());
        return composite.getSize();
    }

}

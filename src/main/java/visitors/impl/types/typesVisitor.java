package visitors.impl.types;

import filesystem.api.Component;
import filesystem.api.Composite;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.tika.mime.MediaType;
import utils.CommandLineTable;
import visitors.impl.common.VisitorImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static utils.SizeFormatter.formatSize;
import static utils.SizeFormatter.translateSizeInUnit;

final class typesVisitor extends VisitorImpl {

    public static final String TYPES = "types";

    private final CommandLineTable commandLineTable = new CommandLineTable();
    private final Map<MediaType, Pair<Long, Long>> typeSize = new HashMap<>();

    typesVisitor(){
        super();
    }

    @Override
    public void visit(Component component) {
        try {
            long size = component.getSize() ;
            MediaType contentType = component.getContentType();
            Pair<Long, Long> pair;
            pair = typeSize.computeIfAbsent(contentType, new Function<MediaType, Pair<Long, Long>>() {
                @Override
                public Pair<Long, Long> apply(MediaType k) {
                    return Pair.of(1L, size);
                }
            });
            if (pair.getRight() != component.getSize())
                typeSize.computeIfPresent(contentType, new BiFunction<MediaType, Pair<Long, Long>, Pair<Long, Long>>() {
                    @Override
                    public Pair<Long, Long> apply(MediaType k, Pair<Long, Long> v) {
                        return Pair.of(v.getLeft() + 1L, v.getRight() + size);
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(Composite<Component> composite) {
        for (Component c : composite.getChildren()) {
            c.accept(this);
        }
    }

    @Override
    public void print() {
        initCmdLineTableHeaders();
        commandLineTable.setShowVerticalLines(true);
        var nbFilesNSize = Pair.of(0L, 0L);
        for (Map.Entry<MediaType, Pair<Long, Long>> mediaTypePairEntry : typeSize.entrySet()) {
            Pair<Long, Long> longLongPair = newRow(mediaTypePairEntry);
            nbFilesNSize = Pair.of(nbFilesNSize.getLeft() + longLongPair.getLeft(), nbFilesNSize.getRight() + longLongPair.getRight());
        }
        commandLineTable.addFinalRow(TOTAL + WS + unitStr,
                String.valueOf(nbFilesNSize.getLeft()),
                formatSize(translateSizeInUnit(nbFilesNSize.getRight(), sizeUnit)));
        commandLineTable.print();
    }

    private void initCmdLineTableHeaders() {
        commandLineTable.setHeaders(TYPES, NB_FILES, TOTAL_SIZE + WS + unitStr);
    }

    private Pair<Long, Long> newRow(Map.Entry<MediaType, Pair<Long, Long>> entry) {
        commandLineTable.addRow(entry.getKey().toString(),
                String.valueOf(entry.getValue().getLeft()),
                formatSize(translateSizeInUnit(entry.getValue().getRight(), sizeUnit)));
        return entry.getValue();
    }
}

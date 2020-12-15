package visitors.impl.langs;

import filesystem.api.Component;
import filesystem.api.Composite;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.tika.mime.MediaType;
import utils.CommandLineTable;
import utils.PropertiesConfigExplorer;
import utils.PropertiesConfigLoader;
import visitors.impl.common.VisitorImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static iterators.IteratorFactory.getInstance;
import static utils.SizeFormatter.formatSize;
import static utils.SizeFormatter.translateSizeInUnit;

public class LangsVisitor extends VisitorImpl {
    public static final String LANG = "Langage";
    private static final String NB_LIGNE = "Lignes";

    private final CommandLineTable cmdLineTable= new CommandLineTable();
    private final Map<MediaType, Triple<Long, Long, Long>> mediaTypeFLS = new HashMap<>();
    private List<MediaType> mediaTypes;

    LangsVisitor() {
        super();
    }

    @Override
    public void visit(Component visitable) {
        var iterator = getInstance().getIterator(visitable, mediaTypes);
        if (iterator.hasNext()) {
            miseAjourTable(visitable);
        }
    }

    @Override
    public void visit(Composite<Component> visitable) {
        Component component;
        Iterator<Component> iterator = getInstance().getIterator(visitable, mediaTypes);
        if (iterator.hasNext()) {
            do {
                component = iterator.next();
                miseAjourTable(component);
            } while (iterator.hasNext());
        }
    }

    @Override
    public void print() {
        entête();
        cmdLineTable.setShowVerticalLines(true);
        try {
            PropertiesConfigExplorer explorer = PropertiesConfigExplorer.getInstance()
                    .setConfig(PropertiesConfigLoader.getInstance().getConfiguration());

            Triple<Long, Long, Long> data = mediaTypeFLS.entrySet().stream().map(new Function<Map.Entry<MediaType, Triple<Long, Long, Long>>, Triple<Long, Long, Long>>() {
                @Override
                public Triple<Long, Long, Long> apply(Map.Entry<MediaType, Triple<Long, Long, Long>> e) {
                    cmdLineTable.addRow(explorer.getLang(e.getKey().toString()),
                            String.valueOf(e.getValue().getLeft()),
                            String.valueOf(e.getValue().getMiddle()),
                            formatSize(translateSizeInUnit(e.getValue().getRight(), sizeUnit)));
                    return e.getValue();
                }
            }).reduce(Triple.of(0L, 0L, 0L), (t1, t2) ->
                    Triple.of(t1.getLeft() + t2.getLeft(),
                            t1.getMiddle() + t2.getMiddle(),
                            t1.getRight() + t2.getRight())
            );
            cmdLineTable.addFinalRow(TOTAL + WS + unitStr,
                    String.valueOf(data.getLeft()),
                    String.valueOf(data.getMiddle()),
                    formatSize(translateSizeInUnit(data.getRight(), sizeUnit)));
            cmdLineTable.print();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    void setMediaTypes(List<MediaType> mediaTypes) {
        this.mediaTypes = mediaTypes;
    }

    private void miseAjourTable(Component visitable) {
        try {
            long size = visitable.getSize();
            MediaType mtype = visitable.getContentType();
            long nbLines;
            try (Stream<String> input = Files.lines(visitable.getPath(), StandardCharsets.UTF_8)) {
                nbLines = input.count();
            }
            Triple<Long, Long, Long> newValue = mediaTypeFLS.computeIfAbsent(mtype, new Function<MediaType, Triple<Long, Long, Long>>() {
                        @Override
                        public Triple<Long, Long, Long> apply(MediaType k) {
                            return Triple.of(1L, nbLines, size);
                        }
                    }
            );
            if (newValue.getRight() != size && newValue.getMiddle() != nbLines)
                mediaTypeFLS.computeIfPresent(mtype, new BiFunction<MediaType, Triple<Long, Long, Long>, Triple<Long, Long, Long>>() {
                    @Override
                    public Triple<Long, Long, Long> apply(MediaType k, Triple<Long, Long, Long> v) {
                        return Triple.of(v.getLeft() + 1L, v.getMiddle() + nbLines, v.getRight() + size);
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void entête() {
        cmdLineTable.setHeaders(LANG, NB_FILES, NB_LIGNE, TOTAL_SIZE + WS + unitStr);
    }
}


package fsmount;

import filesystem.api.Component;
import filesystem.api.ComponentType;
import filesystem.impl.FSFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Maps a file tree to higher-level components in {@link filesystem.api}.
 * This approach uses the principles of the design pattern Decorator since each {@link filesystem.api.Component}
 * and {@link filesystem.api.Composite} encapsulates access to the underlying (real) file it maps to.
 * @see filesystem.api.Component
 * @see filesystem.api.Composite
 */
public final class FSMapper {

    private final Path mountPath;

    public FSMapper(Path mountPath) {
        this.mountPath = mountPath;
    }

    /**
     * Returns the complete file tree, starting at the path provided to the constructor,
     * mapped to higher-level composite tree of {@link filesystem.api.Component}
     * @return the composite tree of {@link filesystem.api.Component} that encapsulates
     * access to the underlying file tree.
     * @throws IOException if any I/O error occurs
     */
    public Component mountFS() throws IOException {
        Map<String, Component> componentsPaths = new HashMap<>();
        Files.walkFileTree(mountPath, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
                new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                        Component dirComp = createComponentForFile(dir);
                        componentsPaths.put(dir.toString(), dirComp);
                        Component parent = dir.getParent() != null ? componentsPaths.get(dir.getParent().toString()) : null;
                        if (parent != null && parent.isDirectory())
                            parent.asComposite().addChild(dirComp);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        Component fileComp = createComponentForFile(file);
                        Component parent = file.getParent() != null ? componentsPaths.get(file.getParent().toString()) : null;
                        if (parent != null && parent.isDirectory())
                            parent.asComposite().addChild(fileComp);
                        return FileVisitResult.CONTINUE;
                    }
                });
        return componentsPaths.get(mountPath.toString());
    }

    private Component createComponentForFile(Path path) {
        File file = path.toFile();
        Component component;
        if (file.isDirectory()) {
            component = FSFactory.getInstance().createComponent(ComponentType.DIRECTORY, file);
        } else {
            component = FSFactory.getInstance().createComponent(ComponentType.FILE, file);
        }
        return component;
    }
}

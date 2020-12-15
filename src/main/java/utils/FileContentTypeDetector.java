package utils;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.tika.metadata.TikaMetadataKeys.RESOURCE_NAME_KEY;

/**
 * Provides a file content type detection service, thanks to Apache Tika.
 *
 * This class provides a singleton instance through {@link #getInstance()};
 * however it does not forbid creating a new instance through {@link #newInstance()} if
 * needed.
 *
 * Use the singleton instance whenever you are in a single-threaded context.
 * Use several distinct instances if you want to increase parallelism over this detection service.
 */
public final class FileContentTypeDetector {

    private static final FileContentTypeDetector INSTANCE = new FileContentTypeDetector();

    private final TikaConfig tika;

    private FileContentTypeDetector() {
        try {
            tika = new TikaConfig();
        } catch (TikaException | IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e.getCause());
        }
    }

    public static FileContentTypeDetector getInstance() {
        return INSTANCE;
    }

    public static FileContentTypeDetector newInstance(){
        return new FileContentTypeDetector();
    }

    public MediaType getContentType(File file) throws IOException {
        Metadata metadata = new Metadata();
        metadata.set(RESOURCE_NAME_KEY, file.toString());
        return tika.getDetector().detect(TikaInputStream.get(file.toPath()), metadata);
    }

    public Map<File, MediaType> getContentTypes(List<File> files) throws IOException {
        Map<File,MediaType> contentTypes = new HashMap<>();
        for (File f : files) {
            contentTypes.put(f, getContentType(f));
        }
        return contentTypes;
    }
}

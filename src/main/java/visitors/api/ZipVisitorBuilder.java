package visitors.api;

import org.apache.tika.mime.MediaType;

import java.nio.file.Path;
import java.util.List;

public interface ZipVisitorBuilder extends LangsVisitorBuilder {
    @Override
    ZipVisitorBuilder setMediaTypes(List<MediaType> mediaTypes);

    ZipVisitorBuilder repertoirDestination(Path path);

    ZipVisitorBuilder overwriteDestination(boolean overwrite);}

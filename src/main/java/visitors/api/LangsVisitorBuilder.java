package visitors.api;

import org.apache.tika.mime.MediaType;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public interface LangsVisitorBuilder extends VisitorBuilder{
    @Qualifier
    @Target({FIELD, PARAMETER, METHOD})
    @Retention(RUNTIME)
    @interface Lags {}

    LangsVisitorBuilder setMediaTypes(List<MediaType> mediaTypes);
}

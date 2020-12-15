package visitors.api;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Definition for builder of Size visitor implementation.
 */
public interface SizeVisitorBuilder extends VisitorBuilder {
    /**
     * Annotates this builder so that it can be injected wherever it is required.
     * Placeholders for injection are fields, parameters, and methods.
     */
    @Qualifier
    @Target({FIELD, PARAMETER, METHOD})
    @Retention(RUNTIME)
    @interface FileSize {}

    SizeVisitorBuilder setIncludeFilesOnly(boolean option);
    SizeVisitorBuilder setIncludeDirectories(boolean option);

}

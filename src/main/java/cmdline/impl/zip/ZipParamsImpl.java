package cmdline.impl.zip;

import cmdline.api.BaseParams;
import cmdline.impl.langs.LangsParamImpl;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import org.apache.tika.mime.MediaType;
import utils.PathConverter;

import java.nio.file.Path;
import java.util.List;


final class ZipParamsImpl implements BaseParams {

    @ParametersDelegate
    private final LangsParamImpl zipParams = new LangsParamImpl();


    @Parameter(names = {"-o", "--overwrite"},
            description = "écraser le fichier de repertoire destination")
    private final boolean overwrite = false;

    @Override
    public List<Path> getPaths() {
        return  zipParams.getPaths();
    }

    @Override
    public boolean isHelp() {
        return zipParams.isHelp();
    }

    public List<MediaType> getMediaTypes(){
        return zipParams.getMediaTypes();
    }


    public boolean isOverwriteDestination(){
        return overwrite;
    }
}

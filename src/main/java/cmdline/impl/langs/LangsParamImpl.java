package cmdline.impl.langs;

import cmdline.api.CommonParams;
import cmdline.impl.common.CommonParamsImpl;
import cmdline.impl.common.SizeUnit;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.tika.mime.MediaType;
import utils.PropertiesConfigExplorer;
import utils.PropertiesConfigLoader;
import utils.WSSplitter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public final class LangsParamImpl implements CommonParams {
    @ParametersDelegate
    private final CommonParamsImpl commonParams = new CommonParamsImpl();

    @Parameter(names = {"-l", "--langages"}, variableArity = true,
            description = "langages à prendre en compte pour le filtrage",
            validateWith = LangsValidator.class,
            converter = LangsValidator.class,
            splitter = WSSplitter.class)
    private List<List<MediaType>> mediaTypes;

    private static List<MediaType> concat(List<MediaType> l1, List<MediaType> l2) {
        l1.addAll(l2);
        return l1;
    }

    public List<MediaType> getMediaTypes(){
        if (mediaTypes != null) {
            List<MediaType> mediaTypes = this.mediaTypes.stream().reduce(new ArrayList<>(), LangsParamImpl::concat);
            return mediaTypes;
        } else
            return chargementMediaTypes();
    }

    public List<Path> getPaths() {
        return commonParams.getPaths();
    }

    public SizeUnit getSizeUnit() {
        return commonParams.getSizeUnit();
    }

    public boolean isHelp() {
        return commonParams.isHelp();
    }

    private List<MediaType> chargementMediaTypes() {
        List<MediaType> mediaTypes = new ArrayList<>();
        PropertiesConfigLoader configLoader = PropertiesConfigLoader.getInstance();
        try {
            configLoader.setFileName(LangsValidator.langageSupported);
            Configuration config = configLoader.getConfiguration();
            PropertiesConfigExplorer explorer = PropertiesConfigExplorer.getInstance().setConfig(config);
            List<MediaType> list = new ArrayList<>();
            for (String s : explorer.getValues()) {
                MediaType parse = MediaType.parse(s);
                list.add(parse);
            }
            mediaTypes.addAll(list);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return mediaTypes;
    }
}

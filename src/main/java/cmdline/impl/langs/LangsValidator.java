package cmdline.impl.langs;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.tika.mime.MediaType;
import utils.PropertiesConfigExplorer;
import utils.PropertiesConfigLoader;

import java.util.ArrayList;
import java.util.List;

public final class LangsValidator implements IStringConverter<List<MediaType>>, IParameterValidator {

    public static final String langageSupported = "contenttypes.properties";
    private static Configuration configuration;

    static {
        try {
            configuration = chargerConfiguration();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static List<String> mediaTypeGetKeys() {
        PropertiesConfigExplorer instance = PropertiesConfigExplorer.getInstance();
        instance.setConfig(configuration);
        return instance.getKeys();
    }

    private static Configuration chargerConfiguration() throws ConfigurationException {
        PropertiesConfigLoader configLoader = PropertiesConfigLoader.getInstance();
        return configLoader.setFileName(langageSupported).getConfiguration();
    }

    @Override
    public void validate(String name, String value) {
        var keys = configuration.getKeys();
        String key;
        boolean exist = false;
        if (keys.hasNext()) {
            do {
                key = keys.next();
                if (key.equalsIgnoreCase(value)) {
                    exist = true;
                    break;
                }
            } while (keys.hasNext());
        }
        if (!exist)
            throw new ParameterException("le Langage " + value +
                    " n'est pas pris en charge par le programme, vouliez le rajouter dans ==>src/main/resources/contenttypes.properties ");
    }


    @Override
    public List<MediaType> convert(String value) {
        List<MediaType> mediaTypes = new ArrayList<>();
        String[] types = configuration.getStringArray(value.toUpperCase());
        if (!(types.length == 0)) {
            for (String t : types) {
                mediaTypes.add(MediaType.parse(t));
            }
        } else {
            String type = configuration.getString(value.toUpperCase());
            mediaTypes.add(MediaType.parse(type));
        }
        return mediaTypes;
    }
}

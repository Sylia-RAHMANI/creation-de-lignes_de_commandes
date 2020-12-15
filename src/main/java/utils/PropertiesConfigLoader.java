package utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * Utility to loads properties from a config. file.
 */
public final class PropertiesConfigLoader {

    private static final PropertiesConfigLoader INSTANCE = new PropertiesConfigLoader();
    public static final char LIST_DELIMITER = ',';
    private String fileName;
    private Configuration configuration;

    public static PropertiesConfigLoader getInstance(){
        return INSTANCE;
    }

    public PropertiesConfigLoader setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public Configuration getConfiguration() throws ConfigurationException {
       if (this.configuration == null)
           this.configuration = loadConfig();
        return this.configuration;
    }

    public Configuration reloadConfiguration() throws ConfigurationException {
        this.configuration = loadConfig();
        return this.configuration;
    }

    public static PropertiesConfigLoader newInstance(){
        return new PropertiesConfigLoader();
    }

    private Configuration loadConfig() throws ConfigurationException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.fileBased()
                                .setFileName(this.fileName)
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(LIST_DELIMITER)));
        return builder.getConfiguration();
    }
}

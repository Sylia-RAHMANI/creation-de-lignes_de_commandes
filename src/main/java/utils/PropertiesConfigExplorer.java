package utils;

import org.apache.commons.configuration2.Configuration;

import java.util.*;

/**
 * Utility to explore loaded properties.
 */
public final class PropertiesConfigExplorer {

    private static final PropertiesConfigExplorer INSTANCE = new PropertiesConfigExplorer();
    private Configuration config;
    private final Map<String, String> reverseLangMTypeMap;

    private PropertiesConfigExplorer(){
        reverseLangMTypeMap = new HashMap<>();
    }

    public static PropertiesConfigExplorer getInstance(){return INSTANCE;}

    public PropertiesConfigExplorer setConfig(Configuration config){
        this.config = config;
        initReverseLangMediaType();
        return this;
    }

    public List<String> getKeys(){
        List<String> mtKeys = new ArrayList<>();
        Iterator<String> keys = config.getKeys();
        keys.forEachRemaining(mtKeys::add);
        return mtKeys;
    }

    public List<String> getValues() {
        List<String> values = new ArrayList<>();
        Iterator<String> iter = config.getKeys();
        iter.forEachRemaining(k -> {
            String[] types = config.getStringArray(k);
            if (types.length != 0) {
                values.addAll(Arrays.asList(types));
            } else {
                String type = config.getString(k);
                values.add(type);
            }
        });
        return values;
    }

    public String getLang(String mediaType) {
        return reverseLangMTypeMap.getOrDefault(mediaType, mediaType);
    }

    private void initReverseLangMediaType() {
        config.getKeys().forEachRemaining(l -> {
            String[] mtypes = config.getStringArray(l);
            if (mtypes.length != 0) {
                Arrays.stream(mtypes).forEach(t -> reverseLangMTypeMap.put(t, l));
            } else {
                String mtype = config.getString(l);
                reverseLangMTypeMap.put(mtype, l);
            }
        });
    }
}

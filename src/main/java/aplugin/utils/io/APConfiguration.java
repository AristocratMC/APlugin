package aplugin.utils.io;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Created by Aristocrat on 12/9/2017.
 */
public abstract class APConfiguration {
    protected YamlConfiguration configYaml;

    public YamlConfiguration getYaml() {
        return configYaml;
    }
}

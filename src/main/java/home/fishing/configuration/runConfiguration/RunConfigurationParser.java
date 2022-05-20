package home.fishing.configuration.runConfiguration;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class RunConfigurationParser {

    public RunConfiguration loadRunConfiguration() {
        ClassLoader classLoader = getClass().getClassLoader();
        File configurationFile =  new File(Objects.requireNonNull(
                classLoader.getResource("configuration/applicationConfig.yaml")).getFile());
        final Yaml yaml = new Yaml(new Constructor(RunConfiguration.class));
        try {
            return yaml.load(new FileInputStream(configurationFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Ошибка при парсинге конфигурационного файла: applicationConfig.yaml");
    }
}

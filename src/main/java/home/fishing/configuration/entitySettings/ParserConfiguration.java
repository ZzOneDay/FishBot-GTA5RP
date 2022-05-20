package home.fishing.configuration.entitySettings;

import home.fishing.configuration.runConfiguration.RunConfiguration;
import home.fishing.configuration.entitySettings.colorSettings.ColorSettings;
import home.fishing.configuration.entitySettings.coreSettings.CoreSettings;
import home.fishing.configuration.entitySettings.mouseSettings.MouseSettings;
import home.fishing.configuration.entitySettings.profileSettings.ProfileSettings;
import home.fishing.configuration.entitySettings.targetSettings.TargetSettings;
import home.fishing.configuration.entitySettings.trajectorySettings.TrajectorySettings;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class ParserConfiguration {

    public ColorSettings getColorConfiguration(RunConfiguration configuration) {
        final SettingType type = SettingType.COLOR;
        final String profile = configuration.getProfile();
        return parsingConfigurationFile(ColorSettings.class, profile, type);
    }

    public CoreSettings getCoreConfiguration(RunConfiguration configuration) {
        final SettingType type = SettingType.CORE;
        final String profile = configuration.getProfile();
        return parsingConfigurationFile(CoreSettings.class, profile, type);
    }

    public MouseSettings getMouseConfiguration(RunConfiguration configuration) {
        final SettingType type = SettingType.MOUSE;
        final String profile = configuration.getProfile();
        return parsingConfigurationFile(MouseSettings.class, profile, type);
    }

    public ProfileSettings getProfileConfiguration(RunConfiguration configuration) {
        final SettingType type = SettingType.PROFILE;
        final String profile = configuration.getProfile();
        return parsingConfigurationFile(ProfileSettings.class, profile, type);
    }

    public TargetSettings getTargetConfiguration(RunConfiguration configuration) {
        final SettingType type = SettingType.TARGET;
        final String profile = configuration.getProfile();
        return parsingConfigurationFile(TargetSettings.class, profile, type);
    }

    public TrajectorySettings getTrajectoryConfiguration(RunConfiguration configuration) {
        final SettingType type = SettingType.TRAJECTORY;
        final String profile = configuration.getProfile();
        return parsingConfigurationFile(TrajectorySettings.class, profile, type);
    }

    private <T> T parsingConfigurationFile (Class<T> configurationClass, String profile, SettingType type) {
        final File configurationFile = loadFileConfiguration(profile, type);
        final Yaml yaml = new Yaml(new Constructor(configurationClass));
        try {
            return yaml.load(new FileInputStream(configurationFile));
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка при парсинге конфигурационного файла:" + type + profile);
            e.printStackTrace();
        }
        return null;
    }


    private File loadFileConfiguration(String profileName, SettingType settingType) {
        String path = "configuration/" +profileName + "/" + settingType + profileName + ".yaml";
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
    }
}

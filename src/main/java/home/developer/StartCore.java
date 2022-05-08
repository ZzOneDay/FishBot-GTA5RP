package home.developer;

import home.developer.configuration.CoreConfiguration;
import home.developer.configuration.runConfiguration.RunConfiguration;
import home.developer.configuration.runConfiguration.RunConfigurationParser;
import home.developer.configuration.entitySettings.ParserConfiguration;
import home.developer.configuration.entitySettings.colorSettings.ColorSettings;
import home.developer.configuration.entitySettings.coreSettings.CoreSettings;
import home.developer.configuration.entitySettings.mouseSettings.MouseSettings;
import home.developer.configuration.entitySettings.profileSettings.ProfileSettings;
import home.developer.configuration.entitySettings.targetSettings.TargetSettings;
import home.developer.configuration.entitySettings.trajectorySettings.TrajectorySettings;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Запуск процесса ловли рыбы:
 * Загрузка конфигураций (COLOR,CORE,TRAJECTORY,PROFILE ...)
 * Создание Context
 * Запуск непрерывного процесса ловли рыбы
 */
public class StartCore {
    public boolean run() {
        System.out.println("Загрузка конфигураций:");
        RunConfiguration runConfiguration = new RunConfigurationParser().loadRunConfiguration();
        System.out.println("Профиль загружен успешно:");
        System.out.println("Профиль: название" + runConfiguration.getProfile());
        System.out.println("Профиль: включить звук? " + runConfiguration.getProfile());
        System.out.println("Профиль: включить интеграцию с Discord? " + runConfiguration.getProfile());

        ParserConfiguration parserConfiguration = new ParserConfiguration();
        final ColorSettings colorSettings = parserConfiguration.getColorConfiguration(runConfiguration);
        boolean colorSettingIsReady = colorSettings != null;
        System.out.println("Конфигурация цветов : загружена успешно -> " + colorSettingIsReady);

        CoreSettings coreSettings = parserConfiguration.getCoreConfiguration(runConfiguration);
        boolean coreSettingsIsReady = coreSettings != null;
        System.out.println("Конфигурация ядра процесса : загружена успешно -> " + coreSettingsIsReady);

        MouseSettings mouseSettings = parserConfiguration.getMouseConfiguration(runConfiguration);
        boolean mouseSettingsIsReady = mouseSettings != null;
        System.out.println("Конфигурация работы курсора : загружена успешно -> " + mouseSettingsIsReady);

        ProfileSettings profileSettings = parserConfiguration.getProfileConfiguration(runConfiguration);
        boolean profileSettingsIsReady = profileSettings != null;
        System.out.println("Конфигурация описания профиля : загружена успешно -> " + profileSettingsIsReady);

        TargetSettings targetSettings = parserConfiguration.getTargetConfiguration(runConfiguration);
        boolean targetSettingsIsReady = targetSettings != null;
        System.out.println("Конфигурация координат целей : загружена успешно -> " + targetSettingsIsReady);

        TrajectorySettings trajectorySettings = parserConfiguration.getTrajectoryConfiguration(runConfiguration);
        boolean trajectorySettingsIsReady = trajectorySettings != null;
        System.out.println("Конфигурация траекторий движения : загружена успешно -> " + trajectorySettingsIsReady);

        boolean readyForStart = colorSettingIsReady && coreSettingsIsReady
                && mouseSettingsIsReady && profileSettingsIsReady
                && targetSettingsIsReady && trajectorySettingsIsReady;

        if (readyForStart) {
            //run process
            ApplicationContext context = new AnnotationConfigApplicationContext(CoreConfiguration.class);
            return true;
        } else {
            return false;
        }
    }
}

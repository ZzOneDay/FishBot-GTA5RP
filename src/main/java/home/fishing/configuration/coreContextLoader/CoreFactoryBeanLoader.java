package home.fishing.configuration.coreContextLoader;

import home.fishing.configuration.entitySettings.ParserConfiguration;
import home.fishing.configuration.entitySettings.colorSettings.ColorSettings;
import home.fishing.configuration.entitySettings.coreSettings.CoreSettings;
import home.fishing.configuration.entitySettings.mouseSettings.MouseSettings;
import home.fishing.configuration.entitySettings.profileSettings.ProfileSettings;
import home.fishing.configuration.entitySettings.targetSettings.TargetSettings;
import home.fishing.configuration.entitySettings.trajectorySettings.TrajectorySettings;
import home.fishing.configuration.runConfiguration.RunConfiguration;
import home.fishing.configuration.runConfiguration.RunConfigurationParser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Service;

@Service
public class CoreFactoryBeanLoader implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("Context: Создание контекста: ");
        RunConfiguration runConfiguration = new RunConfigurationParser().loadRunConfiguration();
        configurableListableBeanFactory.registerSingleton("runConfiguration", runConfiguration);
        System.out.println("Context: Регистрация bean runConfiguration в context прошла успешно");

        ParserConfiguration parserConfiguration = new ParserConfiguration();

        final ColorSettings colorSettings = parserConfiguration.getColorConfiguration(runConfiguration);
        configurableListableBeanFactory.registerSingleton("colorSettings", colorSettings);
        System.out.println("Context: Регистрация bean colorSettings в context прошла успешно");

        final CoreSettings coreSettings = parserConfiguration.getCoreConfiguration(runConfiguration);
        configurableListableBeanFactory.registerSingleton("coreSettings", coreSettings);
        System.out.println("Context: Регистрация bean coreSettings в context прошла успешно");

        MouseSettings mouseSettings = parserConfiguration.getMouseConfiguration(runConfiguration);
        configurableListableBeanFactory.registerSingleton("mouseSettings", mouseSettings);
        System.out.println("Context: Регистрация bean mouseSettings в context прошла успешно");

        ProfileSettings profileSettings = parserConfiguration.getProfileConfiguration(runConfiguration);
        configurableListableBeanFactory.registerSingleton("profileSettings", profileSettings);
        System.out.println("Context: Регистрация bean profileSettings в context прошла успешно");

        TargetSettings targetSettings = parserConfiguration.getTargetConfiguration(runConfiguration);
        configurableListableBeanFactory.registerSingleton("targetSettings", targetSettings);
        System.out.println("Context: Регистрация bean targetSettings в context прошла успешно");

        TrajectorySettings trajectorySettings = parserConfiguration.getTrajectoryConfiguration(runConfiguration);
        configurableListableBeanFactory.registerSingleton("trajectorySettings", trajectorySettings);
        System.out.println("Context: Регистрация bean trajectorySettings в context прошла успешно");

        System.out.println("Context: Регистрация beans конфигурации прошла успешно");
    }
}

package home.fishing.configuration.entitySettings.coreSettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoreSettings {
    private StartProcessSettings startProcessSettings;
    private UpdateScreenSettings updateScreenSettings;
}

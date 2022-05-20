package home.fishing.configuration.entitySettings.targetSettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TargetSettings {
    private MouseTargets mouseTargets;
    private ProcessTargets processTargets;
    private InventoryTargets inventoryTargets;
    private CaptchaTargets captchaTargets;
}

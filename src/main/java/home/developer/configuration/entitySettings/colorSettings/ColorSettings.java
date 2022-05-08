package home.developer.configuration.entitySettings.colorSettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ColorSettings {
    private MouseColors mouseColors;
    private ProcessColors processColors;
    private InventoryColors inventoryColors;
    private CaptchaColors captchaColors;
}

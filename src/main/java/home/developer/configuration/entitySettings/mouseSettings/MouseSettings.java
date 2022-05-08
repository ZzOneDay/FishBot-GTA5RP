package home.developer.configuration.entitySettings.mouseSettings;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MouseSettings {
    private MouseDelaySettings push;
    private MouseDelaySettings start;
    private MouseDelaySettings moving;
}

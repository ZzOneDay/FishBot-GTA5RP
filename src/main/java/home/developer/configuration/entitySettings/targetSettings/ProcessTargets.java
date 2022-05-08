package home.developer.configuration.entitySettings.targetSettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProcessTargets {
    private TargetEntity success;
    private TargetEntity fail;
}

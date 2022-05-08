package home.developer.configuration.entitySettings.trajectorySettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrajectorySettings {
    private TrajectoryCenter center;
    private TrajectoryDeltaFunction deltaFunction;
    private TrajectoryDeltaValueY deltaValueY;
}

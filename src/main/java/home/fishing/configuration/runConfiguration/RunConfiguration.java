package home.fishing.configuration.runConfiguration;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RunConfiguration {
    private String profile;
    private boolean audio;
    private boolean discord;
}

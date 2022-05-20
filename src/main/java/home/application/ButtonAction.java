package home.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ButtonAction implements EventHandler<ActionEvent> {
    private final MainFrame mainFrame;


    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == mainFrame.getButtonStart()) {
            System.out.println("Start");
            AlertBox.display("Внимание", "Окно пока еще в разработке");
        }

        if (event.getSource() == mainFrame.getButtonStop()) {
            System.out.println("Stop");
            AlertBox.display("Внимание", "Окно пока еще в разработке");
        }

        if (event.getSource() == mainFrame.getButtonSettings()) {
            System.out.println("Settings");
            SettingsFrame settingsFrame = new SettingsFrame();



            mainFrame.getMainStage().setScene(settingsFrame.getSettingScene(mainFrame));
        }

        if (event.getSource() == mainFrame.getButtonActivation()) {
            System.out.println("Activate");
            AlertBox.display("Внимание", "Окно пока еще в разработке");
        }
    }
}

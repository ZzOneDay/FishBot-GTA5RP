package home.application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class SettingsFrame {

    Scene getSettingScene(MainFrame mainFrame) {
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(new ImageView("view/background2.jpg"));
        Scene scene = new Scene(stackPane, 600,400);
        Button button = new Button("Вернуться");
        button.setOnAction(e -> mainFrame.getMainStage().setScene(mainFrame.getMainScene()));


        stackPane.getChildren().add(button);


        System.out.println("LOADING SETTINGS PAGE");







        return scene;
    }
}

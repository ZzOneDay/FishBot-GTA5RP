package home.application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class MainFrame extends Application {
    private Stage mainStage;
    private Scene mainScene;

    private Button buttonStart;
    private Button buttonStop;
    private Button buttonSettings;
    private Button buttonActivation;

    private final SettingsFrame settingsFrame = new SettingsFrame();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        ButtonAction buttonAction = new ButtonAction(this);

        primaryStage.setTitle("FishBotGTA5RP");
        StackPane stackPane = new StackPane();

        //BACKGROUND
        stackPane.getChildren().add(new ImageView("view/background2.jpg"));

        // START
        buttonStart = new Button("START");
        buttonStart.setPrefSize(160,40);
        buttonStart.setOnAction(buttonAction);

        // STOP
        buttonStop = new Button("STOP");
        buttonStop.setPrefSize(160,40);
        buttonStop.setOnAction(buttonAction);

        // SETTINGS
        buttonSettings = new Button("SETTINGS");
        buttonSettings.setPrefSize(160,40);
        buttonSettings.setOnAction(buttonAction);

        // ACTIVATE
        buttonActivation = new Button("ACTIVATION");
        buttonActivation.setPrefSize(160,40);
        buttonActivation.setOnAction(buttonAction);


        VBox vBox = new VBox(20);
        vBox.getChildren().add(buttonStart);
        vBox.getChildren().add(buttonStop);
        vBox.getChildren().add(buttonSettings);
        vBox.getChildren().add(buttonActivation);

        vBox.setAlignment(Pos.CENTER);

        stackPane.getChildren().add(vBox);



        // SETTING FRAME
        mainScene = new Scene(stackPane, 600, 400);
        primaryStage.setScene(mainScene);
        primaryStage.show();



    }
}

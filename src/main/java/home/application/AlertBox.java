package home.application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String message) {
        Stage window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);

        Button button = new Button("Cancel");
        button.setOnAction(e -> window.close());

        Label label = new Label(message);

        VBox layer = new VBox(50);
        layer.getChildren().add(label);
        layer.getChildren().add(button);
        layer.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layer, 400, 200);
        window.setScene(scene);
        window.show();
    }
}

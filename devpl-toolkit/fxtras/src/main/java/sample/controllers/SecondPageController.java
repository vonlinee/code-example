package sample.controllers;

import io.devpl.fxtras.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class SecondPageController {

    @FXML private Button button;

    @FXML
    public void initialize() {
        button.setOnMouseClicked(event -> {
            try {
                FXRouter.goTo("first");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

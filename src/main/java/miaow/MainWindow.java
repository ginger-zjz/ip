package miaow;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import miaow.gui.DialogBox;

import java.io.InputStream;
import java.util.Objects;

/**
 * Mainwindow
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Miaow miaow;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Miaowuser.png"));
    private Image miaowImage = new Image(this.getClass().getResourceAsStream("/images/Miaowoiia.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setMiaow(Miaow miaow) {
        this.miaow = miaow;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        System.out.println("HANDLE USER INPUT CALLED");

        String input = userInput.getText().trim();
        System.out.println(input);

        if (input.isEmpty()) {
            return;
        }

        String response = miaow.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMiaowDialog(response, miaowImage)
        );

        userInput.clear();

        if (miaow.isExitCommand(input)) {
            Platform.exit();
        }
    }

    private static Image loadImage(String resourcePath) {
        InputStream imageStream = Objects.requireNonNull(
                MainWindow.class.getResourceAsStream(resourcePath),
                "Missing image resource: " + resourcePath);
        return new Image(imageStream);
    }
}
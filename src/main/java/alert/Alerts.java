package alert;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {

    public void invokeEmptyFieldsAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Not all fields are filled");
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.show();
    }

    public void invokeExitAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Exit program");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            Platform.exit();
            System.exit(0);
        } else if (result.get() == ButtonType.NO) {
            alert.close();
        }
    }
}

module atm_interface {
    requires javafx.controls;
    requires javafx.fxml;

    opens atm_interface to javafx.fxml;
    exports atm_interface;
}

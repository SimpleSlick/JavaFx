module com.gradecalc {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.gradecalc to javafx.fxml;
    exports com.gradecalc;
}

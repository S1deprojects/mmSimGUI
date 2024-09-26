module com.example.mmsimgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.mmsimgui to javafx.fxml;
    exports com.example.mmsimgui;
}
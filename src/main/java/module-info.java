module com.parinherm.natterfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires vosk;
    requires kotlinx.coroutines.core.jvm;
    requires java.desktop;
    requires kotlin.stdlib.jdk7;

    opens com.parinherm.natterfx to javafx.fxml;
    exports com.parinherm.natterfx;
}
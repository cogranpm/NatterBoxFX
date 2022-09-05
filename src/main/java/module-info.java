module com.parinherm.natterfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.materialdesign2;
    requires vosk;
    requires kotlinx.coroutines.core.jvm;
    requires java.desktop;
    requires kotlinx.serialization.json;
    requires kotlin.stdlib.jdk7;
    requires kotlinx.coroutines.javafx;
    requires exposed.core;
    requires java.prefs;

    opens com.parinherm.natterfx to javafx.fxml;
    exports com.parinherm.natterfx;
}
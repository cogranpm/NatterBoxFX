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
    requires exposed.dao;
    requires com.h2database;
    requires kotlin.reflect;
    requires org.mariadb.jdbc;
    requires org.slf4j;
    requires exposed.java.time;

    opens com.parinherm.natterfx to javafx.fxml;
    opens com.parinherm.natterfx.database to kotlin.reflect;
    exports com.parinherm.natterfx;
    exports com.parinherm.natterfx.database;
}
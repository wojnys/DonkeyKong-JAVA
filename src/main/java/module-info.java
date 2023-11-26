module lab01 {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires com.google.gson;
    opens lab to javafx.fxml, com.google.gson;
    exports lab;
}
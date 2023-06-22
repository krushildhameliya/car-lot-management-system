module com.example.midtermexam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.midtermexam to javafx.fxml;
    exports com.example.midtermexam;
}
module com.example.kissapeli {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kissapeli to javafx.fxml;
    exports com.example.kissapeli;
}
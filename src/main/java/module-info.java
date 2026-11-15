module com.mycompany.proyecto_ud2_dnr {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.proyecto_ud2_dnr to javafx.fxml;
    opens com.mycompany.proyecto_ud2_dnr.Controlador to javafx.fxml;
    exports com.mycompany.proyecto_ud2_dnr;
}

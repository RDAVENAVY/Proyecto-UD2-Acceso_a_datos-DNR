package com.mycompany.proyecto_ud2_dnr;

import com.mycompany.proyecto_ud2_dnr.Modelo.Trabajador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Stage escenarioPrincipal;
    private static Trabajador trabajadorActivo;   // Mantiene el usuario logueado
    private static Trabajador trabajadorAEditar;  // Mantiene el empleado seleccionado para editar

    @Override
    public void start(Stage stage) throws IOException {
        escenarioPrincipal = stage;
        escenarioPrincipal.setTitle("Gestión de Almacén - UD2");
        cambiarVista("Login");
        escenarioPrincipal.show();
    }

    public static void cambiarVista(String nombreFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                App.class.getResource("/com/mycompany/proyecto_ud2_dnr/Vista/" + nombreFxml + ".fxml")
            );
            Parent root = loader.load();
            Scene scene = new Scene(root);
            escenarioPrincipal.setScene(scene);
            escenarioPrincipal.sizeToScene();
            escenarioPrincipal.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Trabajador getTrabajadorActivo() { return trabajadorActivo; }
    public static void setTrabajadorActivo(Trabajador t) { trabajadorActivo = t; }

    public static Trabajador getTrabajadorAEditar() { return trabajadorAEditar; }
    public static void setTrabajadorAEditar(Trabajador t) { trabajadorAEditar = t; }

    public static void main(String[] args) {
        launch(args);
    }
}

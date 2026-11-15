package com.mycompany.proyecto_ud2_dnr.Controlador;

import com.mycompany.proyecto_ud2_dnr.App;
import com.mycompany.proyecto_ud2_dnr.Modelo.Trabajador;
import com.mycompany.proyecto_ud2_dnr.Modelo.TrabajadorDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InicioController {

    @FXML private TextField txtUsuario;
    @FXML private TextField txtContrasena;
    @FXML private Button btnCancelar;
    @FXML private Button btnIniciarSesion;

    private final TrabajadorDAO trabajadorDAO = new TrabajadorDAO();

    @FXML
    public void initialize() {
        btnCancelar.setOnAction(this::limpiarCampos);
        btnIniciarSesion.setOnAction(this::validarLogin);

        // Si venimos de pulsar "Cambiar usuario", mostramos los datos de la sesión anterior
        if (App.getTrabajadorActivo() != null) {
            txtUsuario.setText(String.valueOf(App.getTrabajadorActivo().getIdTrabajador()));
            if (App.getTrabajadorActivo().getFNacimiento() != null) {
                txtContrasena.setText(App.getTrabajadorActivo().getFNacimiento().toString());
            }
        }
    }

    private void limpiarCampos(ActionEvent event) {
        txtUsuario.clear();
        txtContrasena.clear();
    }

    @FXML
    public void validarLogin(ActionEvent event) {
        try {
            int idTrabajador = Integer.parseInt(txtUsuario.getText().trim());
            LocalDate fechaNacimiento = LocalDate.parse(txtContrasena.getText().trim()); // Formato AAAA-MM-DD

            Trabajador t = trabajadorDAO.validarCredenciales(idTrabajador, fechaNacimiento);

            if (t != null) {
                App.setTrabajadorActivo(t);
                App.cambiarVista("Listado_empleado");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Acceso denegado", "ID de trabajador o fecha de nacimiento incorrectos.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Formato incorrecto", "El usuario debe ser el ID numérico del trabajador.");
        } catch (DateTimeParseException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Formato incorrecto", "La contraseña debe ser la fecha de nacimiento en formato AAAA-MM-DD.");
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Base de Datos", e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
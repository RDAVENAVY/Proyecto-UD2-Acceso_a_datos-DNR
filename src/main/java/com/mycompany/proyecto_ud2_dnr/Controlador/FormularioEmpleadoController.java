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

public class FormularioEmpleadoController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtRol;
    @FXML private TextField txtDni;
    @FXML private TextField txtResidencia;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtTurno;
    @FXML private TextField txtFNacimiento;
    @FXML private TextField txtFInsercion;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;

    @FXML private Button btnCancelar;
    @FXML private Button btnGuardarRegistro;
    @FXML private Button btnVolverListado;

    private final TrabajadorDAO trabajadorDAO = new TrabajadorDAO();

    @FXML
    public void initialize() {
        btnCancelar.setOnAction(this::limpiarFormulario);
        btnGuardarRegistro.setOnAction(this::guardarEmpleado);
        btnVolverListado.setOnAction(this::volverAlListado);

        // Si se pulsó "Modificar" en una tarjeta, rellenamos los campos con sus datos
        Trabajador editando = App.getTrabajadorAEditar();
        if (editando != null) {
            txtNombre.setText(editando.getNombre());
            txtApellidos.setText(editando.getApellidos());
            txtDni.setText(editando.getDni());
            txtRol.setText(editando.getRol());
            txtTurno.setText(editando.getTurno());
            txtResidencia.setText(editando.getResidencia());
            txtTelefono.setText(editando.getTelefono());
            txtCorreo.setText(editando.getCorreo());
            if (editando.getFNacimiento() != null) txtFNacimiento.setText(editando.getFNacimiento().toString());
            if (editando.getFInsercion() != null) txtFInsercion.setText(editando.getFInsercion().toString());
        } else {
            txtFInsercion.setText(LocalDate.now().toString());
        }
    }

    private void limpiarFormulario(ActionEvent event) {
        txtNombre.clear(); txtRol.clear(); txtDni.clear();
        txtResidencia.clear(); txtApellidos.clear(); txtTurno.clear();
        txtFNacimiento.clear(); txtFInsercion.clear();
        txtTelefono.clear(); txtCorreo.clear();
    }

    private void guardarEmpleado(ActionEvent event) {
        if (txtNombre.getText().isBlank() || txtApellidos.getText().isBlank() || txtDni.getText().isBlank()
                || txtRol.getText().isBlank() || txtTurno.getText().isBlank()
                || txtFNacimiento.getText().isBlank() || txtFInsercion.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Rellena todos los campos obligatorios antes de guardar.");
            return;
        }

        String turno = txtTurno.getText().trim();
        if (!turno.equals("Mañana") && !turno.equals("Tarde") && !turno.equals("Noche")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Turno inválido", "El turno debe ser exactamente: Mañana, Tarde o Noche.");
            return;
        }

        try {
            LocalDate fNac = LocalDate.parse(txtFNacimiento.getText().trim());
            LocalDate fIns = LocalDate.parse(txtFInsercion.getText().trim());

            Trabajador t = (App.getTrabajadorAEditar() != null) ? App.getTrabajadorAEditar() : new Trabajador();
            t.setNombre(txtNombre.getText().trim());
            t.setApellidos(txtApellidos.getText().trim());
            t.setDni(txtDni.getText().trim());
            t.setRol(txtRol.getText().trim());
            t.setTurno(turno);
            t.setFNacimiento(fNac);
            t.setFInsercion(fIns);
            t.setTelefono(txtTelefono.getText().trim());
            t.setResidencia(txtResidencia.getText().trim());
            t.setCorreo(txtCorreo.getText().trim());

            boolean exito = (App.getTrabajadorAEditar() != null)
                    ? trabajadorDAO.actualizarTrabajador(t)
                    : trabajadorDAO.insertarTrabajador(t);

            if (exito) {
                App.setTrabajadorAEditar(null);
                App.cambiarVista("Listado_empleado");
            }
        } catch (DateTimeParseException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Fecha incorrecta", "Usa el formato AAAA-MM-DD en las fechas.");
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al guardar en MySQL", e.getMessage());
        }
    }

    private void volverAlListado(ActionEvent event) {
        App.setTrabajadorAEditar(null);
        App.cambiarVista("Listado_empleado");
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

package com.mycompany.proyecto_ud2_dnr.Controlador;

import com.mycompany.proyecto_ud2_dnr.App;
import com.mycompany.proyecto_ud2_dnr.Modelo.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class ListadoEmpleadosController {

    @FXML private ComboBox<String> cmbOrdenarPor;
    @FXML private ComboBox<String> cmbFiltrarPor;
    @FXML private Button btnBorrarEmpleado;
    @FXML private Button btnNuevaSeccion;
    @FXML private Button btnCambiarUsuario;
    @FXML private VBox vboxListaEmpleados;
    @FXML private Button btnListadoPaquetes;
    @FXML private Button btnListadoSecciones;
    @FXML private Button btnListadoVehiculos;

    private final TrabajadorDAO trabajadorDAO = new TrabajadorDAO();
    private final PaqueteDAO paqueteDAO = new PaqueteDAO();
    private final SeccionDAO seccionDAO = new SeccionDAO();
    private final CarretillaDAO carretillaDAO = new CarretillaDAO();

    private boolean modoBorrado = false;
    private List<Trabajador> listaActual;

    @FXML
    public void initialize() {
        cmbOrdenarPor.getItems().addAll(
            "ID (Menor a Mayor)", "ID (Mayor a Menor)",
            "Nombre (A-Z)", "Nombre (Z-A)",
            "Turno", "Rol"
        );
        cmbOrdenarPor.setOnAction(this::aplicarOrden);

        cmbFiltrarPor.getItems().addAll(
            "Todos",
            "Rol: Administrador", "Rol: Operario", "Rol: Encargado",
            "Turno: Mañana", "Turno: Tarde", "Turno: Noche"
        );
        cmbFiltrarPor.setOnAction(this::aplicarFiltro);

        btnBorrarEmpleado.setOnAction(this::alternarModoBorrado);
        btnNuevaSeccion.setOnAction(this::irANuevoEmpleado);
        btnCambiarUsuario.setOnAction(this::cerrarSesion);

        // Botones inferiores: ejecutan las consultas SELECT del resto de entidades
        btnListadoPaquetes.setOnAction(this::mostrarResumenPaquetes);
        btnListadoSecciones.setOnAction(this::mostrarResumenSecciones);
        btnListadoVehiculos.setOnAction(this::mostrarResumenVehiculos);

        recargarTodosLosEmpleados();
    }

    private void recargarTodosLosEmpleados() {
        try {
            listaActual = trabajadorDAO.obtenerTodos();
            dibujarTarjetasEmpleados(listaActual);
        } catch (SQLException e) {
            mostrarInfo("Error de BD", e.getMessage());
        }
    }

    private void aplicarOrden(ActionEvent event) {
        String seleccion = cmbOrdenarPor.getValue();
        if (seleccion == null) return;
        try {
            listaActual = trabajadorDAO.obtenerOrdenados(seleccion);
            dibujarTarjetasEmpleados(listaActual);
        } catch (SQLException e) {
            mostrarInfo("Error al ordenar", e.getMessage());
        }
    }

    private void aplicarFiltro(ActionEvent event) {
        String seleccion = cmbFiltrarPor.getValue();
        if (seleccion == null) return;
        try {
            if (seleccion.equals("Todos")) {
                listaActual = trabajadorDAO.obtenerTodos();
            } else if (seleccion.startsWith("Rol: ")) {
                listaActual = trabajadorDAO.filtrarTrabajadores("rol", seleccion.replace("Rol: ", ""));
            } else if (seleccion.startsWith("Turno: ")) {
                listaActual = trabajadorDAO.filtrarTrabajadores("turno", seleccion.replace("Turno: ", ""));
            }
            dibujarTarjetasEmpleados(listaActual);
        } catch (SQLException e) {
            mostrarInfo("Error al filtrar", e.getMessage());
        }
    }

    private void alternarModoBorrado(ActionEvent event) {
        modoBorrado = !modoBorrado;
        btnBorrarEmpleado.setStyle(modoBorrado ? "-fx-background-color: #ffcccc; -fx-border-color: red;" : "");
        if (listaActual != null) {
            dibujarTarjetasEmpleados(listaActual);
        }
    }

    private void dibujarTarjetasEmpleados(List<Trabajador> empleados) {
        vboxListaEmpleados.getChildren().clear();

        for (Trabajador t : empleados) {
            HBox tarjeta = new HBox(15);
            tarjeta.setAlignment(Pos.CENTER_LEFT);
            tarjeta.setPadding(new Insets(12));
            tarjeta.setStyle("-fx-border-color: #333333; -fx-background-color: #f9f9f9;");

            VBox datosIzq = new VBox(5);
            datosIzq.getChildren().addAll(
                new Label("ID: " + t.getIdTrabajador() + " | " + t.getNombre() + " " + t.getApellidos()),
                new Label("DNI: " + t.getDni() + " | Rol: " + t.getRol() + " | Turno: " + t.getTurno())
            );

            VBox datosDer = new VBox(5);
            datosDer.getChildren().addAll(
                new Label("Teléfono: " + (t.getTelefono() != null ? t.getTelefono() : "-") + " | Correo: " + (t.getCorreo() != null ? t.getCorreo() : "-")),
                new Label("Residencia: " + (t.getResidencia() != null ? t.getResidencia() : "-") + " | Nacimiento: " + t.getFNacimiento())
            );

            Region espaciador = new Region();
            HBox.setHgrow(espaciador, Priority.ALWAYS);

            Button btnModificar = new Button("Modificar");
            btnModificar.setOnAction(e -> {
                App.setTrabajadorAEditar(t);
                App.cambiarVista("Formulario_empleado");
            });

            tarjeta.getChildren().addAll(datosIzq, datosDer, espaciador, btnModificar);

            // Si el modo borrado está activo, aparece la "X" en cada entrada
            if (modoBorrado) {
                Button btnEliminarX = new Button("X");
                btnEliminarX.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; -fx-font-weight: bold;");
                btnEliminarX.setOnAction(e -> {
                    try {
                        if (trabajadorDAO.borrarTrabajador(t.getIdTrabajador())) {
                            recargarTodosLosEmpleados();
                        }
                    } catch (SQLException ex) {
                        mostrarInfo("No se puede borrar", "El trabajador está asignado a un almacén o sección: " + ex.getMessage());
                    }
                });
                tarjeta.getChildren().add(btnEliminarX);
            }

            vboxListaEmpleados.getChildren().add(tarjeta);
        }
    }

    private void irANuevoEmpleado(ActionEvent event) {
        App.setTrabajadorAEditar(null);
        App.cambiarVista("Formulario_empleado");
    }

    private void cerrarSesion(ActionEvent event) {
        App.cambiarVista("Login");
    }

    private void mostrarResumenPaquetes(ActionEvent event) {
        try {
            List<Paquete> paquetes = paqueteDAO.obtenerTodos();
            StringBuilder sb = new StringBuilder("Total paquetes registrados: " + paquetes.size() + "\n\n");
            for (Paquete p : paquetes) {
                sb.append("• ID ").append(p.getIdPaquete()).append(" [Sección ").append(p.getIdSeccion())
                  .append("] - ").append(p.getContenidos()).append(" (").append(p.getPeso()).append(" kg, Estado: ")
                  .append(p.getEstado()).append(")\n");
            }
            mostrarInfo("Consulta de Paquetes (PaqueteDAO)", sb.toString());
        } catch (SQLException e) {
            mostrarInfo("Error", e.getMessage());
        }
    }

    private void mostrarResumenSecciones(ActionEvent event) {
        try {
            List<Seccion> secciones = seccionDAO.obtenerTodas();
            StringBuilder sb = new StringBuilder("Total secciones registradas: " + secciones.size() + "\n\n");
            for (Seccion s : secciones) {
                sb.append("• ID ").append(s.getIdSeccion()).append(": ").append(s.getNombre())
                  .append(" [Categoría: ").append(s.getNombreCategoria()).append("] - Dimensiones: ")
                  .append(s.getDimensiones()).append(" m²\n");
            }
            mostrarInfo("Consulta de Secciones (SeccionDAO)", sb.toString());
        } catch (SQLException e) {
            mostrarInfo("Error", e.getMessage());
        }
    }

    private void mostrarResumenVehiculos(ActionEvent event) {
        try {
            List<Carretilla> vehiculos = carretillaDAO.obtenerTodas();
            StringBuilder sb = new StringBuilder("Total carretillas registradas: " + vehiculos.size() + "\n\n");
            for (Carretilla c : vehiculos) {
                sb.append("• ID ").append(c.getIdVehiculo()).append(" - ").append(c.getMatricula())
                  .append(" (").append(c.getModelo()).append(", Estado: ").append(c.getEstado()).append(")\n");
            }
            mostrarInfo("Consulta de Vehículos (CarretillaDAO)", sb.toString());
        } catch (SQLException e) {
            mostrarInfo("Error", e.getMessage());
        }
    }

    private void mostrarInfo(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}

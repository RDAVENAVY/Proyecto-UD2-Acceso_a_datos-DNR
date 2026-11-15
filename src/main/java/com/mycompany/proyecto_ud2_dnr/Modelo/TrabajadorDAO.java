package com.mycompany.proyecto_ud2_dnr.Modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrabajadorDAO {

    // 1. CONSULTA PREPARADA: Validar Login (ID + Fecha de nacimiento)
    public Trabajador validarCredenciales(int idTrabajador, LocalDate fNacimiento) throws SQLException {
        String sql = "SELECT * FROM trabajador WHERE id_trabajador = ? AND f_nacimiento = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTrabajador);
            ps.setDate(2, Date.valueOf(fNacimiento));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearTrabajador(rs);
                }
            }
        }
        return null;
    }

    // 2. CONSULTA SIMPLE (Statement): Obtener todos los empleados
    public List<Trabajador> obtenerTodos() throws SQLException {
        List<Trabajador> lista = new ArrayList<>();
        String sql = "SELECT * FROM trabajador";

        try (Connection conn = ConexionBD.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearTrabajador(rs));
            }
        }
        return lista;
    }

    // 3. CONSULTA PREPARADA: Insertar nuevo empleado
    public boolean insertarTrabajador(Trabajador t) throws SQLException {
        String sql = "INSERT INTO trabajador (DNI, nombre, apellidos, rol, turno, F_insercion, f_nacimiento, telefono, residencia, correo) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getDni());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getApellidos());
            ps.setString(4, t.getRol());
            ps.setString(5, t.getTurno());
            ps.setDate(6, Date.valueOf(t.getFInsercion()));
            ps.setDate(7, Date.valueOf(t.getFNacimiento()));
            ps.setString(8, t.getTelefono());
            ps.setString(9, t.getResidencia());
            ps.setString(10, t.getCorreo());

            return ps.executeUpdate() > 0;
        }
    }

    // 4. CONSULTA PREPARADA: Modificar empleado existente
    public boolean actualizarTrabajador(Trabajador t) throws SQLException {
        String sql = "UPDATE trabajador SET DNI = ?, nombre = ?, apellidos = ?, rol = ?, turno = ?, "
                   + "F_insercion = ?, f_nacimiento = ?, telefono = ?, residencia = ?, correo = ? "
                   + "WHERE id_trabajador = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getDni());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getApellidos());
            ps.setString(4, t.getRol());
            ps.setString(5, t.getTurno());
            ps.setDate(6, Date.valueOf(t.getFInsercion()));
            ps.setDate(7, Date.valueOf(t.getFNacimiento()));
            ps.setString(8, t.getTelefono());
            ps.setString(9, t.getResidencia());
            ps.setString(10, t.getCorreo());
            ps.setInt(11, t.getIdTrabajador());

            return ps.executeUpdate() > 0;
        }
    }

    // 5. CONSULTA PREPARADA: Borrar empleado al pulsar la 'X'
    public boolean borrarTrabajador(int idTrabajador) throws SQLException {
        String sql = "DELETE FROM trabajador WHERE id_trabajador = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTrabajador);
            return ps.executeUpdate() > 0;
        }
    }

    // 6. CONSULTA PREPARADA: Filtrar por rol o turno
    public List<Trabajador> filtrarTrabajadores(String campo, String valor) throws SQLException {
        List<Trabajador> lista = new ArrayList<>();
        String columna = campo.equalsIgnoreCase("rol") ? "rol" : "turno";
        String sql = "SELECT * FROM trabajador WHERE " + columna + " = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, valor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearTrabajador(rs));
                }
            }
        }
        return lista;
    }

    // 7. CONSULTA SIMPLE: Ordenar segun la seleccion del ComboBox
    public List<Trabajador> obtenerOrdenados(String criterioComboBox) throws SQLException {
        String orderBy;
         switch (criterioComboBox) {
            case "ID (Menor a Mayor)":
                orderBy = "id_trabajador ASC";
                break;
            case "ID (Mayor a Menor)":
                orderBy = "id_trabajador DESC";
                break;
            case "Nombre (A-Z)":
                orderBy = "nombre ASC";
                break;
            case "Nombre (Z-A)":
                orderBy = "nombre DESC";
                break;
            case "Turno":
                orderBy = "turno ASC";
                break;
            case "Rol":
                orderBy = "rol ASC";
                break;
            default:
                orderBy = "id_trabajador ASC";
                break;
        }

        List<Trabajador> lista = new ArrayList<>();
        String sql = "SELECT * FROM trabajador ORDER BY " + orderBy;

        try (Connection conn = ConexionBD.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearTrabajador(rs));
            }
        }
        return lista;
    }

    private Trabajador mapearTrabajador(ResultSet rs) throws SQLException {
        Date fIns = rs.getDate("F_insercion");
        Date fNac = rs.getDate("f_nacimiento");

        return new Trabajador(
            rs.getInt("id_trabajador"),
            rs.getString("DNI"),
            rs.getString("nombre"),
            rs.getString("apellidos"),
            rs.getString("rol"),
            rs.getString("turno"),
            fIns != null ? fIns.toLocalDate() : null,
            fNac != null ? fNac.toLocalDate() : null,
            rs.getString("telefono"),
            rs.getString("residencia"),
            rs.getString("correo")
        );
    }
}

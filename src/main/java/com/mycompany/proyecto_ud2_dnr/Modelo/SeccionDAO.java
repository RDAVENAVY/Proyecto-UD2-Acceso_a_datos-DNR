package com.mycompany.proyecto_ud2_dnr.Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeccionDAO {

    // CONSULTA SIMPLE: Listar todas las secciones
    public List<Seccion> obtenerTodas() throws SQLException {
        List<Seccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM seccion";

        try (Connection conn = ConexionBD.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Seccion(
                    rs.getInt("id_seccion"),
                    rs.getInt("id_almacen"),
                    rs.getString("nombre_categoria"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getInt("dimensiones"),
                    rs.getInt("ocupacion"),
                    rs.getInt("capacidad"),
                    rs.getInt("capacidad_de_pale")
                ));
            }
        }
        return lista;
    }

    // CONSULTA PREPARADA CON JOIN: Trabajadores asignados a una seccion (tabla opera)
    public List<String> obtenerOperariosDeSeccion(int idSeccion) throws SQLException {
        List<String> operarios = new ArrayList<>();
        String sql = "SELECT t.id_trabajador, t.nombre, t.apellidos FROM trabajador t "
                   + "INNER JOIN opera o ON t.id_trabajador = o.id_trabajador "
                   + "WHERE o.id_seccion = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSeccion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    operarios.add(rs.getInt("id_trabajador") + " - " + rs.getString("nombre") + " " + rs.getString("apellidos"));
                }
            }
        }
        return operarios;
    }

    // TRANSACCIÓN: Inserta una seccion y actualiza la ocupación del almacen de forma indivisible
    public boolean registrarSeccionConTransaccion(int idAlmacen, String categoria, String nombre,
                                                  String descripcion, int dimensiones, int capacidad,
                                                  int capacidadPale) throws SQLException {
        String sqlInsertSeccion = "INSERT INTO seccion (id_almacen, nombre_categoria, nombre, descripcion, dimensiones, ocupacion, capacidad, capacidad_de_pale) "
                                + "VALUES (?, ?, ?, ?, ?, 0, ?, ?)";
        String sqlUpdateAlmacen = "UPDATE almacen SET ocupacion = ocupacion + ? WHERE id_almacen = ?";

        Connection conn = null;
        try {
            conn = ConexionBD.getConexion();
            conn.setAutoCommit(false);

            try (PreparedStatement psInsert = conn.prepareStatement(sqlInsertSeccion)) {
                psInsert.setInt(1, idAlmacen);
                psInsert.setString(2, categoria);
                psInsert.setString(3, nombre);
                psInsert.setString(4, descripcion);
                psInsert.setInt(5, dimensiones);
                psInsert.setInt(6, capacidad);
                psInsert.setInt(7, capacidadPale);
                psInsert.executeUpdate();
            }

            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateAlmacen)) {
                psUpdate.setInt(1, dimensiones);
                psUpdate.setInt(2, idAlmacen);
                int filasAfectadas = psUpdate.executeUpdate();
                if (filasAfectadas == 0) {
                    throw new SQLException("No se encontró el almacén indicado para actualizar su ocupación.");
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException exRollback) {
                    exRollback.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // PROCEDIMIENTO ALMACENADO: Llama a sp_eliminar_seccion en MySQL
    public void eliminarSeccionConProcedimiento(int idSeccion) throws SQLException {
        String sqlCall = "{call sp_eliminar_seccion(?)}";

        try (Connection conn = ConexionBD.getConexion();
             CallableStatement cs = conn.prepareCall(sqlCall)) {

            cs.setInt(1, idSeccion);
            cs.execute();
        }
    }
}

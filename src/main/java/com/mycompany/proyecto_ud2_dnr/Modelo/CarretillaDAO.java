package com.mycompany.proyecto_ud2_dnr.Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarretillaDAO {

    // CONSULTA SIMPLE: Listar todos los vehiculos/carretillas
    public List<Carretilla> obtenerTodas() throws SQLException {
        List<Carretilla> lista = new ArrayList<>();
        String sql = "SELECT * FROM carretilla";

        try (Connection conn = ConexionBD.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Carretilla(
                    rs.getInt("id_vehiculo"),
                    rs.getString("matricula"),
                    rs.getString("modelo"),
                    rs.getString("tipo"),
                    rs.getString("tamano"),
                    rs.getFloat("capacidad"),
                    rs.getString("estado"),
                    rs.getString("descripcion")
                ));
            }
        }
        return lista;
    }

    // CONSULTA PREPARADA CON JOIN: Trabajadores habilitados para una carretilla (tabla habilitado)
    public List<String> obtenerHabilitadosPorVehiculo(int idVehiculo) throws SQLException {
        List<String> habilitados = new ArrayList<>();
        String sql = "SELECT t.id_trabajador, t.nombre, t.apellidos FROM trabajador t "
                   + "INNER JOIN habilitado h ON t.id_trabajador = h.id_trabajador "
                   + "WHERE h.id_vehiculo = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVehiculo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    habilitados.add(rs.getInt("id_trabajador") + " - " + rs.getString("nombre") + " " + rs.getString("apellidos"));
                }
            }
        }
        return habilitados;
    }
}

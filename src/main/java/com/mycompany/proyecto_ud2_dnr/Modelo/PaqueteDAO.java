package com.mycompany.proyecto_ud2_dnr.Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaqueteDAO {

    // CONSULTA SIMPLE: Listar todos los paquetes
    public List<Paquete> obtenerTodos() throws SQLException {
        List<Paquete> lista = new ArrayList<>();
        String sql = "SELECT * FROM paquete";

        try (Connection conn = ConexionBD.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearPaquete(rs));
            }
        }
        return lista;
    }

    // CONSULTA PREPARADA: Filtrar paquetes por seccion
    public List<Paquete> obtenerPorSeccion(int idSeccion) throws SQLException {
        List<Paquete> lista = new ArrayList<>();
        String sql = "SELECT * FROM paquete WHERE id_seccion = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSeccion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearPaquete(rs));
                }
            }
        }
        return lista;
    }

    private Paquete mapearPaquete(ResultSet rs) throws SQLException {
        return new Paquete(
            rs.getInt("id_paquete"),
            rs.getInt("id_seccion"),
            rs.getString("nombre_categoria"),
            rs.getString("embalaje"),
            rs.getFloat("peso"),
            rs.getString("contenidos"),
            rs.getString("localizacion"),
            rs.getString("estado")
        );
    }
}

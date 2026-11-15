package com.mycompany.proyecto_ud2_dnr.Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    // CONSULTA SIMPLE: Lista todas las categorias disponibles
    public List<Categoria> obtenerTodas() throws SQLException {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria";

        try (Connection conn = ConexionBD.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Categoria(
                    rs.getString("nombre_categoria"),
                    rs.getString("descripcion")
                ));
            }
        }
        return lista;
    }
}

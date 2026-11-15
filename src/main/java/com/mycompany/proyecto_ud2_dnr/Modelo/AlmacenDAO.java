package com.mycompany.proyecto_ud2_dnr.Modelo;

import java.sql.*;

public class AlmacenDAO {

    // CONSULTA SIMPLE: Obtiene el almacen unico registrado en el sistema
    public Almacen obtenerAlmacenUnico() throws SQLException {
        String sql = "SELECT * FROM almacen LIMIT 1";

        try (Connection conn = ConexionBD.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return new Almacen(
                    rs.getInt("id_almacen"),
                    rs.getInt("id_trabajador"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("dimensiones"),
                    rs.getInt("ocupacion"),
                    rs.getInt("capacidad_de_pale"),
                    rs.getString("direccion")
                );
            }
        }
        return null;
    }
}

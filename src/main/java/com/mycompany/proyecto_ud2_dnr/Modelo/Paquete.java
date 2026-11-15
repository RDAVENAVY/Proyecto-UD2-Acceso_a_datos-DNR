package com.mycompany.proyecto_ud2_dnr.Modelo;

public class Paquete {

    private int idPaquete;
    private int idSeccion;
    private String nombreCategoria;
    private String embalaje;
    private float peso;
    private String contenidos;
    private String localizacion;
    private String estado; // 'bien', 'cuestionable', 'mal'

    public Paquete() {
    }

    public Paquete(int idPaquete, int idSeccion, String nombreCategoria, String embalaje,
                   float peso, String contenidos, String localizacion, String estado) {
        this.idPaquete = idPaquete;
        this.idSeccion = idSeccion;
        this.nombreCategoria = nombreCategoria;
        this.embalaje = embalaje;
        this.peso = peso;
        this.contenidos = contenidos;
        this.localizacion = localizacion;
        this.estado = estado;
    }

    public int getIdPaquete() { return idPaquete; }
    public void setIdPaquete(int idPaquete) { this.idPaquete = idPaquete; }

    public int getIdSeccion() { return idSeccion; }
    public void setIdSeccion(int idSeccion) { this.idSeccion = idSeccion; }

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

    public String getEmbalaje() { return embalaje; }
    public void setEmbalaje(String embalaje) { this.embalaje = embalaje; }

    public float getPeso() { return peso; }
    public void setPeso(float peso) { this.peso = peso; }

    public String getContenidos() { return contenidos; }
    public void setContenidos(String contenidos) { this.contenidos = contenidos; }

    public String getLocalizacion() { return localizacion; }
    public void setLocalizacion(String localizacion) { this.localizacion = localizacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

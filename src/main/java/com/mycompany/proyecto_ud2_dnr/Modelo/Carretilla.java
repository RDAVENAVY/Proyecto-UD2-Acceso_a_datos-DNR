package com.mycompany.proyecto_ud2_dnr.Modelo;

public class Carretilla {

    private int idVehiculo;
    private String matricula;
    private String modelo;
    private String tipo;
    private String tamano;
    private float capacidad;
    private String estado; // 'optimo', 'bien', 'decente', 'revision'
    private String descripcion;

    public Carretilla() {
    }

    public Carretilla(int idVehiculo, String matricula, String modelo, String tipo,
                      String tamano, float capacidad, String estado, String descripcion) {
        this.idVehiculo = idVehiculo;
        this.matricula = matricula;
        this.modelo = modelo;
        this.tipo = tipo;
        this.tamano = tamano;
        this.capacidad = capacidad;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTamano() { return tamano; }
    public void setTamano(String tamano) { this.tamano = tamano; }

    public float getCapacidad() { return capacidad; }
    public void setCapacidad(float capacidad) { this.capacidad = capacidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
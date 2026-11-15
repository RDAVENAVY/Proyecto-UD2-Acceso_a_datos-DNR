package com.mycompany.proyecto_ud2_dnr.Modelo;

import java.time.LocalDate;

public class Trabajador {

    private int idTrabajador;
    private String dni;
    private String nombre;
    private String apellidos;
    private String rol;
    private String turno; // 'Mañana', 'Tarde', 'Noche'
    private LocalDate fInsercion;
    private LocalDate fNacimiento;
    private String telefono;
    private String residencia;
    private String correo;

    public Trabajador() {
    }

    public Trabajador(int idTrabajador, String dni, String nombre, String apellidos, String rol,
                      String turno, LocalDate fInsercion, LocalDate fNacimiento,
                      String telefono, String residencia, String correo) {
        this.idTrabajador = idTrabajador;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.rol = rol;
        this.turno = turno;
        this.fInsercion = fInsercion;
        this.fNacimiento = fNacimiento;
        this.telefono = telefono;
        this.residencia = residencia;
        this.correo = correo;
    }

    public int getIdTrabajador() { return idTrabajador; }
    public void setIdTrabajador(int idTrabajador) { this.idTrabajador = idTrabajador; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public LocalDate getFInsercion() { return fInsercion; }
    public void setFInsercion(LocalDate fInsercion) { this.fInsercion = fInsercion; }

    public LocalDate getFNacimiento() { return fNacimiento; }
    public void setFNacimiento(LocalDate fNacimiento) { this.fNacimiento = fNacimiento; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getResidencia() { return residencia; }
    public void setResidencia(String residencia) { this.residencia = residencia; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}

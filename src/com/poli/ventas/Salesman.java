package com.poli.ventas;


/**
* Representa la información básica de un vendedor.
*/
public class Salesman {
private final String tipoDocumento;
private final String numeroDocumento;
private final String nombres;
private final String apellidos;


public Salesman(String tipoDocumento, String numeroDocumento, String nombres, String apellidos) {
this.tipoDocumento = tipoDocumento;
this.numeroDocumento = numeroDocumento;
this.nombres = nombres;
this.apellidos = apellidos;
}


public String getTipoDocumento() { return tipoDocumento; }
public String getNumeroDocumento() { return numeroDocumento; }
public String getNombres() { return nombres; }
public String getApellidos() { return apellidos; }
}
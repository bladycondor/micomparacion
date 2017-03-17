package org.fautapo.domain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Date;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class Principal implements Serializable {

  /* Private Fields */
  private int    id_usuario;
  private int    id_rol;
  private String id_estado;
  private String estado;
  private int    gestion;
  private int    periodo;
  private Date   fec_registro;
  private Date   fec_modificacion;
  private int    ult_usuario;
  private String nombre_usuario;
  private String fechita;
  private String nombres;  
  private int    id_universidad;
  private int    id_facultad;
  private int    id_programa;
  private String apodo;
  private String clave;
  /* JavaBeans Properties */

  public int getId_usuario() { return id_usuario; }
  public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

  public int getId_rol() { return id_rol; }
  public void setId_rol(int id_rol) { this.id_rol = id_rol; }

  public String getId_estado() { return id_estado; }
  public void setId_estado(String id_estado) { this.id_estado = id_estado; }

  public String getEstado() { return estado; }
  public void setEstado(String estado) { this.estado = estado; }

  public int getGestion() { return gestion; }
  public void setGestion(int gestion) { this.gestion = gestion; }

  public int getPeriodo() { return periodo; }
  public void setPeriodo(int periodo) { this.periodo = periodo; }

  public Date getFec_registro() { return fec_registro; }
  public void setFec_registro(Date fec_registro) { this.fec_registro = fec_registro; }

  public Date getFec_modificacion() { return fec_modificacion; }
  public void setFec_modificacion(Date fec_modificacion) { this.fec_modificacion = fec_modificacion; }

  public int getUlt_usuario() { return ult_usuario; }
  public void setUlt_usuario(int ult_usuario) { this.ult_usuario = ult_usuario; }
  
  public String getNombre_usuario() { return nombre_usuario; }
  public void setNombre_usuario(String nombre_usuario) { this.nombre_usuario = nombre_usuario; }

  public String getFechita() { return fechita; }
  public void setFechita(String fechita) { this.fechita = fechita; }
  
  public String getNombres() { return nombres; }
  public void setNombres(String nombres) { this.nombres = nombres; }

  public String getString(HttpServletRequest request, String variable) {
    variable = request.getParameter(variable);
    if (null == variable) { variable = ""; }
    return variable;
  }

  public int getInt(HttpServletRequest request, String variable) {
    variable = request.getParameter(variable);
    if ((null == variable) || ("".equals(variable))) { variable = "0"; }
    return Integer.parseInt(variable);
  }

  public float getFloat(HttpServletRequest request, String variable) {
    variable = request.getParameter(variable);
    if ((null == variable) || ("".equals(variable))) { variable = "0.0"; }
    return Float.valueOf(variable).floatValue();
  }

  public int getId_universidad() { return id_universidad; }
  public void setId_universidad(int id_universidad) { this.id_universidad = id_universidad; }

  public int getId_facultad() { return id_facultad; }
  public void setId_facultad(int id_facultad) { this.id_facultad = id_facultad; }

  public int getId_programa() { return id_programa; }
  public void setId_programa(int id_programa) { this.id_programa = id_programa; }
  
  public String getApodo() { return apodo; }
  public void setApodo(String apodo) {
    Criptografia md5 = new Criptografia();
    this.apodo = md5.MD5(apodo + "fautapo");
  }

  public String getClave() { return clave; }
  public void setClave(String clave) {
    Criptografia md5 = new Criptografia();
    this.clave = md5.MD5(clave + "modeloinformacional");
  }

}
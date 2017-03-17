package org.fautapo.domain;

import java.util.List;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class Clientes extends Principal {

  /* Private Fields */
  //Usuario
  private List   roles;
  private String nombres;
  private int id_rol;
  private String rol;
  private int    id_nivel_acceso;
  private int    id_universidad;
  private int    id_facultad;
  private int    id_programa;
  private int    id_departamento;
  private int    id_ubicacion_organica;
  private int    id_almacen;
  private String almacen;
  private String filtro;
  private String permiso;
  private List   almacenes;
  //Parametros generales
  private String formato_fecha;
  private String apodo;
  /* JavaBeans Properties */

  public int getId_rol() { return id_rol; }
  public void setId_rol(int id_rol) { this.id_rol = id_rol; }
  
  
  public List getRoles(){ return roles; }
  public void setRoles(List roles){ this.roles = roles; }

  public String getNombres() { return nombres; }
  public void setNombres(String nombres) { this.nombres = nombres; }

  public String getRol() { return rol; }
  public void setRol(String rol) { this.rol = rol; }

  public int getId_nivel_acceso() { return id_nivel_acceso; }
  public void setId_nivel_acceso(int id_nivel_acceso) { this.id_nivel_acceso = id_nivel_acceso; }

  public int getId_universidad() { return id_universidad; }
  public void setId_universidad(int id_universidad) { this.id_universidad = id_universidad; }

  public int getId_facultad() { return id_facultad; }
  public void setId_facultad(int id_facultad) { this.id_facultad = id_facultad; }

  public int getId_programa() { return id_programa; }
  public void setId_programa(int id_programa) { this.id_programa = id_programa; }

  public int getId_departamento() { return id_departamento; }
  public void setId_departamento(int id_departamento) { this.id_departamento = id_departamento; }

  public int getId_ubicacion_organica() { return id_ubicacion_organica; }
  public void setId_ubicacion_organica(int id_ubicacion_organica) { this.id_ubicacion_organica = id_ubicacion_organica; }

  public int getId_almacen() { return id_almacen; }
  public void setId_almacen(int id_almacen) { this.id_almacen = id_almacen; }

  public String getAlmacen() { return almacen; }
  public void setAlmacen(String almacen) { this.almacen = almacen; }

  public String getFiltro() { return filtro; }
  public void setFiltro(String filtro) { this.filtro = filtro; }

  public String getPermiso() { return permiso; }
  public void setPermiso(String permiso) { this.permiso = permiso; }

  public List getAlmacenes(){ return almacenes; }
  public void setAlmacenes(List almacenes){ this.almacenes = almacenes; }

  public String getFormato_fecha() { return formato_fecha; }
  public void setFormato_fecha(String formato_fecha) { this.formato_fecha = formato_fecha; }

  public String getApodo() { return apodo; }
  public void setApodo(String apodo) { this.apodo = apodo; }

}
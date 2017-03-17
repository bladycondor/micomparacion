package org.fautapo.domain;

import org.fautapo.domain.Criptografia;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class Usuarios extends Clientes { // extends Personas {

  /* Private Fields */
  private int    id_persona;  // TEMPORAL
  private String apodo;
  private String clave;
  private String recordatorio;

  private int    id_usuario;
  private int    id_ubicacion_organica;
  private String ubicacion_organica;
  private int    id_proceso;
  private int    pagina;
  private String apodo_normal;
  private String clave_normal; 
  //private int id_sede;
  /* JavaBeans Properties */

// Temporal
  public int getId_persona() { return id_persona; }
  public void setId_persona(int id_persona) { this.id_persona = id_persona; }
  //public void setId_sede(){}
  //public int getid_sede(){return id_sede;}
  
// fin Temporal

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
  
  
  public String getRecordatorio() { return recordatorio; }
  public void setRecordatorio(String recordatorio) { this.recordatorio = recordatorio; }

  public int getId_ubicacion_organica() { return id_ubicacion_organica; }
  public void setId_ubicacion_organica(int id_ubicacion_organica) { this.id_ubicacion_organica = id_ubicacion_organica; }

  public String getUbicacion_organica() { return ubicacion_organica; }
  public void setUbicacion_organica(String ubicacion_organica) { this.ubicacion_organica = ubicacion_organica; }  

  public int getId_proceso() { return id_proceso; }
  public void setId_proceso(int id_proceso) { this.id_proceso = id_proceso; }
  
  public int getPagina() { return pagina; }
  public void setPagina(int pagina) { this.pagina = pagina; }
  
  public String getClave_normal() { return clave_normal; }
  public void setClave_normal(String clave_normal) { this.clave_normal = clave_normal; }

  public String getApodo_normal() { return apodo_normal; }
  public void setApodo_normal(String apodo_normal) { this.apodo_normal = apodo_normal; }

}
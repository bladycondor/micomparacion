package org.fautapo.domain;

import org.fautapo.domain.Departamentos;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class Calendarios extends Departamentos {

  /* Private Fields */
  private int id_programa;
  private String tabla;
  private String fec_inicio;
  private String fec_fin;
  /* JavaBeans Properties */

  public int getId_programa() { return id_programa; }
  public void setId_programa(int id_programa) { this.id_programa = id_programa; }

  public String getTabla() { return tabla; }
  public void setTabla(String tabla) { this.tabla = tabla; }

  public String getFec_inicio() { return fec_inicio; }
  public void setFec_inicio(String fec_inicio) { this.fec_inicio = fec_inicio; }

  public String getFec_fin() { return fec_fin; }
  public void setFec_fin(String fec_fin) { this.fec_fin = fec_fin; }

}
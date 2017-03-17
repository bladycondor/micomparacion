package org.fautapo.domain;

import org.fautapo.domain.Programas;

public class Menciones extends Programas {

  /* Private Fields */
  private int    id_mencion;
  private String    id_plan;
  private String    mencion;
  /* JavaBeans Properties */

  public int getId_mencion() { return id_mencion; }
  public void setId_mencion(int id_mencion) { this.id_mencion = id_mencion; }

  public String getId_plan() { return id_plan; }
  public void setId_plan(String id_plan) { this.id_plan = id_plan; }

  public String getMencion() { return mencion; }
  public void setMencion(String mencion) { this.mencion = mencion; }

}
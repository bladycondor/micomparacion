package org.fautapo.web.dibRep;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class DibRep implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    //CONTROL DE SESION
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");


    String _nombres = cliente.getNombres();

    Abm aux = new Abm();
    String id_tabla;
    String lista_tablas[] = null;
    List tablas_dibrep = new ArrayList();
    String t = request.getParameter("t");
    try{
      lista_tablas = t.split(":");
    } catch (Exception ex) {
      lista_tablas = null;
    }
    String b = request.getParameter("b");
    
    String id_consulta = request.getParameter("c");
    modelo.put("c", id_consulta);

    String descripcion = request.getParameter("descripcion");
    modelo.put("descripcion", descripcion);

    String limite = request.getParameter("limite");
    modelo.put("limite", limite);

    List listadoTablas = this.mi.getListarTablas();
    modelo.put("listadoTablas",listadoTablas);
    t = "";
    if (lista_tablas != null) {
    for (int i = 0; i< lista_tablas.length; i++) {
      String letra =String.valueOf((char)(97 + i)); 
      id_tabla =request.getParameter("tabla_" + letra);
        if(b != null){
          if (!b.equals(id_tabla)){
            Abm abm = new Abm();
	    abm.setId_tabla(Integer.parseInt(id_tabla));
            abm = this.mi.getBuscarTabla(abm);
	    String letra1 =String.valueOf((char)(tablas_dibrep.size() + 97));
	    abm.setAlias(letra1);
            tablas_dibrep.add(abm);
            if (t.equals("")){
	      t = id_tabla;
	    } else {
	      t = t +":" + id_tabla;
	    }
	
	  } else {
	    b=null;
	  }
	} else {
	    Abm abm = new Abm();
	    abm.setId_tabla(Integer.parseInt(id_tabla));
            abm = this.mi.getBuscarTabla(abm);
	    String letra1 = String.valueOf((char)(tablas_dibrep.size() + 97));
	    abm.setAlias(letra1);
            tablas_dibrep.add(abm);      
            if (t.equals("")) {
	      t = id_tabla;
	    } else {
	      t = t +":" + id_tabla;
	    }
        }
    }
    }
    

    String glosa= request.getParameter("glosa");
    if (glosa!=null)
      modelo.put("glosa", glosa);    

    String orden= request.getParameter("orden");
    if (orden != null)
      modelo.put("orden", orden);    

    String contar_reg = request.getParameter("contar_reg");
    if (contar_reg != null)
      modelo.put("contar_reg", contar_reg);

    String _sumas = request.getParameter("_suma");
      modelo.put("_suma", _sumas);

    String op = request.getParameter("op");
      modelo.put("op", op);

    String campo_calculado = request.getParameter("campo_calculado");
      modelo.put("campo_calculado", campo_calculado);
      
    String campo_calculado_etiqueta = request.getParameter("campo_calculado_etiqueta");
    modelo.put("campo_calculado_etiqueta", campo_calculado_etiqueta);



    //CAMPOS MARCADOS
    List campos_marcados = new ArrayList();
    String id_campos[] = request.getParameterValues("id_campos");
    if (id_campos != null)
    for(int i=0; i< id_campos.length; i++) {
      Abm campo_marcado = new Abm(); 
      campo_marcado.setCampo(id_campos[i]);
      campos_marcados.add(campo_marcado);
    }
    modelo.put("campos_marcados",campos_marcados);	


    //CAMPOS CONDICIONES MARCADOS
    List campos_condiciones_marcados = new ArrayList();    
    for(int i=0; i< tablas_dibrep.size(); i++) {
      Abm abm = new Abm();
      abm = (Abm) tablas_dibrep.get(i);
      abm.setPermiso("l");  
      List campos = this.mi.getListarCamposTabla(abm);
      for(int j=0; j < campos.size(); j++) {
        Abm aux1 = new Abm();
        aux1 = (Abm) campos.get(j);
	String valor_campo = request.getParameter("campo_" + abm.getAlias() + "_" + aux1.getId_campo());
	String condicion_campo = request.getParameter("condicion_" + abm.getAlias() + "_" + aux1.getId_campo());
        aux1.setCampo(abm.getAlias() + "_" + aux1.getId_campo());
	aux1.setCondicion(condicion_campo);
	aux1.setValores(valor_campo);
        if (valor_campo != null) {
	  valor_campo = valor_campo.trim();
	  if (!valor_campo.equals(""))
	    if(!valor_campo.equals("0"))
	      campos_condiciones_marcados.add(aux1);
        }
      }
    }
    modelo.put("campos_condiciones_marcados",campos_condiciones_marcados);

    //CAMPOS VARIABLES MARCADOS
    List campos_variables_marcados = new ArrayList();
    String campos_variables[] = request.getParameterValues("variables");
    if (campos_variables != null)
    for(int i=0; i< campos_variables.length; i++) {
      Abm campo_variable_marcado = new Abm(); 
      campo_variable_marcado.setCampo(campos_variables[i]);
      campos_variables_marcados.add(campo_variable_marcado);
    }
    modelo.put("campos_variables_marcados",campos_variables_marcados);	


    //CAMPOS SUMAS SUBTOTAL MARCADOS
    List campos_sumas_st_marcados = new ArrayList();
    String campos_sumas_st[] = request.getParameterValues("sumar_st");
    if (campos_sumas_st != null)
    for(int i=0; i< campos_sumas_st.length; i++) {
      Abm campo_suma_st_marcado = new Abm(); 
      campo_suma_st_marcado.setCampo(campos_sumas_st[i]);
      campos_sumas_st_marcados.add(campo_suma_st_marcado);
    }
    modelo.put("campos_sumas_st_marcados",campos_sumas_st_marcados);	

    //CAMPOS SUMAS TOTALES MARCADOS
    List campos_sumas_marcados = new ArrayList();
    String campos_sumas[] = request.getParameterValues("sumar");
    if (campos_sumas != null)
    for(int i=0; i< campos_sumas.length; i++) {
      Abm campo_suma_marcado = new Abm(); 
      campo_suma_marcado.setCampo(campos_sumas[i]);
      campos_sumas_marcados.add(campo_suma_marcado);
    }
    modelo.put("campos_sumas_marcados",campos_sumas_marcados);	

    //CAMPOS CABEZAS MARCADOS
    List campos_cabezas_marcados = new ArrayList();
    String campos_cabezas[] = request.getParameterValues("cabezas");
    if (campos_cabezas != null)
    for(int i=0; i< campos_cabezas.length; i++) {
      Abm campo_cabeza_marcado = new Abm(); 
      campo_cabeza_marcado.setCampo(campos_cabezas[i]);
      campos_cabezas_marcados.add(campo_cabeza_marcado);
    }
    modelo.put("campos_cabezas_marcados",campos_cabezas_marcados);	



    //CAMPOS CONDICIONES MARCADOS
    List campos_orden_marcados = new ArrayList();
    
    for(int i=0; i< tablas_dibrep.size(); i++) {
      Abm abm = new Abm();
      abm = (Abm) tablas_dibrep.get(i);
      abm.setPermiso("l");  
      List campos = this.mi.getListarCamposTabla(abm);
      for(int j=0; j < campos.size(); j++) {
        Abm aux1 = new Abm();
        aux1 = (Abm) campos.get(j);
	String valor_campo = request.getParameter("orden_" + abm.getAlias() + "_" + aux1.getId_campo());
        aux1.setCampo("orden_" + abm.getAlias() + "_" + aux1.getId_campo());
	aux1.setValores(valor_campo);
        if (valor_campo != null) {
	  valor_campo = valor_campo.trim();
	  if (!valor_campo.equals(""))
	      campos_orden_marcados.add(aux1);
        }
      }
    }
    modelo.put("campos_orden_marcados",campos_orden_marcados);


    //AGREGAR TABLA
    String boton = request.getParameter("boton");
    id_tabla     = request.getParameter("id_tabla");
    if ("Agregar".equals(boton)) {
      if (id_tabla != null){
        if (0!=Integer.parseInt(id_tabla)) {
	  Abm abm = new Abm();
          abm.setId_tabla(Integer.parseInt(id_tabla));
	  int letra = tablas_dibrep.size() + 97;
	  String letra1=String.valueOf((char)letra);
          abm = this.mi.getBuscarTabla(abm);
	  abm.setAlias(letra1);	  
          tablas_dibrep.add(abm);
          if (t.equals("")){
            t = id_tabla;
          } else {
            t = t +":" + id_tabla;
          }
	}
      }
    }
    modelo.put("t", t);
    

    if (tablas_dibrep != null) {    
      if (tablas_dibrep.size() != 0){
        for (int i = 0; i< tablas_dibrep.size(); i++){
	  Abm abm = new Abm();
          abm = (Abm) tablas_dibrep.get(i);
          abm.setPermiso("l");
	  List listaCampos = this.mi.getListarCamposTabla(abm);
	  for (int j = 0; j< listaCampos.size(); j++){
            aux = (Abm) listaCampos.get(j);
	    Abm buscarForaneo = this.mi.getBuscarForanea(aux);
	    if (buscarForaneo != null) {
	      buscarForaneo.setCampo(buscarForaneo.getId_campo_foraneo() + "#~~#" + buscarForaneo.getCampo_foraneo() + "#~~#''");
              buscarForaneo.setCondicion("");
	      aux.setCombo(this.mi.getListarCombos(buscarForaneo));
	      aux.setId_campo_foraneo(buscarForaneo.getId_campo_foraneo());
	    }
	    listaCampos.set(j, aux);
	  }
	  abm.setCampos_dibrep(listaCampos);
          tablas_dibrep.set(i, abm);
        }
      }
    }
    modelo.put("tablas_dibrep", tablas_dibrep);
    return new ModelAndView("dibRep/Generador", modelo);
  }
}
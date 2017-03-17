package org.fautapo.web.administrarTuplas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-20
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-20
*/

public class ConfirmarTupla implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Dominios datosTupla = new Dominios();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    
    String sAccion         = request.getParameter("accion");
    String sAccion1        = request.getParameter("accion1");
    String sId_tupla       = request.getParameter("id_tupla");
    String sId_dominio     = request.getParameter("id_dominio");
    String sTupla          = request.getParameter("tupla");
    String sId_tupla_padre = request.getParameter("id_tupla_padre");
    String sObligatorio    = request.getParameter("obligatorio");

    if ("Adicionar".equals(sAccion)) { 
      if ("".equals(request.getParameter("tupla"))) {
        return new ModelAndView("Error", "mensaje", "Faltan introducir datos");
      }
      datosTupla.setTupla(sTupla);
      if ("si".equals(sObligatorio)) {
        datosTupla.setObligatorio(true);
      }
      else {
        datosTupla.setObligatorio(false);
      }

      //Buscamos los datos del padre
      if (!"0".equals(sId_tupla_padre)) {
        Dominios datosTuplaPadre = new Dominios();
        datosTuplaPadre.setId_tupla(Integer.parseInt(sId_tupla_padre));
        datosTuplaPadre = (Dominios) this.mi.getBuscarTupla(datosTuplaPadre);
        modelo.put("datosTuplaPadre", datosTuplaPadre);
      }
      modelo.put("datosTupla", datosTupla); 
    }
    
    if ("Modificar".equals(sAccion)) {
      if ("".equals(request.getParameter("tupla"))){
        return new ModelAndView("Error", "mensaje", "Faltan introducir datos");
      }
      datosTupla.setTupla(sTupla);
      if ("si".equals(sObligatorio)) {
        datosTupla.setObligatorio(true);
      }
      else {
        datosTupla.setObligatorio(false);
      }
      //Buscamos los datos del padre
      if (!"0".equals(sId_tupla_padre)) {
        Dominios datosTuplaPadre = new Dominios();
        datosTuplaPadre.setId_tupla(Integer.parseInt(sId_tupla_padre));
        datosTuplaPadre = (Dominios) this.mi.getBuscarTupla(datosTuplaPadre);
        modelo.put("datosTuplaPadre", datosTuplaPadre);
      }
      modelo.put("datosTupla", datosTupla);  
    }
    
    if ("Eliminar".equals(sAccion)) {
      datosTupla.setId_tupla(Integer.parseInt(sId_tupla));
      datosTupla = (Dominios) this.mi.getBuscarTupla(datosTupla);
      int iId_tupla_padre = datosTupla.getId_tupla_padre();
      if (iId_tupla_padre !=0) {
        //Buscamos los datos del padre
        Dominios datosTuplaPadre = new Dominios();
        datosTuplaPadre.setId_tupla(iId_tupla_padre);
        datosTuplaPadre = (Dominios) this.mi.getBuscarTupla(datosTuplaPadre);
        modelo.put("datosTuplaPadre", datosTuplaPadre);
      }
      modelo.put("datosTupla", datosTupla);
    }
    modelo.put("accion", sAccion);
    modelo.put("accion1", sAccion1);
    modelo.put("id_tupla", sId_tupla);
    modelo.put("id_dominio", sId_dominio);
    return new ModelAndView("administrarTuplas/ConfirmarTupla", modelo);
  }
}
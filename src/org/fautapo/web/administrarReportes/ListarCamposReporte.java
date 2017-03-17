package org.fautapo.web.administrarReportes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Campos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-29
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-29
*/

public class ListarCamposReporte implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Campos datosCampo; 
    String sDato;

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_proceso = request.getParameter("id_proceso");
    String sId_campo_o = request.getParameter("id_campo_o");	    
    String sChequeo = request.getParameter("chequeo");
    String sSw = request.getParameter("sw");    

    modelo.put("sw", sId_proceso);        
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_campo_o", sId_campo_o);

    if (sSw == null) {
      sSw = "";
    }

    //Listamos los procesos según el acceso del usuario
    List lProcesos = this.mi.getListarProcesosAcceso(cliente);
    modelo.put("lProcesos", lProcesos);
    //Fin Listamos los procesos según el acceso del usuario

    //Listamos los campos de un proceso
    if ((sId_proceso != null) && (!"".equals(sId_proceso))) {
      datosCampo = new Campos();
      datosCampo.setId_proceso(Integer.parseInt(sId_proceso));
      List lCampos = this.mi.getListarCamposProceso(datosCampo);
      modelo.put("lCampos", lCampos);
    }
    //Fin Listamos los campos de un proceso

    //Recuperamos los campos chequeados    
    try {
      if (sChequeo != null) {
        List lCamposChequeados = new ArrayList();
        String sCodigo[] = request.getParameterValues("chequeo");
        for (int i=0; i<sCodigo.length; i++) {
          if (sCodigo[i] != null) {
            datosCampo = new Campos(); 
	    datosCampo.setId_campo(Integer.parseInt(sCodigo[i]));
            datosCampo = (Campos) this.mi.getBuscarCampoForm(datosCampo);
	    lCamposChequeados.add(datosCampo);
          }
        }
	if ((Integer.parseInt(sSw)) != (Integer.parseInt(sId_proceso))) {
          lCamposChequeados = null;
	}
        modelo.put("lCamposChequeados", lCamposChequeados);
      }
    }
    catch (Exception e) {
    }
    //Fin Recuperamos los campos chequeados    
    
    //Recuperamos los campos para el orden
    String sContador = request.getParameter("contadors");
    List lCamposOrden = new ArrayList();
    if ((!"".equals(sId_campo_o)) && (sId_campo_o != null)) {
      if (sContador!=null) {
        int iContador = Integer.parseInt(sContador);
        for (int j=0; j < iContador; j++) {
          sDato = request.getParameter("valor_"+Integer.toString(j+1));
	  if (sDato != null) {
            if ((Integer.parseInt(sDato)) != (Integer.parseInt(sId_campo_o))) {
              datosCampo = new Campos(); 
	      datosCampo.setId_campo(Integer.parseInt(sDato));
              datosCampo = (Campos) this.mi.getBuscarCampoForm(datosCampo);
	      lCamposOrden.add(datosCampo);
            }
          }
        }
      }
      datosCampo = new Campos(); 
      datosCampo.setId_campo(Integer.parseInt(sId_campo_o));
      datosCampo = (Campos) this.mi.getBuscarCampoForm(datosCampo);
      lCamposOrden.add(datosCampo);
    }
    else {
      try {
        int iContador = Integer.parseInt(request.getParameter("contadors"));
        for (int j=0; j < iContador; j++) {
          sDato = request.getParameter("valor_"+Integer.toString(j+1));
          if (sDato != null) {
            datosCampo = new Campos(); 
	    datosCampo.setId_campo(Integer.parseInt(sDato));
            datosCampo = (Campos) this.mi.getBuscarCampoForm(datosCampo);
	    lCamposOrden.add(datosCampo);
          }
        }
      }
      catch (Exception e) {
        int iAux = 0;
      }
    }
    modelo.put("lCamposOrden", lCamposOrden);
    //Fin Recuperamos los campos para el orden
    
    //Recuperamos los campos chequeados para la agrupacion
    try {
      List lCamposAgrupacion = new ArrayList();
      String sCodigo_u[] = request.getParameterValues("chequeo_o");
      for (int j=0; j < sCodigo_u.length; j++) {
        if (sCodigo_u[j] != null) {
          datosCampo = new Campos(); 
          datosCampo.setId_campo(Integer.parseInt(sCodigo_u[j]));
          datosCampo = (Campos) this.mi.getBuscarCampoForm(datosCampo);
	  lCamposAgrupacion.add(datosCampo);
        }
      }
      modelo.put("lCamposAgrupacion", lCamposAgrupacion);
    }
    catch(Exception e) {
      int iY=0;
    }
    //Fin Recuperamos los campos chequeados para la agrupacion

    //Inicializamos las listas vacias
    try {
      if ((Integer.parseInt(sSw)) != (Integer.parseInt(sId_proceso))) { 
//        modelo.put("lCamposAgrupacion", null);
//        modelo.put("lCamposOrden", null);
      }
    } catch(Exception e) {
//      modelo.put("lCamposAgrupacion", null);
//      modelo.put("lCamposOrden", null);
    }
    //Fin Inicializamos las listas vacias

    return new ModelAndView("administrarReportes/ListarCamposReporte", modelo);
  }
}
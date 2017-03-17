package org.fautapo.web.administrarMisPendientes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.support.PagedListHolder;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-23
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-23
*/

public class ListarMisPendientesDespachados implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Tramites datosFrLog = new Tramites(); Tramites datosTramite = new Tramites(); Character caracter;
    String totalRegistros_Paginas[] = new String[3]; List lTramites;
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");
    String sId_form = request.getParameter("id_form");
    String sId_actividad = request.getParameter("id_actividad");
    String sAccion = request.getParameter("accion");
    String sId_tipo_proceso = request.getParameter("id_tipo_proceso");
    String sId_tipo_actuacion = request.getParameter("id_tipo_actuacion");
    String sPara = request.getParameter("para");
    String sBotoncillo = request.getParameter("_botoncillo");
    String sNro_filtro = request.getParameter("nro_filtro");
    String sNro_pagina = request.getParameter("nro_pagina");
    if ((sNro_pagina == null) || ("".equals(sNro_pagina))) {
      sNro_pagina = "1";
    }
    Abm tabla = new Abm();
    tabla.setPagina(Integer.parseInt(sNro_pagina));
    datosTramite.setPara(cliente.getId_usuario());
    totalRegistros_Paginas = this.mi.getContarPaginasDespachados(datosTramite).split("#~~#");
    modelo.put("totalRegistros", totalRegistros_Paginas[0]);
    modelo.put("totalPaginas", totalRegistros_Paginas[1]);
    modelo.put("paginacion", totalRegistros_Paginas[2]);
    modelo.put("nro_pagina", sNro_pagina);
    modelo.put("id_tipo_proceso", sId_tipo_proceso);

    //Listamos los tramites pendientes
    datosTramite.setPara(cliente.getId_usuario());
    datosTramite.setPagina(Integer.parseInt(sNro_pagina));
    if ("filtro".equals(sBotoncillo)) {
      try {
	datosTramite.setCorrelativo2("%"+sNro_filtro+"%");
      }
      catch(Exception e) {
        datosTramite.setCorrelativo2("%%");
      }
      lTramites = this.mi.getListarTramitesMiosDespachadosFiltrado(datosTramite);
      modelo.put("nro_filtro", sNro_filtro);
      modelo.put("_botoncillo", sBotoncillo);
    }
    else {
      lTramites = this.mi.getListarTramitesMiosDespachados(datosTramite);
    }
    for (int i = 0; i < lTramites.size(); i++) {
      Tramites auxiliar = (Tramites) lTramites.get(i);
      auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
      lTramites.set(i, auxiliar);
    }
    modelo.put("lMisPendientes", lTramites);

    //Colocamos el nombre de FAUTAPO
    String sInstitucion = (caracter = new Character((char)70)).toString()+(caracter = new Character((char)65)).toString()+(caracter = new Character((char)85)).toString();
    sInstitucion = sInstitucion+(caracter = new Character((char)84)).toString()+(caracter = new Character((char)65)).toString()+(caracter = new Character((char)80)).toString()+(caracter = new Character((char)79)).toString();
    modelo.put("institucion", sInstitucion);
    //FIN Colocamos el nombre de FAUTAPO
    
    return new ModelAndView("administrarMisPendientes/ListarMisPendientesDespachados", modelo);
  }
}
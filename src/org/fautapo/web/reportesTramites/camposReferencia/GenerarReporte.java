package org.fautapo.web.reportesTramites.camposReferencia;

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
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-29
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-29
*/

public class GenerarReporte implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    String sCampos=""; String sCampos_m = ""; String sCadena_1 = ""; String sAux_1 = "0"; Campos datosCampo; String sCampos2="";
    String totalRegistros_Paginas[] = new String[3];

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String sId_proceso = request.getParameter("id_proceso");
    String sGestion = request.getParameter("gestion");
    //Recuperamos el dato de la gestion
    if (((sGestion == null) || ("".equals(sGestion))) && (sId_proceso == null)) {
      sGestion = Integer.toString(cliente.getGestion());
    }
    modelo.put("gestion", sGestion);
    //Listamos los procesos según el acceso del usuario
    List lProcesos = this.mi.getListarProcesosAcceso(cliente);
    modelo.put("lProcesos", lProcesos);
    //Fin Listamos los procesos según el acceso del usuario

    //Listamos los campos de un proceso
    if ((sId_proceso != null) && (!"".equals(sId_proceso)) && (sGestion != null) && (!"".equals(sGestion))) {

      //Recuperamos el numero de pagina
      String sNro_pagina = request.getParameter("nro_pagina");
      if ((sNro_pagina == null) || ("".equals(sNro_pagina))) {
        sNro_pagina = "1";
      }
      Tramites datosTramite = new Tramites();
      datosTramite.setGestion(Integer.parseInt(sGestion));
      datosTramite.setId_proceso(Integer.parseInt(sId_proceso));
      totalRegistros_Paginas = this.mi.getContarPaginasTramitesGestionProceso(datosTramite).split("#~~#");
      modelo.put("totalRegistros", totalRegistros_Paginas[0]);
      modelo.put("totalPaginas", totalRegistros_Paginas[1]);
      modelo.put("paginacion", totalRegistros_Paginas[2]);
      modelo.put("nro_pagina", sNro_pagina);

      //Empezamos a generar el reporte
      datosCampo = new Campos();
      datosCampo.setId_proceso(Integer.parseInt(sId_proceso));
      List lCampos = this.mi.getListarCamposReferenciaProceso(datosCampo);
      for (int i=0; i < lCampos.size(); i++) {
        datosCampo = (Campos) lCampos.get(i);
        if (i == 0) {
          sCampos = "coalesce(id_tramite, 0) ||'-'||coalesce(correlativo, ' ') ||'-'||coalesce(gestion, 0) ||'-'|| coalesce("+"campo"+datosCampo.getId_campo()+", ' ')";
          sCampos_m = "id_tramite integer, correlativo text, gestion integer, "+"campo"+datosCampo.getId_campo()+" varchar";
	  sCampos2 = "id_campo="+Integer.toString(datosCampo.getId_campo());
        }
        else {
          sCampos = sCampos+"||'-'|| coalesce("+"campo"+datosCampo.getId_campo()+", ' ')";
          sCampos_m = sCampos_m+","+"campo"+datosCampo.getId_campo()+" varchar"; 
	  sCampos2 = sCampos2 +" OR id_campo="+datosCampo.getId_campo();
        }
      }
      Campos campos = new Campos();
      campos.setCampos(sCampos_m);

      //Para el order by
      sCadena_1 =" SELECT ("+sCampos+")::varchar as valor FROM _"+cliente.getId_usuario()+" ORDER BY id_tramite";
      //Fin order by

      campos.setGestion(Integer.parseInt(sGestion));
      campos.setId_proceso(Integer.parseInt(sId_proceso));
      campos.setPagina(Integer.parseInt(sNro_pagina));
      campos.setCadena_1(sCadena_1);
      campos.setTablita("_"+cliente.getId_usuario());
      campos.setCampos2(sCampos2);

      //Para la lista del order by
      List lDatos = new ArrayList();
      try {
        lCampos = this.mi.getListarCamposReporteProceso(campos);
        modelo.put("cantidad_order_by", Integer.toString(lCampos.size()));
        List lTemp = new ArrayList();
        Campos datosTemp = new Campos();
        for (int j=0; j < lCampos.size(); j++) {
          List listita = new ArrayList();
          Campos auxiliar = new Campos();
          Campos aux = (Campos) lCampos.get(j);
          String sCad[] = (aux.getValor()).split("#~~#");
          for (int k=2; k < sCad.length; k++) {
            Tramites datosValores = new Tramites();
	    if (k>2) {
              datosValores.setValor(sCad[k].replace("$$$", "'"));
	    }
	    else {
	      datosValores.setValor(sCad[1]+"/"+sCad[2]);
	    }
            listita.add(datosValores);
          }
          auxiliar.setLista(listita);
  	  if (j>0) {
            auxiliar.setId_campo(Integer.parseInt(sCad[0]));
	  }
	  else {
	    auxiliar.setId_campo(0);
	  }
          lDatos.add(auxiliar);
        }
        if ("1".equals(sAux_1)) {
          datosTemp.setLista(lTemp);
          lDatos.add(datosTemp);
        }
      }
      catch(Exception e) {}
      modelo.put("lDatos", lDatos);
      modelo.put("id_proceso", sId_proceso);
    }
    
    return new ModelAndView("reportesTramites/camposReferencia/ListarReporte", modelo);
  }
}
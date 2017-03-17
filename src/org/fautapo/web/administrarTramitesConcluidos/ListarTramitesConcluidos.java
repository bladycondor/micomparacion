package org.fautapo.web.administrarTramitesConcluidos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.support.PagedListHolder;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Informes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.EnviarCorreo;
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

public class ListarTramitesConcluidos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Tramites datosFrLog = new Tramites(); Tramites datosTramite = new Tramites(); Character caracter;
    String totalRegistros_Paginas[] = new String[3]; List lTramites;

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_proceso = request.getParameter("id_proceso");
    String sAccion = request.getParameter("accion");
    String sBotoncillo = request.getParameter("_botoncillo");
    String sNro_filtro = request.getParameter("nro_filtro");
    String sNro_pagina = request.getParameter("nro_pagina");
    if ((sNro_pagina == null) || ("".equals(sNro_pagina))) {
      sNro_pagina = "1";
    }
    //Listamos los procesos según el acceso del usuario
    List lProcesos = this.mi.getListarProcesosAcceso(cliente);
    modelo.put("lProcesos", lProcesos);
    modelo.put("id_proceso", sId_proceso);

    if ((!"".equals(sId_proceso)) && (sId_proceso != null)) {
      Abm tabla = new Abm();
      tabla.setPagina(Integer.parseInt(sNro_pagina));
      datosTramite.setId_proceso(Integer.parseInt(sId_proceso));
      totalRegistros_Paginas = this.mi.getContarPaginasConcluidos(datosTramite).split("#~~#");
      modelo.put("totalRegistros", totalRegistros_Paginas[0]);
      modelo.put("totalPaginas", totalRegistros_Paginas[1]);
      modelo.put("paginacion", totalRegistros_Paginas[2]);
      modelo.put("nro_pagina", sNro_pagina);

      //Listamos los tramites concluidos de acuerdo al proceso seleccionado 
      datosTramite.setId_proceso(Integer.parseInt(sId_proceso));
      datosTramite.setPagina(Integer.parseInt(sNro_pagina));
      if ("filtro".equals(sBotoncillo)) {
        try {
    	  datosTramite.setCorrelativo2("%"+sNro_filtro+"%");
        }
        catch(Exception e) {
          datosTramite.setCorrelativo2("%%");
        }
        lTramites = this.mi.getListarTramitesConcluidosPorProcesoFiltrado(datosTramite);
        modelo.put("nro_filtro", sNro_filtro);
        modelo.put("_botoncillo", sBotoncillo);
      }
      else {
        lTramites = this.mi.getListarTramitesConcluidosPorProceso(datosTramite);
      }
      for (int i = 0; i < lTramites.size(); i++) {
        Tramites auxiliar = (Tramites) lTramites.get(i);
        auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
        lTramites.set(i, auxiliar);
      }
      modelo.put("lMisPendientes", lTramites);
    }
    
    //Colocamos el nombre de FAUTAPO
    String sInstitucion = (caracter = new Character((char)70)).toString()+(caracter = new Character((char)65)).toString()+(caracter = new Character((char)85)).toString();
    sInstitucion = sInstitucion+(caracter = new Character((char)84)).toString()+(caracter = new Character((char)65)).toString()+(caracter = new Character((char)80)).toString()+(caracter = new Character((char)79)).toString();
    modelo.put("institucion", sInstitucion);
    //FIN Colocamos el nombre de FAUTAPO
    
    return new ModelAndView("administrarTramitesConcluidos/ListarTramitesConcluidos", modelo);
  }
}
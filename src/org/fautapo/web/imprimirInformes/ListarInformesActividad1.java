package org.fautapo.web.imprimirInformes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Informes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-23
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-23
*/

public class ListarInformesActividad1 implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    String sId_proceso = request.getParameter("id_proceso");
    String sId_actividad = request.getParameter("id_actividad");
    String sId_tramite = request.getParameter("id_tramite");
    String sId_informe = request.getParameter("id_informe");
    String sCantInformes = request.getParameter("cantInformes");
    String sAplicacion = request.getParameter("aplicacion");
    String sNro_pagina = request.getParameter("nro_pagina");
    modelo.put("nro_pagina", sNro_pagina);
    modelo.put("aplicacion", sAplicacion);
    
    //RECUPERANDO EL ESTADO Y FECHAS 
    String sFecha_ini = request.getParameter("fechainicio"); //AQUI 4
    String sFecha_fin = request.getParameter("fechafin");
    String sFechadellunes = request.getParameter("fechadellunes");
    String sId_estado = request.getParameter("id_estado");
    String sNombreInforme = request.getParameter("nombre_informe");
    String sNro_filtro = request.getParameter("nro_filtro");
    String sBotoncillo = request.getParameter("_botoncillo");
    modelo.put("nro_filtro", sNro_filtro);
    modelo.put("_botoncillo", sBotoncillo);
    modelo.put("nombre_informe", sNombreInforme);
    modelo.put("fechainicio", sFecha_ini);
    modelo.put("fechafin", sFecha_fin);
    modelo.put("fechadellunes", sFechadellunes);
    modelo.put("id_estado", sId_estado);
    //FIN RECUPERA

    String sId_tipo_proceso = request.getParameter("id_tipo_proceso");
    modelo.put("id_tipo_proceso", sId_tipo_proceso);

    String sBanderaKardex= request.getParameter("banderakardex");
    modelo.put("banderakardex", sBanderaKardex);    modelo.put("id_tramite", sId_tramite);

    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_actividad", sId_actividad);
    modelo.put("id_informe", sId_informe);
    modelo.put("cantInformes", sCantInformes);

    return new ModelAndView("imprimirInformes/ListarInformesActividad1", modelo);
  }

}
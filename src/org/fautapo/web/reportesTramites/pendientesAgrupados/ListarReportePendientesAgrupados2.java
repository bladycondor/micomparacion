package org.fautapo.web.reportesTramites.pendientesAgrupados;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Informes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.EnviarCorreo;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-28
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-28
*/

public class ListarReportePendientesAgrupados2 implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Tramites datosTramite = new Tramites(); Tramites datosFrLog;
    int iResultado;
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    
    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");    
    String sId_form = request.getParameter("id_form");
    String sId_actividad = request.getParameter("id_actividad"); 
    String sAccion = request.getParameter("accion");
    String sId_tipo_proceso = request.getParameter("id_tipo_proceso");    
    String sId_tipo_actuacion = request.getParameter("id_tipo_actuacion");
    String sActuacion = request.getParameter("actuacion");
    String sPara = request.getParameter("para");
    String sAplicacion = request.getParameter("aplicacion");
    String sId_usuario = request.getParameter("id_usuario");
    modelo.put("id_tipo_proceso", sId_tipo_proceso);
    modelo.put("id_usuario", sId_usuario);
    modelo.put("aplicacion", sAplicacion);

    //RECUPERANDO EL ESTADO Y FECHAS 
    String sFecha_ini = request.getParameter("fechainicio");
    String sFecha_fin = request.getParameter("fechafin");
    String sFechadellunes = request.getParameter("fechadellunes");
    String sId_estado = request.getParameter("id_estado");

    //Lista Tipos procesos
    List lTiposProcesos = this.mi.getListarTiposProcesos();
    modelo.put("lTiposProcesos", lTiposProcesos);     

    //Dando formato a las fechas
    if (!"".equals(sId_estado)) {
      datosTramite.setPara(Integer.parseInt(sId_usuario));
      datosTramite.setId_estado(sId_estado);
      if (((!"".equals(sFecha_ini)) && (!"".equals(sFecha_fin))) && ((sFecha_ini != null) &&(sFecha_fin != null)))  {
        datosTramite.setFecha_ini(sFecha_ini);
        datosTramite.setFecha_fin(sFecha_fin);
        List lTramites = this.mi.getListarTramitesMiosAgrupados(datosTramite);
	modelo.put("fechainicio", sFecha_ini);
        modelo.put("fechafin", sFecha_fin);
	
	for (int i = 0; i < lTramites.size(); i++) {
          Tramites auxiliar = (Tramites) lTramites.get(i);
          if (auxiliar.getId_tipo_actuacion() == 2) {
            auxiliar.setOrden(Integer.parseInt(auxiliar.getActuacion()));
          }
          else {
            auxiliar.setOrden(0);
          }
          auxiliar.setUsuarios(this.mi.getListarUsuariosActividadSiguiente(auxiliar));
          auxiliar.setFilas(auxiliar.getUsuarios().size());
          auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
          lTramites.set(i, auxiliar);
        }
	modelo.put("lMisPendientes", lTramites);
      }
      else {
        datosTramite.setFecha_fin(sFechadellunes);
	List lTramites = this.mi.getListarTramitesMiosAgrupados2(datosTramite);
	modelo.put("fechadellunes", sFechadellunes);
	for (int i = 0; i < lTramites.size(); i++) {
          Tramites auxiliar = (Tramites) lTramites.get(i);
          if (auxiliar.getId_tipo_actuacion() == 2) {
            auxiliar.setOrden(Integer.parseInt(auxiliar.getActuacion()));
          }
          else {
            auxiliar.setOrden(0);
          }
          auxiliar.setUsuarios(this.mi.getListarUsuariosActividadSiguiente(auxiliar));
          auxiliar.setFilas(auxiliar.getUsuarios().size());
          auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
          lTramites.set(i, auxiliar);
        }
	modelo.put("lMisPendientes", lTramites);
      }
    }//Fin control de id_estado
    modelo.put("nombres", cliente.getNombres());
    modelo.put("id_estado", sId_estado);
    return new ModelAndView("reportesTramites/pendientesAgrupados/ListarReportePendientesAgrupados2", modelo);
  }
}
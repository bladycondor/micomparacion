package org.fautapo.web.reportesTramites.camposReferencia;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Adjuntos;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class BuscarTramite implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    int iResultado;
    Tramites datosTramite; Tramites datosTramite2;
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sAccion = request.getParameter("boton");
    String sId_tramite = request.getParameter("id_tramite");
    String sAplicacion = request.getParameter("aplicacion");
    modelo.put("aplicacion", sAplicacion);
    
    //Sacamos el formato de la fecha definida en parametros
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
    //FIN - Sacamos el formato de la fecha definida en parametros

    //Sacamos el formato de la hora definida en parametros
    Abm formatoHora = new Abm();
    formatoHora.setCampo("formato_hora");
    formatoHora.setCodigo("dibrap");
    modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoHora));
    //FIN - Sacamos el formato de la hora definida en parametros

    if ((!"".equals(sId_tramite)) && (sId_tramite != null)) {
      try {
        datosTramite = new Tramites();
        datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
        datosTramite.setId_ubicacion_organica(cliente.getId_ubicacion_organica());
        iResultado = this.mi.getBuscarTramiteExisteUbicacionOrganica(datosTramite);
	if (iResultado == 0) {
	  return new ModelAndView("Aviso", "mensaje", "Este proceso no es de esta área");
	}
	 
	//Sacamos los datos del tramite
	datosTramite2 = (Tramites) this.mi.getBuscarTramiteUbicacionOrganica(datosTramite);
	modelo.put("datosTramiteUbiOrg", datosTramite2);
      
        //Sacamos los datos del tramite
	datosTramite = new Tramites();
	datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
	datosTramite = (Tramites) this.mi.getBuscarTramite(datosTramite);
	modelo.put("datosTramite", datosTramite);

	List lValores = this.mi.getListarDatosTramite(datosTramite);
	modelo.put("lValores", lValores);

        //Sacamos los valores del tramite
	datosTramite = new Tramites();
	datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
	datosTramite = (Tramites) this.mi.getBuscarTramite(datosTramite);
	modelo.put("datosTramite", datosTramite);
	  
        //Buscamos los datos del proceso
        Actividades datosProceso = new Actividades();
	datosProceso.setId_proceso(datosTramite.getId_proceso());
	datosProceso = this.mi.getBuscarProceso(datosProceso);
        modelo.put("datosProceso", datosProceso);
	//Fin
	  
	//Sacamos los datos de la actividad actual
        Actividades datosActividad = new Actividades();
	datosActividad.setId_actividad(datosTramite.getId_actividad_actual());
        datosActividad = this.mi.getBuscarActividad(datosActividad);
        modelo.put("datosActividad", datosActividad);
	//Fin Sacamos los datos de la actividad actual

        //Listamos los campos de referencia
	List lReferencias = this.mi.getListarCamposReferencia(datosTramite);
        modelo.put("lReferencias", lReferencias);
        //Fin Listamos los campos de referencia

        //Listamos las actividades del proceso
	List lActividades = this.mi.getListarActividades(datosProceso);
	modelo.put("lActividades", lActividades);
        //Fin Listamos las actividades del proceso

	//Listamos el recorrido del tramite
        List lTramitesLog = this.mi.getListarTramiteLog(datosTramite); //getListarTramites(datosTramite));
        for (int i = 0; i < lTramitesLog.size(); i++) {
	  datosTramite2 = (Tramites) lTramitesLog.get(i);
          String sIntervalo =datosTramite2.getDias();
          sIntervalo = sIntervalo.replace('.', '-');  //reemplazando el . por -
          String sTiempo[] = sIntervalo.split(":");
          String sTiempo_segundos = sTiempo[2];
          String sTiempo_minutos = sTiempo[1];
          String sTiempo_diayhora = sTiempo[0];
      
          String sTiempo_dia[] = sTiempo[0].split("day");  ///SACANDO EL DIA APARTE y LA HORA APARTE
    	  if (sTiempo_dia.length > 1) {
    	    String sTiempo_horas1 = sTiempo_dia[1].replace('s',' ');
            String sTiempo_dias1  = sTiempo_dia[0];
    	    datosTramite2.setDia(Integer.parseInt(sTiempo_dias1.trim()));
    	    datosTramite2.setHoras(Integer.parseInt(sTiempo_horas1.trim()));
    	  }
    	  else {
    	    String sTiempo_dias1="0";
    	    String sTiempo_horas1 = sTiempo[0];
    	    datosTramite2.setDia(Integer.parseInt(sTiempo_dias1.trim()));
    	    datosTramite2.setHoras(Integer.parseInt(sTiempo_horas1.trim()));
    	  }
      
    	  String sTiempo_segundos_mili[] = sTiempo[2].split("-");  ///SACANDO LOS SEGUNDO Y MILISEGUNDOS APARTE
    	  if (sTiempo_segundos_mili.length >1) {
    	    String sTiempo_segundos1 = sTiempo_segundos_mili[0];
    	    String sTiempo_milisegundo = sTiempo_segundos_mili[1];
    	    datosTramite2.setSegundos(Integer.parseInt(sTiempo_segundos1.trim()));
    	    datosTramite2.setMilisegundos(Integer.parseInt(sTiempo_milisegundo.trim()));
    	  }
    	  else{
    	    String sTiempo_segundos1 = sTiempo[2];
    	    String sTiempo_milisegundo ="0";
    	    datosTramite2.setSegundos(Integer.parseInt(sTiempo_segundos1.trim()));
    	    datosTramite2.setMilisegundos(Integer.parseInt(sTiempo_milisegundo.trim()));
    	  }
          datosTramite2.setMinutos(Integer.parseInt(sTiempo_minutos.trim()));
          lTramitesLog.set(i, datosTramite2);
        }
        modelo.put("lTramitesLog", lTramitesLog);
        //FIN

        //Listamos los adjuntos del tramite
        Adjuntos datosAdjunto = new Adjuntos();
        datosAdjunto.setId_tramite(datosTramite.getId_tramite());
        List lAdjuntos = this.mi.getListarAdjuntos(datosAdjunto);
        modelo.put("lAdjuntos", lAdjuntos);
        //Fin Listamos los adjuntos del tramite
        return new ModelAndView("reportesTramites/camposReferencia/ListarDetalleTramite", modelo);
      }
      catch(Exception e) {
	return new ModelAndView("reportesTramites/camposReferencia/ListarCamposReporte", modelo);
      }
    }
    return new ModelAndView("reportesTramites/camposReferencia/ListarCamposReporte", modelo);
  }
}
package org.fautapo.web.administrarMisPendientesAgrupados;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.Format;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Informes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Abm;
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

public class ListarMisPendientesAgrupados implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

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

    //Estadistica
    //Sacamos la fecha actual
    Date dFecha_actual = new Date();
    String sDiax   = Integer.toString(dFecha_actual.getDate());
    String sMesx   = Integer.toString(dFecha_actual.getMonth() + 1);
    String sAniox  = Integer.toString(dFecha_actual.getYear() + 1900);
    
    //Generando la fecha de hoy y el dia correspondiente
    Date dFechita = new Date();
    int iAnio = dFechita.getYear()+ 1900;
    int iMes  = dFechita.getMonth();
    String sDia="";
    int iNro_dia=0;
    switch(dFechita.getDay()) {
      case(0): sDia = "Domingo";   iNro_dia=dFechita.getDay();break;
      case(1): sDia = "Lunes";     iNro_dia=dFechita.getDay();break;     
      case(2): sDia = "Martes";    iNro_dia=dFechita.getDay();break;
      case(3): sDia = "Miercoles"; iNro_dia=dFechita.getDay();break;
      case(4): sDia = "Jueves";    iNro_dia=dFechita.getDay();break;
      case(5): sDia = "Viernes";   iNro_dia=dFechita.getDay();break;
      case(6): sDia = "Sabado";    iNro_dia=dFechita.getDay();break;
    }
    String sDiaHoy= sDia;
    int iNro_dianuevo = iNro_dia;
        
    //Aqui sacamos la fecha del último lunes
    GregorianCalendar calendario_aux = new GregorianCalendar();
    Date dFechanueva = new Date();
    switch(iNro_dianuevo){
      case(1): dFechanueva = calendario_aux.getTime();break; //Si es Lunes no descontamos nada
      case(2): calendario_aux.add(Calendar.DATE, -1); dFechanueva = calendario_aux.getTime();break; //Si es Martes se descuenta 1 día
      case(3): calendario_aux.add(Calendar.DATE, -2); dFechanueva = calendario_aux.getTime();break;  //Si es Miércoles se descuenta 2 días
      case(4): calendario_aux.add(Calendar.DATE, -3); dFechanueva = calendario_aux.getTime();break;  //Si es Jueves se descuenta 3 días
      case(5): calendario_aux.add(Calendar.DATE, -4); dFechanueva = calendario_aux.getTime();break;  //Si es Viernes se descuenta 4 días
      case(6): calendario_aux.add(Calendar.DATE, -5); dFechanueva = calendario_aux.getTime();break;  //Si es Sábado se descuenta 5 días
      case(0): calendario_aux.add(Calendar.DATE, -6); dFechanueva = calendario_aux.getTime();break;  //Si es Domingo se descuenta 6 días
    }
	
    Date dFechainicio= dFechanueva; 
    String sDialunes="";
    switch(dFechainicio.getDay()) {
      case(0): sDialunes = "Domingo"; break;
      case(1): sDialunes = "Lunes";break;     
      case(2): sDialunes = "Martes";break;
      case(3): sDialunes = "Miercoles";break;
      case(4): sDialunes = "Jueves";break;
      case(5): sDialunes = "Viernes";break;
      case(6): sDialunes = "Sabado";break;
    }
    String sDiaSemana= sDialunes;

    Date dFechafin = new Date();
    //Sumando un dia al dia actual
    GregorianCalendar calendario_hoy = new GregorianCalendar();
    calendario_hoy.add(Calendar.DATE, +1);
    Date dFechamasuno = calendario_hoy.getTime();
    //Restando un dia al dia actual y sacando su dia
    GregorianCalendar calendario_ayer = new GregorianCalendar();
    calendario_ayer.add(Calendar.DATE, -1);
    Date dFechamenosuno = calendario_ayer.getTime();
    String sDiaayer="";
    int iNro_diaayer=0;
    switch(dFechamenosuno.getDay()) {
      case(0): sDiaayer = "Domingo";   iNro_dia=dFechita.getDay();break;
      case(1): sDiaayer = "Lunes";     iNro_dia=dFechita.getDay();break;     
      case(2): sDiaayer = "Martes";    iNro_dia=dFechita.getDay();break;
      case(3): sDiaayer = "Miercoles"; iNro_dia=dFechita.getDay();break;
      case(4): sDiaayer = "Jueves";    iNro_dia=dFechita.getDay();break;
      case(5): sDiaayer = "Viernes";   iNro_dia=dFechita.getDay();break;
      case(6): sDiaayer = "Sabado";    iNro_dia=dFechita.getDay();break;
    }
    String sDiaAyer= sDiaayer;
	
    //Saca todos los contadores de mis tramites activos totales, de hoy, semanal y antes de la semana

    //Cambiamos el formato de las fechas
    SimpleDateFormat sdf = new SimpleDateFormat(this.mi.getDibBuscarParametro(formatoFecha));
    String sFecha_inicio = String.valueOf(sdf.format(dFechainicio));
    String sFecha_fin = String.valueOf(sdf.format(dFechafin));
    //Fin - Cambiamos el formato de las fechas
    
    Tramites datosTramite = new Tramites();
    datosTramite.setPara(cliente.getId_usuario());
    datosTramite.setId_estado("A");
    datosTramite.setFecha_ini(sFecha_inicio);
    datosTramite.setFecha_fin(sFecha_fin);
    datosTramite = (Tramites) this.mi.getContarTramitesPorFechaEstado(datosTramite);
    modelo.put("totalTramitesActivos", datosTramite);
    //Fin
	
    //Saca todos los contadores de mis tramites por recibir total, de hoy, semanal y antes de la semana
    datosTramite = new Tramites();
    datosTramite.setPara(cliente.getId_usuario());
    datosTramite.setId_estado("P");
    datosTramite.setFecha_ini(sFecha_inicio);
    datosTramite.setFecha_fin(sFecha_fin);
    datosTramite = (Tramites) this.mi.getContarTramitesPorFechaEstado(datosTramite);
    modelo.put("totalTramitesPorRecibir", datosTramite);
    //Fin
	
    //Saca todos los contadores de mis tramites bloqueados total de hoy, semanal y antes de la semana 
    datosTramite = new Tramites();
    datosTramite.setPara(cliente.getId_usuario());
    datosTramite.setId_estado("B");
    datosTramite.setFecha_ini(sFecha_inicio);
    datosTramite.setFecha_fin(sFecha_fin);
    datosTramite = (Tramites) this.mi.getContarTramitesPorFechaEstado(datosTramite);
    modelo.put("totalTramitesBloqueados", datosTramite);
    //Fin
        
    //Saca todos los contadores de mis tramites despachados total de hoy, semanal y antes de la semana 
    datosTramite = new Tramites();
    datosTramite.setPara(cliente.getId_usuario());
    datosTramite.setId_estado("P");
    datosTramite.setFecha_ini(sFecha_inicio);
    datosTramite.setFecha_fin(sFecha_fin);
    datosTramite = (Tramites) this.mi.getContarTramitesPorFechaEstado2(datosTramite);
    modelo.put("totalTramitesDespachados", datosTramite);
    //Fin
        
    //Saca todos los contadores de mis tramites despachados y confirmados total de hoy, semanal y antes de la semana 
    datosTramite = new Tramites();
    datosTramite.setPara(cliente.getId_usuario());
    datosTramite.setId_estado("A");
    datosTramite.setFecha_ini(sFecha_inicio);
    datosTramite.setFecha_fin(sFecha_fin);
    datosTramite = (Tramites) this.mi.getContarTramitesPorFechaEstado2(datosTramite);
    modelo.put("totalTramitesConfirmados", datosTramite);
    //Fin
        
    //Saca el total de todos mis tramites activos, pendientes, bloqueados, despachados total, de hoy, semanal y antes de la semana 
    datosTramite = new Tramites();
    datosTramite.setPara(cliente.getId_usuario());
    datosTramite.setFecha_ini(sFecha_inicio);
    datosTramite.setFecha_fin(sFecha_fin);
    datosTramite = (Tramites) this.mi.getContarTramitesPorFecha2(datosTramite);
    modelo.put("totalTramites", datosTramite);
    //Fin
	
    //Saca el total de todos mis tramites activos, pendientes, bloqueados, despachados y confirmados total, de hoy, semanal y antes de la semana 
    datosTramite = new Tramites();
    datosTramite.setPara(cliente.getId_usuario());
    datosTramite.setFecha_ini(sFecha_inicio);
    datosTramite.setFecha_fin(sFecha_fin);
    datosTramite = (Tramites) this.mi.getContarTramitesPorFecha3(datosTramite);
    modelo.put("totalTramites2", datosTramite);
    //Fin
	
    String sFechactual= sDiaHoy + dFechita;
    modelo.put("fechahoy", dFechita);
    modelo.put("diaactual", sDiaHoy);
    modelo.put("diaayer", sDiaAyer);
    modelo.put("diasemana", sDiaSemana);
    modelo.put("fechaayer", dFechamenosuno);
    modelo.put("fechamaniana", dFechamasuno);
    modelo.put("fechadellunes", dFechainicio);
    
    return new ModelAndView("administrarMisPendientesAgrupados/ListarMisPendientesAgrupados", modelo);
  }
}
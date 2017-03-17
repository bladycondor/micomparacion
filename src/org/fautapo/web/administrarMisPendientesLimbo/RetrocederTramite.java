package org.fautapo.web.administrarMisPendientesLimbo;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Informes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-24
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-24
*/

public class RetrocederTramite implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
 
    String sProveido = request.getParameter("proveido");
    String sId_tramite = request.getParameter("id_tramite");
    String sId_proceso = request.getParameter("id_proceso");
    String sId_actividad = request.getParameter("id_actividad");

    modelo.put("id_tramite", sId_tramite);
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_actividad", sId_actividad);

    if ("".equals(sProveido)) {
      return new ModelAndView("administrarMisPendientesLimbo/RetrocederTramite", modelo);
    }
    
    //Retrocedemos el tramite
    Tramites datosTramite = new Tramites();
    datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
    datosTramite.setProveido(sProveido);
    datosTramite.setUlt_usuario(cliente.getId_usuario());
    int iResultado = this.mi.setRetrocederTramiteLimbo(datosTramite);
    if (iResultado == 0) {
      return new ModelAndView("Error", "mensaje", "El trámite no se pudo retroceder");
    }
/*
    //Para listar mis pendientes
    if ((sId_estado ==null) || ("".equals(sId_estado))) {
    */
      datosTramite.setId_proceso(Integer.parseInt(sId_proceso));
      datosTramite.setPara(cliente.getId_usuario());
      List lTramites = this.mi.getListarTramitesMiosLimbo(datosTramite);
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
      modelo.put("lMisPendientesLimbo", lTramites);

      //Listamos los procesos según el acceso del usuario
      List lProcesos = this.mi.getListarProcesosAcceso(cliente);
      modelo.put("lProcesos", lProcesos);

      return new ModelAndView("administrarMisPendientesLimbo/ListarMisPendientes", modelo);
/*    }
    else{
      //Para listar mis pendientes agrupados
      datosTramite.setPara(cliente.getId_usuario());
      datosTramite.setId_estado(sId_estado);
      if (((!"".equals(sFecha_ini)) && (!"".equals(sFecha_fin))) && ((sFecha_ini != null) &&(sFecha_fin != null))) {
        String sFechainicio[] = sFecha_ini.split("/");
        int iAnio1 = Integer.parseInt(sFechainicio[2]) - 1900;
        int iMes1 = Integer.parseInt(sFechainicio[1]) -1;
        int iDia1 = Integer.parseInt(sFechainicio[0]);
        String sFechafin[] = sFecha_fin.split("/");
        int iAnio2 = Integer.parseInt(sFechafin[2]) -1900;
        int iMes2 = Integer.parseInt(sFechafin[1]) -1;
        int iDia2 = Integer.parseInt(sFechafin[0]);
    		    
        Date dFechainicio1 = new Date(iAnio1, iMes1, iDia1);
        Date dFechafin1 = new Date(iAnio2, iMes2, iDia2);
        Date dFechainicio2 = new Date(sFecha_ini);
        Date dFechafin2 = new Date(sFecha_ini);
      
        datosTramite.setFechaini(dFechainicio1);
        datosTramite.setFechafin(dFechafin1);
        List lTramites = this.mi.getListarTramitesMiosAgrupados(datosTramite);  // CAMBIAR AQUI ***********************
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
        String sFecha_lunes[] = sFechadellunes.split("/");
        int iAnio1 = Integer.parseInt(sFecha_lunes[2]) - 1900;
        int iMes1 = Integer.parseInt(sFecha_lunes[1]) -1;
        int iDia1 = Integer.parseInt(sFecha_lunes[0]);      
        Date dFecha_lunes_fin = new Date(iAnio1, iMes1, iDia1);
	
        //Date fechaentrada = new Date(fechadellunes);
        datosTramite.setFechafin(dFecha_lunes_fin);
	
	List lTramites = this.mi.getListarTramitesMiosAgrupados(datosTramite);
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
      return new ModelAndView("administrarMisPendientesAgrupados/MostrarTramitesMiosAgrupadosEstadoSalida", modelo);
    }//FIN ENTRANDO AGRUPADOS
    */
  }
}
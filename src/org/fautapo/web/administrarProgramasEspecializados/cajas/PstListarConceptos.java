package org.fautapo.web.administrarProgramasEspecializados.cajas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Literales;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class PstListarConceptos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    double iDescuento=0; int iId_postulante=0; String sId_perfil_proceso="0";
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();

    int iId_tramite = cliente.getInt(request, "id_tramite");
    int iId_proceso = cliente.getInt(request, "id_proceso");
    modelo.put("id_tramite", Integer.toString(iId_tramite));
    modelo.put("id_proceso", Integer.toString(iId_proceso));
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));

    //Buscamos los datos del proceso
    Actividades datosProceso = new Actividades();
    datosProceso.setId_proceso(iId_proceso);
    modelo.put("datosProceso", this.mi.getBuscarProceso(datosProceso));

    Tramites tramite = new Tramites();
    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("id_postulante");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    iId_postulante = Integer.parseInt(tramite.getValores());

    Perfiles perfil = new Perfiles();
    perfil.setId_proceso(iId_proceso);
    List listaPerfiles = this.mi.getTrnMiListarPerfilesProceso(perfil);
    if (listaPerfiles.size() == 1) {
      perfil = (Perfiles) listaPerfiles.get(0);
      sId_perfil_proceso = perfil.getId_perfil_proceso();
      //Buscamos en wayka si hay un campo cantidad
      try {
        tramite = new Tramites();
        tramite.setId_tramite(iId_tramite);
        tramite.setEtiqueta("cantidad");
        tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
        int iCantidad = Integer.parseInt(tramite.getValores());
        sId_perfil_proceso = sId_perfil_proceso+"~"+iCantidad;
      } catch(Exception e) {};
    } else {
      tramite = new Tramites();
      tramite.setId_tramite(iId_tramite);
      tramite.setEtiqueta("id_perfil_proceso");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      sId_perfil_proceso = tramite.getValores();
    }

    //Sacamos los datos del Postulante
    Postulantes postulante = new Postulantes();
    postulante.setId_postulante(iId_postulante);
    modelo.put("postulante", this.mi.getPstBuscarPostulanteNombres(postulante));

    //Sacamos los datos del perfil_proceso
    Perfiles datosPerfilProceso = new Perfiles();
    datosPerfilProceso.setId_perfil_proceso(sId_perfil_proceso);
    datosPerfilProceso = this.mi.getPrcBuscarPerfil(datosPerfilProceso);

    //Sacamos los datos del perfil
    Perfiles datosPerfil = new Perfiles();
    datosPerfil.setId_perfil(datosPerfilProceso.getId_perfil());
    datosPerfil = this.mi.getPrfBuscarPerfil(datosPerfil);
    modelo.put("datosPerfil", datosPerfil);

    try {
      tramite = new Tramites();
      tramite.setId_tramite(iId_tramite);
      tramite.setEtiqueta("descuento");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      iDescuento = Double.valueOf(tramite.getValores()).doubleValue();

      tramite = new Tramites();
      tramite.setId_tramite(iId_tramite);
      tramite.setEtiqueta("id_tipo_descuento");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      //Averiguamos el nombre del tipo de descuento
      Perfiles descuento = new Perfiles();
      descuento.setId_tipo_descuento(Integer.parseInt(tramite.getValores()));
      modelo.put("descuento", this.mi.getTrnBuscarTipoDescuento(descuento));
    } catch(Exception e) {};

    perfil.setId_postulante(iId_postulante);
    perfil.setId_perfil_proceso(sId_perfil_proceso);
    perfil.setDescuento(iDescuento);

    List listaConceptos = this.mi.getPstListarConceptos(perfil);
    modelo.put("listaConceptos", listaConceptos);
    double total = 0;
    for (int i = 0; i < listaConceptos.size(); i++){
      Perfiles cajita = (Perfiles) listaConceptos.get(i);
      total += cajita.getPagado();
    }
    modelo.put("total", String.valueOf(total));
    Literales literal = new Literales();
    modelo.put("literal", literal.convert(total));

    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    return new ModelAndView("administrarProgramasEspecializados/cajas/PstListarConceptos", modelo);
  }
}
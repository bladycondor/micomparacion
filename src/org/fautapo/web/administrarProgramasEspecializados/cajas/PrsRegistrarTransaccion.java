package org.fautapo.web.administrarProgramasEspecializados.cajas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Literales;
import org.fautapo.domain.Instituciones;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class PrsRegistrarTransaccion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();

    int iResultado = 0; double iDescuento = 0; int iId_tipo_descuento = 0; int iCantidad = 0; String sId_perfil_procesox = ""; int iNro_recibo=0;
    int iId_tramite = cliente.getInt(request, "id_tramite");
    int iId_proceso = cliente.getInt(request, "id_proceso");
    modelo.put("id_tramite", Integer.toString(iId_tramite));
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));

    //INICIO - Verificamos que el tramite no haya sido atendido
    Tramites datosTramite = new Tramites();
    datosTramite.setId_tramite(iId_tramite);
    datosTramite = this.mi.getBuscarTramite(datosTramite);
    if ("L".equals(datosTramite.getId_estado())) {
      modelo.put("mensaje", "El tramite ya fue atendido");
      return new ModelAndView("administrarProgramasEspecializados/cajas/Error", modelo);
    }
    //FIN -Verificamos que el tramite no haya sido atendido

    Tramites tramite = new Tramites();
    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("id_persona");
    tramite = this.mi.getBuscarCampoGw(tramite);
    int iId_persona = Integer.parseInt(tramite.getValores());

    String sId_perfil_proceso = "";
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
        int iCantidadx = Integer.parseInt(tramite.getValores());
        sId_perfil_proceso = sId_perfil_proceso+"~"+iCantidadx;
      } catch(Exception e) {};
    } else {
      tramite = new Tramites();
      tramite.setId_tramite(iId_tramite);
      tramite.setEtiqueta("id_perfil_proceso");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      sId_perfil_proceso = tramite.getValores();
    }

    String sPerfiles_procesos[] = sId_perfil_proceso.split(":");
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
      iId_tipo_descuento = Integer.parseInt(tramite.getValores());
      //Averiguamos el nombre del tipo de descuento
      Perfiles descuento = new Perfiles();
      descuento.setId_tipo_descuento(iId_tipo_descuento);
      modelo.put("descuento", this.mi.getTrnBuscarTipoDescuento(descuento));
    } catch(Exception e) {
      tramite = new Tramites();
      tramite.setId_tramite(iId_tramite);
    }
    tramite = (Tramites) this.mi.getBuscarTramite(tramite);

    Perfiles transaccion = new Perfiles();
    Perfiles trn_detalle = new Perfiles();
    for (int i=0; i < sPerfiles_procesos.length; i++) {
      String sDatos_perfil_proceso[] = sPerfiles_procesos[i].split("~");
      if (sDatos_perfil_proceso.length == 1) {
        sId_perfil_procesox = sPerfiles_procesos[i];
	iCantidad = 1;
      } else {
        sId_perfil_procesox = sDatos_perfil_proceso[0];
	iCantidad = Integer.parseInt(sDatos_perfil_proceso[1]);
      }
      // tabla: transacciones
      transaccion.setId_perfil_proceso(sId_perfil_procesox);
      transaccion = this.mi.getPrcBuscarPerfil(transaccion);
      transaccion.setId_persona(iId_persona);
      transaccion.setId_programa(0);
      transaccion.setId_tipo_descuento(iId_tipo_descuento);
      transaccion.setId_sede(cliente.getId_almacen());
      transaccion.setRemitente(tramite.getDe());
      transaccion.setCantidad(iCantidad);
      transaccion.setDeposito(0);
      transaccion.setGestion(cliente.getGestion());
      transaccion.setPeriodo(cliente.getPeriodo());
      transaccion.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setPstRegistrarTransaccion(transaccion);

      //tabla: trn_detalles
      transaccion.setDescuento(iDescuento);
      transaccion.setId_perfil_proceso(sPerfiles_procesos[i]);
      List listaConceptos = this.mi.getPrsListarConceptos(transaccion);
      if (iResultado > 0) {
        for (int j=0; j < listaConceptos.size(); j++) {
          Perfiles cajita = (Perfiles) listaConceptos.get(j);
  	  trn_detalle.setId_transaccion(iResultado);
	  trn_detalle.setId_perfil(transaccion.getId_perfil());
	  trn_detalle.setId_concepto(cajita.getId_concepto());
          trn_detalle.setId_tipo_perfil(transaccion.getId_tipo_perfil());
	  trn_detalle.setId_tipo_clasificacion(cajita.getId_tipo_clasificacion());
	  trn_detalle.setCantidad(cajita.getCantidad());
	  trn_detalle.setCosto(cajita.getCosto());
          trn_detalle.setDescuento(cajita.getDescuento());
	  trn_detalle.setUlt_usuario(cliente.getId_usuario());
          int iDetalle = this.mi.setRegistrarTrnDetalle(trn_detalle);
        }
      }
    }

    //Sacamos los datos de la transaccion
    Perfiles datosTransaccion = new Perfiles();
    datosTransaccion.setId_transaccion(iResultado);

    //Sacamos el listado de trn_detalles
    List lDetalles = this.mi.getTrnListarTrnDetalles(datosTransaccion);
    //Sacamos los datos para la impresion del RECIBO
    if (lDetalles.size() > 0) {
      //Sacamos el numero de recibo
      Perfiles datosRecibo = new Perfiles();
      datosRecibo.setId_sede(cliente.getId_almacen());
      datosRecibo.setGestion(cliente.getGestion());
      iNro_recibo = this.mi.getTrnBuscarSiguienteNroRecibo(datosRecibo);
    }
    List lDetalles2 = new ArrayList();
    for (int i=0; i<lDetalles.size(); i++) {
      Perfiles auxiliar = (Perfiles) lDetalles.get(i);
      auxiliar.setId_transaccion(iResultado);
      auxiliar.setNro_recibo(iNro_recibo+"/"+cliente.getGestion());
      this.mi.setTrnActualizarNroRecibo(auxiliar);
      lDetalles2.add(auxiliar);
    }
    modelo.put("lDetalles", lDetalles2);

    //Sacamos los datos de la transaccion
    datosTransaccion = this.mi.getTrnBuscarTransaccion(datosTransaccion);
    modelo.put("datosTransaccion", datosTransaccion);

    Literales literal = new Literales();
    modelo.put("literal", literal.convert(datosTransaccion.getTotal()));

    //Sacamos los datos de la Persona
    Postulantes postulante = new Postulantes();
    postulante.setId_persona(iId_persona);
    modelo.put("postulante", this.mi.getPstBuscarPersona(postulante));

    //Sacamos los datos del perfil
    Perfiles datosPerfil = new Perfiles();
    datosPerfil.setId_perfil(datosTransaccion.getId_perfil());
    datosPerfil = this.mi.getPrfBuscarPerfil(datosPerfil);
    modelo.put("datosPerfil", datosPerfil);

    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    //Sacamos el formato de la hora
    Abm formatoHora = new Abm();
    formatoHora.setCampo("formato_hora");
    formatoHora.setCodigo("dibrap");
    modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoHora));

    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }
    
    Instituciones datosInstitucionSede = new Instituciones();
    datosInstitucionSede.setId_institucion(cliente.getId_almacen()); //--------------------------ESTATICO
    datosInstitucionSede = this.mi.getBuscarInstitucionSede(datosInstitucionSede);
    if (datosInstitucionSede !=null) {
      modelo.put("datosInstitucionsede", datosInstitucionSede);
    }
    
    tramite.setUlt_usuario(cliente.getId_usuario());
    this.mi.setRegistrarTrPrFrLogLimbo(tramite);

    return new ModelAndView("administrarProgramasEspecializados/cajas/PrsListarConceptosImpresion", modelo);
  }
}
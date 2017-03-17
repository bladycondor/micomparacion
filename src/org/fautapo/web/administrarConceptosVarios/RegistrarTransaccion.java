package org.fautapo.web.administrarConceptosVarios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
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

public class RegistrarTransaccion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap(); int iNro_recibo = 0;
    
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));

    String sId_perfiles_conceptos[] = request.getParameterValues("id_perfil_concepto");
    String sCantidades[] = request.getParameterValues("cantidades");
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("id_perfil_varios");
    formatoFecha.setCodigo("cajas");
    int iId_perfil = Integer.parseInt(this.mi.getDibBuscarParametro(formatoFecha));

    Perfiles transaccion = new Perfiles();
    transaccion.setId_perfil(iId_perfil);
    transaccion.setId_persona(0);
    transaccion.setId_programa(0);
    transaccion.setId_tipo_descuento(0);
    transaccion.setId_sede(cliente.getId_almacen());
    transaccion.setRemitente(cliente.getId_usuario());
    transaccion.setDeposito(0);
    transaccion.setGestion(cliente.getGestion());
    transaccion.setPeriodo(cliente.getPeriodo());
    transaccion.setUlt_usuario(cliente.getId_usuario());
    int iResultado = this.mi.setPstRegistrarTransaccion(transaccion);
    
    for (int i = 0; i< sId_perfiles_conceptos.length; i++) {
      Perfiles trn_detalle = new Perfiles();
      trn_detalle.setId_perfil_concepto(Integer.parseInt(sId_perfiles_conceptos[i]));
      trn_detalle = this.mi.getBuscarPerfilConcepto(trn_detalle);
      trn_detalle.setId_transaccion(iResultado);
      trn_detalle.setId_tipo_perfil(1);
      trn_detalle.setId_tipo_clasificacion(1);
      trn_detalle.setCantidad(Integer.parseInt(sCantidades[i]));
      trn_detalle.setDescuento(0);
      trn_detalle.setUlt_usuario(cliente.getId_usuario());
      int iDetalle = this.mi.setRegistrarTrnDetalle(trn_detalle);
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

    //Sacamos los datos del perfil
    Perfiles datosPerfil = new Perfiles();
    datosPerfil.setId_perfil(datosTransaccion.getId_perfil());
    datosPerfil = this.mi.getPrfBuscarPerfil(datosPerfil);
    modelo.put("datosPerfil", datosPerfil);

    //Sacamos el formato de la fecha
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
    return new ModelAndView("administrarConceptosVarios/ListarConceptosImpresion", modelo);
  }
}
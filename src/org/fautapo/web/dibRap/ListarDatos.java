package org.fautapo.web.dibRap;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Abm;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Enlaces;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class ListarDatos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) {
      return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente.");
    }
    modelo.put("cliente", cliente);

    String sId_tabla   = cliente.getString(request, "t");   // id_tabla
    String sPermiso    = cliente.getString(request, "p");   // Permiso
    String sNro_pagina = cliente.getString(request, "n"); // Paginacion
    String s_f         = cliente.getString(request, "_f"); //
    String s_order     = cliente.getString(request, "_order"); //
    String s_order_ant = cliente.getString(request, "_order_ant"); //

    String sCampo      = cliente.getString(request, "_campo");     //
    String sCondicion  = cliente.getString(request, "_condicion"); // Condicion (filtro)
    String sFiltro     = cliente.getString(request, "_filtro");    //
    String sBotoncillo = cliente.getString(request, "_botoncillo");

    Abm tabla = new Abm();
    tabla.setId_tabla(Integer.parseInt(sId_tabla));
    tabla = this.mi.getBuscarTabla(tabla);
    if (tabla == null) {
      return new ModelAndView("Error", "mensaje", "No existe esta dirección");
    }
    String sId_enlace = cliente.getString(request, "e"); // id_enlace
    modelo.put("id_enlace", sId_enlace);
    modelo.put("permiso", sPermiso);

    if ((!"".equals(sFiltro)) && ("filtro".equals(sBotoncillo))){
      if (" like ".equals(sCondicion)) {
        tabla.setCondicion("UPPER( " + sCampo + " )" + sCondicion + "UPPER('%" + sFiltro + "%')");
      } else {
        tabla.setCondicion("UPPER( " + sCampo + " )" + sCondicion + "UPPER('" + sFiltro + "')");
      }
    } else {
      tabla.setCondicion("");
    }
    //tabla.setCondicion((sFiltro == null)? "" : sFiltro);
    if ("todo".equals(sBotoncillo)){
      s_f = "";
      sFiltro = "";
      sCampo  = "";
      sCondicion = "";
    }
    if (!"".equals(s_f)) {
      if (!"".equals(tabla.getCondicion())) {
        tabla.setCondicion(" AND " + tabla.getCondicion());
      }
      tabla.setCondicion(s_f + tabla.getCondicion());
    }

    if ("".equals(sNro_pagina)) {
      sNro_pagina = "1";
    }
    tabla.setPagina(Integer.parseInt(sNro_pagina));

    tabla.setPermiso("l");
    List listaNombresCampos;
    List listaRegistros;
    String totalRegistros_Paginas[] = new String[3];

    String sId_actividad = cliente.getString(request, "a");
    if (!"".equals(sId_actividad)) {  // Variable viene desde huaica
      tabla.setColumnas(Integer.parseInt(sId_actividad));  // columnas <- id_actividad
      listaNombresCampos = this.mi.getListarCamposTablaActividad(tabla);
      if ("".equals(s_order)) {
        s_order = ((Abm) listaNombresCampos.get(0)).getCampo();
      } else if (s_order.equals(s_order_ant)) {
        s_order = s_order + " DESC";
      }
      tabla.setCampo(s_order);
      listaRegistros = this.mi.getEjecutarListado3(tabla);
      modelo.put("id_actividad", sId_actividad);
    } else {  // dibRap clásico

      // Inicio Combustible
      //buscar enlace en "_dib_enl_campos"
      Enlaces enlace = new Enlaces();
      enlace.setId_enlace(Integer.parseInt(sId_enlace));
      enlace = this.mi.getEnlBuscarEnlace(enlace);
      if (enlace != null) {
        tabla.setColumnas(Integer.parseInt(sId_enlace));  // columnas <- id_enlace
        listaNombresCampos = this.mi.getEnlListarCamposTabla(tabla);
        if ("".equals(s_order)) {
          s_order = ((Abm) listaNombresCampos.get(0)).getCampo();
        } else if (s_order.equals(s_order_ant)) {
          s_order = s_order + " DESC";
        }
        tabla.setCampo(s_order);
        listaRegistros = this.mi.getEnlEjecutarListado(tabla);
      }
      // Fin Combustible
      else {
        totalRegistros_Paginas = this.mi.getDibContadorClasico(tabla).split("#~~#");
        listaNombresCampos = this.mi.getListarCamposTabla(tabla);
        if ("".equals(s_order)) {
          s_order = ((Abm) listaNombresCampos.get(0)).getCampo();
        } else if (s_order.equals(s_order_ant)) {
          s_order = s_order + " DESC";
        }
        tabla.setCampo(s_order);
        listaRegistros = this.mi.getEjecutarListado2(tabla);
      }
    }

    tabla.setPermiso("p");
    List listaLlavesPrimarias = this.mi.getListarCamposTabla(tabla);
    List listaValoresPrimarios = this.mi.getListarRegistros(tabla);
    modelo.put("listaValoresPrimarios", listaValoresPrimarios);

    modelo.put("totalRegistros", totalRegistros_Paginas[0]);
    modelo.put("totalPaginas", totalRegistros_Paginas[1]);
    modelo.put("paginacion", totalRegistros_Paginas[2]);
    modelo.put("listaNombresCampos", listaNombresCampos);
    modelo.put("listaRegistros", listaRegistros);
    modelo.put("nro_campos", Integer.toString(listaNombresCampos.size()));
    String matrizDatos[][] = new String[listaRegistros.size()][listaNombresCampos.size()];
    for (int j = 0; j < listaRegistros.size(); j++) {
      Abm fila = (Abm) listaRegistros.get(j);
      String campos[] = fila.getValores().split("#~~#");  // Separador de campos
      for (int i = 0; i < campos.length; i++) {
        matrizDatos[j][i] = campos[i];
      }
    }
    if ("0".equals(totalRegistros_Paginas[0])) {
      tabla.setCondicion("");
      sFiltro = "";
      sCampo  = "";
      sCondicion = "";
    }
    modelo.put("tabla", tabla);
    modelo.put("filtro", sFiltro);
    modelo.put("campo", sCampo);
    modelo.put("condicion", sCondicion);
    modelo.put("matrizDatos", matrizDatos);
    return new ModelAndView("dibRap/ListarDatos", modelo);
  }
}
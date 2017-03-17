package org.fautapo.web.dibRapFiltrado;

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
    cliente.getId_usuario();
    modelo.put("cliente", cliente);

    String sId_tabla = request.getParameter("t"); // id_tabla
    String sPermiso  = request.getParameter("p"); // Permiso
    String sFiltro   = request.getParameter("f"); // Condicion (filtro)
    String sNro_pagina = request.getParameter("n"); // Paginacion

    Abm tabla = new Abm();
    tabla.setId_tabla(Integer.parseInt(sId_tabla));
    tabla = this.mi.getBuscarTabla(tabla);
    if (tabla == null) {
      return new ModelAndView("Error", "mensaje", "No existe esta dirección");
    }
    // Filtro de la sesión
    if ("".equals(cliente.getFiltro())) {
      return new ModelAndView("Error", "mensaje", "No tiene autorización para ver estos datos");
    }

    String sId_enlace = request.getParameter("e"); // id_enlace
    modelo.put("id_enlace", sId_enlace);
    modelo.put("permiso", sPermiso);
    modelo.put("condicion", sFiltro);

    if (null == sNro_pagina) {
      sNro_pagina = "1";
    }
    tabla.setPagina(Integer.parseInt(sNro_pagina));

    modelo.put("tabla", tabla);
    tabla.setPermiso("p");
    List listaLlavesPrimarias = this.mi.getListarCamposTabla(tabla);
    tabla.setCampo("");
    // Filtro de la sesión
    tabla.setCondicion(cliente.getFiltro());
    List listaValoresPrimarios = null;
    try {
      listaValoresPrimarios = this.mi.getListarRegistros(tabla);
    } catch(Exception e) {
      tabla.setCondicion((sFiltro == null)? "" : sFiltro);
      listaValoresPrimarios = this.mi.getListarRegistros(tabla);
    }
    modelo.put("listaValoresPrimarios", listaValoresPrimarios);

    tabla.setPermiso("l");
    List listaNombresCampos;
    List listaRegistros;

    String sId_actividad = request.getParameter("a");
    if (!"".equals(sId_actividad)) {  // Variable viene desde huaica
      tabla.setColumnas(Integer.parseInt(sId_actividad));  // columnas <- id_actividad
      listaNombresCampos = this.mi.getListarCamposTablaActividad(tabla);
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
        listaRegistros = this.mi.getEnlEjecutarListado(tabla);
      }
      // Fin Combustible
      else {
        listaNombresCampos = this.mi.getListarCamposTabla(tabla);
        listaRegistros = this.mi.getEjecutarListado2(tabla);
      }
    }

    modelo.put("listaNombresCampos", listaNombresCampos);
    modelo.put("nro_campos", Integer.toString(listaNombresCampos.size()));
    String matrizDatos[][] = new String[listaRegistros.size()][listaNombresCampos.size()];
    for (int j = 0; j < listaRegistros.size(); j++) {
      Abm fila = (Abm) listaRegistros.get(j);
      String campos[] = fila.getValores().split("#~~#");  // Separador de campos
      for (int i = 0; i < campos.length; i++) {
        matrizDatos[j][i] = campos[i];
      }
    }
    modelo.put("matrizDatos", matrizDatos);
    return new ModelAndView("dibRapFiltrado/ListarDatos", modelo);
  }
}
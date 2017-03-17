package org.fautapo.web.dibRapFiltrado;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Enlaces;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
 */

public class ModificaRegistro implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    int iId_tabla = Integer.parseInt(request.getParameter("t")); //id_tabla
    String sValoresPrimarios = request.getParameter("c"); //Llave Primaria
    String sPermiso = request.getParameter("p"); // Permiso
    String sFiltro  = request.getParameter("f"); // Condicion (filtro)

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    modelo.put("id_enlace", request.getParameter("e"));
    modelo.put("permiso", sPermiso);
    modelo.put("condicion", sFiltro);
    //LA TABLA A USARSE
    Abm tabla = new Abm();
    tabla.setId_tabla(iId_tabla);
    tabla = this.mi.getBuscarTabla(tabla);
    modelo.put("tabla", tabla);
    tabla.setPermiso("p");
    List listaLlavesPrimarias = this.mi.getListarCamposTabla(tabla);
    String vValoresPrimarios[] = sValoresPrimarios.split("#~~#");
    String sCondicion = "";
    for (int i = 0; i < listaLlavesPrimarias.size(); i++) {
      Abm campo = (Abm) listaLlavesPrimarias.get(i);
      sCondicion = sCondicion + campo.getCampo() + " = '" + vValoresPrimarios[i] + "' AND ";
    }
    tabla.setCondicion(sCondicion.substring(0, sCondicion.length() - 4));
    //PERMISO PARA SACAR CAMPOS DE MODIFICACION
    tabla.setCampo("");

    List listaCampos = null;
    List listaRegistros = null;
    String sId_actividad = request.getParameter("a");
    tabla.setPermiso("lm");
    if (!"".equals(sId_actividad)) {  // Variable viene desde huaica
      tabla.setColumnas(Integer.parseInt(sId_actividad));  // columnas <- id_actividad
      listaCampos = this.mi.getListarCamposTablaActividad(tabla);
      listaRegistros = this.mi.getListarRegistrosActividad(tabla);
      modelo.put("id_actividad", sId_actividad);
    } else {  // dibRap clásico
      // Inicio Combustible
      //buscar enlace en "_dib_enl_campos"
      Enlaces enlace = new Enlaces();
      enlace.setId_enlace(Integer.parseInt(request.getParameter("e")));
System.out.println("ID_ENLACE : " + enlace.getId_enlace());
      enlace = this.mi.getEnlBuscarEnlace(enlace);
      if (enlace != null) {
System.out.println("DENTRO DEL IF DE ENLACES");
        tabla.setColumnas(enlace.getId_enlace());  // columnas <- id_enlace
        listaCampos = this.mi.getEnlListarCamposTabla(tabla);
        listaRegistros = this.mi.getEnlListarRegistros(tabla);
      }
      // Fin Combustible
      else {
        tabla.setPermiso("m");
        listaCampos = this.mi.getListarCamposTabla(tabla);
        listaRegistros = this.mi.getListarRegistros(tabla);
      }
    }

    modelo.put("listaCampos", listaCampos);
    Abm fila = (Abm) listaRegistros.get(0);
    String matrizDatos[] = fila.getValores().split("#~~#");
    modelo.put("matrizDatos", matrizDatos);
    String sObligatorios = "";
    for (int i = 0; i < listaCampos.size(); i++) {
      Abm aux = (Abm)listaCampos.get(i);
      if (aux.getPermiso().indexOf("o") > -1) {
        sObligatorios = sObligatorios + ";" + aux.getCampo();
      }
      //aux.setValores(matrizDatos[i]);
      Abm foranea = this.mi.getBuscarForanea(aux);
      if (foranea != null) {
        if ("".equals(aux.getCampo_padre())) {
          aux.setCampo_padre("''");
        }
	if (aux.getPermiso().indexOf("m") == -1) {
          foranea.setCondicion(foranea.getId_campo_foraneo() + " = '" + matrizDatos[i] + "'");
	} else {
  	  aux.setCondicion(foranea.getCondicion());
	  // Filtro de la sesión
          foranea.setCondicion(("".equals(cliente.getFiltro()))? sFiltro : cliente.getFiltro());
	}
        foranea.setCampo(foranea.getId_campo_foraneo() + "#~~#" + foranea.getCampo_foraneo() + "#~~#" + aux.getCampo_padre());
	try {
          aux.setCombo(this.mi.getListarCombos(foranea));
        } catch (Exception e) {
          foranea.setCondicion(aux.getCondicion());
          aux.setCombo(this.mi.getListarCombos(foranea));
	}
      }
      listaCampos.set(i, aux);
    }
    modelo.put("listaCampos", listaCampos);
    modelo.put("valoresPrimarios", sValoresPrimarios);
    if (!"".equals(sObligatorios)) {
      modelo.put("obligatorios", sObligatorios.substring(1));
    }
    return new ModelAndView("dibRapFiltrado/ModificaRegistro", modelo);
  }
}
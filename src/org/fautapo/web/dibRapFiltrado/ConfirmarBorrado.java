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

public class ConfirmarBorrado implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    int iId_tabla = Integer.parseInt(request.getParameter("t"));
    String sId_enlace = request.getParameter("e");
    modelo.put("id_enlace", sId_enlace);
    String sPermiso = request.getParameter("p"); // Permiso
    modelo.put("permiso", sPermiso);
    String sFiltro = request.getParameter("f"); // Condicion (filtro)
    modelo.put("condicion", sFiltro);
    String sValoresPrimarios = request.getParameter("c");
    modelo.put("valoresPrimarios", sValoresPrimarios);
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
    //PERMISO PARA SACAR CAMPOS DE LISTADO
    tabla.setPermiso("l");
    tabla.setCampo("");
    List listaCampos;
    List listaRegistros;

    String sId_actividad = request.getParameter("a");
    if (!"".equals(sId_actividad)) {  // Variable viene desde huaica
      tabla.setColumnas(Integer.parseInt(sId_actividad));  // columnas <- id_actividad
      listaCampos = this.mi.getListarCamposTablaActividad(tabla);
      listaRegistros = this.mi.getListarRegistrosActividad(tabla);
      modelo.put("id_actividad", sId_actividad);
    } else {  // dibRap clásico
      // Inicio Combustible
      //buscar enlace en "_dib_enl_campos"
      Enlaces enlace = new Enlaces();
      enlace.setId_enlace(Integer.parseInt(sId_enlace));
      enlace = this.mi.getEnlBuscarEnlace(enlace);
      if (enlace != null) {
        tabla.setColumnas(Integer.parseInt(sId_enlace));  // columnas <- id_enlace
        listaCampos = this.mi.getEnlListarCamposTabla(tabla);
        listaRegistros = this.mi.getEnlListarRegistros(tabla);
      }
      // Fin Combustible
      else {
        listaCampos = this.mi.getListarCamposTabla(tabla);
        listaRegistros = this.mi.getListarRegistros(tabla);
      }
    }

    modelo.put("listaCampos", listaCampos);
    Abm fila = (Abm) listaRegistros.get(0);
    String matrizDatos[] = fila.getValores().split("#~~#");
    for (int i = 0; i < listaCampos.size(); i++) {
      Abm foranea = (Abm) listaCampos.get(i);
      foranea = (Abm) this.mi.getBuscarForanea(foranea);
      if (foranea != null) {
        foranea.setTabla(foranea.getTabla_foranea());
        foranea.setCampo_padre(foranea.getId_campo_foraneo());
        String tipo_dato = ((Abm) this.mi.getBuscarCampoTabla(foranea)).getTipo_dato();
        foranea.setCondicion(foranea.getId_campo_foraneo() + " = '" + matrizDatos[i] + "'");
        foranea.setCampo(foranea.getId_campo_foraneo() + "#~~#" + foranea.getCampo_foraneo() + "#~~#''");
        foranea = (Abm) (this.mi.getListarCombos(foranea)).get(1);
        matrizDatos[i] = foranea.getCampo();
      }
    }
    modelo.put("matrizDatos", matrizDatos);
    return new ModelAndView("dibRapFiltrado/ConfirmarBorrado", modelo);
  }
}
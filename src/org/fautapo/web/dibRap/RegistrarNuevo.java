package org.fautapo.web.dibRap;

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

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class RegistrarNuevo implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String sId_enlace = request.getParameter("e");
    modelo.put("id_enlace", sId_enlace);
    String sPermiso = request.getParameter("p"); // Permiso
    modelo.put("permiso", sPermiso);
    String sFiltro = request.getParameter("f"); // Condicion (filtro)
    modelo.put("condicion", sFiltro);
    int iId_tabla = Integer.parseInt(request.getParameter("t"));
    Abm tabla = new Abm();
    tabla.setId_tabla(iId_tabla);
    tabla = this.mi.getBuscarTabla(tabla);
    modelo.put("tabla", tabla);
    tabla.setPermiso("a");
    List listaCampos = null;
    // Inicio Combustible
    //buscar enlace en "_dib_enl_campos"
    Enlaces enlace = new Enlaces();
    enlace.setId_enlace(Integer.parseInt(sId_enlace));
    enlace = this.mi.getEnlBuscarEnlace(enlace);
    if (enlace != null) {
      tabla.setColumnas(Integer.parseInt(sId_enlace));  // columnas <- id_enlace
      listaCampos = this.mi.getEnlListarCamposTabla(tabla);
    }
    // Fin Combustible
    else {
      listaCampos = this.mi.getListarCamposTabla(tabla);
    }

    String sql = "INSERT INTO " + tabla.getTabla() +"(";
    String campos = "";
    String valores = "";
    for (int i = 0; i < listaCampos.size(); i++) {
      Abm abm = (Abm) listaCampos.get(i);
      if (!"<NULL>".equals(request.getParameter(abm.getCampo()))) {
        campos = campos + abm.getCampo() + ", ";
        valores = valores + (abm.getTipo_dato().indexOf("fecha") > -1 ? "_cadena_fecha('" : "'") + request.getParameter(abm.getCampo()) + (abm.getTipo_dato().indexOf("fecha") > -1 ? "')" : "'::" + abm.getTipo_dato()) + ", ";
      }
    }
    sql = sql + campos + "ult_usuario) values(";
    sql = sql + valores + cliente.getId_usuario() + ");";

    tabla.setSql(sql);
    try {
      this.mi.setEjecutarConsulta(tabla);
      modelo.put("mensaje", "Los datos se registraron correctamente");
    } catch (Exception e) {
      // No es lo adecuado, pero suma
      String mensajes[] = ((String[]) (e.getCause().getMessage().split("SQLException: ERROR: ")))[1].split("Detail:");

      System.out.println("dibRap - " + mensajes[0]);
      //String problema = "Hubo un error al realizar la transacción en la relación '" + tabla.getTabla() + "'.<br/><br/>" + mensajes[0];
      String problema = mensajes[0];
      if (mensajes.length > 1) {
        System.out.println("dibRap - DETALLE:" + mensajes[1]);
        problema += "<br> DETALLE:" + mensajes[1] + "<hr/>SQL='" + sql + "'";
      }
      return new ModelAndView("Error", "mensaje", problema);
    }
    return new ModelAndView("dibRap/Aviso", modelo);
  }
}
package org.fautapo.web.administrarProgramasEspecializados.nuevo;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Enlaces;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Campos;
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
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();

    int iId_proceso = cliente.getInt(request, "id_proceso");
    int iId_tramite = cliente.getInt(request, "id_tramite");

    Tramites tramite = new Tramites();
    if (iId_proceso > 0 && iId_tramite == 0) {   // Crear un tramite
      tramite.setId_proceso(iId_proceso);
      tramite.setPara(cliente.getId_usuario());
      iId_tramite = this.mi.setInsertarTramiteLimbo(tramite); //CREA UN TRAMITE
      if (iId_tramite == 0) {
        return new ModelAndView("Error","mensaje","El trámite no se creó");
      }
    }
    int iId_tabla = cliente.getInt(request, "id_tabla");
    //modelo.put("id_tramite", sId_tramite);
    Abm tabla = new Abm();
    tabla.setId_tabla(iId_tabla);
    tabla = this.mi.getBuscarTabla(tabla);
    if (tabla == null) { return new ModelAndView("Error", "mensaje", "No existe la Tabla/Relación indicada"); }
    tabla.setPermiso("a");
    List listaCampos = this.mi.getListarCamposTabla(tabla);
    if (listaCampos.size() == 0) { return new ModelAndView("Error","mensaje", "La relación '" + tabla.getTabla() + "' no permite agregar datos"); }

    // Todo un testamento para encontrar el nombre del campo de la etiqueta "pref_"tabla
    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("pref_" + tabla.getTabla());
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    String prefijo = "";
    if (tramite != null) {
      int iId_campo = tramite.getId_campo();
      Campos tr_campo = new Campos();
      tr_campo.setId_campo(iId_campo);
      tr_campo = this.mi.getBuscarCampoForm(tr_campo);
      prefijo = tr_campo.getCampo();
    }

    String sql = "INSERT INTO " + tabla.getTabla() +"(";
    String campos = "";
    String valores = "";
    for (int i = 0; i < listaCampos.size(); i++) {
      Abm abm = (Abm) listaCampos.get(i);
      if (!"".equals(request.getParameter(abm.getCampo()))) {
        campos = campos + abm.getCampo() + ", ";
        valores = valores + (abm.getTipo_dato().indexOf("fecha") > -1 ? "_cadena_fecha('" : "'") + request.getParameter(abm.getCampo()) + (abm.getTipo_dato().indexOf("fecha") > -1 ? "')" : "'::" + abm.getTipo_dato()) + ", ";
        // Escribir en todos y cada uno de las etiquetas/campos de Huaica
	tramite.setId_tramite(iId_tramite);
	tramite.setEtiqueta(prefijo + abm.getCampo());
	tramite.setValor(request.getParameter(abm.getCampo())); // Corregir para que guarde el valor del combo y NO el id_...
        tramite.setUlt_usuario(cliente.getId_usuario());
        this.mi.setRegistrarValorLimbo2(tramite);
      }
    }
    sql = sql + campos + "ult_usuario) values(" + valores + cliente.getId_usuario() + ");";
    tabla.setSql(sql);
    try {
      String vPrimas[] = this.mi.setDibInsertarRegistro(tabla).split(":");      
      //Tramites tramite = new Tramites();
      tramite.setId_tramite(iId_tramite);
      tramite.setEtiqueta(vPrimas[0]);
      tramite.setValor(vPrimas[1]);
      tramite.setUlt_usuario(cliente.getId_usuario());
      this.mi.setRegistrarValorLimbo2(tramite);
      modelo.put("mensaje", "Los datos se registraron correctamente");
    } catch (Exception e) {
      // No es lo adecuado, pero suma
      String mensajes[] = ((String[]) (e.getCause().getMessage().split("SQLException: ERROR: ")))[1].split("Detail:");

      System.out.println("dibRap - " + mensajes[0]);
      String problema = mensajes[0];
      if (mensajes.length > 1) {
        System.out.println("dibRap - DETALLE:" + mensajes[1]);
        problema += "<br> DETALLE:" + mensajes[1] + "<hr/>SQL='" + sql + "'";
      }
      return new ModelAndView("Error", "mensaje", problema);
    }
    
    return new ModelAndView("administrarProgramasEspecializados/nuevo/Aviso", modelo);
  }
}
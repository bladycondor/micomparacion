package org.fautapo.web.abmDptoGrupos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Facultades;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-01-13
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarFacultades implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    modelo.put("cliente", cliente);
    
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);

    // Comprobamos si es quien debe ingresar al módulo, de acuerdo a su clave
    Usuarios usuario = new Usuarios();
    usuario.setId_usuario(cliente.getId_usuario());
    usuario.setClave(request.getParameter("clave"));
    if (null == this.mi.getComprobarUsuario(usuario)) { return new ModelAndView("abmDptoGrupos/Entrada", "cliente", cliente); }
/*
    //Listarmos los datos de las facultades
    Facultades facultad = new Facultades();
    facultad.setId_universidad(cliente.getId_universidad());
    modelo.put("lFacultades", this.mi.getUnvListarFacultades(facultad));
*/
    //Listando los tipos_evaluaciones
    List lTiposEvaluaciones = this.mi.getTpsListarTiposEvaluaciones();
    modelo.put("lTiposEvaluaciones", lTiposEvaluaciones);

    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));

    return new ModelAndView("abmDptoGrupos/ListarFacultades", modelo);
  }
}
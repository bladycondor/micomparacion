package org.fautapo.web.administrarPerfilesMaterias;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Universidades;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarPlanes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    modelo.put("cliente", cliente);

    //Si dio volver recuperamos los datos
    String sId_facultad = request.getParameter("id_facultad");
    String sId_programa = request.getParameter("id_programa");
    String sId_plan = request.getParameter("id_plan");
    String sId_perfil = request.getParameter("id_perfil");

    if (("".equals(sId_facultad)) && ("".equals(sId_programa)) && ("".equals(sId_plan)) && ("".equals(sId_perfil))) {
      // Comprobamos es quien debe, de acuerdo a su clave
      Usuarios datosUsuario = new Usuarios();
      datosUsuario.setId_usuario(cliente.getId_usuario());
      datosUsuario.setClave(request.getParameter("clave"+request.getParameter("hora")));
      if (null == this.mi.getComprobarUsuario(datosUsuario)) {
        return new ModelAndView("administrarPerfilesMaterias/Entrada", "cliente", cliente);
      }
    }
    modelo.put("id_facultad", sId_facultad);
    modelo.put("id_programa", sId_programa);
    modelo.put("id_plan", sId_plan);
    modelo.put("id_perfil", sId_perfil);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));

    //Sacamos el listado de las facultades
    Universidades datosUniversidad = new Universidades();
    datosUniversidad.setId_universidad(cliente.getId_universidad());
    List lFacultades = this.mi.getUnvListarFacultades(datosUniversidad);
    modelo.put("lFacultades", lFacultades);

    //Sacamos el listado de los programas
    List lProgramas = this.mi.getUnvListarProgramas(datosUniversidad);    
    modelo.put("lProgramas", lProgramas);

    //Sacamos el listado de los planes
    List lPlanes = this.mi.getUnvListarPlanes(datosUniversidad);
    modelo.put("lPlanes", lPlanes);

    //Sacamos el listado de los perfiles
    List lPerfiles = this.mi.getTrnListarPerfiles();
    modelo.put("lPerfiles", lPerfiles);

    //Listando los tipos_evaluaciones
    List lTiposEvaluaciones = this.mi.getTrnListarEvaluacionesVerano();
    modelo.put("lTiposEvaluaciones", lTiposEvaluaciones);

    return new ModelAndView("administrarPerfilesMaterias/ListarPlanes", modelo);
  }
}
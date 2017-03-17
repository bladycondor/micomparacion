package org.fautapo.web.habilitarPostulantespsa;

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

public class ListarProgramas implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    //Si dio volver recuperamos los datos
    
    String sGestion = cliente.getString(request, "gestion");
    String sPeriodo = cliente.getString(request, "periodo");

   

 //   if (("".equals(sId_facultad)) && ("".equals(sId_programa)) ) {
      // Comprobamos es quien debe, de acuerdo a su clave
      Usuarios datosUsuario = new Usuarios();
      datosUsuario.setId_usuario(cliente.getId_usuario());
      datosUsuario.setClave(request.getParameter("clave"+request.getParameter("hora")));
 
      if (null == this.mi.getComprobarUsuario(datosUsuario)) {
	    return new ModelAndView("Error","mensaje","Clave incorrecta, por favor intente nuevamente");
      //  return new ModelAndView("habilitarPostulantes/Entrada", "cliente", cliente);
      }
   // }
     
    modelo.put("cliente", cliente);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));

    //Sacamos el listado de las facultades
    Universidades datosUniversidad = new Universidades();
    datosUniversidad.setId_universidad(cliente.getId_universidad());
    List lFacultades = this.mi.getUnvListarFacultades(datosUniversidad);
    modelo.put("lFacultades", lFacultades);
    
    //Sacamos el listado de los programas
    List lProgramas = this.mi.getUnvListarProgramas(datosUniversidad);    
    modelo.put("lProgramas", lProgramas);
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);

    return new ModelAndView("habilitarPostulantespsa/ListarProgramas", modelo);
  }
}
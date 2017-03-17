package org.fautapo.web.cambioPinDocente.general;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class BuscarDocentePersona implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    String sDip = request.getParameter("dip");
    String sNombre = request.getParameter("nombre"); 
    
    if ((sDip == null) ||(sNombre == null)) {
      // Comprobamos si es quien debe ingresar al módulo, de acuerdo a su clave
      Usuarios usuario = new Usuarios();
      usuario.setId_usuario(cliente.getId_usuario());
      usuario.setClave(request.getParameter("clave" + request.getParameter("hora")));
      if (null == this.mi.getComprobarUsuario(usuario)) {
        return new ModelAndView("Error", "mensaje","No autorizado.Por favor ingrese su clave nuevamente");
      }
    }  
    modelo.put("dip", sDip);
    modelo.put("nombre", sNombre);
    return new ModelAndView("cambioPinDocente/general/BuscarDocentePersona", modelo);  
  }
}

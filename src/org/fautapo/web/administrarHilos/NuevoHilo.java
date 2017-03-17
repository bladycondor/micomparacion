package org.fautapo.web.administrarHilos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Hilos;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-04
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-04
*/

public class NuevoHilo implements Controller {
        
  private MiFacade mi;
 
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    int iId_usuario = cliente.getId_usuario();
    String sNombres = cliente.getNombres();
    int iId_rol = cliente.getId_rol();
    
    String sPrivado = request.getParameter("privado");
    String sId_tipo_hilo = request.getParameter("id_tipo_hilo");
    String sId_destinatario = request.getParameter("id_destinatario");
    String sAsunto = request.getParameter("asunto");
    String sBoton = request.getParameter("boton");
    
    modelo.put("nombres", sNombres);

    //Creamos un nuevo hilo
    if ("Crear".equals(sBoton) && (!"".equals(sAsunto))) {
      Hilos hilo = new Hilos();
      hilo.setId_tipo_hilo(Integer.parseInt(sId_tipo_hilo));
      hilo.setId_destinatario(Integer.parseInt(sId_destinatario));
      hilo.setAsunto(sAsunto);
      hilo.setId_duenio(iId_usuario);
      if ("si".equals(sPrivado)) {
        hilo.setPrivado(true);
      }
      else {
        hilo.setPrivado(false);
      }
      int iResultado = this.mi.setRegistrarHilo(hilo);
      return new ModelAndView("administrarHilos/NuevoHilo1", modelo);
    }
    
    //Listando los usuarios
    Usuarios usuario = new Usuarios();
    usuario.setId_usuario(iId_usuario);
    List lUsuarios = this.mi.getListarUsuariosHilos(usuario);
    modelo.put("lUsuarios", lUsuarios);

    //Listando los tipos de hilos
    List lHilos = this.mi.getListarTiposHilos();
    modelo.put("lHilos", lHilos);

    return new ModelAndView("administrarHilos/NuevoHilo", modelo);
  }
}

package org.fautapo.web.administrarHilos;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Hilos;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
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

public class EliminarHilos1 implements Controller {
        
  private MiFacade mi;
 
  public void setMi(MiFacade mi) { this.mi = mi;}

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    int iId_usuario = cliente.getId_usuario();
    String sNombres = cliente.getNombres();
    int iId_rol = cliente.getId_rol();

    String sAsunto = request.getParameter("asunto");
    String sId_hilo = request.getParameter("id_hilo");

    modelo.put("asunto", sAsunto);
    
    Hilos hilo = new Hilos();
    hilo.setId_hilo(Integer.parseInt(sId_hilo));
    this.mi.setBorrarHilo(hilo);

    return new ModelAndView("administrarHilos/EliminarHilos1", modelo);
  }
}
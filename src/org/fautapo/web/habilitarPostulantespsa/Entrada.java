package org.fautapo.web.habilitarPostulantespsa;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-01-13
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class Entrada implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
      
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesi�n ha terminado. Vuelva a la p�gina inicial e ingrese de nuevo.");
   // String sGestion = cliente.getString(request, "gestion");
   // String sPeriodo = cliente.getString(request, "periodo");
    modelo.put("usuario", cliente.getNombres());   
   modelo.put("gestion",Integer.toString(cliente.getGestion()));
    modelo.put("periodo",Integer.toString(cliente.getPeriodo()));
  
    return new ModelAndView("habilitarPostulantespsa/Entrada", "cliente", cliente);
  }
}
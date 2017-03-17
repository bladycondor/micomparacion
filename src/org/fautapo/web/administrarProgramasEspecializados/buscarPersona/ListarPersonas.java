package org.fautapo.web.administrarProgramasEspecializados.buscarPersona;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class ListarPersonas implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }

    String sDip     = cliente.getString(request, "dip");
    String sNombre  = cliente.getString(request, "nombre");
    String sBotonDip = request.getParameter("botonDip");
    String sBotonNombre = request.getParameter("botonNombre");
    

    if ("".equals(sDip) && "".equals(sNombre))
      return new ModelAndView("administrarProgramasEspecializados/buscarPersona/EntradaBuscarPersonas");
    Map modelo = new HashMap();
    Personas persona = new Personas();
    if (!"".equals(sDip)) {
      persona.setDip(sDip);
      //List lPersonas = this.mi.getPrsListarPersonasDip(persona);
      List lPersonas = this.mi.getListarItemsPersonasDip(persona);
      modelo.put("lPersonas", lPersonas);
    }


    //Para wayka
    modelo.put("id_proceso", cliente.getString(request, "id_proceso"));
    modelo.put("titulo", cliente.getString(request, "titulo"));
    modelo.put("id_tramite", cliente.getString(request, "id_tramite"));
    
    return new ModelAndView("administrarProgramasEspecializados/buscarPersona/ListarPersonas", modelo);
  }
}
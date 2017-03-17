package org.fautapo.web.administrarDocentes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class ListarDocentesPersonas implements Controller {
  
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }

    String sDip     = cliente.getString(request, "dip");
    String sNombre  = cliente.getString(request, "nombre");
    List lDocentes;
    modelo.put("nombre", sNombre);
    modelo.put("dip", sDip);
    
    if ("".equals(sDip) && "".equals(sNombre))
      return new ModelAndView("administrarDocentes/Entrada");
    
    Docentes docente = new Docentes();
    if ("".equals(sDip)) {
      docente.setNombres(sNombre);
      lDocentes = this.mi.getBuscarListaDocentesNombres(docente);
    } else {
      docente.setDip(sDip);
      lDocentes = this.mi.getBuscarListaDocentesDip(docente);
    }
    modelo.put("lDocentes", lDocentes);
    
    return new ModelAndView("administrarDocentes/ListarDocentesPersonas", modelo);
  }
}
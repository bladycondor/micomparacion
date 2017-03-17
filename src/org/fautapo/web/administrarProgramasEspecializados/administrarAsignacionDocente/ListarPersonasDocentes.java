package org.fautapo.web.administrarProgramasEspecializados.administrarAsignacionDocente;

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

public class ListarPersonasDocentes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }

    String sDip     = cliente.getString(request, "dip");
    String sNombre  = cliente.getString(request, "nombre");
    
    //Para wayka
    String sId_proceso  = request.getParameter("id_proceso");
    String sId_tramite  = request.getParameter("id_tramite");
    

    if ("".equals(sDip) && "".equals(sNombre))
      return new ModelAndView("listardocentes/Entrada");
    Map modelo = new HashMap();
    List lDocentes;
    Docentes docente = new Docentes();
    if ("".equals(sDip)) {
      docente.setNombres(sNombre);
      lDocentes = this.mi.getBuscarListaDocentesNombres(docente);
    } else {
      docente.setDip(sDip);
      lDocentes = this.mi.getBuscarListaDocentesDip(docente);
    }
    
    //modelo.put("id_tramite", cliente.getString(request, "id_tramite"));
    modelo.put("lDocentes", lDocentes);
    
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_tramite", sId_tramite);
    
    return new ModelAndView("administrarProgramasEspecializados/administrarAsignacionDocente/ListarPersonasDocentes", modelo);
  }
}
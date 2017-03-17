package org.fautapo.web.administrarPostulantes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class ListarPostulantes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesi�n termin�. Por favor, ingresa nuevamente."); }

    String sDip     = cliente.getString(request, "dip");
    String sNombre  = cliente.getString(request, "nombre");
    String sBotonDip = request.getParameter("botonDip");
    String sBotonNombre = request.getParameter("botonNombre");
    

    if ("".equals(sDip) && "".equals(sNombre))
      return new ModelAndView("administrarPostulantes/EntradaBuscarPostulantes");
    Map modelo = new HashMap();
    List lPstPersonas; //List lPostulantes;
    Postulantes postulante = new Postulantes();
    if ("".equals(sDip)) {
      postulante.setNombres(sNombre);
      lPstPersonas = this.mi.getPstListarPersonasNombre(postulante);
      for(int i=0; i < lPstPersonas.size();i++) {
        postulante = (Postulantes) lPstPersonas.get(i);
	int iId_persona = postulante.getId_persona();
	List lPostulantes = this.mi.getMiListarPostulantesPorPersona(postulante);
        postulante.setPostulantes(lPostulantes);
	lPstPersonas.set(i,postulante);
      }
    } else {
      postulante.setDip(sDip);
      lPstPersonas = this.mi.getPstListarPersonasDip(postulante);
      for(int i=0; i < lPstPersonas.size();i++) {
        postulante = (Postulantes) lPstPersonas.get(i);
	int iId_persona = postulante.getId_persona();
	List lPostulantes = this.mi.getMiListarPostulantesPorPersona(postulante);
        postulante.setPostulantes(lPostulantes);
	lPstPersonas.set(i,postulante);
      }
    }
    modelo.put("lPstPersonas", lPstPersonas);
    return new ModelAndView("administrarPostulantes/ListarPostulantes", modelo);
  }
}
package org.fautapo.web.cambioPinDocente.general;

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
    
    Docentes datosDocente = new Docentes();
    List lDocentes;
    String sDip        = cliente.getString(request, "dip");
    String sNombre     = cliente.getString(request, "nombre");
    int iId_docente    = cliente.getInt(request,"id_docente");  
    
    modelo.put("nombre", sNombre);
    modelo.put("dip", sDip);
    
    if (iId_docente > 0) {
      //Buscar Docente
      datosDocente.setId_docente(iId_docente);
      datosDocente = this.mi.getBuscarDocente(datosDocente);
      modelo.put("datosDocente",datosDocente); 
      if(datosDocente != null)
        return new ModelAndView("cambioPinDocente/general/CambioPinDocenteAviso", modelo);
      else  	
        return new ModelAndView("Aviso","mensaje", "No existe el docente");
    }
    
    if ("".equals(sDip) && "".equals(sNombre))
      return new ModelAndView("cambioPinDocente/general/BuscarDocentePersona");
    
    if ("".equals(sDip)) {
      datosDocente.setNombres(sNombre);
      lDocentes = this.mi.getBuscarListaDocentesNombres(datosDocente);
    } 
    else {
      datosDocente.setDip(sDip);
      lDocentes = this.mi.getBuscarListaDocentesDip(datosDocente);
    }
    modelo.put("lDocentes", lDocentes);
    
    return new ModelAndView("cambioPinDocente/general/ListarDocentesPersonas", modelo);
  }
}
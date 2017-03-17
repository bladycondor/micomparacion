package org.fautapo.web.cambioPinDocente.docente;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04_03
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-03
*/

public class CambioPinDocenteVerificacion implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
     Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Debe volver a la pagina inicial e ingresar de nuevo.");
    int iId_docente = cliente.getId_usuario();
    String nombres = cliente.getNombres();
    String id_rol = Integer.toString(cliente.getId_rol());    
    
    
    String clave = request.getParameter("clave"+ request.getParameter("hora"));
    
    if("".equals(clave)){
       modelo.put("nombres", cliente.getNombres());
       modelo.put("id_rol", Integer.toString(cliente.getId_rol()));
       
       return new ModelAndView("cambioPinDocente/docente/CambioPinDocenteEntrada", modelo);
    }
    
    Docentes verificar = new Docentes();
    verificar.setId_docente(iId_docente);
    verificar.setClave(clave);
    
    if(!"1".equals(request.getParameter("bandera"))){
      verificar = this.mi.getComprobarDocente(verificar);
      if(verificar == null) {
        String mensaje = "Clave incorrecta";
        modelo.put("mensaje",mensaje);
        return new ModelAndView("Error",modelo);
      }
    }
    
    modelo.put("id_rol", id_rol);
    modelo.put("nombres", nombres);
    modelo.put("id_docente", Integer.toString(iId_docente));
    modelo.put("clave", clave);

    return new ModelAndView("cambioPinDocente/docente/CambioPinDocenteAviso", modelo);
  }
}

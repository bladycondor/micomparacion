package org.fautapo.web.cambioPinDocente.docente;

import java.util.HashMap;
import java.util.Map;
import java.lang.*;
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

public class CambioPinDocenteRegistro implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
  try {
      
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    String aux_id_docente = Integer.toString(cliente.getId_usuario());
    String nombres = cliente.getNombres();  
    String id_rol = Integer.toString(cliente.getId_rol());  
    

    modelo.put("nombres", nombres);
    modelo.put("id_rol", id_rol);
    int id_docente = Integer.valueOf(aux_id_docente).intValue();
    String nueva_clave = request.getParameter("nueva_clave");
    String conf_nueva_clave = request.getParameter("conf_nueva_clave");
    modelo.put("id_docente", aux_id_docente);
    modelo.put("nueva_clave", nueva_clave);
    modelo.put("conf_nueva_clave", conf_nueva_clave);
    
    int nrocarac = nueva_clave.length();
    int valor = 6;
    if(("".equals(nueva_clave)) && ("".equals(conf_nueva_clave))){
      return new ModelAndView("cambioPinDocente/docente/CambioPinDocenteRegistro",modelo);
    }
    
  if(nrocarac >= valor){    
    if(nueva_clave.trim().equals(conf_nueva_clave.trim())){
      Docentes docente = new Docentes();
      docente.setId_docente(id_docente);
      docente.setClave(nueva_clave.trim());
      docente.setId_rol(cliente.getId_rol());
      docente.setUlt_usuario(cliente.getId_usuario());      
      int iValor =this.mi.setCambioPinDocente(docente);
      //return new ModelAndView("cambiopindocente/CambioPinDocenteSalida2", modelo);
      if(iValor == 1){
        return new ModelAndView("Aviso","mensaje","Se realizo el cambio de clave");
      }
      else{
        return new ModelAndView("Aviso","mensaje","No se realizó el cambio de clave. Intentelo de nuevo");
      }
    } else {
       String mensaje = "No coincide la confirmacion de la clave";
       modelo.put("mensaje",mensaje);
       return new ModelAndView("Error",modelo);
    }
   } else {
      String mensaje = "Digite un minimo de 6 caracteres";
       modelo.put("mensaje",mensaje);
       return new ModelAndView("Error",modelo);
    }  
  }
  catch(Exception e){
    return new ModelAndView("Aviso","mensaje","El tiempo de su Sesión a terminado, vuelva a entrar al Sistema");
  }
    
  }
}



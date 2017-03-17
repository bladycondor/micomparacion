package org.fautapo.web.cambioPinDocente.general;

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

public class RegistrarPinDocente implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
      
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    int iId_docente = cliente.getInt(request,"id_docente");
    String sNombre = request.getParameter("nombre");
    String sDip = request.getParameter("dip"); 
    String sNueva_clave = request.getParameter("nueva_clave");
    String sConf_nueva_clave = request.getParameter("conf_nueva_clave");
    String sNuevo_apodo = request.getParameter("nuevo_apodo");
    String sConf_nuevo_apodo = request.getParameter("conf_nuevo_apodo");
    
    int iNroCaracClave = sNueva_clave.length();
    int iNroCaracApodo = sNuevo_apodo.length();
    int iValor = 6;String sMensaje="";int iResultado=0;

    modelo.put("nombre", sNombre);
    modelo.put("dip", sDip);
    modelo.put("nueva_clave", sNueva_clave);
    modelo.put("conf_nueva_clave", sConf_nueva_clave);
    modelo.put("nuevo_apodo", sNuevo_apodo);
    modelo.put("conf_nuevo_apodo", sConf_nuevo_apodo);
    modelo.put("usuario", cliente.getNombres());
    
     //Buscamos al docente
    Docentes datosDocente = new Docentes();
    datosDocente.setId_docente(iId_docente);
    datosDocente = this.mi.getBuscarDocente(datosDocente);
    modelo.put("datosDocente",datosDocente); 
    modelo.put("id_docente", Integer.toString(iId_docente));
    if(datosDocente == null)
      return new ModelAndView("Aviso","mensaje","No existen datos del docente con R.D."+Integer.toString(iId_docente));

    if(("".equals(sNueva_clave)) && ("".equals(sConf_nueva_clave)) && ("".equals(sNuevo_apodo)) && ("".equals(sConf_nuevo_apodo))) {
      return new ModelAndView("cambioPinDocente/general/NuevoPinDocente",modelo);
    }
    
    if((iNroCaracClave >= iValor) && (iNroCaracApodo >= iValor)){    
      if(!sNueva_clave.trim().equals(sConf_nueva_clave.trim()))
        return new ModelAndView("Error","mensaje","No coincide en la confirmaci&oacute;n de la clave");
      if(!sNuevo_apodo.trim().equals(sConf_nuevo_apodo.trim()))
        return new ModelAndView("Error","mensaje","No coincide en la confirmaci&oacute;n del apodo");  	
     
      Docentes docente = new Docentes();
      docente.setId_docente(iId_docente);
      docente.setClave(sNueva_clave.trim());
      docente.setApodo(sNuevo_apodo.trim());
      docente.setId_rol(cliente.getId_rol());
      docente.setUlt_usuario(cliente.getId_usuario());
      iResultado =this.mi.setModificarApodoClaveDocente(docente);
      switch (iResultado) {
        case 0: sMensaje="No se realizo el registro <br> Revise los datos."; break;  
        case 1: return new ModelAndView("cambioPinDocente/general/Aviso","mensaje","Se realizo el registro"); 
        case 2: sMensaje="La clave que ingreso ya existe. <br>D&iacute;gite una clave diferente."; break;
        case 3: sMensaje="El apodo que digito ya existe. <br>D&iacute;gite un apodo diferente.";break;
      }
      modelo.put("mensaje",sMensaje);
      return new ModelAndView("Error",modelo);        
    } 
    else {
       return new ModelAndView("Error","mensaje","D&iacute;gite para el apodo y para la clave<br>un m&iacute;nimo de 6 caracteres");
    }  
      
  }
}
  


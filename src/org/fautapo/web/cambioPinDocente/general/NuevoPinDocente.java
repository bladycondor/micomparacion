package org.fautapo.web.cambioPinDocente.general;

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


public class NuevoPinDocente implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    
    String sUsuario = cliente.getNombres();
    int iId_docente = cliente.getInt(request,"id_docente");
    int iId_departamento=cliente.getId_departamento();
    if (iId_docente > 0) {
	//if(getBuscarDocentexdepartamento(iId_docente,iId_departamento)==null){ 
	//return new ModelAndView("Aviso","mensaje","No pertenece a su unidad academica"+Integer.toString(iId_docente));}
	//else {
      //Buscamos al docente
      Docentes datosDocente = new Docentes();
      datosDocente.setId_docente(iId_docente);
	   // datosDocente.setId_departamento(cliente.getId_departamento());
      datosDocente = this.mi.getBuscarDocente(datosDocente);
	
      modelo.put("datosDocente",datosDocente); 
      modelo.put("id_docente", Integer.toString(iId_docente));
      if(datosDocente == null)
        return new ModelAndView("Aviso","mensaje","El docente con ID: "+Integer.toString(iId_docente)+" No pertenece a su Unidad Academica.");
		//}

      //return new ModelAndView("cambioPinDocente/general/CambioPinDocenteRegistro", modelo);
    }
    else {
      return new ModelAndView("Error", "mensaje","No ingreso el Registro de Docente");
    }
    
    modelo.put("usuario", sUsuario);
    return new ModelAndView("cambioPinDocente/general/NuevoPinDocente", modelo);
  }
}

package org.fautapo.web.cambioPinEstudiante.estudiante;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Personas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-06-22
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-06-22
 */

public class CambioPinEstudiante implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");

    int iId_estudiante = cliente.getId_usuario();
    int iGestion = cliente.getGestion();  
    int iPeriodo = cliente.getPeriodo();  
    String sNombres = cliente.getNombres();  
    
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(iId_estudiante);
    datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    //Datos de la matricula
    Estudiantes datosMatricula = new Estudiantes();
    datosMatricula.setId_estudiante(iId_estudiante);
    datosMatricula.setGestion(iGestion);
    datosMatricula.setPeriodo(iPeriodo);
    datosMatricula = this.mi.getMtrBuscarMatricula(datosMatricula);
    if(datosMatricula == null)
      return new ModelAndView("Aviso","mensaje","No esta matriculado para el periodo acad&eacute;mico"+ Integer.toString(iPeriodo)+"/"+ Integer.toString(iGestion));      
    
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("nombres", sNombres);

    return new ModelAndView("cambioPinEstudiante/estudiante/CambioPinEstudiante", modelo);
  }
}

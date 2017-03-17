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

public class Entrada implements Controller {
        
  private MiFacade mi;
 
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
  
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    String sNombres = cliente.getNombres();
    int iGestion = cliente.getGestion();
    int iPeriodo = cliente.getPeriodo();        
    int iId_estudiante = cliente.getId_usuario();
    int iId_rol = cliente.getId_rol();  
    String sMensaje="";
    String sClave = request.getParameter("clave"+request.getParameter("hora"));
    System.out.println("sClave-------->  " + sClave);    
    String sBandera = request.getParameter("bandera");
    
    modelo.put("nombres", sNombres);
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    
    if (("".equals(sClave)) || (sClave ==null)) {
      return new ModelAndView("cambioPinEstudiante/estudiante/Entrada", modelo);  
    }
    
    Estudiantes datosMatricula = new Estudiantes();
    datosMatricula.setId_estudiante(iId_estudiante);
    datosMatricula.setGestion(iGestion);
    datosMatricula.setPeriodo(iPeriodo);
    Estudiantes buscarMatricula = this.mi.getMtrBuscarMatricula(datosMatricula);
    if(buscarMatricula ==null)
      return new ModelAndView("Aviso","mensaje","No esta matriculado para el periodo acad&eacute;mico"+ Integer.toString(iPeriodo)+"/"+ Integer.toString(iGestion));
    
    String sClaveEstudiante = buscarMatricula.getClave();
    System.out.println("LA clave del estudiante -->"+ sClaveEstudiante);
    
    /*if ("".equals(request.getParameter("bandera")) || (request.getParameter("bandera") == null)) {
      if (!sClave.equals(sClaveEstudiante)) {
        sMensaje = "Clave incorrecta";
        modelo.put("mensaje",sMensaje);
        return new ModelAndView("Error",modelo);
      }
    */  

    //Comprobamos la entrada
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(iId_estudiante);
    datosEstudiante.setClave(sClave);
    Estudiantes datosClave = this.mi.getComprobarEstudiante(datosEstudiante);
    if(datosClave == null) {
      sMensaje ="El Estudiante no tiene acceso para la gestion academica "+ Integer.toString(iPeriodo)+"/"+ Integer.toString(iGestion);
      modelo.put("mensaje", sMensaje);
      return new ModelAndView("cambioPinEstudiante/estudiante/Aviso", modelo);
    } 
    
    datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
 
    modelo.put("datosEstudiante",datosEstudiante);
    modelo.put("id_estudiante", Integer.toString(iId_estudiante));
    return new ModelAndView("cambioPinEstudiante/estudiante/Recomendaciones", modelo);
  
  }
}
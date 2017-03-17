package org.fautapo.web.busquedasAcademicas.documentosPersonasEstudiantesPos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Personas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class BuscarEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante;
    
    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sCi = request.getParameter("ci");
    String sNombres = request.getParameter("nombres");
    
    if ("".equals(sId_estudiante) && ("".equals(sNombres)) && ("".equals(sCi))) {
      return new ModelAndView("busquedasAcademicas/documentosPersonasEstudiantesPos/Entrada", modelo);
    }
    
    if (!"".equals(sId_estudiante)) {
      //Sacando los datos del estudiante    
      datosEstudiante = new Estudiantes();
      try {
       datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      }
      catch(Exception e) {
        return new ModelAndView("Error","mensaje","Para el R.U. inserte un dato entero ");
      }	
      datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
      if (datosEstudiante == null) {
        return new ModelAndView("Aviso","mensaje","El R.U.: "+ sId_estudiante +"no existe");
      }
      List lEstudiantes = new ArrayList();
      lEstudiantes.add(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
      return new ModelAndView("busquedasAcademicas/documentosPersonasEstudiantesPos/ListarEstudiantes", modelo);
    }
    
    //Si la busqueda es por CI
    if (!"".equals(sCi)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setDip(sCi+"%");
      List lEstudiantes = this.mi.getEstListarEstudiantesDip2(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
      return new ModelAndView("busquedasAcademicas/documentosPersonasEstudiantesPos/ListarEstudiantes", modelo);
    }
    
    //Si la busqueda es por nombre
    if (!"".equals(sNombres)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setNombres("%"+sNombres+"%");
      List lEstudiantes = this.mi.getEstListarEstudiantesNombres2(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
      return new ModelAndView("busquedasAcademicas/documentosPersonasEstudiantesPos/ListarEstudiantes", modelo);
    }
    return new ModelAndView("busquedasAcademicas/documentosPersonasEstudiantesPos/Entrada",modelo);
  }
}

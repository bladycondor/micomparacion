package org.fautapo.web.reportesAcademicos.verFichaAcademicaAutoridad;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarFichaAcademica implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante;
    
    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sCi = request.getParameter("ci");
    String sNombres = request.getParameter("nombres");
    int iGestion = cliente.getInt(request, "gestion");
    int iPeriodo = cliente.getInt(request, "periodo");
    
    //Votamos los datos
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));

    if (("".equals(sId_estudiante) && ("".equals(sNombres)) && ("".equals(sCi)))
        || (sId_estudiante == null) && (sNombres == null) && (sCi == null)) {
      return new ModelAndView("reportesAcademicos/verFichaAcademicaAutoridad/BuscarEstudiantes", modelo);
    }
    
    if (!"".equals(sId_estudiante)) {
      //Sacando los datos del estudiante    
      datosEstudiante = new Estudiantes();
      try {
       datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      } catch(Exception e) {
        return new ModelAndView("Error", "mensaje", "El R.U. no es valido, introduzca un numero");
      }
      datosEstudiante.setId_universidad(cliente.getId_universidad());
      datosEstudiante.setId_facultad(cliente.getId_facultad());
      datosEstudiante.setId_programa(cliente.getId_programa());
      datosEstudiante = this.mi.getEstBuscarEstudianteAccesos(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);
      if (datosEstudiante == null) {
        return new ModelAndView("reportesAcademicos/verFichaAcademicaAutoridad/Aviso","mensaje", "El estudiante no es de su Area Verifique");
      }
      //Sacamos los datos del Programa
      Programas datosPrograma = new Programas();
      datosPrograma.setId_programa(datosEstudiante.getId_programa());
      datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
      modelo.put("datosPrograma", datosPrograma);
    
      //Listamos la ficha academica del estudiante
      List lFichaAcademica = this.mi.getEstListarFichaAcademica(datosEstudiante);
      modelo.put("lFichaAcademica", lFichaAcademica);

      //Listamos la ficha academica convalidada del estudiante
      List lFichaAcademicaConvalidada = this.mi.getEstListarFichaAcademicaConvalidada2(datosEstudiante);
      modelo.put("lFichaAcademicaConvalidada", lFichaAcademicaConvalidada);

      //Sacamos los datos del Estudiante
      datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
      modelo.put("datosEstudiante2", datosEstudiante);
  
      return new ModelAndView("reportesAcademicos/verFichaAcademicaAutoridad/ListarFichaAcademica", modelo);
    }
    
    //Si la busqueda es por CI
    if (!"".equals(sCi)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setDip(sCi);
      datosEstudiante.setId_universidad(cliente.getId_universidad());
      datosEstudiante.setId_facultad(cliente.getId_facultad());
      datosEstudiante.setId_programa(cliente.getId_programa());
      List lEstudiantes = this.mi.getEstListarEstudiantesDipAccesos(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
    }
    //Si la busqueda es por nombre
    if (!"".equals(sNombres)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setNombres(sNombres);
      datosEstudiante.setId_universidad(cliente.getId_universidad());
      datosEstudiante.setId_facultad(cliente.getId_facultad());
      datosEstudiante.setId_programa(cliente.getId_programa());
      List lEstudiantes = this.mi.getEstListarEstudiantesNombresAccesos(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
    }
    return new ModelAndView("reportesAcademicos/verFichaAcademicaAutoridad/ListarDatosEstudiantes", modelo);
  }
}
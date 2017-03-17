package org.fautapo.web.reportesAcademicos.verFichaAcademicaModificar;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Notas;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
 */

public class ModificaRegistro implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante;
    
    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
	int iId_estudiante = cliente.getInt(request, "id_estudiante");
 	int _yabe = cliente.getInt(request, "_yabe");   
    modelo.put("id_estudiante", Integer.toString(iId_estudiante));	
	modelo.put("_yabe", Integer.toString(_yabe));	

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
        return new ModelAndView("Error", "mensaje", "No existe el R.U.");
      }
      //Sacamos los datos del Programa
      Programas datosPrograma = new Programas();
      datosPrograma.setId_programa(datosEstudiante.getId_programa());
      datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
      modelo.put("datosPrograma", datosPrograma);
    
     //Sacamos la nota del estudiante
      Notas lFichaAcademica = new Notas(); 
	  lFichaAcademica.setId_nota(_yabe);	 
      lFichaAcademica = this.mi.getEstListarFichaAcademicaBuscar(lFichaAcademica);
	  if (lFichaAcademica == null) {
        return new ModelAndView("Error", "mensaje", "No encuentra el registro de la asignatura");
      }
      modelo.put("lFichaAcademica", lFichaAcademica);

      //Sacamos los datos del Estudiante
      datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
      modelo.put("datosEstudiante2", datosEstudiante);    
       
	  //Listando los tipos_evaluaciones
      List lListarTiposEvaluaciones = this.mi.getTpsListarTiposEvaluaciones();
      modelo.put("lListarTiposEvaluaciones", lListarTiposEvaluaciones);
	  
	  //Listando los estados de las notas
      List lListarTiposEstados = this.mi.getTpsListarTiposEstados();
      modelo.put("lListarTiposEstados", lListarTiposEstados);

	  }      
   return new ModelAndView("reportesAcademicos/verFichaAcademicaModificar/ModificaRegistro", modelo);	
  }

}
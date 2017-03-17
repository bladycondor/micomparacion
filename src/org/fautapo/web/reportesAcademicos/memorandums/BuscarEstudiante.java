package org.fautapo.web.reportesAcademicos.memorandums;

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
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import java.util.Date;

public class BuscarEstudiante implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante;
    modelo.put("cliente", cliente);
    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sCi = request.getParameter("ci");
    String sNombres = request.getParameter("nombres");
    
    if ("".equals(sId_estudiante) && ("".equals(sNombres)) && ("".equals(sCi))) {
      return new ModelAndView("reportesAcademicos/memorandums/Entrada", modelo);
    }
    
    if (!"".equals(sId_estudiante)) {
      //Sacando los datos del estudiante    
      datosEstudiante = new Estudiantes();
      try {
       datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      }
      catch(Exception e) {
        return new ModelAndView("Error", "mensaje", "El R.U. deber ser un dato entero, por favor Verifique");
      }	
      datosEstudiante.setId_universidad(cliente.getId_universidad());
      datosEstudiante.setId_facultad(cliente.getId_facultad());
      datosEstudiante.setId_programa(cliente.getId_programa());
      datosEstudiante = this.mi.getEstBuscarEstudianteAccesos(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);
      
      if (datosEstudiante == null) {
        return new ModelAndView("reportesAcademicos/memorandums/Aviso", "mensaje", "El estudiante no es de su Area, Por favor verifique");
      }
      datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
      List lEstudiantes = new ArrayList();
      lEstudiantes.add(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
      modelo.put("datosEstudiante", datosEstudiante);

      Estudiantes becario = new Estudiantes();
      becario.setGestion(cliente.getGestion());
      becario.setPeriodo(cliente.getPeriodo());
      becario.setId_estudiante(Integer.parseInt(sId_estudiante));
      becario = (Estudiantes)this.mi.getDesignacionBecaTrabajo(becario);
      
      if(becario==null){
        return new ModelAndView("reportesAcademicos/memorandums/Entrada", "mensaje", "El estudiante no tiene niguna designacion, Por favor verifique");      
      }
      modelo.put("becario",becario);      
      //Sacamos los datos de la institucion
      Instituciones datosInstitucion = new Instituciones();
      datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
      datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
      if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }      
    return new ModelAndView("reportesAcademicos/memorandums/Memo", modelo);
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
      datosEstudiante = (Estudiantes)lEstudiantes.get(0);
      modelo.put("datosEstudiantes", datosEstudiante);
    }
    //Si la busqueda es por nombre
    if (!"".equals(sNombres)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setNombres(sNombres);
      datosEstudiante.setId_universidad(cliente.getId_universidad());
      datosEstudiante.setId_facultad(cliente.getId_facultad());
      datosEstudiante.setId_programa(cliente.getId_programa());
      List lEstudiantes = this.mi.getEstListarEstudiantesNombresAccesos(datosEstudiante);
      datosEstudiante = (Estudiantes)lEstudiantes.get(0);
      modelo.put("lEstudiantes", lEstudiantes);
      modelo.put("datosEstudiantes", datosEstudiante);
    }
    

    return new ModelAndView("reportesAcademicos/memorandums/Memo",modelo);
  }
}

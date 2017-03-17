package org.fautapo.web.rectificacionDeNotas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Notas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */

public class ListarNotas implements Controller {

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
    int iGestion = cliente.getInt(request, "gestion");
    int iPeriodo = cliente.getInt(request, "periodo");
    
    //Votamos los datos
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    
    if (("".equals(sId_estudiante) && ("".equals(sNombres)) && ("".equals(sCi)))
        || (sId_estudiante == null) && (sNombres == null) && (sCi == null)) {
      return new ModelAndView("rectificacionDeNotas/BuscarEstudiantes",modelo);
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
        return new ModelAndView("rectificacionDeNotas/Aviso","mensaje", "El estudiante no es de su Area Verifique");
      }

      //Verificamos si tiene matricula para la gestion y periodo
      Estudiantes datosMatricula = new Estudiantes();
      datosMatricula.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosMatricula.setGestion(iGestion);
      datosMatricula.setPeriodo(iPeriodo);
      datosMatricula = this.mi.getMtrBuscarMatricula(datosMatricula);
      if (datosMatricula == null) {
        return new ModelAndView("rectificacionDeNotas/Aviso", "mensaje", "El estudiante con R.U. "+sId_estudiante+" no esta matriculado para la gestion "+iGestion+" y periodo "+iPeriodo);
      }
      if ("B".equals(datosMatricula.getId_estado())) {
        return new ModelAndView("rectificacionDeNotas/Aviso", "mensaje", "La matricula del estudiante con R.U. "+sId_estudiante+" esta bloqueada");
      }
      
      //Sacando los datos personales del Estudiante encontrado
      Personas datosPersona = new Personas();
      datosPersona.setId_persona(datosEstudiante.getId_persona());
      datosPersona = this.mi.getPrsBuscarPersona(datosPersona);
      modelo.put("datosPersona", datosPersona);

      //Sacamos los datos del Programa
      Programas datosPrograma = new Programas();
      datosPrograma.setId_programa(datosEstudiante.getId_programa());
      datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
      modelo.put("datosPrograma", datosPrograma);

      //Listamos las materias a rectificar
      Notas datosNota = new Notas();
      datosNota.setId_estudiante(datosEstudiante.getId_estudiante());
      datosNota.setGestion(iGestion);
      datosNota.setPeriodo(iPeriodo);
      List lNotas = this.mi.getListarNotasRectificar(datosNota);
      modelo.put("lNotas", lNotas);
      return new ModelAndView("rectificacionDeNotas/ListarNotas", modelo);      
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
    return new ModelAndView("rectificacionDeNotas/ListarDatosEstudiantes", modelo);
  }
}
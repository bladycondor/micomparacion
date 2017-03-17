package org.fautapo.web.inscripcionMaterias.estudianteHorarios;

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

public class RegistrarProgramacionMaterias implements Controller {

  private MiFacade mi;;
     
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");


    List lMaterias = new ArrayList();
    String sCadena ="";
    //Recuperando valores
    int iTotal_materias = Integer.parseInt(request.getParameter("total_materias"));
    //Sacando los datos del estudiante    
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(cliente.getId_usuario());
    datosEstudiante = this.mi.getEstBuscarEstudiante(datosEstudiante);
    modelo.put("datosEstudiante",datosEstudiante);    
    
    //Sacando los datos personales del Estudiante encontrado
    Personas datosPersona = new Personas();
    datosPersona.setId_persona(datosEstudiante.getId_persona());
    datosPersona = this.mi.getPrsBuscarPersona(datosPersona);
    modelo.put("datosPersona", datosPersona);

    //Sacando el programa en que esta el estudiante
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(datosEstudiante.getId_programa());
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    //Buscamos el periodo
    Programas buscarPeriodo= new Programas();
    buscarPeriodo.setId_programa(datosEstudiante.getId_programa());
    buscarPeriodo = this.mi.getPrdBuscarPrgPeriodo(buscarPeriodo);
    modelo.put("id_periodo", Integer.toString(buscarPeriodo.getId_periodo()));    
    
    for (int i = 0; i < iTotal_materias; i++) {
      sCadena = sCadena + request.getParameter("materia" + i)+"|";
    }
    
    Programas programacion = new Programas();
    programacion.setId_estudiante(datosEstudiante.getId_estudiante());
    programacion.setMaterias(sCadena);
    programacion.setGestion(cliente.getGestion());
    programacion.setPeriodo(cliente.getPeriodo());
    programacion.setId_rol(cliente.getId_rol());  //CRCB
    programacion.setUlt_usuario(cliente.getId_usuario());
    lMaterias = this.mi.setEstListarProgramarMaterias(programacion);
    if (lMaterias.size() > 0 ) {
      modelo.put("lMaterias", lMaterias);
      return new ModelAndView("inscripcionMaterias/estudianteHorarios/ListarMateriasNoRegistradas", modelo);
    } 

    return new ModelAndView("Aviso","mensaje","Las materias elegidas fueron registradas correctamente");
  }
}
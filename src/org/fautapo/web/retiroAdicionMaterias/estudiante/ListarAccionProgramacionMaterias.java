package org.fautapo.web.retiroAdicionMaterias.estudiante;

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
import org.fautapo.domain.Planes;
import org.fautapo.domain.Grupos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class ListarAccionProgramacionMaterias implements Controller {

  private MiFacade mi;;

  public void setMi(MiFacade mi) { this.mi = mi;}

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    //Definicion de variables
    Estudiantes datosEstudiante;

    
    //Recuperando variables del jsp
    String sNombres = request.getParameter("nombres");
    

    // Comprobamos es quien debe ser, de acuerdo a su clave
    Estudiantes estudiante = new Estudiantes();
    estudiante.setId_estudiante(cliente.getId_usuario());
    estudiante.setClave(request.getParameter("clave" + request.getParameter("hora")));
    estudiante = this.mi.getComprobarEstudiante(estudiante);
    if(null== estudiante){
      return new ModelAndView("retiroAdicionMaterias/estudiante/Entrada", "cliente",cliente);
    }
    
    //Sacando los datos del estudiante    
    datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(estudiante.getId_estudiante());
    datosEstudiante = this.mi.getEstBuscarEstudiante(datosEstudiante);
    modelo.put("datosEstudiante",datosEstudiante);
    if(datosEstudiante == null)
    {
      return new ModelAndView("Aviso","mensaje","No existe el  R.U.   "+ Integer.toString(estudiante.getId_estudiante()));
    }
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
      
    //Buscamos si existe una lista de menciones por id_programa e id_plan
    Planes menciones = new Planes();
    menciones.setId_programa(datosEstudiante.getId_programa());
    menciones.setId_plan(datosEstudiante.getId_plan());
    List lMenciones = this.mi.getMncListarMenciones(menciones);
    modelo.put("lMenciones", lMenciones);
    
    return new ModelAndView("retiroAdicionMaterias/estudiante/RetirarAdicionarCambiarMaterias", modelo);

  }
}
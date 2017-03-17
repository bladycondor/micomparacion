package org.fautapo.web.inscripcionMaterias.estudianteHorarios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
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
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class ListarProgramacionMateriasMencion implements Controller {

  private MiFacade mi;;
     
  public void setMi(MiFacade mi) { this.mi = mi;}

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo         = new HashMap();
    String mensaje;    

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");

    String sUlt_usuario = cliente.getId_usuario()+"|"+cliente.getId_rol() ;    
    String sId_mencion = request.getParameter("id_mencion");
    
    //Sacando los datos del estudiante    
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(cliente.getId_usuario());
    datosEstudiante = this.mi.getEstBuscarEstudiante(datosEstudiante);
    modelo.put("datosEstudiante",datosEstudiante);
    if(datosEstudiante == null)
    {
      return new ModelAndView("Aviso","mensaje","No existe el  R.U.   "+ Integer.toString(cliente.getId_usuario()));
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
     
    
    // PARECE QUE REGISTRA AL ESTUDIANTE CON UN IDMENCION ?????
    if (sId_mencion == null) {
      return new ModelAndView("inscripcionMaterias/estudianteHorarios/ListarMencionesProgramacion", modelo);
    }
    else {
      Planes plan = new Planes();
      plan.setId_estudiante(datosEstudiante.getId_estudiante());
      plan.setId_programa(datosEstudiante.getId_programa());
      plan.setId_plan(datosEstudiante.getId_plan());
      plan.setId_mencion(Integer.parseInt(sId_mencion));
      plan.setId_rol(cliente.getId_rol());         //CRCB
      plan.setUlt_usuario(cliente.getId_usuario());
      int iValor = this.mi.setEstRegistrarMencionEstudiante(plan);
    }
    

    //Sacamos los parametros de programacion de prg_detalles
    Programas parametro = new Programas();
    parametro.setId_programa(datosEstudiante.getId_programa());
    parametro.setId_plan(datosEstudiante.getId_plan());
    parametro.setId_tipo_grado(datosEstudiante.getId_tipo_grado());
    parametro.setId_tipo_programacion(2); //COMO ESTUDIANTE
    parametro.setGestion(cliente.getGestion());
    parametro.setPeriodo(cliente.getPeriodo());
    List lParametros = this.mi.getPrgBuscarDetalles(parametro);
    if(lParametros.size() == 0)
    {
      String sMensaje="No existe parámetros de programación";
      modelo.put("mensaje", sMensaje);
      return new ModelAndView("inscripcionMaterias/estudianteHorarios/ListarProgramacionMaterias", modelo);      
    }
    modelo.put("lParametros",lParametros);
	
    //Sacando el nivel maximo de mtr_planes segun id_estudiante
    parametro.setId_estudiante(cliente.getId_usuario());
    int max_nivel_academico = this.mi.getBuscarNivelMaximoPlanesEst(parametro);
    //Sacando el listado de las materias programadas para el estudiante
    Materias programacion = new Materias();
    programacion.setId_estudiante(cliente.getId_usuario());
    programacion.setGestion(cliente.getGestion());
    programacion.setPeriodo(cliente.getPeriodo());
    programacion.setMax_niveles(max_nivel_academico);
  
    List lMaterias = this.mi.getEstListarProgramacionMateriasReq(programacion);
    for (int i = 0; i < lMaterias.size(); i++) {
      Materias materia = (Materias) lMaterias.get(i);
      if (materia.getCupo_restante() > 0)
      {
        programacion.setId_materia(materia.getId_materia());
 	programacion.setId_modelo_ahorro(materia.getId_modelo_ahorro());
        materia.setGrupos(this.mi.getDptoListarMateriaGrupo(programacion));
	lMaterias.set(i, materia);
      }
    }
    modelo.put("lMaterias", lMaterias);
    return new ModelAndView("inscripcionMaterias/estudianteHorarios/ListarProgramacionMaterias", modelo);          

  }
}
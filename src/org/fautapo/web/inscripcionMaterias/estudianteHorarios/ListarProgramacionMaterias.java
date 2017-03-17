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


public class ListarProgramacionMaterias implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    

    Estudiantes datosEstudiante;
    
    //Recuperando variables del jsp
    String sNombres = request.getParameter("nombres");
    // Comprobamos es quien debe ser, de acuerdo a su clave
    Estudiantes estudiante = new Estudiantes();
    estudiante.setId_estudiante(cliente.getId_usuario());
    estudiante.setClave(request.getParameter("clave" + request.getParameter("hora")));
    estudiante = this.mi.getComprobarEstudiante(estudiante);
    if (null== estudiante) {
       List lTiposProgramaciones = this.mi.getTpsListarProgramaciones();
       modelo.put("lTiposProgramaciones", lTiposProgramaciones);
       modelo.put("cliente",cliente);
      return new ModelAndView("inscripcionMaterias/estudianteHorarios/Entrada", modelo);
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
      
    if( lMenciones.size() == 0 )
    {
      //Sacamos los parametros de programacion de prg_detalles
      Programas parametro = new Programas();
      parametro.setId_programa(datosEstudiante.getId_programa());
      parametro.setId_plan(datosEstudiante.getId_plan());
      parametro.setId_tipo_grado(datosEstudiante.getId_tipo_grado());
      parametro.setId_tipo_programacion(2); //COMO ESTUDIANTE
      parametro.setGestion(cliente.getGestion());
      parametro.setPeriodo(cliente.getPeriodo());
      List lParametros = this.mi.getPrgBuscarDetalles(parametro);
      if(lParametros.size() == 0) {
        String sMensaje="No existe parámetros de programación";
        modelo.put("mensaje", sMensaje);
        return new ModelAndView("inscripcionMaterias/estudianteHorarios/ListarProgramacionMaterias", modelo);      
      }
      modelo.put("lParametros",lParametros);
      //Sacando el nivel maximo de mtr_planes segun id_estudiante
      //Materias programacion = new Materias();
      parametro.setId_estudiante(estudiante.getId_estudiante());
      int max_nivel_academico = this.mi.getBuscarNivelMaximoPlanesEst(parametro);
      //Sacando el listado de las materias programadas para el estudiante
      Materias programacion = new Materias();
      programacion.setId_estudiante(estudiante.getId_estudiante());
      programacion.setGestion(cliente.getGestion());
      programacion.setPeriodo(cliente.getPeriodo());
      programacion.setMax_niveles(max_nivel_academico);
      programacion.setId_tipo_evaluacion(1); //ESTATICO SOLO PARA CURSOS REGULARES
      List lMaterias = this.mi.getEstListarProgramacionMateriasReq(programacion);
      for (int i = 0; i < lMaterias.size(); i++)
      {
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
    
    return new ModelAndView("inscripcionMaterias/estudianteHorarios/ListarMencionesProgramacion", modelo);
  }
}

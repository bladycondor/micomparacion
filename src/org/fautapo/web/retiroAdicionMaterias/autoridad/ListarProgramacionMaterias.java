package org.fautapo.web.retiroAdicionMaterias.autoridad;

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
import org.fautapo.domain.Grupos;
import org.fautapo.domain.Libretas;
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

  private MiFacade mi;;
     
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo         = new HashMap();
    

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");    
    
    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_programa = request.getParameter("id_programa");
    String sAccion = request.getParameter("accion");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    modelo.put("accion",sAccion);
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    
    //Buscando el tipo_evaluacion Verano-Mesa-Regular
    Libretas datosTipoEval = new Libretas();
    datosTipoEval.setId_tipo_evaluacion(Integer.parseInt(sId_tipo_evaluacion));
    datosTipoEval = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEval);
    modelo.put("datosTipoEval",datosTipoEval);
    
    //Sacando el programa en que esta el estludiante
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);

    //Sacando los datos del estudiante    
    Estudiantes datosEstudiante = new Estudiantes();
    try {
      datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
    } catch(Exception e) {
      return new ModelAndView("Error", "mensaje", "El R.U. no es valido, introduzca un numero");
    }
    datosEstudiante.setId_programa(Integer.parseInt(sId_programa));
    datosEstudiante = this.mi.getEstBuscarEstudiantePrograma(datosEstudiante);
    modelo.put("datosEstudiante",datosEstudiante);
    if (datosEstudiante == null) {
      return new ModelAndView("retiroAdicionMaterias/autoridad/Aviso","mensaje","El estudiante con R.U. : "+ sId_estudiante + "no esta registrado en el Programa : "+ datosPrograma.getPrograma() + ". Verifique.");
    } 
    //Verificamos si tiene matricula para la gestion y periodo
    Estudiantes datosMatricula = new Estudiantes();
    datosMatricula.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosMatricula.setGestion(Integer.parseInt(sGestion));    
    datosMatricula.setPeriodo(Integer.parseInt(sPeriodo));    
    datosMatricula = this.mi.getMtrBuscarMatricula(datosMatricula);
    if (datosMatricula == null) {
      return new ModelAndView("retiroAdicionMaterias/autoridad/Aviso", "mensaje", "El estudiante con R.U. "+sId_estudiante+" no esta matriculado para la gestion "+sGestion+" y periodo "+sPeriodo);
    }
    if ("B".equals(datosMatricula.getId_estado())) {
      return new ModelAndView("retiroAdicionMaterias/autoridad/Aviso", "mensaje", "La matricula del estudiante con R.U. "+sId_estudiante+" esta bloqueada");
    }

    //Sacando los datos personales del Estudiante encontrado
    Personas datosPersona = new Personas();
    datosPersona.setId_persona(datosEstudiante.getId_persona());
    datosPersona = this.mi.getPrsBuscarPersona(datosPersona);
    modelo.put("datosPersona", datosPersona);
    
    //Buscamos el periodo
    Programas buscarPeriodo= new Programas();
    buscarPeriodo.setId_programa(datosEstudiante.getId_programa());
    buscarPeriodo = this.mi.getPrdBuscarPrgPeriodo(buscarPeriodo);
    modelo.put("id_periodo", Integer.toString(buscarPeriodo.getId_periodo()));
    
    //Sacando los parametros de programacion de prg_detalles
    Programas parametro = new Programas();
    parametro.setId_programa(datosEstudiante.getId_programa());
    parametro.setId_plan(datosEstudiante.getId_plan());
    parametro.setId_tipo_grado(datosEstudiante.getId_tipo_grado());
    parametro.setId_tipo_programacion(1); //COMO AUTORIDAD
    parametro.setGestion(Integer.parseInt(sGestion));
    parametro.setPeriodo(Integer.parseInt(sPeriodo));
    List lParametros = this.mi.getPrgBuscarDetalles(parametro);
    if (lParametros.size() == 0) {
      String sMensaje="No existe parámetros de programación";
      //modelo.put("gestion", sGestion);
      //modelo.put("periodo", sPeriodo);
      modelo.put("mensaje", sMensaje);
      return new ModelAndView("Aviso","mensaje", sMensaje);      
    }
    else{
      if ("Adicion".equals(sAccion)) {
	Materias programacion = new Materias();
	programacion.setId_estudiante(Integer.parseInt(sId_estudiante));
	programacion.setGestion(Integer.parseInt(sGestion));
	programacion.setPeriodo(Integer.parseInt(sPeriodo));
	programacion.setId_tipo_evaluacion(Integer.parseInt(sId_tipo_evaluacion));
        List lMaterias = this.mi.getEstPrgListarProgramacionMateriasAut(programacion);
	for (int i = 0; i < lMaterias.size(); i++) {
          Materias materia = (Materias) lMaterias.get(i);
          if (materia.getCupo_restante() > 0) {
    	    programacion.setId_materia(materia.getId_materia());
 	    programacion.setId_modelo_ahorro(materia.getId_modelo_ahorro());
            materia.setGrupos(this.mi.getDptoListarMateriaGrupo(programacion));
	    lMaterias.set(i, materia);
          }
        }
	modelo.put("lMaterias",lMaterias);
	//modelo.put("gestion", sGestion);
        //modelo.put("periodo", sPeriodo);
	modelo.put("lParametros", lParametros);
        return new ModelAndView("retiroAdicionMaterias/autoridad/ListarProgramacionMaterias", modelo);            
      
      }
      if ("Retiro".equals(sAccion)) {
        Programas retiro = new Programas();
	retiro.setId_estudiante(datosEstudiante.getId_estudiante());
	retiro.setGestion(Integer.parseInt(sGestion));
	retiro.setPeriodo(Integer.parseInt(sPeriodo));
	retiro.setId_tipo_evaluacion(Integer.parseInt(sId_tipo_evaluacion));  // Listar por Verano-Mesa-Regular
	List lMateriasRetiro = this.mi.getEstListarProgramacionesEstudiante(retiro);
	//modelo.put("gestion", sGestion);
        //modelo.put("periodo", sPeriodo);
	modelo.put("lParametros", lParametros);
	modelo.put("lMateriasRetiro", lMateriasRetiro);
        return new ModelAndView("retiroAdicionMaterias/autoridad/ListarRetirarMaterias", modelo);
      }
      
      if ("Cambio de grupo".equals(sAccion)) {
	Materias programacion = new Materias();
	programacion.setId_estudiante(Integer.parseInt(sId_estudiante));
	programacion.setGestion(Integer.parseInt(sGestion));
	programacion.setPeriodo(Integer.parseInt(sPeriodo));
	programacion.setId_tipo_evaluacion(Integer.parseInt(sId_tipo_evaluacion));  //Listar si es para Verano.Mesa-REgular
        List lMaterias = this.mi.getEstPrgListarProgramacionMateriasAut(programacion);
	for (int i = 0; i < lMaterias.size(); i++) {
          Materias materia = (Materias) lMaterias.get(i);
          if (materia.getCupo_restante() > 0) {
    	    programacion.setId_materia(materia.getId_materia());
 	    programacion.setId_modelo_ahorro(materia.getId_modelo_ahorro());
            materia.setGrupos(this.mi.getDptoListarMateriaGrupo(programacion));
	    lMaterias.set(i, materia);
          }
        }
	modelo.put("lMaterias",lMaterias);
	//modelo.put("gestion", sGestion);
        //modelo.put("periodo", sPeriodo);
	modelo.put("lParametros", lParametros);
        return new ModelAndView("retiroAdicionMaterias/autoridad/ListarCambiarGrupoMaterias", modelo);
      }  
    }
   return new ModelAndView("retiroAdicionMaterias/autoridad/ProgramacionMateriasSalida1", modelo);
   
  }
}
package org.fautapo.web.inscripcionMaterias.autoridad;

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
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_programa = request.getParameter("id_programa");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    
    //Votamos los datos
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("id_programa", sId_programa);
    modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    
    //Buscamos el tipo Evaluacion para programar
    Libretas datosTipoEval = new Libretas();
    datosTipoEval.setId_tipo_evaluacion(Integer.parseInt(sId_tipo_evaluacion));
    datosTipoEval = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEval);
    modelo.put("datosTipoEval",datosTipoEval);

    //Buscamos el programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    if ("".equals(sId_estudiante) && ("".equals(sNombres)) && ("".equals(sCi))){
      //modelo.put("gestion", sGestion);
      //modelo.put("periodo", sPeriodo);
      //modelo.put("id_programa", sId_programa);
      //modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
      
      //Buscamos el programa
      //Programas datosPrograma = new Programas();
      //datosPrograma.setId_programa(Integer.parseInt(sId_programa));
      //datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
      //modelo.put("datosPrograma", datosPrograma);
      return new ModelAndView("inscripcionMaterias/autoridad/BuscarEstudiantes",modelo);
    }
    
    if (!"".equals(sId_estudiante)) {
      //Sacando los datos del estudiante    
      datosEstudiante = new Estudiantes();
      try {
       datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      } catch(Exception e) {
        return new ModelAndView("Error", "mensaje", "El R.U. no es valido, introduzca un numero");
      }
      datosEstudiante.setId_programa(Integer.parseInt(sId_programa));
      datosEstudiante = this.mi.getEstBuscarEstudiantePrograma(datosEstudiante);
      modelo.put("datosEstudiante",datosEstudiante);
      if (datosEstudiante == null) {
        return new ModelAndView("inscripcionMaterias/autoridad/Aviso","mensaje","El estudiante con R.U. : "+ sId_estudiante + "no esta registrado en el Programa : "+ datosPrograma.getPrograma() + ". Verifique.");
      }
      
      //Verificamos si tiene matricula para la gestion y periodo
      Estudiantes datosMatricula = new Estudiantes();
	  Estudiantes datosMatriculax = new Estudiantes();
	  Estudiantes datosMatriculaNuevo = new Estudiantes();
	  
      datosMatricula.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosMatricula.setGestion(Integer.parseInt(sGestion));    
      datosMatricula.setPeriodo(Integer.parseInt(sPeriodo));    
      datosMatricula = this.mi.getMtrBuscarMatricula(datosMatricula);

      datosMatriculax.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosMatriculax.setGestion(Integer.parseInt(sGestion));    
      datosMatriculax.setPeriodo(Integer.parseInt(sPeriodo));    
      


      datosMatriculaNuevo.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosMatriculaNuevo.setGestion(Integer.parseInt(sGestion));    
      datosMatriculaNuevo.setPeriodo(Integer.parseInt(sPeriodo));    
      datosMatriculaNuevo = this.mi.getMtrBuscarMatriculaNuevo(datosMatriculaNuevo);

      if (datosMatricula == null) {
        return new ModelAndView("inscripcionMaterias/autoridad/Aviso", "mensaje", "El estudiante con R.U. "+sId_estudiante+" no esta matriculado para la gestion "+sGestion+" y periodo "+sPeriodo);
      }
      if ("B".equals(datosMatricula.getId_estado())) {
        return new ModelAndView("inscripcionMaterias/autoridad/Aviso", "mensaje", "La matricula del estudiante con R.U. "+sId_estudiante+" esta bloqueada");
      }
  
  
      //Sacando los datos personales del Estudiante encontrado
      Personas datosPersona = new Personas();
      datosPersona.setId_persona(datosEstudiante.getId_persona());
      datosPersona = this.mi.getPrsBuscarPersona(datosPersona);
      int id_documento = this.mi.getBuscarPrsDocumentacionCompleta(datosPersona);
	  int id_compromiso = this.mi.getMiEstListarCompromisosCant(datosMatriculax);
	  
	  if((Integer.parseInt(sId_programa)!=68) && (Integer.parseInt(sId_programa)!=69))
	   if (id_compromiso ==0) {
	    if (id_documento ==0) {
	      if (datosMatriculaNuevo == null)
		   if (Integer.parseInt(sId_tipo_evaluacion) !=3)   
	         { return new ModelAndView("Aviso", "mensaje", "El estudiante con R.U. = "+ sId_estudiante+" tiene deudas con Documentos de Admisión Pasar por Registros e Inscripciones");}
	   }
	  }
	  modelo.put("datosPersona", datosPersona);

      //Sacando el programa en que esta el estludiante
      Programas datosProgramaEst = new Programas();
      datosProgramaEst.setId_programa(datosEstudiante.getId_programa());
      datosProgramaEst = this.mi.getPrgBuscarPrograma(datosProgramaEst);
      modelo.put("datosPrograma", datosProgramaEst);
      
      //Buscamos el periodo
      Programas buscarPeriodo= new Programas();
      buscarPeriodo.setId_programa(datosEstudiante.getId_programa());
      buscarPeriodo = this.mi.getPrdBuscarPrgPeriodo(buscarPeriodo);
      modelo.put("id_periodo", Integer.toString(buscarPeriodo.getId_periodo()));
      
      if (buscarPeriodo != null) {
        //Sacando los parametros de progrmacion de prg_detalles
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
          return new ModelAndView("inscripcionMaterias/autoridad/ListarProgramacionMaterias", modelo);      
        }
	else{	
	  Materias programacion = new Materias();
	  programacion.setId_estudiante(Integer.parseInt(sId_estudiante));
	  programacion.setGestion(Integer.parseInt(sGestion));
	  programacion.setPeriodo(Integer.parseInt(sPeriodo));
	  programacion.setId_tipo_evaluacion(Integer.parseInt(sId_tipo_evaluacion));  //Aumentado el listado de dpto_grupos salga por Regular-Verano-Mesa
          //List lMaterias = this.mi.getEstListarProgramacionMateriasReq(programacion);
          List lMaterias = this.mi.getEstPrgListarProgramacionMateriasAut(programacion);
	  for (int i = 0; i < lMaterias.size(); i++) {
            Materias materia = (Materias) lMaterias.get(i);
            if (materia.getCupo_restante() > 0) {
    	      programacion.setId_materia(materia.getId_materia());
 	      programacion.setId_modelo_ahorro(materia.getId_modelo_ahorro());
              materia.setGrupos(this.mi.getDptoListarMateriaGrupo(programacion));  //Tambien por id_tipo_evaluacion Verano-Mesa-Regular
	      lMaterias.set(i, materia);
            }
          }
	  modelo.put("lMaterias",lMaterias);
	}  
    //CODIGO SEGMENTADO 1
    
    
    
    	  // inicio codigo para listar programacion estudiante
	  
       Materias programacionEst = new Materias();
      programacionEst.setId_estudiante(Integer.parseInt(sId_estudiante));
      programacionEst.setGestion(Integer.parseInt(sGestion));
      programacionEst.setPeriodo(Integer.parseInt(sPeriodo));
      programacionEst.setMax_niveles(10);
      //programacionEst.setMax_niveles(max_nivel_academico);
  
      List lMateriasEst = this.mi.getEstListarProgramacionMateriasReq(programacionEst);
      for (int i = 0; i < lMateriasEst.size(); i++)
      {
        Materias materiaEst = (Materias) lMateriasEst.get(i);
        if (materiaEst.getCupo_restante() > 0)
	{
	  programacionEst.setId_materia(materiaEst.getId_materia());
 	  programacionEst.setId_modelo_ahorro(materiaEst.getId_modelo_ahorro());
          materiaEst.setGrupos(this.mi.getDptoListarMateriaGrupo(programacionEst));
	  lMateriasEst.set(i, materiaEst);
        }
      }
      modelo.put("lMateriasEst", lMateriasEst);
    // fin condigo listar programcion estudiante     

    
    
    
    
    //CODIGO SEGMENTADO 1
        modelo.put("lParametros", lParametros);
      }
        //modelo.put("gestion", sGestion);
        //modelo.put("periodo", sPeriodo);
      return new ModelAndView("inscripcionMaterias/autoridad/ListarProgramacionMaterias", modelo);      
    
    }
    
    //Si la busqueda es por CI
    if (!"".equals(sCi)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setDip(sCi);
      datosEstudiante.setId_programa(Integer.parseInt(sId_programa));
      List lEstudiantes = this.mi.getEstListarEstudiantesDip(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
      //modelo.put("gestion", sGestion);
      //modelo.put("periodo", sPeriodo);
      //modelo.put("id_programa", sId_programa);
      //return new ModelAndView("inscripcionMaterias/autoridad/ListarDatosEstudiantes", modelo);
    }
    //Si la busqueda es por nombre
    if (!"".equals(sNombres)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setNombres(sNombres);
      datosEstudiante.setId_programa(Integer.parseInt(sId_programa));
      List lEstudiantes = this.mi.getEstListarEstudiantesNombres(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
      //modelo.put("gestion", sGestion);
      //modelo.put("periodo", sPeriodo);
      //modelo.put("id_programa", sId_programa);
      //return new ModelAndView("inscripcionMaterias/autoridad/ListarDatosEstudiantes", modelo);      
    }
    
    //modelo.put("gestion", sGestion);
    //modelo.put("periodo", sPeriodo);
    //modelo.put("id_programa", sId_programa);
    return new ModelAndView("inscripcionMaterias/autoridad/ListarDatosEstudiantes", modelo);
    
  }
}

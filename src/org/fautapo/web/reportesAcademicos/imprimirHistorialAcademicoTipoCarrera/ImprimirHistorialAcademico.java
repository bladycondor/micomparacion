package org.fautapo.web.reportesAcademicos.imprimirHistorialAcademicoTipoCarrera;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Notas;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Literales;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ImprimirHistorialAcademico implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante = new Estudiantes();
    
    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sCi = request.getParameter("ci");
    String sNombres = request.getParameter("nombres");
    String sId_programa = request.getParameter("id_programa");
    
    //Buscamos el programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    if ("".equals(sId_estudiante) && ("".equals(sNombres)) && ("".equals(sCi))) {
      return new ModelAndView("reportesAcademicos/imprimirHistorialAcademicoTipoCarrera/BuscarEstudiantes", modelo);
    }
    
    if ((!"".equals(sId_estudiante)) && (sId_estudiante!=null)) {
      //Sacando los datos del estudiante
      datosEstudiante = new Estudiantes();
      try {
       datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      } catch(Exception e) {
        return new ModelAndView("Error", "mensaje", "El R.U. no es valido, introduzca un numero");
      }
      datosEstudiante.setId_programa(Integer.parseInt(sId_programa));
      datosEstudiante = this.mi.getEstBuscarEstudiantePrograma(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);
      if (datosEstudiante == null) {
        return new ModelAndView("reportesAcademicos/imprimirHistorialAcademicoTipoCarrera/Aviso","mensaje","El estudiante con R.U. : "+ sId_estudiante + "no esta registrado en el Programa : "+ datosPrograma.getPrograma() + ". Verifique.");
      }

      //Sacando los datos personales del Estudiante encontrado
      Personas datosPersona = new Personas();
      datosPersona.setId_persona(datosEstudiante.getId_persona());
      datosPersona = this.mi.getPrsBuscarPersona(datosPersona);
      modelo.put("datosPersona", datosPersona);

      //Sacamos los datos de la Facultad
      Facultades datosFacultad = new Facultades();
      datosFacultad.setId_facultad(datosPrograma.getId_facultad());
      datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
      modelo.put("datosFacultad", datosFacultad);
  
      //Buscamos el grado_academico por programa e id_plan
      Libretas datosGrados = new Libretas();
      datosGrados.setId_programa(datosEstudiante.getId_programa());
      datosGrados.setId_plan(datosEstudiante.getId_plan());
      datosGrados = this.mi.getBuscarGradoAcademicoPrograma(datosGrados);
      modelo.put("datosGrados", datosGrados);

      //Listamos la ficha academica CONVALIDADA del estudiante
      List lMaterias = this.mi.getListarPlanMateriasNotas3(datosEstudiante);
      List lMateriasNotas = new ArrayList();
      for (int i=0; i<lMaterias.size();i++) {
        Notas datosNotas = (Notas) lMaterias.get(i);
        Literales literal = new Literales();
	datosNotas.setLiteral(literal.convertNumber(datosNotas.getNota()));
        System.out.println("tipo_evaluacion " + datosNotas.getTipo_evaluacion() );
	System.out.println("gestion  " + datosNotas.getGestion() );
	System.out.println("id_materia  " + datosNotas.getId_materia() );
	System.out.println("nota  " + datosNotas.getNota() );
	
	
	lMateriasNotas.add(i, datosNotas);
      }
      modelo.put("lMaterias", lMateriasNotas);

      List lFichaAcademica = this.mi.getEstListarFichaAcademicaConvalidada2(datosEstudiante);
	  
      modelo.put("total_materias_aprobadas", Integer.toString(lFichaAcademica.size()));

      //Sacamos el plan de estudios
      Planes datosPlan = new Planes();
      datosPlan.setId_programa(datosEstudiante.getId_programa());
      datosPlan.setId_plan(datosEstudiante.getId_plan());
      datosPlan.setId_tipo_grado(datosEstudiante.getId_tipo_grado());
      List lPlanDeEstudios = this.mi.getListarMateriasPlanRequisitos(datosPlan);
   //   modelo.put("total_materias_plan", Integer.toString(lPlanDeEstudios.size()));
      modelo.put("total_materias_plan", Integer.toString(lMateriasNotas.size()));

      //Sacamos el porcentaje de materias aprobadas
      double porcentaje = (lFichaAcademica.size() % lPlanDeEstudios.size()) * 100;
      modelo.put("porcentaje", Double.toString(porcentaje));
      
      //Sacamos el porcentaje de materias aprobadas
      Notas datosNota = new Notas();
      datosNota.setId_estudiante(datosEstudiante.getId_estudiante());
      double promedio = this.mi.getBuscarPromedioDeNotas(datosNota);
      modelo.put("promedio", Double.toString(promedio));
      
      //Sacamos los datos de la institucion
      Instituciones datosInstitucion = new Instituciones();
      datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
      datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
      if (datosInstitucion !=null) {
        modelo.put("datosInstitucion", datosInstitucion);
      }
 
    Instituciones datosInstitucionSede = new Instituciones();
    datosInstitucionSede.setId_institucion(cliente.getId_almacen()); //--------------------------ESTATICO
    datosInstitucionSede = this.mi.getBuscarInstitucionSede(datosInstitucionSede);
    if (datosInstitucionSede !=null) {
      modelo.put("datosInstitucionsede", datosInstitucionSede);
    }
	
      //Sacamos el formato de la fecha
      Abm formatoFecha = new Abm();
      formatoFecha.setCampo("formato_fecha");
      formatoFecha.setCodigo("dibrap");
      modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

      return new ModelAndView("reportesAcademicos/imprimirHistorialAcademicoTipoCarrera/ImprimirHistorialAcademico", modelo);
    }
    
    //Si la busqueda es por CI
    if (!"".equals(sCi)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setDip(sCi);
      datosEstudiante.setId_programa(Integer.parseInt(sId_programa));
      List lEstudiantes = this.mi.getEstListarEstudiantesDip(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
    }
    //Si la busqueda es por nombre
    if (!"".equals(sNombres)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setNombres(sNombres);
      datosEstudiante.setId_programa(Integer.parseInt(sId_programa));
      List lEstudiantes = this.mi.getEstListarEstudiantesNombres(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
    }

    return new ModelAndView("reportesAcademicos/imprimirHistorialAcademicoTipoCarrera/ListarDatosEstudiantes", modelo);
  }
}
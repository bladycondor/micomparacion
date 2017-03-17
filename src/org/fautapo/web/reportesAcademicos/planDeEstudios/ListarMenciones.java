package org.fautapo.web.reportesAcademicos.planDeEstudios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Abm;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Menciones;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarMenciones implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante = new Estudiantes();
    
    int iId_programa = cliente.getInt(request, "id_programa");
    String sId_estudiante = request.getParameter("id_estudiante");
    String sCi = request.getParameter("ci");
    String sNombres = request.getParameter("nombres");

    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(iId_programa);
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);

    if (((sId_estudiante == null) && (sNombres==null) && (sCi==null)) ||
        (("".equals(sId_estudiante)) && ("".equals(sNombres)) && ("".equals(sCi)))) {
      return new ModelAndView("reportesAcademicos/planDeEstudios/BuscarEstudiantes", modelo);
    }
    
    if ((!"".equals(sId_estudiante)) && (sId_estudiante!=null)) {
      //Sacando los datos del estudiante
      datosEstudiante = new Estudiantes();
      try {
       datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      } catch(Exception e) {
        return new ModelAndView("Error", "mensaje", "El R.U. no es valido, introduzca un numero");
      }
      datosEstudiante.setId_programa(iId_programa);
      datosEstudiante = this.mi.getEstBuscarEstudiantePrograma(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);
      if (datosEstudiante == null) {
        return new ModelAndView("reportesAcademicos/planDeEstudios/Aviso","mensaje","El estudiante con R.U. : "+ sId_estudiante + "no esta registrado en el Programa : "+ datosPrograma.getPrograma() + ". Verifique.");
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

      //Sacamos el listado de las materias del plan nuevo
      Planes datosPlan = new Planes();
      datosPlan.setId_programa(datosEstudiante.getId_programa());
      datosPlan.setId_plan(datosEstudiante.getId_plan());
      datosPlan.setId_tipo_grado(datosEstudiante.getId_tipo_grado());
      List lMenciones = this.mi.getMncListarMenciones(datosPlan);
      modelo.put("lMenciones", lMenciones);
      if (lMenciones.size() == 0) {
        List lPlanDeEstudios = this.mi.getListarMateriasPlanRequisitos(datosPlan);
        modelo.put("lPlanDeEstudios", lPlanDeEstudios);
    
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

        return new ModelAndView("reportesAcademicos/planDeEstudios/ListarPlanEstudios", modelo);
      }
      //Sacando la mencion del estudiante
      Menciones datosMencion = new Menciones();
      datosMencion = this.mi.getEstBuscarMencion(datosEstudiante);
      modelo.put("datosMencion", datosMencion);

      return new ModelAndView("reportesAcademicos/planDeEstudios/ListarMenciones", modelo);
    }

    //Si la busqueda es por CI
    if (!"".equals(sCi)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setDip(sCi);
      datosEstudiante.setId_programa(iId_programa);
      List lEstudiantes = this.mi.getEstListarEstudiantesDip(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
    }
    
    //Si la busqueda es por nombre
    if (!"".equals(sNombres)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setNombres(sNombres);
      datosEstudiante.setId_programa(iId_programa);
      List lEstudiantes = this.mi.getEstListarEstudiantesNombres(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
    }

    return new ModelAndView("reportesAcademicos/planDeEstudios/ListarDatosEstudiantes", modelo);

  }
}
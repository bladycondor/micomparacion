package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesAntiguos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-01-13
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ModificarDatosEstudiante implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    
    //Para Wayka
    String sId_tramite = request.getParameter("id_tramite");
    String sId_proceso = cliente.getString(request, "id_proceso");
    String sId_estudiante = cliente.getString(request, "id_estudiante");
    Planes datoPlan = new Planes();
    Programas bProg = new Programas(); 
    Postulantes tiposDoc = new Postulantes();
    Personas dPaises = new Personas();
    
    modelo.put("id_proceso", cliente.getString(request, "id_proceso"));
    modelo.put("id_tramite", sId_tramite);
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));
    modelo.put("nombre", cliente.getString(request, "nombre"));
    modelo.put("ru", cliente.getString(request, "ru"));
    
    if((sId_proceso != null) && (!"".equals(sId_proceso))){
      Actividades proceso = new Actividades();
      proceso.setId_proceso(Integer.parseInt(sId_proceso));
      proceso = this.mi.getBuscarProceso(proceso);
      System.out.println("El PROCESO TITULO -->"+ proceso.getProceso());
      modelo.put("titulo", proceso.getProceso());
      
    }
    
    //if((sId_proceso != null) && (!"".equals(sId_proceso))){
    if((sId_estudiante != null) && (!"".equals(sId_estudiante))) {
      //Si el tamite existe busco el id_estudiante
      //int iId_facultad = cliente.getId_facultad();        
      //int iId_estudiante = Integer.parseInt(tramite.getValores());
      //Busco datos del Estudiante
      Estudiantes datosEst = new Estudiantes();
      datosEst.setId_estudiante(Integer.parseInt(sId_estudiante));
      //Listamos sus matriculas
      System.out.println("El ID ESTUDIANTE -->"+ datosEst.getId_estudiante());
      List lMatriculasEstudiante = this.mi.getMtrListarMatriculasEstudiante(datosEst);
      modelo.put("lMatriculasEstudiante", lMatriculasEstudiante);
      System.out.println("El tamanio MATRICULAs ESTUDIANTE -->"+ Integer.toString(lMatriculasEstudiante.size()));
      
      //Buscar Datos del Estudiante
      datosEst = this.mi.getEstBuscarEstudiantePrs(datosEst);
      modelo.put("datosEst",datosEst);
      bProg.setId_programa(datosEst.getId_programa());
      bProg = this.mi.getPrgBuscarPrograma(bProg);
      modelo.put("id_facultad", Integer.toString(bProg.getId_facultad()));
      //Buscar Persona Colegio
      Personas datosPrs = new Personas();
      datosPrs.setId_persona(datosEst.getId_persona());
      datosPrs = this.mi.getPrsBuscarPersona(datosPrs);
      Personas datosCol = this.mi.getBuscarPersonaColegio(datosPrs);
      modelo.put("datosPrs",datosPrs);
      modelo.put("datosCol",datosCol);
	
      //Listando Facultades
      Universidades datosUniversidad = new Universidades();
      datosUniversidad.setId_universidad(cliente.getId_universidad());
      List lFacultades = this.mi.getUnvListarFacultades(datosUniversidad);
      modelo.put("lFacultades", lFacultades);
      //Listando Programa
      //Sacamos el listado de los programas
      List lProgramas = this.mi.getUnvListarProgramas(datosUniversidad);    
      modelo.put("lProgramas", lProgramas);
      //Listar Plan del programa actual
      List lPlanesActual= this.mi.getListarPrgPlanesActual(datoPlan);
      modelo.put("lPlanesActual",lPlanesActual);
    
      //Listando los tipos
      Programas datosTipoAdmision = new Programas();
      datosTipoAdmision.setGestion(cliente.getGestion());
      datosTipoAdmision.setPeriodo(cliente.getPeriodo());
      List lTiposAdmisiones = this.mi.getListarTiposAdmisionesPrograma(datosTipoAdmision);
      modelo.put("lTiposAdmisiones",lTiposAdmisiones);
    
      List lTiposClasificaciones = this.mi.getListarTiposClasificaciones();
      modelo.put("lTiposClasificaciones", lTiposClasificaciones);
      //Listar TiposDocumentos*tipoclasificacion
      List lTiposDocumentosClasf = this.mi.getListarTiposDocumentosClasificacionVigente(tiposDoc);
      modelo.put("lTiposDocumentosClasf", lTiposDocumentosClasf);
      //Listando Paises
      List lPaises = this.mi.getListarPaises();
      modelo.put("lPaises", lPaises);              
      List lDepartamentos = this.mi.getListarDepartamentos(dPaises);
      modelo.put("lDepartamentos", lDepartamentos);
      List lProvincias = this.mi.getListarProvincias(dPaises);
      modelo.put("lProvincias", lProvincias);
      List lLocalidades = this.mi.getListarLocalidades(dPaises);
      modelo.put("lLocalidades", lLocalidades);
      //Listar Tipos
      List lTiposSexos = this.mi.getListarTiposSexos();
      modelo.put("lTiposSexos", lTiposSexos);
      List lTiposEstadosCiviles = this.mi.getListarTiposEstadosCiviles();
      modelo.put("lTiposEstadosCiviles", lTiposEstadosCiviles);
      List lTiposEmpresasTelefonicas = this.mi.getListarTiposEmpresasTelef();
      modelo.put("lTiposEmpresasTelefonicas", lTiposEmpresasTelefonicas);
      List lTiposInstituciones = this.mi.getListarTiposInstituciones();
      modelo.put("lTiposInstituciones", lTiposInstituciones);
      List lColegiosTipoInst = this.mi.getListarColegiosTipoIns(dPaises);
      modelo.put("lColegiosTipoInst", lColegiosTipoInst);
      List lTiposTurnos = this.mi.getListarTiposTurnos();
      modelo.put("lTiposTurnos", lTiposTurnos);
      List lTiposGrados = this.mi.getListarTiposGrados();
      modelo.put("lTiposGrados", lTiposGrados);
      List lTiposEstudiantes = this.mi.getListarTiposEstudiantes();
      modelo.put("lTiposEstudiantes", lTiposEstudiantes);
     
      
      //Tipo estudiante Nuevo y Tipo Grado
      Personas tipoEst = new Personas();
      tipoEst.setId_tipo_estudiante(datosEst.getId_tipo_estudiante()); //Estudiante Nuevo
      tipoEst= this.mi.getBuscarTipoEstudiante(tipoEst);
      modelo.put("tipoEst",tipoEst);
      datoPlan.setId_tipo_grado(datosEst.getId_tipo_grado());//Grado Universitario
      datoPlan = this.mi.getBuscarTiposGrados(datoPlan);
      modelo.put("datoPlan", datoPlan);	
      //Buscar Tipo clasificacion estudiante
      Estudiantes datosClas = new Estudiantes();
      datosClas.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosClas = this.mi.getBuscarTipoClasificacionEstudiante(datosClas);
      modelo.put("datosClas",datosClas);
      
      return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguos/ModificarDatosEstudiante", modelo);
    
    }
    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguos/EntradaBuscarEstudiantes", modelo);
  }
}
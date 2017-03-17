package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesAntiguosDocumentos;

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

public class EntradaBuscarEstudiantes implements Controller {

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
    
    modelo.put("id_proceso", cliente.getString(request, "id_proceso"));
    modelo.put("id_tramite", sId_tramite);
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));
    
    if((sId_proceso != null) && (!"".equals(sId_proceso))){
      Actividades proceso = new Actividades();
      proceso.setId_proceso(Integer.parseInt(sId_proceso));
      proceso = this.mi.getBuscarProceso(proceso);
      System.out.println("El PROCESO TITULO -->"+ proceso.getProceso());
      modelo.put("titulo", proceso.getProceso());
      modelo.put("proceso", proceso);
    }
    
    if((sId_tramite != null) && (!"".equals(sId_tramite))){
      //Si el tamite existe busco el id_estudiante
      int iId_facultad = cliente.getId_facultad();        
      
      Tramites tramite = new Tramites();
      tramite.setId_tramite(Integer.parseInt(sId_tramite));
      tramite.setEtiqueta("id_estudiante");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      String sId_estudiante = tramite.getValores();
      if(("".equals(sId_estudiante)) || (sId_estudiante == null)){
        return new ModelAndView("Error","mensaje", "En este Nro. de tramite no se encuentra el R.U. del estudiante");
      }

      //Busco datos del Estudiante
      Estudiantes datosEst = new Estudiantes();
      datosEst.setId_estudiante(Integer.parseInt(sId_estudiante));
      //Listamos sus matriculas
      List lMatriculasEstudiante = this.mi.getMtrListarMatriculasEstudiante(datosEst);
      modelo.put("lMatriculasEstudiante", lMatriculasEstudiante);
      datosEst = this.mi.getEstBuscarEstudiantePrs(datosEst);
      modelo.put("datosEst",datosEst);
      //Buscar Persona
      Personas datosPrs = new Personas();
      datosPrs.setId_persona(datosEst.getId_persona());
      datosPrs = this.mi.getPrsBuscarPersona(datosPrs);
      Personas datosCol = this.mi.getBuscarPersonaColegio(datosPrs);
      modelo.put("datosPrs",datosPrs);
      modelo.put("datosCol",datosCol);
	
      Planes datoPlan = new Planes();
      Programas bProg = new Programas(); 
      Postulantes tiposDoc = new Postulantes();
      Personas dPaises = new Personas();
      //Listando Programa
      Programas datoPrograma = new Programas();
      datoPrograma.setId_facultad(iId_facultad);
      List lProgramas = this.mi.getFclListarProgramas(datoPrograma);
      modelo.put("lProgramas",lProgramas);
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
      datosClas.setId_estudiante(datosEst.getId_estudiante());
      datosClas = this.mi.getBuscarTipoClasificacionEstudiante(datosClas);
      modelo.put("datosClas",datosClas);
      
      return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguosDocumentos/ModificarDatosEstudiante", modelo);
    
    }
    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguosDocumentos/EntradaBuscarEstudiantes", modelo);
  }
}
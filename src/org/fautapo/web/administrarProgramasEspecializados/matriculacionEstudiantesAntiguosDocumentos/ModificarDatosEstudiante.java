package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesAntiguosDocumentos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.Facultades;
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
    int iSuspencionGestion=0;
    Planes datoPlan = new Planes();
    Programas bProg = new Programas(); 
    Postulantes tiposDoc = new Postulantes();
    Personas dPaises = new Personas();
    //modelo.put("gestion_matricula", Integer.toString(cliente.getGestion()));
    //modelo.put("periodo_matricula", Integer.toString(cliente.getPeriodo()));
    
    modelo.put("id_proceso", cliente.getString(request, "id_proceso"));
    modelo.put("id_tramite", sId_tramite);
    modelo.put("ru", cliente.getString(request, "ru"));
    
    if((sId_proceso != null) && (!"".equals(sId_proceso))){
      Actividades proceso = new Actividades();
      proceso.setId_proceso(Integer.parseInt(sId_proceso));
      proceso = this.mi.getBuscarProceso(proceso);
      System.out.println("El PROCESO TITULO -->"+ proceso.getProceso());
      modelo.put("titulo", proceso.getProceso());
    }
    
    if((sId_estudiante != null) && (!"".equals(sId_estudiante))) {
      //Busco datos del Estudiante
      Estudiantes datosEst = new Estudiantes();
      datosEst.setId_estudiante(Integer.parseInt(sId_estudiante));
      
      //Primero verificamos si tiene que esta dentro del limite de regularizacion y tiene suspencion
      //Listar el ultimo est_regularizacion
      datosEst.setId_tipo_regularizacion(1); //Suspencion=1
      Estudiantes ultimoEstRegularizacion= this.mi.getMiBuscarUltimoEstRegularizacion(datosEst);
      modelo.put("ultimoEstRegularizacion",ultimoEstRegularizacion);
      
      if(ultimoEstRegularizacion != null) {
        //Sacando el parametro gestiones_suspenciones en _parameteros
	//  Abm formatoParametro = new Abm();
        //  formatoParametro.setCampo("gestiones_suspenciones");
        //  formatoParametro.setCodigo("mi");
        // int iGestion_suspencion = Integer.parseInt(this.mi.getDibBuscarParametro(formatoParametro));
	//  System.out.println("El id gestion suspencion -->"+ Integer.toString(iGestion_suspencion));
	//  iSuspencionGestion = ultimoEstRegularizacion.getGestion() + iGestion_suspencion; 
	//  if((iSuspencionGestion == cliente.getGestion()) && (ultimoEstRegularizacion.getPeriodo() == cliente.getPeriodo()) )
	return new ModelAndView("Aviso","mensaje", "El estudiante con R.U. "+sId_estudiante+
	   " tiene SUSPENCION por lo que  puede estar Bloqueado. Debe regularizar su situacion para el tipo de regularizacion "+ultimoEstRegularizacion.getTipo_regularizacion()+".");
      }
      
      //Listamos sus matriculas
      List lMatriculasEstudiante = this.mi.getMtrListarMatriculasEstudiante(datosEst);
      modelo.put("lMatriculasEstudiante", lMatriculasEstudiante);
      
      //Buscar Datos del Estudiante
      datosEst = this.mi.getEstBuscarEstudiantePrs(datosEst);
      modelo.put("datosEst",datosEst);
      bProg.setId_programa(datosEst.getId_programa());
      bProg = this.mi.getPrgBuscarPrograma(bProg);
      modelo.put("datosPrograma", bProg);
      Facultades datosFacultad = new Facultades();
      datosFacultad.setId_facultad(bProg.getId_facultad());
      datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
      modelo.put("datosFacultad", datosFacultad);
      //Buscar Persona Colegio
      Personas datosPrs = new Personas();
      datosPrs.setId_persona(datosEst.getId_persona());
      datosPrs = this.mi.getPrsBuscarPersona(datosPrs);
      Personas datosCol = this.mi.getBuscarPersonaColegio(datosPrs);
      modelo.put("datosPrs",datosPrs);
      modelo.put("datosCol",datosCol);
      
      //Listando Tipos Clasificacion
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
      
      
      //Listamos tipos descuentos
      List lTiposDescuentos = this.mi.getTrnListarTiposDescuentos();
      modelo.put("lTiposDescuentos", lTiposDescuentos);    
      
      //Sacamos el formato de la fecha
      Abm formatoFecha = new Abm();
      formatoFecha.setCampo("formato_fecha");
      formatoFecha.setCodigo("dibrap");
      modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
      //Sacamos el formata de la gestion siguiente
      formatoFecha.setCampo("gestion_siguiente");
      formatoFecha.setCodigo("mi");
      modelo.put("gestion_siguiente", this.mi.getDibBuscarParametro(formatoFecha));
      //Sacamos el formata de la periodo siguiente
      formatoFecha.setCampo("periodo_siguiente");
      formatoFecha.setCodigo("mi");
      modelo.put("periodo_siguiente", this.mi.getDibBuscarParametro(formatoFecha));
      
      return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguosDocumentos/ModificarDatosEstudiante", modelo);
    
    }
    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguosDocumentos/EntradaBuscarEstudiantes", modelo);
  }
}
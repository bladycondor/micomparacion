package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesNuevos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class BuscarDatosEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    int iId_facultad = cliente.getId_facultad();
   
    int iId_estudiante  = cliente.getInt(request, "id_estudiante");
    int iGestion = cliente.getInt(request, "gestion");
    int iPeriodo = cliente.getInt(request, "periodo");
    String sId_tipo_admision_entrada = request.getParameter("id_tipo_admision_entrada");
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("id_tipo_admision_entrada", sId_tipo_admision_entrada);
    modelo.put("nombre", request.getParameter("nombre"));
    modelo.put("dip", request.getParameter("dip"));

    Programas bProg = new Programas(); 
    Postulantes tiposDoc = new Postulantes();
    Personas dPaises = new Personas();
    Personas datosPersEst = new Personas();
    String sId_facultad ="";
    
    //Como es admision especial buscamos los datos del estudiante
    //Buscamos datos del postulante aprobado
    if (iId_estudiante >0) {
      Estudiantes buscarEst = new Estudiantes();
      buscarEst.setId_estudiante(iId_estudiante);
      buscarEst = this.mi.getEstBuscarEstudiantePrs(buscarEst);
      modelo.put("buscarEst", buscarEst);
      
      //Buscar datosPersonaEstudiane - datosPstColegio
      if(buscarEst != null) {
        datosPersEst.setId_persona(buscarEst.getId_persona());
	Personas datosPrs = this.mi.getPrsBuscarPersona(datosPersEst);
	Personas bEstColegio = this.mi.getBuscarPersonaColegio(datosPersEst);
	modelo.put("datosPrs",datosPrs);
	modelo.put("bEstColegio", bEstColegio);
      }
      else{
        return new ModelAndView("Aviso","mensaje","No se puede encontrar al estudiante");
      }
      
      bProg.setId_programa(buscarEst.getId_programa());
      bProg = this.mi.getPrgBuscarPrograma(bProg);
      modelo.put("id_facultad", Integer.toString(bProg.getId_facultad()));  
    }
    
    //Buscar Tipo Admision
    if(!"".equals(sId_tipo_admision_entrada)) {
      Estudiantes datosTipoAdm = new Estudiantes();
      datosTipoAdm.setId_tipo_admision(Integer.parseInt(sId_tipo_admision_entrada));
      datosTipoAdm = this.mi.getBuscarTipoAdmision(datosTipoAdm);
      modelo.put("datosTipodm",datosTipoAdm);
    }
    
    //Listando Facultades
    Universidades datosUniversidad = new Universidades();
    datosUniversidad.setId_universidad(cliente.getId_universidad());
    List lFacultades = this.mi.getUnvListarFacultades(datosUniversidad);
    modelo.put("lFacultades", lFacultades);

    //Sacamos el listado de los programas
    List lProgramas = this.mi.getUnvListarProgramas(datosUniversidad);    
    modelo.put("lProgramas", lProgramas);
    //Listar Plan del programa actual
    List lPlanesActual= this.mi.getListarPrgPlanesUniversitarios();
    modelo.put("lPlanesActual", lPlanesActual);
    System.out.println("El tamaio de lista de planes -->"+Integer.toString(lPlanesActual.size()));
    
    //Listando los tipos
    List lTiposAdmisiones = this.mi.getListarTiposAdmisiones();
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
    //Tipo estudiante Nuevo y Tipo Grado
    Personas tipoEst = new Personas();
    tipoEst.setId_tipo_estudiante(1); //Estudiante Nuevo
    tipoEst= this.mi.getBuscarTipoEstudiante(tipoEst);
    modelo.put("tipoEst",tipoEst);
    Planes datoPlan = new Planes();
    datoPlan.setId_tipo_grado(1);//Grado Universitario
    datoPlan = this.mi.getBuscarTiposGrados(datoPlan);
    modelo.put("datoPlan", datoPlan);
    
    //Listamos tipos descuentos
    List lTiposDescuentos = this.mi.getTrnListarTiposDescuentos();
    modelo.put("lTiposDescuentos", lTiposDescuentos);    
    
    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
    
        
    //Para wayka
    modelo.put("id_proceso", cliente.getString(request, "id_proceso"));
    modelo.put("titulo", cliente.getString(request, "titulo"));
    modelo.put("id_tramite", cliente.getString(request, "id_tramite"));
    
    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesNuevos/BuscarDatosEstudiante", modelo);
    
    
  }
}
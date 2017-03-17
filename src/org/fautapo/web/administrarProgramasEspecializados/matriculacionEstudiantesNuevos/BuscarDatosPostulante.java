package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesNuevos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class BuscarDatosPostulante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    int iId_facultad = cliente.getId_facultad();
   
    int iId_postulante  = cliente.getInt(request, "id_postulante");
    int iGestion = cliente.getInt(request, "gestion");
    int iPeriodo = cliente.getInt(request, "periodo");
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("nombre", request.getParameter("nombre"));
    modelo.put("dip", request.getParameter("dip"));
    
    Programas bProg = new Programas(); 
    Postulantes tiposDoc = new Postulantes();
    Personas dPaises = new Personas();
    String sId_facultad ="";
    //Buscamos datos del postulante aprobado
    if (iId_postulante >0) {
      Postulantes bPst = new Postulantes();
      bPst.setId_postulante(iId_postulante);
	  bPst.setIns_sede(cliente.getId_almacen());
      bPst = this.mi.getPstBuscarPostulanteNombresSede(bPst);
      modelo.put("bPst", bPst);
      //Buscar datosPstColegio
      if(bPst != null) {
        bPst.setId_persona(bPst.getId_persona());
	Postulantes bPstColegio = this.mi.getBuscarPstPersonaColegio(bPst);
	modelo.put("bPstColegio", bPstColegio);
      }else{return new ModelAndView("Error","mensaje","El estudiante con ID: "+iId_postulante+" no pertenece a su sede y segun Reglamento Academico no puede realizar la matriculacion ");
	  }
      
      bProg.setId_programa(bPst.getId_programa());
      bProg = this.mi.getPrgBuscarPrograma(bProg);
      modelo.put("id_facultad", Integer.toString(bProg.getId_facultad()));  
    }
    

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
    List lPlanesActual= this.mi.getListarPrgPlanesUniversitarios();
    modelo.put("lPlanesActual",lPlanesActual);
    
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
    
    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesNuevos/BuscarDatosPostulante", modelo);
    
    
  }
}
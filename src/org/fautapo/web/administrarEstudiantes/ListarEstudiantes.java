package org.fautapo.web.administrarEstudiantes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.Facultades;
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

public class ListarEstudiantes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }

    String sDip     = cliente.getString(request, "dip");
    String sNombre  = cliente.getString(request, "nombre");
    String sId_estudiante  = cliente.getString(request, "id_estudiante");
    String sBotonDip = request.getParameter("botonDip");
    String sBotonNombre = request.getParameter("botonNombre");
    List lEstPersonas; //List lEstudiantes;
    
    Estudiantes datosEstudiante = new Estudiantes();
    Planes datoPlan = new Planes();
    Programas bProg = new Programas(); 
    Postulantes tiposDoc = new Postulantes();
    Personas dPaises = new Personas();
    
    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    if (("".equals(sId_estudiante)) &&("".equals(sDip)) && ("".equals(sNombre)))
      return new ModelAndView("administrarEstudiantes/EntradaBuscarPostulantes");
      
    
    //Buscar por id_estudiante
    if((sId_estudiante != null) && (!"".equals(sId_estudiante))) {
      //Busco datos del Estudiante
      Estudiantes datosEst = new Estudiantes();
      datosEst.setId_estudiante(Integer.parseInt(sId_estudiante));
	  datosEst.setIns_sede(cliente.getId_almacen());
	  datosEst = this.mi.getEstBuscarEstudiantePrsPreSede(datosEst);
      modelo.put("datosEst",datosEst);
     
      if(datosEst == null)
        return new ModelAndView("Aviso", "mensaje", "De acuerdo a Reglamento usted no puede modificar datos del Estudiante "+ sId_estudiante+" "+" porque no pertenece a su Area y/o Sede");
     /* Perfiles datosTransaccion = new Perfiles();
      datosTransaccion.setIns_sede(cliente.getId_almacen());
	  datosTransaccion=this.mi.getTrnBuscarTransaccionEstSede(datosTransaccion);
     
    if (datosTransaccion==null) {
      return new ModelAndView("Aviso", "mensaje", "No existe el "+ sId_estudiante+" no es de su Área");
    }*/
   // Estudiantes datosEst = new Estudiantes();
	datosEst.setId_estudiante(Integer.parseInt(sId_estudiante));  
      //Listamos sus matriculas
      List lMatriculasEstudiante = this.mi.getMtrListarMatriculasEstudiante(datosEst);
      modelo.put("lMatriculasEstudiante", lMatriculasEstudiante);
      
      //Buscar Datos del Estudiante
      datosEst = this.mi.getEstBuscarEstudiantePrsPre(datosEst);
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
      //Buscar Tipo clasificacion persona
      Estudiantes datosClas = new Estudiantes();
      datosClas.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosClas = this.mi.getBuscarTipoClasificacionEstudiante(datosClas);
      modelo.put("datosClas",datosClas);
      
      
      
      return new ModelAndView("administrarEstudiantes/ModificarDatosEstudiante", modelo);
    }
    
    if ("".equals(sDip)) {
      datosEstudiante.setNombres(sNombre);
      lEstPersonas = this.mi.getEstListarEstudiantesNombres2(datosEstudiante);
      //System.out.println("Nuemero de la lista  por nombres-->"+ Integer.toString(lEstPersonas.size()));
    } else {
      datosEstudiante.setDip(sDip);
      lEstPersonas = this.mi.getEstListarEstudiantesDip2(datosEstudiante);
      //System.out.println("Nuemero de la lista  por dip-->"+ Integer.toString(lEstPersonas.size()));
    }
    modelo.put("lEstPersonas", lEstPersonas);
    return new ModelAndView("administrarEstudiantes/ListarEstudiantes", modelo);
  }
}
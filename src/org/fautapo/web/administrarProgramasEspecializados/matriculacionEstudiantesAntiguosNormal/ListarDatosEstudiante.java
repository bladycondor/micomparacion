package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesAntiguosNormal;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Personas;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarDatosEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    int iId_estudiante = 0; 
	int iGestion = 0;
	int iPeriodo = 0;
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    
    String sId_estudiante = request.getParameter("id_estudiante");
    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_estudiante", sId_estudiante);
    modelo.put("id_tramite", sId_tramite);

    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));

	
    try {
	  iGestion =  cliente.getGestion();
	  iPeriodo =  cliente.getPeriodo();
      iId_estudiante = Integer.parseInt(sId_estudiante);
    }
    catch(Exception e) {
      return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguosNormal/Entrada", modelo);
    }

    //Sacamos los datos del Estudiante
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(iId_estudiante);
    datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);	
	
	//Sacamos los datos de la documentación de la persona
	//Personas datosPersona = new Personas();
	//datosPersona.setId_persona(datosEstudiante.getId_persona());
	//int id_documento = this.mi.getBuscarPrsDocumentacionCompleta(datosPersona);
	
	//Sacamos los datos de la matricula
	Estudiantes datosMatricula = new Estudiantes();
    datosMatricula.setId_estudiante(iId_estudiante);
	datosMatricula.setGestion(iGestion);
	datosMatricula.setPeriodo(iPeriodo);
    datosMatricula = this.mi.getMtrBuscarMatricula(datosMatricula);
    
	//Sacamos si el estudiante no tiene deudas pendientes
	Estudiantes datosDeuda = new Estudiantes();
    datosDeuda.setId_estudiante(iId_estudiante);	
    datosDeuda = this.mi.getBuscarUltimaEstDeuda(datosDeuda);	
	
	int iId_proceso = cliente.getInt(request, "id_proceso");
	
	if ((iId_proceso==4) || (iId_proceso==3)||(iId_proceso==25)){
    if(datosEstudiante ==null) { return new ModelAndView("Aviso", "mensaje", "No existe el estudiante con R.U. = "+ sId_estudiante);}
    if(datosMatricula !=null) { return new ModelAndView("Aviso", "mensaje", "El Estudiante con R.U.= "+ sId_estudiante+" ya se encuentra Inscrito en el Periodo : "+iPeriodo+" Gestion : "+iGestion);}
	if(datosDeuda !=null) { return new ModelAndView("Aviso", "mensaje", "El Estudiante con R.U.= "+ sId_estudiante+" tiene deudas de : "+datosDeuda.getTipo_deuda()+" pasar por Registros e Inscripciones ");}	
	//if(id_documento ==0) { return new ModelAndView("Aviso", "mensaje", "El estudiante con R.U. = "+ sId_estudiante+" tiene deudas con Documentos de Admisión");}//
    if(!"A".equals(datosEstudiante.getId_estado())) return new ModelAndView("Aviso","mensaje","El estudiante con R.U. = "+sId_estudiante+". No esta activo para ser matriculado");
    }   
    //Buscamos los datos del proceso
    Actividades datosProceso = new Actividades();
    datosProceso.setId_proceso(Integer.parseInt(request.getParameter("id_proceso")));
    datosProceso = this.mi.getBuscarProceso(datosProceso);
    modelo.put("datosProceso", datosProceso);

    try {
      //Sacamos el id_campo
      Tramites datosTramite = new Tramites();
      datosTramite.setId_proceso(Integer.parseInt(sId_proceso));
      datosTramite.setEtiqueta("requisitos");
      int iId_campo = this.mi.getBuscarIdCampoLimbo(datosTramite);

      //Buscamos los datos del campo
      Campos datosCampo = new Campos();
      datosCampo.setId_campo(iId_campo);
      datosCampo = this.mi.getBuscarCampoForm(datosCampo);

      //Sacamos los datos del dominio
      Dominios datosDominio =  new Dominios();
      datosDominio.setId_dominio(datosCampo.getId_dominio());
      datosDominio = this.mi.getBuscarDominio(datosDominio);
    
      //Sacamos los datos de los requisitos
      datosTramite = new Tramites();
      datosTramite.setId_dominio(datosDominio.getId_dominio());
      datosTramite.setId_tupla_padre(datosDominio.getId_tupla_padre());
      datosTramite.setId_tipo_dominio(datosDominio.getId_tipo_dominio());
      datosTramite.setId_dominio_padre(datosDominio.getId_dominio_padre());
      List lTuplas = this.mi.getListarCombos2(datosTramite);
      modelo.put("lTuplas", lTuplas);
    } catch(Exception e) {
      System.out.println("El proceso no tiene requisitos");
    }

    try {
      //Verificamos si el perfil puede tener descuento
      Perfiles datosPerfil = new Perfiles();
      datosPerfil.setId_proceso(Integer.parseInt(sId_proceso));
      List lPerfiles = this.mi.getTrnMiListarPerfilesProceso(datosPerfil);
      datosPerfil = (Perfiles) lPerfiles.get(0);
      int iTieneDescuento = this.mi.getTrnPerfilTieneDescuento(datosPerfil);
      modelo.put("tieneDescuento", Integer.toString(iTieneDescuento));
      modelo.put("descuento", "0");
      //Listamos tipos descuentos
      List lTiposDescuentos = this.mi.getTrnListarTiposDescuentos();
      modelo.put("lTiposDescuentos", lTiposDescuentos);    
      
    } catch(Exception e) {
      return new ModelAndView("Error", "mensaje", "El proceso no tiene asignado un perfil");
    }
    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguosNormal/ListarDatosEstudiante", modelo);
  }
}
package org.fautapo.web.administrarCertificados.reimprimirCertificados;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.String;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Literales;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Usuarios;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class ListarConceptosImpresion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
     
	
	Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();
    modelo.put("cliente", cliente);

    // Comprobamos si es quien debe ingresar al módulo, de acuerdo a su clave
    Usuarios usuario = new Usuarios();
    usuario.setId_usuario(cliente.getId_usuario());
    usuario.setClave(request.getParameter("clave" + request.getParameter("hora")));

    if (null == this.mi.getComprobarUsuario(usuario)) {
      return new ModelAndView("administrarCertificados/reimprimirCertificados/Aviso", "mensaje", "Clave incorrecta, por favor, vuelva a intentarlo");
    }
    
    String sNro_certificado = cliente.getString(request, "nro_certificado");
	String sSedes = cliente.getString(request, "sede");
	
	System.out.println(sNro_certificado);
	System.out.println(sSedes);
  
 
 //*naga
    Estudiantes estnuevo = new Estudiantes();
 
	estnuevo.setNrocertificado_gestion(sNro_certificado);
	System.out.println("nro cert" + estnuevo.getNrocertificado_gestion());
	
	estnuevo.setid_sede(Integer.parseInt(sSedes));
	System.out.println("sede" + estnuevo.getid_sede());
	
	//CONTROL DE NRO TRANSACCION
	int control_nro;
	/*Estudiantes ng = new Estudiantes();
    ng.setNrocertificado_gestion(sNro_certificado);
	ng.setid_sede(sSedes);*/
	 control_nro = this.mi.getcert_buscar_nro_certificado_gestioncode(estnuevo);
	 if (control_nro== 0) {
        return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasCode/Aviso","mensaje","El numero de certificado o la sede no corresponden a un certificado emitido ");
     }
	//
	List estcert = this.mi.getListarCertGen(estnuevo);
	 /*  System.out.println("aqui viene la lista");
	  System.out.println(estcert.size());
	
	/ Estudiantes estudiante = new Estudiantes();*/
        //estudiante.setId_persona(datosTransaccion.getId_persona());
        //estudiante.setId_programa(datosTransaccion.getId_programa());
		//estnuevo.setid_sede(Integer.parseInt(sSedes));
		//estnuevo.setnro_certificado(Integer.parseInt(sNro_certificado));
	//estnuevo = this.mi.getListarCertGen(estnuevo);
	//estudiante = this.mi.getEstBuscarEstudianteNombres(estudiante);
	
      //Control Solo valorado de certificado de notas
	  Estudiantes datosIdcertificados = new Estudiantes();
	    for (int i=0; i<estcert.size();i++) {
        datosIdcertificados = (Estudiantes) estcert.get(i);
        }
		//if (datosPerfiles.getId_concepto()!= 27) return new ModelAndView("Aviso", "mensaje", "El nro de recibo no es valido para emitir un certificado de notas.");
	
	 	System.out.println("objeto  datosIdcertificados --> "+ datosIdcertificados.getid_certificados_generados());
 	//fin control
		
	  //*naga	
		 Estudiantes estnuevo1 = new Estudiantes();

	//estnuevo1.setNrocertificado_gestion(sNro_certificado);
	//System.out.println("nro cert" + estnuevo.getNrocertificado_gestion());
	
	//estnuevo.setid_sede(Integer.parseInt(sSedes));
	//System.out.println("sede" + estnuevo.getid_sede());
	
	
	List estcert1 = this.mi.getListarNotasCertificados(datosIdcertificados);
	 /*  System.out.println("aqui viene la lista");
	  System.out.println(estcert.size());
	
	/ Estudiantes estudiante = new Estudiantes();*/
        //estudiante.setId_persona(datosTransaccion.getId_persona());
        //estudiante.setId_programa(datosTransaccion.getId_programa());
		//estnuevo.setid_sede(Integer.parseInt(sSedes));
		//estnuevo.setnro_certificado(Integer.parseInt(sNro_certificado));
	//estnuevo = this.mi.getListarCertGen(estnuevo);
	//estudiante = this.mi.getEstBuscarEstudianteNombres(estudiante);
	  modelo.put("estcert", estcert);
      modelo.put("estcert1", estcert1);
  /**/
  /**/
  
  
  
    String sNro_recibo = cliente.getString(request, "nro_recibo");
	System.out.println("EL ID estudiante RXXXXXXXXXXXXXXXX -->"+sNro_recibo);
	
    //Sacamos los datos para la impresion del RECIBO
    Perfiles datosTransaccion = new Perfiles();

    datosTransaccion.setNro_recibo(sNro_recibo);
	datosTransaccion.setIns_sede(cliente.getId_almacen());
 
    //Sacamos los datos de la transaccion
	//datosTransaccion=this.mi.getTrnBuscarTransaccionReciboSede(datosTransaccion);
   // datosTransaccion = this.mi.getTrnBuscarTransaccionRecibo(datosTransaccion);
   
    if (datosTransaccion==null) {
      return new ModelAndView("administrarCertificados/reimprimirCertificados/Aviso", "mensaje", "No existe una transaccion con Nro. de Certificado en esta sede"+sNro_recibo);
    }
    
    Literales literal = new Literales();
    modelo.put("literal", literal.convert(datosTransaccion.getPagado()));
    datosTransaccion.setIns_sede(cliente.getId_almacen());
    //Sacamos el listado de trn_detalles
    List lDetalles = this.mi.getTrnListarTransaccionesReciboSede(datosTransaccion);
    
	modelo.put("lDetalles", lDetalles);

    /* Datos de la Persona
    if (datosTransaccion.getId_persona() > 0) {
      if (datosTransaccion.getId_programa() > 0) {
        Estudiantes estudiante = new Estudiantes();
        estudiante.setId_persona(datosTransaccion.getId_persona());
        estudiante.setId_programa(datosTransaccion.getId_programa());
	estudiante = this.mi.getMiPrsBuscarEstudiante(estudiante);
	estudiante = this.mi.getEstBuscarEstudianteNombres(estudiante);
        modelo.put("estudiante", estudiante);
      } else {
        Personas persona = new Personas();
        persona.setId_persona(datosTransaccion.getId_persona());
        persona = this.mi.getPrsBuscarPersona(persona);
        modelo.put("estudiante", persona);
      }
    } else if (datosTransaccion.getId_persona_pst() > 0) {
      Postulantes postulante = new Postulantes();
      postulante.setId_persona(datosTransaccion.getId_persona_pst());
      if (datosTransaccion.getId_programa() > 0) {
        postulante.setId_programa(datosTransaccion.getId_programa());
	postulante = this.mi.getMiPrsBuscarPostulante(postulante);
        postulante = this.mi.getPstBuscarPostulanteNombres(postulante);
      } else
        postulante = this.mi.getPstBuscarPersona(postulante);
      modelo.put("estudiante", postulante);
    }
    // fin - Datos de la Persona*/

	
    //Sacamos los datos del perfil
    Perfiles datosPerfil = new Perfiles();
    datosPerfil.setId_perfil(datosTransaccion.getId_perfil());
    datosPerfil = this.mi.getPrfBuscarPerfil(datosPerfil);
    modelo.put("datosPerfil", datosPerfil);

    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    //Sacamos el formato de la hora
    formatoFecha.setCampo("formato_hora");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoFecha));

    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    modelo.put("datosInstitucion", datosInstitucion);
    modelo.put("datosTransaccion", datosTransaccion); 
    Instituciones datosInstitucionSede = new Instituciones();
    datosInstitucionSede.setId_institucion(cliente.getId_almacen()); //--------------------------ESTATICO
    datosInstitucionSede = this.mi.getBuscarInstitucionSede(datosInstitucionSede);
    if (datosInstitucionSede !=null) {
      modelo.put("datosInstitucionsede", datosInstitucionSede);
    } 
  return new ModelAndView("administrarCertificados/reimprimirCertificados/ReimprimirCertificadoNotas", modelo);
  
  }
  
}
package org.fautapo.web.reportesAcademicos.imprimirCertificadoNotasCode;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Programas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Literales;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Personas;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class ListarProgramasPlanes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    String sGestion = request.getParameter("gestion");	
    String sPeriodo = request.getParameter("periodo");
	String sNro_recibo = cliente.getString(request, "nrocertificado");
	//String sId_programa = request.getParameter("id_programa");
    if ((sGestion != null) && (sPeriodo != null)) {
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
	modelo.put("nrocertificado",sNro_recibo);
	// modelo.put("id_programa", sId_programa);
	System.out.println("recivo%%1 --> "+sNro_recibo);
	System.out.println("gestion%%1--> "+sGestion);
	System.out.println("periodo%%1 --> "+sPeriodo);
	  //BUSCAR CERTIFICADO
	    //Sacamos los datos para la impresion del RECIBO
    Perfiles datosTransaccion = new Perfiles();
    datosTransaccion.setNro_recibo(sNro_recibo);
	datosTransaccion.setIns_sede(cliente.getId_almacen());
    //Sacamos los datos de la transaccion
	datosTransaccion=this.mi.getTrnBuscarTransaccionReciboSede(datosTransaccion);
    Literales literal = new Literales();
    modelo.put("literal", literal.convert(datosTransaccion.getPagado()));
    datosTransaccion.setIns_sede(cliente.getId_almacen());
	
    //Sacamos el listado de trn_detalles
    List lDetalles = this.mi.getTrnListarTransaccionesReciboSede(datosTransaccion);
    
	modelo.put("lDetalles", lDetalles);

    // Datos de la Persona
    if (datosTransaccion.getId_persona() > 0) {
      if (datosTransaccion.getId_programa() > 0) {
        Estudiantes estudiante = new Estudiantes();
        estudiante.setId_persona(datosTransaccion.getId_persona());
        estudiante.setId_programa(datosTransaccion.getId_programa());
	estudiante = this.mi.getMiPrsBuscarEstudiante(estudiante);
	estudiante = this.mi.getEstBuscarEstudianteNombres(estudiante);
        modelo.put("estudiante", estudiante);
		System.out.println("estudianteNOMBRES%%1 --> "+estudiante);
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
    // fin - Datos de la Persona
    //Buscar Programa
    //  Programas datosPrograma = new Programas();
     // datosPrograma.setId_programa(Integer.parseInt(sId_programa));
     // datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
     // modelo.put("datosPrograma", datosPrograma);
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    //return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasCode/ListarProgramasPlanes", modelo);
    return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasCode/ListarProgramasPlanes", modelo);
	}
    
    // Comprobamos si es quien debe ingresar al módulo, de acuerdo a su clave
  
   
	modelo.put("nrocertificado",sNro_recibo);
	
	
	System.out.println("recivo%%2 --> "+sNro_recibo);
	System.out.println("gestion%%2 --> "+sGestion);
	System.out.println("periodo%%2 --> "+sPeriodo); 
	
    return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasCode/ListarProgramasPlanes", modelo);
  }
}

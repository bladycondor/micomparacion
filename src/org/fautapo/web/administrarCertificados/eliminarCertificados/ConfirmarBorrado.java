package org.fautapo.web.administrarCertificados.eliminarCertificados;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;

import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Abm;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
 */

public class ConfirmarBorrado implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();

    String sClave = cliente.getString(request, "clave"+ request.getParameter("hora"));
    if("".equals(sClave)) return new ModelAndView("administrarCertificados/Entrada", null);

    //Comprobamos la clave del usuario
    Usuarios usuario = new Usuarios();
    usuario.setId_usuario(cliente.getId_usuario());
    usuario.setClave(sClave);
    if (this.mi.getVerificarUsuario(usuario) == 0) 
      return new ModelAndView("administrarCertificados/eliminarCertificados/Error", "mensaje", "Clave incorrecta, vuelva a intentar.");

    String sNro_certificado = cliente.getString(request, "nro_certificado");
	String sSedes = cliente.getString(request, "sede");
	
	System.out.println("nCer"+sNro_certificado); 
	System.out.println("sede"+sSedes); 
	
	List certgen = new ArrayList();
	
	Estudiantes estudiante = new Estudiantes();
    estudiante.setNrocertificado_gestion(sNro_certificado);
	estudiante.setid_sede(Integer.parseInt(sSedes));
	certgen = this.mi.getListarCertGen(estudiante);
    //Estudiantes estudiante = new Estudiantes();
    //estudiante.setNrocertificado_gestion(sNro_certificado);
	//estudiante.setsede(sSedes);
	System.out.println("GET LISTAR CERTIFICADOS "+ certgen.size()); 
	System.out.println(certgen);
    //estudiante  = this.mi.getListarCertificadosGenerados(estudiante);
    /*for(int x=0;x < LCertGen.size();x++) {
      System.out.println(LCertGen.get(1));
    }*/

    //List lNotas = new ArrayList();
    //modelo.put("LCertGen",LCertGen);
	
     modelo.put("certgen", certgen);
	 
	 
    
    // modelo.put("datosPrograma", datosPrograma);*/

	   
	//Programas datosPrograma = new Programas();
    //datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    //datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    //  modelo.put("datosPrograma", datosPrograma);
    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    return new ModelAndView("administrarCertificados/eliminarCertificados/ConfirmarBorrado", modelo);
  }

}
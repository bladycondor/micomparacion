package org.fautapo.web.cambioPinEstudiante.estudiante;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Personas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-06-22
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-06-22
 */

public class GuardarNuevoPin implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    String sNombres = cliente.getNombres();
    int iGestion = cliente.getGestion();
    int iPeriodo = cliente.getPeriodo();        
    int iId_estudiante = cliente.getId_usuario();
    String sMensaje="";
    String sClave_nueva = request.getParameter("clave_nueva");
    String sConfirmar_clave = request.getParameter("confirmar_clave");
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("nombres", sNombres);
    modelo.put("clave_nueva", sClave_nueva);
    modelo.put("confirmar_clave", sConfirmar_clave);
    
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(iId_estudiante);
    datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    if(datosEstudiante == null){
        return new ModelAndView("Aviso","mensaje","No se encuentran datos del estuadiante con R.U. "+ Integer.toString(iId_estudiante));      
    }
    //Datos de la matricula
    Estudiantes datosMatricula = new Estudiantes();
    datosMatricula.setId_estudiante(iId_estudiante);
    datosMatricula.setGestion(iGestion);
    datosMatricula.setPeriodo(iPeriodo);
    datosMatricula = this.mi.getMtrBuscarMatricula(datosMatricula);
    if(datosMatricula == null){
        return new ModelAndView("Aviso","mensaje","No esta matriculado para el periodo acad&eacute;mico"+ Integer.toString(iPeriodo)+"/"+ Integer.toString(iGestion));      
    }
    
    if (("".equals(sClave_nueva)) && ("".equals(sConfirmar_clave))) {
      return new ModelAndView("cambioPinEstudiante/estudiante/CambioPinEstudiante", modelo);
    }
    
    int sNrocarac = sClave_nueva.length();
    int sValor = 6;

    if(sNrocarac >= sValor){    
      if(sClave_nueva.equals(sConfirmar_clave)) {
        Estudiantes sDatosEst = new Estudiantes();
        sDatosEst.setId_estudiante(iId_estudiante);
	sDatosEst.setClave(sClave_nueva);
	sDatosEst.setGestion(iGestion);
	sDatosEst.setPeriodo(iPeriodo);
	sDatosEst.setId_rol(cliente.getId_rol());
	sDatosEst.setUlt_usuario(cliente.getId_usuario());
        int iResultado = this.mi.setMtrModificarPinEstudiante(sDatosEst);
	switch (iResultado) {
          case 0: sMensaje="No se realiz&oacute; el registro <br> Revise los datos."; break;  
          case 1: return new ModelAndView("cambioPinEstudiante/estudiante/Aviso","mensaje","Se realiz&oacute; el registro"); 
          case 2: sMensaje="La clave que ingreso ya existe. <br>D&iacute;gite una clave diferente."; break;
        }
        modelo.put("mensaje",sMensaje);
        return new ModelAndView("Error",modelo);
      }
      else {
        sMensaje = "No coincide la confirmaci&oacute;n de la clave";
        modelo.put("mensaje",sMensaje);
        return new ModelAndView("Error", modelo);    
      }
    }else {
       sMensaje = "D&iacute;gite un m&iacute;nimo de 6 caracteres";
       modelo.put("mensaje",sMensaje);
       return new ModelAndView("Error",modelo);
    }      

  }
}
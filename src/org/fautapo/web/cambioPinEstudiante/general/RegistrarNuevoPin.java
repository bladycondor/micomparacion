package org.fautapo.web.cambioPinEstudiante.general;

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

public class RegistrarNuevoPin implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    String sUsuario = cliente.getNombres();
    String sNombres = request.getParameter("nombres");
    String sDip = request.getParameter("dip");
    String sMensaje="";
    int iGestion = cliente.getInt(request,"gestion");
    int iPeriodo = cliente.getInt(request,"periodo");        
    int iId_estudiante = cliente.getInt(request,"id_estudiante");
    int iId_matricula = cliente.getInt(request,"id_matricula");
    
    
    String sClave_nueva = request.getParameter("clave_nueva");
    String sConfirmar_clave = request.getParameter("confirmar_clave");
    String sApodo_nuevo = request.getParameter("apodo_nuevo");
    String sConfirmar_apodo = request.getParameter("confirmar_apodo");
    
    modelo.put("dip", sDip);
    modelo.put("nombres", sNombres);
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("clave_nueva", sClave_nueva);
    modelo.put("confirmar_clave", sConfirmar_clave);
    modelo.put("apodo_nuevo", sApodo_nuevo);
    modelo.put("confirmar_apodo", sConfirmar_apodo);
    modelo.put("id_estudiante", Integer.toString(iId_estudiante));
    modelo.put("usuario", cliente.getNombres());
    //Buscamos datos del Estudiante
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(iId_estudiante);
    datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
    modelo.put("datosEstudiante",datosEstudiante);
    
    //Buscar Matriculacion
    datosEstudiante.setId_estudiante(iId_estudiante);
    datosEstudiante.setGestion(iGestion);
    datosEstudiante.setPeriodo(iPeriodo);
    Estudiantes datosMatriculaEst = this.mi.getMtrBuscarMatricula(datosEstudiante);
    modelo.put("datosMatriculaEst", datosMatriculaEst);
    if(datosMatriculaEst == null)
      return new ModelAndView("Error","mensaje", "El estudiante con R.U."+Integer.toString(iId_estudiante)+" no esta matriculado para la gesti&ocute;n acad&eacute;mica "+ Integer.toString(iPeriodo)+"<b>/</b>"+Integer.toString(iGestion));

    if (("".equals(sClave_nueva)) && ("".equals(sConfirmar_clave)) && ("".equals(sApodo_nuevo)) && ("".equals(sConfirmar_apodo))) {
      return new ModelAndView("cambioPinEstudiante/general/NuevoPinEstudiante", modelo);
    }
    
    //Longitud
    int iNroCaracClave = sClave_nueva.length();
    int iNroCaracApodo = sApodo_nuevo.length();
    int iValor = 6;

   if((iNroCaracClave >= iValor) && (iNroCaracApodo >=iValor)) {    
     if(!sClave_nueva.equals(sConfirmar_clave)) 
       return new ModelAndView("Error","mensaje","No coincide en la confirmaci&oacute; de la clave");
     
     if(!sApodo_nuevo.equals(sConfirmar_apodo)) 
       return new ModelAndView("Error","mensaje","No coincide en la confirmaci&oacute; del apodo");  
    
     //datosEstudiante.setId_estudiante(iId_estudiante);
     datosEstudiante.setClave(sClave_nueva);
     datosEstudiante.setApodo(sApodo_nuevo);
     datosEstudiante.setGestion(iGestion);
     datosEstudiante.setPeriodo(iPeriodo);
     datosEstudiante.setId_rol(cliente.getId_rol());
     datosEstudiante.setUlt_usuario(cliente.getId_usuario());
     int iResultado = this.mi.setMtrModificarApodoClaveEstudiante(datosEstudiante);
     /*switch (iResultado) {
       case 0: return new ModelAndView("Error","mensaje","No se realizo el registro <br> Revise los datos.");  
       case 1: return new ModelAndView("cambioPinEstudiante/general/Aviso","mensaje","Se realizo el registro"); 
       case 2: return new ModelAndView("Aviso","mensaje","La clave que ingreso ya existe. <br>D&iacute;gite una clave diferente.");
       case 3: return new ModelAndView("Aviso","mensaje","El apodo que digito ya existe. <br>D&iacute;gite un apodo diferente.");
     }*/
     switch (iResultado) {
       case 0: sMensaje="No se realizo el registro <br> Revise los datos."; break;  
       case 1: return new ModelAndView("cambioPinEstudiante/general/Aviso","mensaje","Se realizo el registro"); 
       case 2: sMensaje="La clave que ingreso ya existe. <br>D&iacute;gite una clave diferente."; break;
       case 3: sMensaje="El apodo que digito ya existe. <br>D&iacute;gite un apodo diferente.";break;
     }
     modelo.put("mensaje",sMensaje);
     return new ModelAndView("Error",modelo);
   
   }else {
      sMensaje = "D&iacute;gite tanto en el apodo como en la clave<br>un m&iacute;nimo de 6 caracteres";
      modelo.put("mensaje",sMensaje);
      return new ModelAndView("Error",modelo);
    }      

  }
}
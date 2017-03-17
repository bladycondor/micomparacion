package org.fautapo.web.definirEvaluacion;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-08
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-08
 */

public class ListarAsignaciones implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    String sNombres = cliente.getNombres();
    int iId_docente = cliente.getId_usuario();
    int iId_rol = cliente.getId_rol();    

    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sAvanzado = request.getParameter("avanzado");
    String sAux1   = request.getParameter("aux");
    String sClave = request.getParameter("clave"+request.getParameter("hora"));
    String sMensaje="";
    //int _gestion = Integer.parseInt(gestion);
    //int _periodo = Integer.parseInt(periodo);
    
    modelo.put("aux", sAux1);    
    modelo.put("gestion", sGestion);    
    modelo.put("periodo", sPeriodo);
    modelo.put("nombres", sNombres);
    modelo.put("avanzado", sAvanzado);
    
    
    
    if("".equals(sClave)){
       modelo.put("usuario", sNombres);
       return new ModelAndView("definirEvaluacion/Entrada", modelo);
    }
    
    if(("".equals(sGestion)) || ("".equals(sPeriodo))){
       return new ModelAndView("Error","mensaje","Inserte la gestion y el periodo para la administraci&oacute;n de Libretas.");
    }
    
    //Verifiamos la clave
    Docentes verificar = new Docentes();
    verificar.setId_docente(iId_docente);
    verificar.setClave(sClave);
    if(!"1".equals(request.getParameter("bandera"))) {
      verificar = this.mi.getComprobarDocente(verificar);
      if(verificar == null) {
         sMensaje= "Clave incorrecta";
        modelo.put("mensaje",sMensaje);
        return new ModelAndView("Error",modelo);
      }
    }  
    
    //Sacamos la asignacion del docente
    Asignaciones asignacion = new Asignaciones();
    asignacion.setId_docente(iId_docente);
    asignacion.setGestion(Integer.parseInt(sGestion));
    asignacion.setPeriodo(Integer.parseInt(sPeriodo));
    List datosAsignacion = this.mi.getDctListarAsignacionDocente(asignacion);
    Asignaciones aux = new Asignaciones();
    for (int i = 0; i < datosAsignacion.size(); i++){
      aux = (Asignaciones)datosAsignacion.get(i);
      int iId_modelo_ahorro = aux.getId_modelo_ahorro();
      int iId_programa = aux.getId_programa();
      int iId_materia = aux.getId_materia();
      if (iId_modelo_ahorro > 0){
        Asignaciones datos = new Asignaciones();
	datos.setId_modelo_ahorro(iId_modelo_ahorro);
	datos.setId_programa(iId_programa);
	datos.setGestion(Integer.parseInt(sGestion));
	datos.setPeriodo(Integer.parseInt(sPeriodo));
	datos.setId_materia(iId_materia);
        List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
        aux.setMateria_ahorro(materiaAhorro);
        datosAsignacion.set(i, aux);
      } else {
        datosAsignacion.set(i, aux);
	
      }
    }
    
    List datosAsignaciones = datosAsignacion;
    modelo.put("datosAsignacion", datosAsignaciones);
    modelo.put("id_rol",Integer.toString(iId_rol));     
    return new ModelAndView("definirEvaluacion/ListarAsignaciones", modelo);
    
  }
}
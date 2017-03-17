package org.fautapo.web.reportesAcademicos.verLibretasPorcentaje;

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
    String _nombres = cliente.getNombres();
    int _id_docente = cliente.getId_usuario();
    int _id_rol = cliente.getId_rol();    

    String gestion = request.getParameter("gestion");
    String periodo = request.getParameter("periodo");
    String sAvanzado = request.getParameter("avanzado");
    String sAux1   = request.getParameter("aux");
    
    int _gestion = Integer.parseInt(gestion);
    int _periodo = Integer.parseInt(periodo);
    
    modelo.put("aux", sAux1);    
    modelo.put("gestion", gestion);    
    modelo.put("periodo", periodo);
    modelo.put("nombres", _nombres);
    modelo.put("avanzado", sAvanzado);
    
    String clave = request.getParameter("clave"+request.getParameter("hora"));
    
    if("".equals(clave)){
       modelo.put("usuario", _nombres);
       return new ModelAndView("reportesAcademicos/verLibretas/Entrada", modelo);
    }
    
    Docentes verificar = new Docentes();
    verificar.setId_docente(_id_docente);
    verificar.setClave(clave);
    
    if(!"1".equals(request.getParameter("bandera"))) {
      verificar = this.mi.getComprobarDocente(verificar);
      if(verificar == null) {
        String mensaje = "Clave incorrecta";
        modelo.put("mensaje",mensaje);
        return new ModelAndView("Error",modelo);
      }
    }  
    
    //Sacamos la asignacion del docente
    Asignaciones asignacion = new Asignaciones();
    asignacion.setId_docente(_id_docente);
    asignacion.setGestion(Integer.parseInt(gestion));
    asignacion.setPeriodo(Integer.parseInt(periodo));
    List datosAsignacion = this.mi.getDctListarAsignacionDocente(asignacion);
    Asignaciones aux = new Asignaciones();
    for (int i = 0; i < datosAsignacion.size(); i++){
      aux = (Asignaciones)datosAsignacion.get(i);
      int id_modelo_ahorro = aux.getId_modelo_ahorro();
      int id_programa = aux.getId_programa();
      int id_materia = aux.getId_materia();
      if (id_modelo_ahorro > 0){
        Asignaciones datos = new Asignaciones();
	datos.setId_modelo_ahorro(id_modelo_ahorro);
	datos.setId_programa(id_programa);
	datos.setGestion(_gestion);
	datos.setPeriodo(_periodo);
	datos.setId_materia(id_materia);
        List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
        aux.setMateria_ahorro(materiaAhorro);
        datosAsignacion.set(i, aux);
      } else {
        datosAsignacion.set(i, aux);
	
      }
    }
    
    List datosAsignaciones = datosAsignacion;
    modelo.put("datosAsignacion", datosAsignaciones);
    modelo.put("id_rol",Integer.toString(_id_rol));     
    return new ModelAndView("reportesAcademicos/verLibretasPorcentaje/ListarAsignaciones", modelo);
  }
}
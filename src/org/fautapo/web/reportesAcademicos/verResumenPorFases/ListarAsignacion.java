package org.fautapo.web.reportesAcademicos.verResumenPorFases;

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
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Departamentos;
import org.fautapo.domain.Facultades;
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

public class ListarAsignacion implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Debe volver a la pagina inicial e ingresar de nuevo.");

    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sBandera = request.getParameter("bandera");
    int _id_docente = cliente.getId_usuario();

    modelo.put("usuario", cliente.getNombres());
    modelo.put("id_docente", Integer.toString(cliente.getId_usuario()));
    modelo.put("gestion", sGestion);    
    modelo.put("periodo", sPeriodo);
    
    if ("0".equals(sBandera)) {
      // Comprobamos si es quien debe ingresar al módulo, de acuerdo a su clave
      String clave = request.getParameter("clave"+request.getParameter("hora"));
      if ("".equals(clave)) {
         return new ModelAndView("reportesAcademicos/verNotasEvaluacionContinuaEstudiantes/Entrada", modelo);
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
    }
    //Sacamos la asignacion del docente
    Asignaciones asignacion = new Asignaciones();
    asignacion.setId_docente(cliente.getId_usuario());
    asignacion.setGestion(Integer.parseInt(sGestion));
    asignacion.setPeriodo(Integer.parseInt(sPeriodo));
    List datosAsignacion = this.mi.getDctListarAsignacionDocente(asignacion);
    Asignaciones aux = new Asignaciones();
    for (int i = 0; i < datosAsignacion.size(); i++) {
      aux = (Asignaciones)datosAsignacion.get(i);
      int id_modelo_ahorro = aux.getId_modelo_ahorro();
      int id_programa = aux.getId_programa();
      int id_materia = aux.getId_materia();
      if (id_modelo_ahorro > 0){
        Asignaciones datos = new Asignaciones();
	datos.setId_modelo_ahorro(id_modelo_ahorro);
	datos.setId_programa(id_programa);
	datos.setGestion(Integer.parseInt(sGestion));
	datos.setPeriodo(Integer.parseInt(sPeriodo));
	datos.setId_materia(id_materia);
        List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
        aux.setMateria_ahorro(materiaAhorro);
        datosAsignacion.set(i, aux);
      } else {
        datosAsignacion.set(i, aux);
	
      }
    }
    List datosAsignaciones = datosAsignacion;
    modelo.put("lAsignacionMaterias", datosAsignaciones);

    return new ModelAndView("reportesAcademicos/verResumenPorFases/ListarAsignacion", modelo);
  }
}
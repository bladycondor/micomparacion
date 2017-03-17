package org.fautapo.web.admisionAuxiliar.buscarEstudiante;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Personas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */

public class RegistrarAdmisionEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    modelo.put("cliente",cliente);
    Estudiantes datosEstudiante = new Estudiantes();
    String sId_estudiante = request.getParameter("id_estudiante");
    String sAccion = request.getParameter("accion");
    
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));    
    datosEstudiante.setId_rol(cliente.getId_rol());
    datosEstudiante.setUlt_usuario(cliente.getId_usuario());
    
    if("Admitir".equals(sAccion)) {
	int x = this.mi.setRegistrarAdmisionEstudianteAuxiliar(datosEstudiante);
	modelo.put("mensaje","EL ESTUDIANTE "+sId_estudiante+" FUE ADMITIDO COMO AUXILIAR");
    }    
    else{
	int y = this.mi.setEliminarAdmisionEstudianteAuxiliar(datosEstudiante);
	modelo.put("mensaje","EL ESTUDIANTE "+sId_estudiante+" FUE ELIMINADO COMO AUXILIAR");
    }
    return new ModelAndView("admisionAuxiliar/buscarEstudiante/Entrada",modelo);
  }
}

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

public class ListarEstudiantes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante;
    Estudiantes datosEstudianteAdmitido;
    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    int iGestion; int iPeriodo;
    if (!"".equals(sId_estudiante)) {
      //Sacando los datos del estudiante    
      datosEstudiante = new Estudiantes();      
      try {
       iGestion = Integer.parseInt(request.getParameter("gestion"));
       iPeriodo = Integer.parseInt(request.getParameter("periodo"));
       datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
       datosEstudiante.setGestion(iGestion);
       datosEstudiante.setPeriodo(iPeriodo);             
      }
      catch(Exception e) {
        return new ModelAndView("Error", "mensaje", "El R.U., la gestion y periodo  debe ser un dato entero, por favor Verifique");
      }	
      
      datosEstudiante = this.mi.getEstBuscarEstudianteNombresMatriculados(datosEstudiante);
      if (datosEstudiante == null) {        
        modelo.put("mensaje","ESTUDIANTE NO MATRICULADO EN ("+iGestion+"/"+iPeriodo+")");
	modelo.put("cliente",cliente);
        return new ModelAndView("admisionAuxiliar/buscarEstudiante/Entrada",modelo);
      }      
            
      datosEstudianteAdmitido = this.mi.getEstBuscarEstudianteAdmitidoAuxiliar(datosEstudiante);
      if(datosEstudianteAdmitido==null) datosEstudiante.setAdjunto("Admitir");
      else datosEstudiante.setAdjunto("Eliminar");
      
      List lEstudiantes = new ArrayList();
      lEstudiantes.add(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
      return new ModelAndView("admisionAuxiliar/buscarEstudiante/ListarEstudiantes",modelo);
    }else
    return new ModelAndView("admisionAuxiliar/buscarEstudiante/Entrada", "cliente", cliente);    
  }
}

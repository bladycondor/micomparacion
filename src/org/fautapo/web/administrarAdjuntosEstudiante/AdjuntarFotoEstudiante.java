package org.fautapo.web.administrarAdjuntosEstudiante;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.lang.Boolean;
import org.apache.commons.fileupload.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-27
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-27
*/

public class AdjuntarFotoEstudiante implements Controller {
        
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos las variables de la sesion
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");

    String sAplicacion = request.getParameter("aplicacion");
    String sId_estudiante = request.getParameter("id_estudiante");   
    String sBandera = request.getParameter("bandera");   
    
    //Buscar Docente
    if(("".equals(sId_estudiante)) || (sId_estudiante == null)){
      return new ModelAndView("Error", "mensaje","Faltan el R.U. del estudiante");
    }

    Estudiantes datosEst = new Estudiantes();
    datosEst.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosEst = this.mi.getEstBuscarEstudiantePrs(datosEst);
    modelo.put("datosEst", datosEst);
    if(datosEst == null) {
      return new ModelAndView("Error","mensaje","No existe el estudiante con el R.U. " + sId_estudiante);
    }

    modelo.put("id_estudiante", sId_estudiante);
    modelo.put("aplicacion", sAplicacion);
    modelo.put("bandera", sBandera);
    
    return new ModelAndView("administrarAdjuntosEstudiante/AdjuntarFotoEstudiante", modelo);
  }
}

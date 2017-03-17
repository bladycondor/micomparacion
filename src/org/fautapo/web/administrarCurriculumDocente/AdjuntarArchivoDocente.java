package org.fautapo.web.administrarCurriculumDocente;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.lang.Boolean;
import org.apache.commons.fileupload.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-27
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-27
*/

public class AdjuntarArchivoDocente implements Controller {
        
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos las variables de la sesion
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");

    String sAplicacion = request.getParameter("aplicacion");
    String sId_docente = request.getParameter("id_docente");   
    String sBandera = request.getParameter("bandera");   
    
    //Buscar Docente
    if(("".equals(sId_docente)) && (sId_docente == null)){
      return new ModelAndView("Error", "mensaje","Faltan datos");
    }
    Docentes datosDoc = new Docentes();
    datosDoc.setId_docente(Integer.parseInt(sId_docente));
    datosDoc = this.mi.getBuscarDocente(datosDoc);
    modelo.put("datosDoc", datosDoc);
    if(datosDoc == null) {
      return new ModelAndView("Error","mensaje","No existe el docente");
    }

    modelo.put("id_docente", sId_docente);
    modelo.put("aplicacion", sAplicacion);
    modelo.put("bandera", sBandera);
    
    return new ModelAndView("administrarCurriculumDocente/AdjuntarArchivoDocente", modelo);
  }
}

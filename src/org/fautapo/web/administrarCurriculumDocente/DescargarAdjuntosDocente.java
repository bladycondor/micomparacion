package org.fautapo.web.administrarCurriculumDocente;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Iterator;
import java.io.*;
import java.lang.Boolean;
import org.apache.commons.fileupload.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Curriculum;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.apache.commons.fileupload.*;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-27
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-27
*/

public class DescargarAdjuntosDocente implements Controller {
        
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos las variables de la sesion
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");

    String sAplicacion = request.getParameter("aplicacion");
    String sId_docente = request.getParameter("id_docente");   
    
    //Listamos los adjuntos
    Curriculum datosAdjunto = new Curriculum();
    datosAdjunto.setId_docente(Integer.parseInt(sId_docente));
    datosAdjunto.setId_estado("A");
    List lAdjuntos = this.mi.getListarAdjuntosDocente(datosAdjunto);
    modelo.put("lAdjuntos", lAdjuntos);
    modelo.put("aplicacion", sAplicacion);
    //Fin Listamos los adjuntos
    
    return new ModelAndView("administrarCurriculumDocente/DescargarAdjuntosDocente", modelo);
  }
}

package org.fautapo.web.administrarHilos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.sql.Timestamp;
import java.util.Iterator;
import java.io.*;
import org.apache.commons.fileupload.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Hilos;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-04
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-04
*/

public class Adjuntar implements Controller {
        
  private MiFacade mi;
 
  public void setMi(MiFacade mi) { this.mi = mi;}

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    int iContador = 0;
    
    //Sacamos las variables de la sesion
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sAplicacion = request.getParameter("aplicacion");
    String sId_hilo = request.getParameter("id_hilo");
    String sId_segmento = request.getParameter("id_segmento");
    String sAsunto = request.getParameter("asunto");
    Timestamp fecha = new Timestamp(System.currentTimeMillis());

    modelo.put("id_hilo", sId_hilo);
    modelo.put("asunto", sAsunto);
    modelo.put("aplicacion", sAplicacion);
    
    Hilos hilo = new Hilos();
    hilo.setId_segmento(Integer.parseInt(sId_segmento));
    try {
      DiskFileUpload fu = new DiskFileUpload();
      fu.setSizeMax(2048 * 8192); // 4Mb
      fu.setSizeThreshold(4096);
      fu.setRepositoryPath("/tmp");
      List lFileItems = fu.parseRequest(request);
      Iterator i = lFileItems.iterator();
      while (i.hasNext()) {
        iContador = iContador + 1;
        FileItem actual = (FileItem)i.next();
        String sFileName = actual.getName();
	if (sFileName == null) {
          return new ModelAndView("administrarHilos/Adjuntar1", modelo);
	}
	
        int iAux = sFileName.lastIndexOf('\\');
	String sNom = sFileName.substring(iAux + 1, sFileName.length());
        if (!"".equals(sFileName)) {
          File fichero = new File(sNom);
	  String sAdjunto = (fecha.toString() + Integer.toString(iContador)).replace(' ', '_');
	  sAdjunto = sAdjunto.replace(':', '_');
	  sAdjunto = sAdjunto.replace('-', '_');
	  sAdjunto = sAdjunto.replace('.', '_');
	  int iAuxiliar = sFileName.lastIndexOf('.');
	  String sExtension = sFileName.substring(iAuxiliar + 1, sFileName.length());
          String sAdjunto_a = sAdjunto.toString();
          sAdjunto = sAdjunto_a + "." + sExtension;
	  fichero = new File("/opt/tomcat/webapps" + sAplicacion + "adjuntos/" + sAdjunto);
          actual.write(fichero);
          hilo.setAdjunto(sAdjunto);
	  hilo.setNombre_archivo(sNom);
          int iResultado = this.mi.setRegistrarSgmAdjunto(hilo);
	}
      }
    }
    catch(Exception e) {
      System.out.println("error:" + e);
    }
    return new ModelAndView("administrarHilos/Adjuntar", modelo);
  }
}

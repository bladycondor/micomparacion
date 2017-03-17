package org.fautapo.web.administrarAdjuntos;

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
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Adjuntos;
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

public class AdjuntarArchivo1 implements Controller {
        
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    int iResultado = 0; int iContador =0;
    //Sacamos las variables de la sesion
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sAplicacion = request.getParameter("aplicacion");
    String sId_tramite = request.getParameter("id_tramite");   
    String sId_proceso = request.getParameter("id_proceso");    
    String sId_actividad_actual = request.getParameter("id_actividad_actual");    
    String sId_form = request.getParameter("id_form");    

    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_actividad_actual", sId_actividad_actual);
    modelo.put("id_actividad", sId_actividad_actual);
    modelo.put("id_form", sId_form);
    modelo.put("id_tramite", sId_tramite);
    modelo.put("aplicacion", sAplicacion);
    
    //Para adjuntar un archivo
    Timestamp tFecha =new Timestamp(System.currentTimeMillis());
    try {
      DiskFileUpload fu = new DiskFileUpload();
      fu.setSizeMax(2048*8192); // 4Mb
      fu.setSizeThreshold(4096);
      fu.setRepositoryPath("/tmp");
      List fileItems = fu.parseRequest(request);
      Iterator i = fileItems.iterator();
      while (i.hasNext()) {
        iContador = iContador + 1;
        FileItem actual = (FileItem)i.next();
        String fileName = actual.getName();
        if (fileName == null) {
          return new ModelAndView("administrarAdjuntos/AdjuntarArchivo", modelo);
     	}
	  
        int iAuxiliar = fileName.lastIndexOf('\\');
	String sNombre = fileName.substring(iAuxiliar+1,fileName.length());
        if (!"".equals(fileName)) {
          File fichero = new File(sNombre);
  	  String sAdjunto = (tFecha.toString()+Integer.toString(iContador)).replace(' ','_');
	  sAdjunto = sAdjunto.replace(':', '_');
	  sAdjunto = sAdjunto.replace('-', '_');
	  sAdjunto = sAdjunto.replace('.', '_');
	  int iAuxiliar2 = fileName.lastIndexOf('.');
	  String sExtension = fileName.substring(iAuxiliar2+1,fileName.length());
          String sAdjunto_a = sAdjunto.toString();
          sAdjunto = sAdjunto_a+"."+sExtension;
	  fichero = new File("/opt/tomcat/webapps"+sAplicacion+"adjuntos/"+sAdjunto);
          actual.write(fichero);
	  Adjuntos datosAdjunto = new Adjuntos();
          datosAdjunto.setId_tramite(Integer.parseInt(sId_tramite));
          datosAdjunto.setId_actividad(Integer.parseInt(sId_actividad_actual));
          datosAdjunto.setAdjunto(sAdjunto);
          datosAdjunto.setArchivo(sNombre);
          datosAdjunto.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarAdjunto(datosAdjunto);
          if (iResultado == 0) {
            return new ModelAndView("Error","mensaje","El archivo no se adjunto");
          }
	}
      }
    }
    catch(Exception e) {}
    
    modelo.put("mensaje","Los archivos se adjuntaron correctamente");
    
    return new ModelAndView("administrarAdjuntos/AdjuntarArchivo1", modelo);
  }
}

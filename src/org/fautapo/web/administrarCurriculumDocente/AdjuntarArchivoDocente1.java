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
import org.fautapo.domain.Docentes;
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

public class AdjuntarArchivoDocente1 implements Controller {
        
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    int iResultado = 0; int iContador =0;
    //Sacamos las variables de la sesion
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    
    String sAplicacion = request.getParameter("aplicacion");
    String sId_docente = request.getParameter("id_docente");   
    String sId_persona = request.getParameter("id_persona");    
    String sMensaje;
    modelo.put("id_docente", sId_docente);
    modelo.put("id_persona", sId_persona);
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
          return new ModelAndView("administrarCurriculumDocente/AdjuntarArchivoDocente", modelo);
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
	  fichero = new File("/opt/tomcat/webapps"+sAplicacion+"adjuntosMi/adjuntosDocentes/"+sAdjunto);
          actual.write(fichero);
	  Curriculum datosAdjunto = new Curriculum();
          datosAdjunto.setId_docente(Integer.parseInt(sId_docente));  // AQUISITO
          datosAdjunto.setAdjunto(sAdjunto);
          datosAdjunto.setNombre_archivo(sNombre);
	  datosAdjunto.setId_estado("A");
          datosAdjunto.setId_rol(cliente.getId_rol());
	  datosAdjunto.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarDctAdjuntos(datosAdjunto);
	  if (iResultado == 0) {
	    return new ModelAndView("Error","mensaje","El archivo no se adjunto");
          }
	  else {
	    //Listamos la Foto
	    //Listamos su curriculum 
	    Docentes datosDoc = new Docentes();
	    datosDoc.setId_docente(Integer.parseInt(sId_docente));
            datosDoc = this.mi.getBuscarDocente(datosDoc);
            modelo.put("datosDoc", datosDoc);
            if(datosDoc != null) {
              Curriculum datosC = new Curriculum();
              datosC.setId_persona(datosDoc.getId_persona());
              List lCurriculum = this.mi.cvGetListarRubrosPersona(datosC);  //Modif
              modelo.put("lCurriculum",lCurriculum);
	      //Lista Adj.
              datosC.setId_docente(datosDoc.getId_docente());
              datosC.setId_estado("I");
              List lImagenes = this.mi.getListarAdjuntosDocente(datosC);
	      modelo.put("lImagenes", lImagenes);
              modelo.put("bandera", "1");  
              return new ModelAndView("administrarCurriculumDocente/ListarCurriculumDocente", modelo); 
	    } 
	    modelo.put("mensaje","El archivo se adjunto correctamente");
	  }
	}
      }
    }
    catch(Exception e) {
       modelo.put("mensaje","No se logro adjuntar");
    }
    return new ModelAndView("administrarCurriculumDocente/AdjuntarArchivoDocente1", modelo);
  }
}

package org.fautapo.web.administrarAdjuntosEstudiante;

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
import org.fautapo.domain.Estudiantes;
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

public class AdjuntarFotoEstudiante1 implements Controller {
        
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    int iResultado = 0; int iContador =0;
    //Sacamos las variables de la sesion
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    
    String sAplicacion = request.getParameter("aplicacion");
    String sId_estudiante = request.getParameter("id_estudiante");   

    System.out.println("IMPRIMIR LA APLICACION -->"+ sAplicacion);
    System.out.println("IMPRIMIR ID ESTUDIANTE -->"+ sId_estudiante);
    //System.out.println("IMPRIMIR ID PERSONA -->"+ sId_persona);
    modelo.put("id_estudiante", sId_estudiante);
    //modelo.put("id_persona", sId_persona);
    modelo.put("aplicacion", sAplicacion);
    
    if(("".equals(sId_estudiante)) || (sId_estudiante == null)){
      return new ModelAndView("Error", "mensaje","Faltan el dato del R.U.");
    }
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
          return new ModelAndView("administrarAdjuntosEstudiante/AdjuntarFotoEstudiante", modelo);
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
	  fichero = new File("/opt/tomcat/webapps"+sAplicacion+"adjuntosMi/fotosEstudiantes/"+sAdjunto);

	  System.out.println("El nombre del fichero-->" + fichero);
          actual.write(fichero);
	  Estudiantes datosAdjunto = new Estudiantes();
          datosAdjunto.setId_estudiante(Integer.parseInt(sId_estudiante));  // AQUISITO
	  System.out.println("El id_estudiante-->" + datosAdjunto.getId_estudiante());
          datosAdjunto.setAdjunto(sAdjunto);
	  System.out.println("El adjunto -->" + datosAdjunto.getAdjunto());
          datosAdjunto.setNombre_archivo(sNombre);
	  System.out.println("El adjunto -->" + datosAdjunto.getNombre_archivo());
	  datosAdjunto.setId_estado("I");
          datosAdjunto.setId_rol(cliente.getId_rol());
	  System.out.println("El adjunto -->" + datosAdjunto.getId_rol());
	  datosAdjunto.setUlt_usuario(cliente.getId_usuario());
	  System.out.println("El adjunto -->" + datosAdjunto.getUlt_usuario());
          iResultado = this.mi.setRegistrarEstAdjuntos(datosAdjunto);
          if (iResultado == 0) {
            return new ModelAndView("Error","mensaje","La imagen no se adjunto");
          }
	  else {
	    //Listamos la Foto
	    //Sacamos los datos
            Estudiantes datosEst = new Estudiantes();
            datosEst.setId_estudiante(Integer.parseInt(sId_estudiante));
            datosEst = this.mi.getEstBuscarEstudiantePrs(datosEst);
            modelo.put("datosEst", datosEst);
            if(datosEst != null) {
	      //Lista Adj.
              datosEst.setId_estado("I");
              List lImagenes = this.mi.getListarAdjuntosEstudiante(datosEst);
	      modelo.put("lImagenes", lImagenes);
              modelo.put("bandera", "1");  
              return new ModelAndView("administrarAdjuntosEstudiante/ListarDatosFotoEstudiante", modelo); 
	    } 
	    modelo.put("mensaje","El archivo se adjunto correctamente");
	  }
	}
      }
    }
    catch(Exception e) {
      modelo.put("mensaje","No se logro adjuntar la imagen. Puede que ya tenga una imagen adjunta para el estudiante con R.U. "+ sId_estudiante+" Eliminelo primero");
    }
    return new ModelAndView("administrarAdjuntosEstudiante/AdjuntarFotoEstudiante1", modelo);
  }
}

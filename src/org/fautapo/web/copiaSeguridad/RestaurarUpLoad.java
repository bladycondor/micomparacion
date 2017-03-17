package org.fautapo.web.copiaSeguridad;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class RestaurarUpLoad implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  private String sCarpeta = null;
  private String sCopia = null;

  public boolean procesaFicheros(HttpServletRequest req) {
    try {
      // construimos el objeto que es capaz de parsear la perici√≥n
      DiskFileUpload fu = new DiskFileUpload();
      // maximo numero de bytes
      //fu.setSizeMax(1024*512); // 512 K
      // tama√±o por encima del cual los ficheros son escritos directamente en disco
      fu.setSizeThreshold(4096);
      // directorio en el que se escribir√°n los ficheros con tama√±o superior al soportado en memoria
      fu.setRepositoryPath("/tmp");
      // ordenamos procesar los ficheros
      List fileItems = fu.parseRequest(req);
      if(fileItems == null) {
        return false;
      }
      // Iteramos por cada fichero
      Iterator i = fileItems.iterator();
      FileItem actual = null;

      while (i.hasNext()) {
        actual = (FileItem)i.next();
        String fileName = actual.getName();
        // construimos un objeto file para recuperar el trayecto completo
        File fichero = new File(fileName);

        sCopia = fichero.getName();

        // nos quedamos solo con el nombre y descartamos el path
        fichero = new  File(this.sCarpeta + fichero.getName());
        // escribimos el fichero colgando del nuevo path
        actual.write(fichero);
      }
    } catch(Exception e) {
      System.out.println("DIBRAP - ERROR: " + e.getMessage());
      return false;
    }
    return true;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesiÛn terminÛ. Por favor, ingresa nuevamente."); }

    String sSistema = cliente.getString(request, "sistema");
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("copia_seguridad");
    formatoFecha.setCodigo("dibrap");
    this.sCarpeta = this.mi.getDibBuscarParametro(formatoFecha) + "/subidos/";
    boolean estado = new File(this.sCarpeta).mkdirs();  // Por si acaso, si no existe, creamos la carpeta
    try {
      this.procesaFicheros(request);
      String sComando[] = {"tbRestaurar.sh", request.getRealPath("/"), sCarpeta + sCopia};
      Process pr = Runtime.getRuntime().exec(sComando);
      InputStream is = pr.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader (is));
      String aux = br.readLine();
      while (aux != null) {
        aux = br.readLine();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ModelAndView("Error", "mensaje", "Hubo un problema al restaurar la Base de Datos: " + e.getMessage());
    }
    return new ModelAndView("Aviso", "mensaje", "Se restaurÛ correctamente la copia de seguridad a la Base de Datos");
  }
}
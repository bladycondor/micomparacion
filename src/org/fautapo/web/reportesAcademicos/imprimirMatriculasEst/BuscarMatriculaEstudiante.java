package org.fautapo.web.reportesAcademicos.imprimirMatriculasEst;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


import javax.swing.*;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeImageHandler;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class BuscarMatriculaEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }


  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    //Para wayka
    String sAplicacion = request.getParameter("aplicacion");
    String sId_estudiante = request.getParameter("id_estudiante");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("aplicacion", sAplicacion);
    Estudiantes datoEst = new Estudiantes();
    
    if (("".equals(sId_estudiante)) || (sId_estudiante == null) || ("".equals(sGestion)) || (sGestion == null) || ("".equals(sPeriodo)) || (sPeriodo == null) ) {
      return new ModelAndView("Error", "mensaje", "Faltan Datos");
    }
    
    //Verificar si esta matriculado
    datoEst.setId_estudiante(Integer.parseInt(sId_estudiante));    
    datoEst.setGestion(Integer.parseInt(sGestion));    
    datoEst.setPeriodo(Integer.parseInt(sPeriodo));    
    datoEst = this.mi.getMtrBuscarMatricula(datoEst);
    if (datoEst == null) {
      return new ModelAndView("Aviso", "mensaje", "El estudiante con R.U. "+sId_estudiante+" no esta matriculado para la gestion "+sGestion+" y periodo "+sPeriodo);
    }
    
    //Buscamos la matricula
    datoEst.setId_matricula(datoEst.getId_matricula());
    datoEst = this.mi.getBuscarMatriculaEstPrs(datoEst);
    modelo.put("datoEst", datoEst);
    modelo.put("id_matricula", Integer.toString(datoEst.getId_matricula()));

    //Sacamos el tipo clasificacion
    Estudiantes datosClasificacion = new Estudiantes();
    datosClasificacion = this.mi.getBuscarTipoClasificacionEstudiante(datoEst);
    modelo.put("datosClasificacion", datosClasificacion);
        
    //BARBECUDE 
    Barcode barcode = BarcodeFactory.createCode128(Integer.toString(datoEst.getId_estudiante()));
    try {
      // Necesitamos un canal de salida donde escribir la imagen
      String fichero = "/opt/tomcat/webapps"+sAplicacion+"adjuntosMi/barcodeEstudiantes/";
      FileOutputStream fos = new FileOutputStream(fichero+"barcode_"+Integer.toString(datoEst.getId_estudiante())+".jpg");
      //Permite que la utilidad de imagenes de Barbecue haga todo el trabajo sucio
      BarcodeImageHandler.outputBarcodeAsJPEGImage(barcode, fos);
      modelo.put("ruta",sAplicacion+"adjuntosMi/barcodeEstudiantes/"+"barcode_"+Integer.toString(datoEst.getId_estudiante())+".jpg");
    } catch (IOException e) {
      // Gestion de errores
    }
    // FIN BARBECUE
      
    //Mostramos la imagen del estudiante, siempre sera uno por imagen cargada
    Estudiantes imagenEst= new Estudiantes();
    imagenEst.setId_estudiante(datoEst.getId_estudiante());
    imagenEst.setId_estado("I");
    List lImagenes = this.mi.getListarAdjuntosEstudiante(imagenEst);
    modelo.put("lImagenes", lImagenes);
      
    return new ModelAndView("reportesAcademicos/imprimirMatriculasEst/VerDatosMatriculaEstudiante", modelo);
  }
}

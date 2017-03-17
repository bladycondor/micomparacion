package org.fautapo.web.administrarProgramasEspecializados.reporteEstudianteCertificado;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Literales;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-23
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-23
*/

public class verReporteEstudianteCertificado implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    int iResultado; int iId_tramite; String sCadena = ""; Tramites datosTramite = new Tramites();
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_proceso = request.getParameter("id_proceso");
    String sId_estudiante = request.getParameter("id_estudiante");
    String sNombres = request.getParameter("nombres");
    String sFacultad = request.getParameter("facultad");
    String sPrograma = request.getParameter("programa");
    String sPeriodo = request.getParameter("periodo");
    String sGestion = request.getParameter("gestion");
    modelo.put("id_estudiante", sId_estudiante); 
    modelo.put("nombres", sNombres); 
    modelo.put("facultad", sFacultad); 
    modelo.put("nom_programa", sPrograma); 
    modelo.put("periodo", sPeriodo); 
    modelo.put("gestion", sGestion); 
    modelo.put("cliente", cliente);
    
    //Sacamos datos certificado de calificaciones
    Libretas buscar = new Libretas();
    buscar.setId_estudiante(Integer.parseInt(sId_estudiante));
    buscar.setPeriodo(Integer.parseInt(sPeriodo));
    buscar.setGestion(Integer.parseInt(sGestion));  
    List lcertificados = this.mi.getListarCerticadoCalificaciones(buscar);
    modelo.put("lcertificados", lcertificados); 
    
    Libretas aux = new Libretas();
    Literales conv = new Literales();
    
    List lliterales = new ArrayList();
    for (int i = 0; i < lcertificados.size(); i++){
      aux = (Libretas)lcertificados.get(i);
      double nota = aux.getNota();
      int iId_nota = aux.getId_nota();
      String nota_literal[]= conv.convert(nota).split("0/");
      Libretas aux1 = new Libretas();
      aux1.setNota_literal(nota_literal[0]);
      aux1.setId_nota(iId_nota);
      lliterales.add(aux1);
    }
    modelo.put("lliterales", lliterales); 
     
    //Sacamos el programa  
    Programas datosPrograma = new Programas();
    datosPrograma.setId_estudiante(Integer.parseInt(sId_estudiante));
    Programas buscarPrograma = this.mi.getPrgBuscarProgramaEstudiante(datosPrograma);
    modelo.put("programa", buscarPrograma);
        
    //Sacamos estudiante persona
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
    Estudiantes buscarEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
    modelo.put("estudiante", buscarEstudiante);    
    System.out.print("---------"+buscarEstudiante);

    return new ModelAndView("administrarProgramasEspecializados/reporteEstudianteCertificado/verReporteEstudianteCertificado", modelo);
  }
}
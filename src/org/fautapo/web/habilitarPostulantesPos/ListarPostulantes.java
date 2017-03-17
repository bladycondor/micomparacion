package org.fautapo.web.habilitarPostulantesPos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class ListarPostulantes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }

    String sGestion = cliente.getString(request, "gestion");
    String sPeriodo = cliente.getString(request, "periodo");
    //Si dio volver recuperamos los datos
    String sId_facultad = request.getParameter("id_facultad");
    String sId_programa = request.getParameter("id_programa");
    List listaPostulantes;

    Postulantes postulante = new Postulantes();
    postulante.setGestion(Integer.parseInt(sGestion));
    postulante.setPeriodo(Integer.parseInt(sPeriodo));

    if ((!"".equals(sId_programa)) && (!"".equals(sGestion)) && (!"".equals(sPeriodo)) ) {
      postulante.setId_programa(Integer.parseInt(sId_programa));
      listaPostulantes = this.mi.getMiListarPstProgramaGestionPeriodo(postulante);
      modelo.put("listaPostulantes", listaPostulantes);
      Programas programa = new Programas();
      programa.setId_programa(Integer.parseInt(sId_programa));
      programa = this.mi.getPrgBuscarPrograma(programa);
      modelo.put("programa", programa);
    }
    else{
      return new ModelAndView("Error","mensaje","Faltan datos");
    }
    
    //modelo.put("listaPostulantes", listaPostulantes);
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("id_facultad", sId_facultad);
    modelo.put("id_programa", sId_programa);
    
    return new ModelAndView("habilitarPostulantesPos/ListarPostulantes", modelo);
  }
}
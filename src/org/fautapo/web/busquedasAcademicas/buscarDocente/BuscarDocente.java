package org.fautapo.web.busquedasAcademicas.buscarDocente;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.Asignaciones;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class BuscarDocente implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    Docentes datosDocente;
    
    //Recuperando variables del jsp
    String sId_docente = request.getParameter("id_docente");
    String sCi = request.getParameter("ci");
    String sNombres = request.getParameter("nombres");
    
    if (("".equals(sNombres)) && ("".equals(sCi))) {
      return new ModelAndView("busquedasAcademicas/buscarDocente/Entrada", modelo);
    }

    if ((!"".equals(sId_docente)) && (sId_docente != null)) {
      //Sacando los datos del docente
      datosDocente = new Docentes();
      datosDocente.setId_docente(Integer.parseInt(sId_docente));
      datosDocente = this.mi.getBuscarDocente(datosDocente);
      modelo.put("datosDocente", datosDocente);
      
      //Aqui buscamos la asignacion docente
      Asignaciones datosAsignacion = new Asignaciones();
      datosAsignacion.setId_docente(datosDocente.getId_docente());
      List lAsignaciones = this.mi.getListarAsignacionDocenteTodas(datosAsignacion);
      modelo.put("lAsignaciones", lAsignaciones);
      return new ModelAndView("busquedasAcademicas/buscarDocente/ListarAsignacionDocente", modelo);
    }
    
    //Si la busqueda es por CI
    if (!"".equals(sCi)) {
      datosDocente = new Docentes();
      datosDocente.setDip(sCi+"%");
      List lDocentes = this.mi.getBuscarListaDocentesDip(datosDocente);
      modelo.put("lDocentes", lDocentes);
      return new ModelAndView("busquedasAcademicas/buscarDocente/ListarDocentes", modelo);
    }
  
    //Si la busqueda es por nombre
    if (!"".equals(sNombres)) {
      datosDocente = new Docentes();
      datosDocente.setNombres("%"+sNombres+"%");
      List lDocentes = this.mi.getBuscarListaDocentesNombres(datosDocente);
      modelo.put("lDocentes", lDocentes);
      return new ModelAndView("busquedasAcademicas/buscarDocente/ListarDocentes", modelo);
    }
    return new ModelAndView("busquedasAcademicas/buscarDocente/Entrada",modelo);
  }
}

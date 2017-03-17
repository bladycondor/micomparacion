package org.fautapo.web.busquedasAcademicas.buscarPostulantepsa;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Personas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;



/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class BuscarEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    Postulantes datosPostulantes;
   

    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sCi = request.getParameter("ci");
    String sNombres = request.getParameter("nombres");
    String sGestion = Integer.toString(cliente.getGestion()); 
	System.out.println("gestion--"+sGestion);
	
    String sPeriodo = Integer.toString(cliente.getPeriodo());
    if ("".equals(sId_estudiante) && ("".equals(sNombres)) && ("".equals(sCi))) {
      return new ModelAndView("busquedasAcademicas/buscarPostulantepsa/Entrada", modelo);
    }
        

    //Si la busqueda es por CI
    if (!"".equals(sCi)) {
      datosPostulantes = new Postulantes();
      datosPostulantes.setDip(sCi);
	  System.out.println("la gestion es: "+sGestion);
	   System.out.println("el carnet es: "+datosPostulantes.getDip());
      datosPostulantes.setGestion(Integer.parseInt(sGestion));
    datosPostulantes.setPeriodo(Integer.parseInt(sPeriodo));
      List lEstudiantes = this.mi.getRepAsistenciapostulantepsaci(datosPostulantes);
      modelo.put("lEstudiantes", lEstudiantes);
    }
    
 modelo.put("gestion", sGestion);
   modelo.put("periodo", sPeriodo);
    return new ModelAndView("busquedasAcademicas/buscarPostulantepsa/ListarEstudiantes",modelo);
	
  }
}

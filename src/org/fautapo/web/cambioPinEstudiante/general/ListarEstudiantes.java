package org.fautapo.web.cambioPinEstudiante.general;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.logic.MiFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2007-10-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2007-10-18
*/

public class ListarEstudiantes implements Controller 
{
  private MiFacade mi;
  public void setMi(MiFacade mi)  {this.mi = mi;}
   
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception 
  {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante;
    
    String sId_estudiante = request.getParameter("id_estudiante");
    String sCi = request.getParameter("ci");
    String sNombres = request.getParameter("nombres");
    int iGestion = cliente.getInt(request,"gestion");
    int iPeriodo = cliente.getInt(request,"periodo");
    
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("usuario", cliente.getNombres());

    if (("".equals(sId_estudiante) && ("".equals(sNombres)) && ("".equals(sCi)))
        || (sId_estudiante == null) && (sNombres == null) && (sCi == null)) {
      return new ModelAndView("cambioPinEstudiante/general/BuscarEstudiantes", modelo);
    }
        
    if (!"".equals(sId_estudiante)) {
      //Sacando los datos del estudiante    
      datosEstudiante = new Estudiantes();
      try {
       datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      } catch(Exception e) {
        return new ModelAndView("cambioPinEstudiante/general/Error", "mensaje", "El R.U. no es valido, introduzca un numero");
      }
      datosEstudiante.setId_universidad(cliente.getId_universidad());
      datosEstudiante.setId_facultad(cliente.getId_facultad());
      datosEstudiante.setId_programa(cliente.getId_programa());
      datosEstudiante = this.mi.getEstBuscarEstudianteAccesos(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);
      if (datosEstudiante == null) {
        return new ModelAndView("cambioPinEstudiante/general/Aviso", "mensaje", "El estudiante no es de su Area Verifique");
      }
      
      //Buscar Matriculacion
      datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosEstudiante.setGestion(iGestion);
      datosEstudiante.setPeriodo(iPeriodo);
      Estudiantes datosMatricula = this.mi.getMtrBuscarMatricula(datosEstudiante);
      modelo.put("datosMatricula", datosMatricula );
      if(datosMatricula == null)
        return new ModelAndView("Error","mensaje", "El estudiante no esta matriculado para la gestion acad&eacute;mica "+ Integer.toString(iPeriodo)+"<b>/</b>"+Integer.toString(iGestion));
      //Buscar datos del estudiante
      datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);
      
      return new ModelAndView("cambioPinEstudiante/general/NuevoPinEstudiante", modelo); 
    }

    //Si la busqueda es por CI
    if (!"".equals(sCi)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setDip(sCi);
      datosEstudiante.setId_universidad(cliente.getId_universidad());
      datosEstudiante.setId_facultad(cliente.getId_facultad());
      datosEstudiante.setId_programa(cliente.getId_programa());
      List lEstudiantes = this.mi.getEstListarEstudiantesDipAccesos(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
    }
    //Si la busqueda es por nombre
    if (!"".equals(sNombres)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setNombres(sNombres);
      datosEstudiante.setId_universidad(cliente.getId_universidad());
      datosEstudiante.setId_facultad(cliente.getId_facultad());
      datosEstudiante.setId_programa(cliente.getId_programa());
      List lEstudiantes = this.mi.getEstListarEstudiantesNombresAccesos(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
    }
    return new ModelAndView("cambioPinEstudiante/general/ListarEstudiantes", modelo);
  }
}
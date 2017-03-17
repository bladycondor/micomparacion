package org.fautapo.web.administrarAdjuntosEstudiante;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
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

public class ListarDatosFotoEstudiante implements Controller 
{
  private MiFacade mi;
  public void setMi(MiFacade mi)  {this.mi = mi;}
   
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception 
  {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    String sNombres = cliente.getNombres();
    int iId_rol = cliente.getId_rol();    
    String sBandera = request.getParameter("bandera");
    String sBoton = request.getParameter("botoncillo");
    modelo.put("nombres", sNombres);
    String sId_estudiante = request.getParameter("id_estudiante");
    
    /*String sClave = request.getParameter("clave"+request.getParameter("hora"));
    
    if("".equals(sClave)) {
      modelo.put("usuario", sNombres);
      return new ModelAndView("administrarAdjuntosEstudiante/EntradaAdjuntarFotoEstudiante", modelo); 
    }
    
    Docentes verificar = new Docentes();
    verificar.setId_docente(iId_docente);
    verificar.setClave(sClave);
    
    if(!"1".equals(request.getParameter("bandera"))) {
      verificar = this.mi.getComprobarUsuario(verificar);
      if(verificar == null) {
        String mensaje = "Clave incorrecta";
        modelo.put("mensaje",mensaje);
        return new ModelAndView("Error",modelo);
      }
    }
    */
    
    if(!"".equals(sId_estudiante) &&(sId_estudiante != null)) {
      //Sacamos los datos
      Estudiantes datosEst = new Estudiantes();
      datosEst.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosEst = this.mi.getEstBuscarEstudiantePrs(datosEst);
      modelo.put("datosEst", datosEst);
      if(datosEst != null) {
        //Si pidieron eliminar
        if("Eliminar".equals(sBoton)){
          datosEst.setId_est_adjunto(cliente.getInt(request,"id_est_adjunto"));
	  int iElimina = this.mi.setEliminarEstAdjunto(datosEst);
        }
        //Listar Imagenes
        datosEst.setId_estado("I");
        List lImagenes = this.mi.getListarAdjuntosEstudiante(datosEst);
        modelo.put("lImagenes", lImagenes);
      }
      else {
        return new ModelAndView("Error","mensaje","No existe el estudiante con R.U. "+ sId_estudiante);
      }
    }
    else {
      modelo.put("usuario", sNombres);
      return new ModelAndView("administrarAdjuntosEstudiante/EntradaAdjuntarFotoEstudiante", modelo);
    }
    modelo.put("bandera", "1");  
    return new ModelAndView("administrarAdjuntosEstudiante/ListarDatosFotoEstudiante", modelo); 
  }
}
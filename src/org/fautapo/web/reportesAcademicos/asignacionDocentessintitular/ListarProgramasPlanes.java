package org.fautapo.web.reportesAcademicos.asignacionDocentessintitular;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.Departamentos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */

public class ListarProgramasPlanes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Debe volver a la pagina inicial e ingresar de nuevo.");
    int id_facultad = cliente.getId_facultad();
    String _nombres = cliente.getNombres();
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sBandera = request.getParameter("bandera");
    modelo.put("gestion", sGestion);    
    modelo.put("periodo", sPeriodo);
    modelo.put("usuario", _nombres);
    if("0".equals(sBandera)){
      // Comprobamos si es quien debe ingresar al módulo, de acuerdo a su clave
      Usuarios usuario = new Usuarios();
      usuario.setId_usuario(cliente.getId_usuario());
    
      String clave = request.getParameter("clave"+request.getParameter("hora"));
      usuario.setClave(clave);

      modelo.put("id_facultad", Integer.toString(id_facultad));
    
      if("".equals(clave)){
         return new ModelAndView("reportesAcademicos/asignacionDocentessintitular/Entrada", modelo);
      }
    
      if (null == this.mi.getComprobarUsuario(usuario)){
        String mensaje = "Clave incorrecta";
        modelo.put("mensaje",mensaje);
        return new ModelAndView("Error",modelo);
      }
    }
    
    //Sacamos el listado de las facultades
    Universidades datosUniversidad = new Universidades();
    datosUniversidad.setId_universidad(cliente.getId_universidad());
    List lFacultades = this.mi.getUnvListarFacultades(datosUniversidad);
    modelo.put("lFacultades", lFacultades);
    
    //Sacamos el listado de los programas
    List lProgramas = this.mi.getUnvListarProgramas(datosUniversidad);
    modelo.put("lProgramas", lProgramas);

    //Sacamos el listado de los planes
    List lPlanes = this.mi.getUnvListarPlanes(datosUniversidad);
    modelo.put("lPlanes", lPlanes);
	
    //Listando los tipos_evaluaciones
    List lTiposEvaluaciones = this.mi.getTpsListarTiposEvaluaciones();
    modelo.put("lTiposEvaluaciones", lTiposEvaluaciones);
    
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));
    
    modelo.put("cliente",cliente);
    return new ModelAndView("reportesAcademicos/asignacionDocentessintitular/ListarProgramasPlanes", modelo);
  }
}

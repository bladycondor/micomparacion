package org.fautapo.web.administrarModificarFaseProgramaDesignacion;

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


public class listarProgramasTiposEvaluaciones implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Debe volver a la pagina inicial e ingresar de nuevo.");
    String _nombres = cliente.getNombres();
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sBandera = request.getParameter("bandera");
    String sId_programa = request.getParameter("id_programa");
    modelo.put("gestion", sGestion);    
    modelo.put("periodo", sPeriodo);
    modelo.put("usuario", _nombres);
    
    if("".equals(sId_programa) ||(sId_programa == null)) {
      // Comprobamos si es quien debe ingresar al módulo, de acuerdo a su clave
      Usuarios usuario = new Usuarios();
      usuario.setId_usuario(cliente.getId_usuario());
      String clave = request.getParameter("clave"+request.getParameter("hora"));
      usuario.setClave(clave);
      if("".equals(clave)){
         return new ModelAndView("administrarModificarFaseProgramaDesignacion/Entrada", modelo);
      }
      if (null == this.mi.getComprobarUsuario(usuario)){
        String mensaje = "Clave incorrecta";
        modelo.put("mensaje",mensaje);
        return new ModelAndView("Error",modelo);
      }
    }
    
    //Listando los tipos_evaluaciones
    List lListarTiposEvaluaciones = this.mi.getTpsListarTiposEvaluaciones();
    modelo.put("lListarTiposEvaluaciones", lListarTiposEvaluaciones);
    
    modelo.put("cliente",cliente);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));
    
    return new ModelAndView("administrarModificarFaseProgramaDesignacion/listarProgramasTiposEvaluaciones", modelo);
  }
}

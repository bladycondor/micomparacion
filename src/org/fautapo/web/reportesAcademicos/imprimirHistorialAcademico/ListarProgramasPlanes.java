package org.fautapo.web.reportesAcademicos.imprimirHistorialAcademico;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Programas;
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
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");

    String sId_programa = request.getParameter("id_programa");
    System.out.println (" HISTORIAL ACADEMICO------> PROGRAMA----->" +sId_programa );
    
    if (sId_programa != null) {
      //Buscar Programa
      Programas datosPrograma = new Programas();
      datosPrograma.setId_programa(Integer.parseInt(sId_programa));
      datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
      modelo.put("datosPrograma", datosPrograma);
      modelo.put("id_programa", sId_programa);
      return new ModelAndView("reportesAcademicos/imprimirHistorialAcademico/BuscarEstudiantes", modelo);
    }
    
    // Comprobamos si es quien debe ingresar al módulo, de acuerdo a su clave
    Usuarios usuario = new Usuarios();
    usuario.setId_usuario(cliente.getId_usuario());
    usuario.setClave(request.getParameter("clave" + request.getParameter("hora")));
    if (null == this.mi.getComprobarUsuario(usuario)) {
      return new ModelAndView("reportesAcademicos/imprimirHistorialAcademico/Entrada", "cliente", cliente);
    }
    modelo.put("cliente", cliente);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));
    
    return new ModelAndView("reportesAcademicos/imprimirHistorialAcademico/ListarProgramasPlanes", modelo);
  }
}

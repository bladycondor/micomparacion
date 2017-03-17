package org.fautapo.web.reportesAcademicos.verNroEstProgYNoProgMaterias;

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


public class formularioGradosProgramasProgYNoProg implements Controller {

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

    int _id_programa = cliente.getId_programa();
    Programas datosProg = new Programas();
    datosProg.setId_programa(_id_programa);
    
    modelo.put("gestion", sGestion);    
    modelo.put("periodo", sPeriodo);
    modelo.put("usuario", _nombres);

    //Listando los tipos_evaluaciones
    List lGrados = this.mi.getListarGradosProgramas(datosProg);
    modelo.put("lListarGradosProgramas", lGrados);
    
    return new ModelAndView("reportesAcademicos/verNroEstProgYNoProgMaterias/formularioGradosProgramasProgYNoProg", modelo);
  }
}
package org.fautapo.web.administrarCalendarioAcademico;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Departamentos;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Calendarios;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarCalendarios implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    //Si dio volver recuperamos los datos
    int iGestion = cliente.getInt(request, "gestion");
    int iPeriodo = cliente.getInt(request, "periodo");
    int iId_departamento = cliente.getInt(request, "id_departamento");
    int iId_programa = cliente.getInt(request, "id_programa");
    String sTabla = request.getParameter("tabla");

    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));

    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(iId_programa);
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    //Sacamos los datos del Departamento
    Departamentos datosDepartamento = new Departamentos();
    datosDepartamento.setId_departamento(iId_departamento);
    datosDepartamento = this.mi.getDptBuscarDepartamento(datosDepartamento);
    modelo.put("datosDepartamento", datosDepartamento);

    //Listamos los datos del calendario
    Calendarios datosCalendario = new Calendarios();
    datosCalendario.setId_programa(iId_programa);
    datosCalendario.setId_departamento(iId_departamento);
    datosCalendario.setTabla(sTabla);
    datosCalendario.setGestion(iGestion);
    datosCalendario.setPeriodo(iPeriodo);
    List lCalendario = this.mi.getListarCalendarios(datosCalendario);
    modelo.put("lCalendario", lCalendario);
      
    return new ModelAndView("administrarCalendarioAcademibo/ListarCalendario", modelo);
  }
}
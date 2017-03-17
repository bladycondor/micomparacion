package org.fautapo.web.reportesAcademicos.rendimiento_academico;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import org.fautapo.domain.Estudiantes;
/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarRendimiento implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    
    int gestion     = Integer.parseInt(request.getParameter("gestion"));
    int periodo     = Integer.parseInt(request.getParameter("periodo"));

    String programas[]     = request.getParameterValues("id_programa");    
    String id_programa   = programas[0].split(":")[0];
    String programa      = programas[0].split(":")[1];    
    modelo.put("programa",programa);
    Estudiantes pro = new Estudiantes();
    pro.setId_programa(Integer.parseInt(id_programa));    
    pro.setGestion(gestion);
    pro.setPeriodo(periodo);
    modelo.put("gestion", Integer.toString(gestion));
    modelo.put("periodo", Integer.toString(periodo));
    modelo.put("programa", programa);    
    List listarRendimientos = this.mi.getRendimientoAcademico(pro);
    modelo.put("listarRendimientos", listarRendimientos);
    System.out.println("ID PROGRAMA:"+id_programa);
    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }
    
	Instituciones datosInstitucionSede = new Instituciones();
    datosInstitucionSede.setId_institucion(cliente.getId_almacen()); //--------------------------ESTATICO
    datosInstitucionSede = this.mi.getBuscarInstitucionSede(datosInstitucionSede);
    if (datosInstitucionSede !=null) {
      modelo.put("datosInstitucionsede", datosInstitucionSede);
    }
    
    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    return new ModelAndView("reportesAcademicos/rendimiento_academico/ListarRendimiento", modelo);
  }
}




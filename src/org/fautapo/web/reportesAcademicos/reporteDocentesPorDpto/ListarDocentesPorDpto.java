package org.fautapo.web.reportesAcademicos.reporteDocentesPorDpto;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Departamentos;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ListarDocentesPorDpto implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

//
    String periodo = request.getParameter("periodo");
    String gestion = request.getParameter("gestion");
    String id_departamento = request.getParameter("id_departamento");
    modelo.put("gestion", gestion);
    modelo.put("periodo", periodo);

    Docentes docente= new Docentes();    
    docente.setGestion(Integer.parseInt(gestion));
    docente.setPeriodo(Integer.parseInt(periodo));
    docente.setId_departamento(Integer.parseInt(id_departamento));    
//    
    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1);
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
    //Datos del Departamento
    Departamentos dpto=new Departamentos();
    dpto.setId_departamento(Integer.parseInt(id_departamento));
    dpto=this.mi.getDptBuscarDepartamento(dpto);
    modelo.put("dpto",dpto);
    //Envia la lista de Docentes
    List listarDocentesPorDpto=this.mi.getListarDocentesPorDpto(docente);
    modelo.put("listarDocentesPorDpto", listarDocentesPorDpto);
    
    return new ModelAndView("reportesAcademicos/reporteDocentesPorDpto/ListarDocentesPorDpto", modelo);
  }
}
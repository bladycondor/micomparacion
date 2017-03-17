package org.fautapo.web.administrarLibretas;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-08
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-08
 */

public class Salida implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
  
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    String sId_tipo_nota_s     = "";  //AUXILIAR 
    //String sId_materia         = request.getParameter("id_materia");
    //String sId_grupo           = request.getParameter("id_grupo");  
    //String sGrupo              = request.getParameter("grupo");  
    String sId_programa        = request.getParameter("id_programa");
    //String sId_departamento    = request.getParameter("id_departamento");
    //String sId_fase            = request.getParameter("id_fase");
    //String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    //String sTipo_evaluacion    = request.getParameter("tipo_evaluacion");
    //String sId_modelo_ahorro   = request.getParameter("id_modelo_ahorro");
    sId_tipo_nota_s            = request.getParameter("id_tipo_nota_s");
    String sNro_nota_s         = request.getParameter("nro_nota_s");
    //String sGestion            = request.getParameter("gestion");
    //String sPeriodo            = request.getParameter("periodo"); 
    String sId_tipo_grado      = request.getParameter("id_tipo_grado"); 
    //String sAuxiliar           = request.getParameter("auxiliar"); 
    //System.out.println("auxiliar------------> " + sAuxiliar);
    
    //Nuevo
    int iId_asignacion = cliente.getInt(request,"id_asignacion");
    //Buscamos la asignacion docente
    Asignaciones buscarAsignacion = new Asignaciones();
    buscarAsignacion.setId_asignacion(iId_asignacion);
    Asignaciones datosAsignacion = this.mi.getDctBuscarAsignacionDocente(buscarAsignacion);
    modelo.put("datosAsignacion", datosAsignacion);
    if(datosAsignacion == null)
      return new ModelAndView("Error", "mensaje", "No se encontr&oacute; la asignaci&oacute;n docente para la materia");
    String sId_tipo_evaluacion = Integer.toString(datosAsignacion.getId_tipo_evaluacion());

    
    modelo.put("nombres", cliente.getNombres());
    modelo.put("id_rol",Integer.toString(cliente.getId_rol()));
    modelo.put("nro_nota", sNro_nota_s);
    modelo.put("id_tipo_nota_s", sId_tipo_nota_s);
    modelo.put("id_tipo_grado", sId_tipo_grado);    


    //Sacamos el programa 
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    Programas buscarPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("id_programa", sId_programa);
    modelo.put("programa", buscarPrograma.getPrograma());

    //Sacamos la fase actual según la asignacion del docente
    Libretas buscarFase = new Libretas();
    buscarFase.setId_fase(datosAsignacion.getId_fase());
    buscarFase.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    buscarFase.setId_departamento(datosAsignacion.getId_departamento());
    buscarFase.setGestion(datosAsignacion.getGestion());
    buscarFase.setPeriodo(datosAsignacion.getPeriodo());
    Libretas datosFase = this.mi.getLbrBuscarFase(buscarFase);
    if(datosFase.getFase() == null) {
      return new ModelAndView("Aviso","mensaje", "No se puede encontrar la fase.");
    }
    //Verificando si la fase es mayor a 100
    if(datosFase.getId_fase() >= 100) {
      return new ModelAndView("Error","mensaje", "No esta permitido administrar notas para la fase ::&nbsp;"+datosFase.getFase());
    }
    modelo.put("fase", datosFase.getFase());
    
    //Verificamos si es una materia con modelo ahorro
    //Buscamos la materia modelo_ahorro
    Asignaciones datos = new Asignaciones();
    if (datosAsignacion.getId_modelo_ahorro() > 0) {
      datos.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
      datos.setId_materia(datosAsignacion.getId_materia());
      datos.setId_programa(datosPrograma.getId_programa());
      datos.setGestion(datosAsignacion.getGestion());
      datos.setPeriodo(datosAsignacion.getPeriodo());
      List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
      Asignaciones aux = new Asignaciones();
      for (int i = 0; i < materiaAhorro.size(); i++) {
        aux = (Asignaciones)materiaAhorro.get(i);
        modelo.put("materia_modelo_ahorro", aux.getModelo_ahorro());
      }
    }
    modelo.put("id_modelo_ahorro", Integer.toString(datosAsignacion.getId_modelo_ahorro()));
    modelo.put("mensaje", "La nota se registro correctamente");
    return new ModelAndView("administrarLibretas/Salida", modelo);
  }
}
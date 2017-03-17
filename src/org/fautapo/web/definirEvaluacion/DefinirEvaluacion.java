package org.fautapo.web.definirEvaluacion;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Programas;
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

public class DefinirEvaluacion implements Controller {
        
  private MiFacade mi;
 
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    String sNombres = cliente.getNombres();
    int iId_rol = cliente.getId_rol();
    int iId_docente = cliente.getId_usuario();
    
    int iId_asignacion        = cliente.getInt(request,"id_asignacion");
    String sGestion            = request.getParameter("gestion");
    String sPeriodo            = request.getParameter("periodo");
    int iId_modelo_ahorro      = cliente.getInt(request,"id_modelo_ahorro");
    String sId_materia         = request.getParameter("id_materia");
    String sId_fase            = request.getParameter("id_fase");
    String sId_programa        = request.getParameter("id_programa");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    String sId_grupo           = request.getParameter("id_grupo");
    String sId_departamento    = request.getParameter("id_departamento");    
    
    
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("nombres", sNombres);
    modelo.put("id_docente", Integer.toString(iId_docente));
    modelo.put("id_rol", Integer.toString(iId_rol));
    modelo.put("id_grupo", sId_grupo);
    modelo.put("id_fase", sId_fase);
    modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    modelo.put("id_departamento", sId_departamento);    
    modelo.put("id_modelo_ahorro", Integer.toString(iId_modelo_ahorro));    
    modelo.put("id_asignacion", Integer.toString(iId_asignacion));    
    
    //Verificando
    if(iId_asignacion == 0)
      return new ModelAndView("Error","mensaje","No ingreso la Asignaci&oacute;n del Docente. Verifique los datos.");
    
    //Buscamos la asignacion del Docente
    Asignaciones datosAsignacion = new Asignaciones();
    datosAsignacion.setId_asignacion(iId_asignacion);
    datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
    if(datosAsignacion == null)
      return new ModelAndView("Error","mensaje","No  se existen datos para la asignaci&oacute;n del docente seleccionado.");
    modelo.put("datosAsignacion", datosAsignacion);
      
   //Buscar el programa
    Programas buscarPrograma = new Programas();
    buscarPrograma.setId_programa(Integer.parseInt(sId_programa));
    Programas datosPrograma = this.mi.getPrgBuscarPrograma(buscarPrograma);
    modelo.put("id_programa", Integer.toString(datosPrograma.getId_programa()));
    modelo.put("programa", datosPrograma.getPrograma());
    //modelo.put(datosPrograma.);
    
    //Buscar Datos de la materia
    Materias datosMateria = new Materias();
    datosMateria.setId_materia(datosAsignacion.getId_materia());
    Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);
    modelo.put("sigla", buscarMateria.getSigla());
    modelo.put("id_materia", Integer.toString(buscarMateria.getId_materia()));
    modelo.put("materia", buscarMateria.getMateria());
    //Verificamos si el departamento de la materia coincide con la asignacion
    if(datosAsignacion.getId_departamento() != buscarMateria.getId_departamento())
      return new ModelAndView("Error", "mensaje","Alerta!!. El departamento de la materia no coincide con la asignaci&oacute;n del docente. Consulte al administrador del sistema.");
    
    //Verificamos si es una materia con modelo ahorro
    //Buscamos la materia modelo_ahorro
    Asignaciones datos = new Asignaciones();
    if (iId_modelo_ahorro > 0) {
      datos.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
      datos.setId_materia(datosAsignacion.getId_materia());
      datos.setId_programa(datosPrograma.getId_programa());
      datos.setGestion(datosAsignacion.getGestion());
      datos.setPeriodo(datosAsignacion.getPeriodo());
      List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
      Asignaciones aux = new Asignaciones();
      for (int i = 0; i < materiaAhorro.size(); i++) {
       aux = (Asignaciones)materiaAhorro.get(i);
       modelo.put("materia_ahorro", aux.getModelo_ahorro());
      }
    }
    modelo.put("id_modelo_ahorro", Integer.toString(datosAsignacion.getId_modelo_ahorro()));
    
    //Sacamos la fase actual según la asignacion del docente
    Libretas buscarFase = new Libretas();
    buscarFase.setId_fase(datosAsignacion.getId_fase());
    buscarFase.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    buscarFase.setId_departamento(datosAsignacion.getId_departamento());
    buscarFase.setGestion(datosAsignacion.getGestion());
    buscarFase.setPeriodo(datosAsignacion.getPeriodo());
    Libretas datosFase = this.mi.getLbrBuscarFase(buscarFase);
    if(datosFase.getFase() == null) {
      return new ModelAndView("Aviso","mensaje", "No existen mas fases. Por lo que no puede realizar m&aacute;s definiciones de Evaluaci&oacute;n.");
    }
    modelo.put("fase", datosFase.getFase());
    
    //En caso de existir fases listar Tipos de Notas
    Libretas datosTiposNotas = new Libretas();
    datosTiposNotas.setGestion(datosAsignacion.getGestion());
    datosTiposNotas.setPeriodo(datosAsignacion.getPeriodo());
    datosTiposNotas.setId_materia(datosAsignacion.getId_materia());
    datosTiposNotas.setId_fase(datosAsignacion.getId_fase());
    datosTiposNotas.setId_grupo(datosAsignacion.getId_grupo());
    datosTiposNotas.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    datosTiposNotas.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
    datosTiposNotas.setId_departamento(datosAsignacion.getId_departamento());
    List lTiposNotas = this.mi.getLbrListarTiposNotas(datosTiposNotas);
    modelo.put("lTiposNotas",lTiposNotas);
    
    return new ModelAndView("definirEvaluacion/DefinirEvaluacion", modelo);
  }
}
package org.fautapo.web.reportesAcademicos.verFichaAcademicaEstudiante;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarFichaAcademica implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    
    // Comprobamos es quien debe, de acuerdo a su clave
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(cliente.getId_usuario());
    datosEstudiante.setClave(request.getParameter("clave"+request.getParameter("hora")));

    if (null == this.mi.getComprobarEstudiante(datosEstudiante)) {
      return new ModelAndView("reportesAcademicos/verFichaAcademicaEstudiante/Entrada", "cliente", cliente);
    }
    modelo.put("cliente", cliente);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));
    
    //Sacamos los datos del Estudiante
    datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(cliente.getId_usuario());
    datosEstudiante = this.mi.getEstBuscarEstudiante(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    
    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(datosEstudiante.getId_programa());
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    //Listamos la ficha academica del estudiante
    List lFichaAcademica = this.mi.getEstListarFichaAcademica(datosEstudiante);
    modelo.put("lFichaAcademica", lFichaAcademica);
    
    //Listamos la ficha academica convalidada del estudiante
    List lFichaAcademicaConvalidada = this.mi.getEstListarFichaAcademicaConvalidada(datosEstudiante);
    modelo.put("lFichaAcademicaConvalidada", lFichaAcademicaConvalidada);
    
    return new ModelAndView("reportesAcademicos/verFichaAcademicaEstudiante/ListarFichaAcademica", modelo);
  }
}
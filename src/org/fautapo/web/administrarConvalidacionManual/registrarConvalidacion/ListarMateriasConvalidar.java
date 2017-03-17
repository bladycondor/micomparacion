package org.fautapo.web.administrarConvalidacionManual.registrarConvalidacion;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class ListarMateriasConvalidar implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante;
    
    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sCi = request.getParameter("ci");
    String sNombres = request.getParameter("nombres");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_programa = request.getParameter("id_programa");
    String sId_tipo_convalidacion = request.getParameter("id_tipo_convalidacion");
    //Recuperamos las materias seleccionadas
    String sId_materia_conv[] = request.getParameterValues("id_materia_conv");
    List lMateriasSeleccionadas = new ArrayList();
    String sId_materia;
    Materias datosMateria;// = new Materias();
    //Votamos los datos
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("dip", sGestion);
    modelo.put("nombres", sNombres);
    
    if ("".equals(sId_estudiante)) {
      return new ModelAndView("Error","mensaje", "No ingreso el R.U. el estudiante.");
      //return new ModelAndView("administrarConvalidacionManual/BuscarEstudiantes",modelo);
    }
    
    //Sacando los datos del estudiante    
    datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
    modelo.put("datosEstudiante",datosEstudiante);
    
    //Buscar Tipos Convalidacion
    if ("".equals(sId_tipo_convalidacion)) return new ModelAndView("Error","mensaje", "No selecciono el Tipo de Convalidacion.");
    Planes buscarTipoConv =  new Planes();
    buscarTipoConv.setId_tipo_convalidacion(Integer.parseInt(sId_tipo_convalidacion));
    buscarTipoConv= this.mi.getBuscarTipoConvalidacion(buscarTipoConv);
    modelo.put("buscarTipoConv", buscarTipoConv);
    
    
    //Buscar materias seleccionadas
    if (sId_materia_conv != null) {
      System.out.println("El tamanio del id_materias_conv -->"+Integer.toString(sId_materia_conv.length));
      for (int i = 0; i< sId_materia_conv.length; i++) {
        datosMateria = new Materias();
	sId_materia = sId_materia_conv[i];
        System.out.println("EL ID  MATERIA SELECCIONADA -->"+ i +" = "+ sId_materia);
        datosMateria.setId_materia(Integer.parseInt(sId_materia));
	datosMateria = this.mi.getMtrBuscarMateria(datosMateria);
	lMateriasSeleccionadas.add(datosMateria);
      }
    }
    else {
      return new ModelAndView("Error","mensaje", "No selecciono ninguna materia del Plan de Estudio.");
    }
    
    
    
    //Listar Universidades
    List lUniversidades = this.mi.getUnvListarUniversidades();
    modelo.put("lUniversidades", lUniversidades);
    //Listar Tipos Materias
    List lTiposMaterias = this.mi.getMtrListarTiposMaterias();
    modelo.put("lTiposMaterias", lTiposMaterias);
    
    //Listamos por ahora las facultades, programa, planes actuales
    //Listando Facultades por la Universidades
    Universidades datosUniversidad = new Universidades();
    System.out.println("LA universidad del Rol -->"+ Integer.toString(cliente.getId_universidad()));
    datosUniversidad.setId_universidad(cliente.getId_universidad()); // De la institucion
    List lFacultades = this.mi.getUnvListarFacultades(datosUniversidad);
    modelo.put("lFacultades", lFacultades);
    System.out.println("El tamino de las facultades -->"+ Integer.toString(lFacultades.size()));
    
    //Sacamos el listado de los programas
    List lProgramas = this.mi.getUnvListarProgramas(datosUniversidad);    
    modelo.put("lProgramas", lProgramas);
    System.out.println("El tamino de los Programas -->"+ Integer.toString(lProgramas.size()));

    //Listar Plan del programa actual
    List lPlanesActual= this.mi.getListarPrgPlanesUniversitarios();
    modelo.put("lPlanesActual",lPlanesActual);
    
    System.out.println("El tamanio de la lista lMateriaSeleccionadas  -->"+Integer.toString(lMateriasSeleccionadas.size()));
    modelo.put("lMateriasSeleccionadas", lMateriasSeleccionadas);
    modelo.put("id_estudiante", sId_estudiante);

    return new ModelAndView("administrarConvalidacionManual/registrarConvalidacion/ListarMateriasConvalidar", modelo);
    
  }
}

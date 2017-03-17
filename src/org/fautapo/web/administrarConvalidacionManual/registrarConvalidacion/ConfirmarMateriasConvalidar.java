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


public class ConfirmarMateriasConvalidar implements Controller {

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
    String sId_tipo_convalidacion = request.getParameter("id_tipo_convalidacion");
    String sResolucion = request.getParameter("resolucion");
    int iId_universidad = cliente.getInt(request, "id_universidad");
    int iId_programa = cliente.getInt(request, "id_programa");
    int iId_facultad = cliente.getInt(request, "id_facultad");
    String sPlan_origen = cliente.getString(request, "plan_origen");
    //Recuperamos Nuevament las materias seleccionadas
    String sId_materia_conv[] = request.getParameterValues("id_materia_conv");
    List lMateriasSeleccionadas = new ArrayList();
    String sId_materia;
    Materias datosMateria;// = new Materias();
    //Votamos los datos
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("dip", sGestion);
    modelo.put("nombres", sNombres);
    modelo.put("id_universidad", Integer.toString(iId_universidad));
    modelo.put("id_facultad", Integer.toString(iId_facultad));
    modelo.put("id_programa", Integer.toString(iId_programa));
    modelo.put("plan_origen", sPlan_origen);
    modelo.put("resolucion", sResolucion);
    
    if ("".equals(sId_estudiante)) {
      return new ModelAndView("Error","mensaje","No ingreso el R.U. del estudiante");
    }
    
    //Sacando los datos del estudiante    
    datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
    modelo.put("datosEstudiante",datosEstudiante);
    
    //Buscar materias seleccionadas
    if (sId_materia_conv != null) {
      System.out.println("El tamanio del id_materias_conv -->"+Integer.toString(sId_materia_conv.length));
      for (int i = 0; i< sId_materia_conv.length; i++) {
        datosMateria = new Materias();
	sId_materia = sId_materia_conv[i];
        System.out.println("EL ID  MATERIA SELECCIONADA CONFIRMAR -->"+ i +" = "+ sId_materia);
        datosMateria.setId_materia(Integer.parseInt(sId_materia));
	datosMateria = this.mi.getMtrBuscarMateria(datosMateria); //Buscamos la materia
	datosMateria.setId_materia(datosMateria.getId_materia());
	datosMateria.setMateria(datosMateria.getMateria());
	datosMateria.setId_tipo_materia(Integer.parseInt(request.getParameter("id_tipo_materia"+sId_materia)));
	Materias datosTipoMateria = this.mi.getMtrBuscarTipoMateria(datosMateria);//Buscar Tipo Materia Seleccionado
	datosMateria.setTipo_materia(datosTipoMateria.getTipo_materia());
	datosMateria.setSigla_origen(request.getParameter("sigla_origen"+sId_materia));
	datosMateria.setMateria_origen(request.getParameter("materia_origen"+sId_materia));
	datosMateria.setSimilitud(Integer.parseInt(request.getParameter("similitud"+sId_materia)));	
	datosMateria.setNota_origen(Integer.parseInt(request.getParameter("nota_origen"+sId_materia)));	
	lMateriasSeleccionadas.add(datosMateria);
      }
    }
    else {
      return new ModelAndView("Error","mensaje", "No selecciono ninguna materia del Plan de Estudio.");
    }
    
    //Buscar Tipos Convalidacion
    Planes buscarTipoConv =  new Planes();
    buscarTipoConv.setId_tipo_convalidacion(Integer.parseInt(sId_tipo_convalidacion));
    buscarTipoConv= this.mi.getBuscarTipoConvalidacion(buscarTipoConv);
    modelo.put("buscarTipoConv", buscarTipoConv);
    
    //Buscar Universidad
    Universidades datosUniv = new Universidades();
    datosUniv.setId_universidad(iId_universidad);
    datosUniv = this.mi.getUnvBuscarUniversidad(datosUniv);
    modelo.put("datosUniv", datosUniv);
    
    //Buscar Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(iId_programa);
    datosPrograma  = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    System.out.println("El tamanio de la lista lMateriaSeleccionadas EOEO  -->"+Integer.toString(lMateriasSeleccionadas.size()));
    modelo.put("lMateriasSeleccionadas", lMateriasSeleccionadas);
    modelo.put("id_estudiante", sId_estudiante);

    return new ModelAndView("administrarConvalidacionManual/registrarConvalidacion/ConfirmarMateriasConvalidar", modelo);
    
  }
}

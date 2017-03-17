package org.fautapo.web.retiroAdicionMaterias.estudiante;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Grupos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class RegistrarAccionProgramacionMaterias implements Controller {

  private MiFacade mi;;
     
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    
    //String sUlt_usuario = cliente.getId_usuario()+"|"+cliente.getId_rol() ;
    
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    //Definicion de variables
    String sCadena="";
    List lMaterias = new ArrayList();

    int iResultado;
    //Recuperamos datos del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sId_periodo    = request.getParameter("id_periodo");
    int iTotal_materias   = Integer.parseInt(request.getParameter("total_materias"));
    int iGestion = cliente.getGestion();
    int iPeriodo = cliente.getPeriodo();
    String sAccion = request.getParameter("accion");
    
    //Sacando los datos del estudiante    
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(cliente.getId_usuario());
    datosEstudiante = this.mi.getEstBuscarEstudiante(datosEstudiante);
    
    //Buscamos el periodo
    Programas buscarPeriodo= new Programas();
    buscarPeriodo.setId_programa(datosEstudiante.getId_programa());
    buscarPeriodo = this.mi.getPrdBuscarPrgPeriodo(buscarPeriodo);
    
    boolean bBandera = true;
    
    //Recuperamos los datos de todas las  materias seleccionadas
    for (int i = 0; i < iTotal_materias; i++) {
      sCadena = sCadena + request.getParameter("materia" + i)+"|";
      System.out.println("IMPRIMIENDO LA CADENA -->" + sCadena);
    }
    //Definimos las entradas para los metodos
    Programas programacion = new Programas();
    programacion.setId_estudiante(datosEstudiante.getId_estudiante());
    programacion.setMaterias(sCadena);
    programacion.setId_rol(cliente.getId_rol());  //CRCB
    programacion.setUlt_usuario(cliente.getId_usuario());
    
    //Verificando la accion      
    if("Retiro".equals(sAccion)) {
      programacion.setGestion(iGestion);
      programacion.setPeriodo(iPeriodo);
      this.mi.setEstPrgRetirarProgramacionesMaterias(programacion);
      return new ModelAndView("Aviso","mensaje","Las materias elegidas fueron retiradas correctamente");
    
    }
    
    if("Adicion".equals(sAccion)){
      programacion.setGestion(iGestion);
      programacion.setPeriodo(iPeriodo);
      lMaterias = this.mi.setEstListarProgramarMaterias(programacion);
      if (lMaterias.size() > 0 ) {
        modelo.put("lMaterias", lMaterias);
        return new ModelAndView("retiroAdicionMaterias/estudiante/ListarMateriasNoRegistradas", modelo);
      }
      return new ModelAndView("Aviso","mensaje","Las materias elegidas fueron adicionadas correctamente"); 
    }
    
    if("Cambio de grupo".equals(sAccion)){
      programacion.setGestion(cliente.getGestion());
      programacion.setPeriodo(cliente.getPeriodo());
      lMaterias = this.mi.setEstPrgRegistrarListarCambiarGrupos(programacion);
      if (lMaterias.size() > 0 ) {
        modelo.put("lMaterias", lMaterias);
        return new ModelAndView("retiroAdicionMaterias/estudiante/ListarMateriasNoRegistradas", modelo);
      }
      return new ModelAndView("Aviso","mensaje","Las materias elegidas fueron cambiadas de grupo correctamente"); 
    }
    
    

    return new ModelAndView("Aviso","mensaje","Las materias elegidas fueron registradas correctamente ");

  }
}
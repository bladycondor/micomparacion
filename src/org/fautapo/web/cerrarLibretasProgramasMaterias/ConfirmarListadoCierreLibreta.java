package org.fautapo.web.cerrarLibretasProgramasMaterias;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Programas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

public class ConfirmarListadoCierreLibreta implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    String _sNombres = cliente.getNombres();
    //int _iId_docente = cliente.getId_usuario();
    int _iId_rol = cliente.getId_rol();  
    //Recuperamos las variables
    int iGestion = Integer.parseInt(request.getParameter("gestion"));
    int iPeriodo = Integer.parseInt(request.getParameter("periodo"));
    int iId_programa = Integer.parseInt(request.getParameter("id_programa"));
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
    int iId_asignacion = cliente.getInt(request, "id_asignacion");
    
    String sId_materia = request.getParameter("id_materia");
    String sId_modelo_ahorro = request.getParameter("id_modelo_ahorro");
    String sId_fase = request.getParameter("id_fase");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_docente = request.getParameter("id_docente");
    String sId_grupo = request.getParameter("id_grupo");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    
    modelo.put("id_materia", sId_materia);
    modelo.put("id_modelo_ahorro", sId_modelo_ahorro);
    modelo.put("id_fase", sId_fase);
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("id_docente", sId_docente);
    modelo.put("id_grupo", sId_grupo);
    modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    
    
    //Buscando el programa
    Programas programa = new Programas();
    programa.setId_programa(iId_programa);
    Programas datosPrograma = this.mi.getPrgBuscarPrograma(programa);
    modelo.put("programa", datosPrograma);
    
    //Buscar Tipo evaluacion
    Libretas datosTipoEval = new Libretas();
    datosTipoEval.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosTipoEval = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEval);
    modelo.put("datosTipoEval", datosTipoEval);
    
    //Buscamos la asignacion docente
    Asignaciones datosAsignacion = new Asignaciones();
    datosAsignacion.setId_asignacion(iId_asignacion);
    datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
    modelo.put("datosAsignacion", datosAsignacion);
    
    if(datosAsignacion == null)
      return new ModelAndView("Error", "mensaje", "No se encontro la asignacion docente para la materia");
    
    
    //Listar los estudiantes listos para el cierre de libretas en est_libretas con id_fase=1000 
    Libretas datosLibreta = new Libretas();
    datosLibreta.setId_materia(datosAsignacion.getId_materia());
    System.out.println("La materia es -->"+(Integer.toString(datosLibreta.getId_materia())));
    datosLibreta.setId_departamento(datosAsignacion.getId_departamento());
    System.out.println("El id_departamento es -->"+(Integer.toString(datosLibreta.getId_departamento())));
    datosLibreta.setId_grupo(datosAsignacion.getId_grupo());
    System.out.println("La id_grupo es -->"+(Integer.toString(datosLibreta.getId_grupo())));
    datosLibreta.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
    System.out.println("La Id_modelo_ahorro es -->"+(Integer.toString(datosLibreta.getId_modelo_ahorro())));
    datosLibreta.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    System.out.println("La Id_tipo_evaluacion es -->"+(Integer.toString(datosLibreta.getId_tipo_evaluacion())));
    datosLibreta.setGestion(datosAsignacion.getGestion());
    System.out.println("La Gestio es -->"+(Integer.toString(datosLibreta.getGestion())));
    datosLibreta.setPeriodo(datosAsignacion.getPeriodo());
    System.out.println("La Periodo es -->"+(Integer.toString(datosLibreta.getPeriodo())));
    //List lEstudiantesParaCierre = this.mi.getListarEstudiantesParaCierreLibreta(datosLibreta);
    List lEstudiantesParaCierre = this.mi.getListarEstudiantesEnEstLibretas(datosLibreta);
    modelo.put("lEstudiantesParaCierre", lEstudiantesParaCierre);
    System.out.println("El tamanio del listado -->"+(Integer.toString(lEstudiantesParaCierre.size())));
    
    
    
    return new ModelAndView("cerrarLibretasProgramasMaterias/ConfirmarListadoCierreLibreta", modelo);
  }
}

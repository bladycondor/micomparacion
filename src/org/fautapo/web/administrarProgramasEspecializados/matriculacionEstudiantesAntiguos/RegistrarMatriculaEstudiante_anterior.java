package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesAntiguos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class RegistrarMatriculaEstudiante_anterior implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    //Para wayka
    Tramites datosTramite = new Tramites();
    String sId_proceso = request.getParameter("id_proceso");
    String sTitulo = request.getParameter("titulo");
    String sId_tramite = request.getParameter("id_tramite");
    modelo.put("id_tramite",sId_tramite);
    modelo.put("titulo", sTitulo);
    modelo.put("id_proceso", sId_proceso);
    //String sId_proceso = "2"; //request.getParameter("id_proceso");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));
    int iResultadoDoc=0;
     //Sacando el el dato de wayka
    if (Integer.parseInt(sId_tramite) == 0) {
        return new ModelAndView("Error","mensaje","El tramite no ha pasado");
    } 
    int iId_tramite = cliente.getInt(request, "id_tramite");
    modelo.put("id_tramite", Integer.toString(iId_tramite));
    Tramites tramite = new Tramites();
    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("id_estudiante");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    int iId_estudiante = Integer.parseInt(tramite.getValores());
    
    if (iId_estudiante > 0) {
      Estudiantes datoEst = new Estudiantes();
      datoEst.setId_estudiante(iId_estudiante);
      datoEst.setGestion(cliente.getGestion());
      datoEst.setPeriodo(cliente.getPeriodo());
      datoEst.setId_rol(cliente.getId_rol());
      datoEst.setUlt_usuario(cliente.getId_usuario());
      int iId_matricula_resultado = this.mi.setRegistrarMatriculaEstudiante(datoEst);
      
      if (iId_matricula_resultado > 0) {
        //Buscamos la matricula
	datoEst.setId_matricula(iId_matricula_resultado);
        datoEst = this.mi.getBuscarMatriculaEstPrs(datoEst);
        modelo.put("datoEst",datoEst);    
	modelo.put("id_matricula", Integer.toString(iId_matricula_resultado));
        //Registramos los valores de id_perfil en wayka
        //Datos del Id_matricula
        datosTramite= new Tramites();    
        datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
        datosTramite.setEtiqueta("id_matricula");
        datosTramite.setValor(Integer.toString(iId_matricula_resultado));
        datosTramite.setUlt_usuario(cliente.getId_usuario());
        int iResultado1 = this.mi.setRegistrarValorLimbo2(datosTramite);
	
	//Registramos en tr_pr_fr_log
	datosTramite= new Tramites();    
        datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
        int iResultado = this.mi.setRegistrarTrPrFrLogLimbo(datosTramite);      
      }
      else{
        //Buscamos si ya esta registrado
	tramite = new Tramites();
        tramite.setId_tramite(iId_tramite);
        tramite.setEtiqueta("id_matricula");
        tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
        int iId_matricula = Integer.parseInt(tramite.getValores());
	//Buscamos la matricula
	datoEst.setId_matricula(iId_matricula);
        datoEst = this.mi.getBuscarMatriculaEstPrs(datoEst);
        modelo.put("datoEst",datoEst);    
	modelo.put("id_matricula", Integer.toString(iId_matricula_resultado));
      }
    }
    
     //Registramos en tr_pr_fr_log
     //datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
     //int iResultado = this.mi.setRegistrarTrPrFrLogLimbo(datosTramite);      
    
    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguos/VerDatosMatriculaEstudiante", modelo);
  }
}

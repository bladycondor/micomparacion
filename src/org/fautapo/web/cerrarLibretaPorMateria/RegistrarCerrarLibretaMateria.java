package org.fautapo.web.cerrarLibretaPorMateria;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

public class RegistrarCerrarLibretaMateria implements Controller {

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
    String sMensaje;   
    String sBoton = request.getParameter("boton");
    String sId_materia = request.getParameter("id_materia");
    String sId_modelo_ahorro = request.getParameter("id_modelo_ahorro");
    String sId_fase = request.getParameter("id_fase");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_docente = request.getParameter("id_docente");
    String sId_grupo = request.getParameter("id_grupo");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    
    
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    

    if((!"".equals(sId_materia)) && (!"".equals(sId_modelo_ahorro) && (!"".equals(sGestion)) && (!"".equals(sId_tipo_evaluacion))
       && (!"".equals(sPeriodo)) && (!"".equals(sId_docente)) && (!"".equals(sId_grupo)))){

      Libretas cerrarLibreta = new Libretas();
      cerrarLibreta.setId_docente(Integer.parseInt(sId_docente));
      System.out.println("EL ID_DOCENTE -->"+ cerrarLibreta.getId_docente());
      cerrarLibreta.setId_materia(Integer.parseInt(sId_materia));
      System.out.println("LA materia -->"+ cerrarLibreta.getId_materia());
      cerrarLibreta.setId_grupo(Integer.parseInt(sId_grupo));
      System.out.println("El id_Grupo -->"+ cerrarLibreta.getId_grupo());
      cerrarLibreta.setId_fase(Integer.parseInt(sId_fase));
      System.out.println("El id_fase -->"+ cerrarLibreta.getId_fase());
      cerrarLibreta.setGestion(Integer.parseInt(sGestion));
      System.out.println("LA GESTION -->"+ cerrarLibreta.getGestion());
      cerrarLibreta.setPeriodo(Integer.parseInt(sPeriodo));
      System.out.println("EL PERIODO -->"+ cerrarLibreta.getPeriodo());
      cerrarLibreta.setId_modelo_ahorro(Integer.parseInt(sId_modelo_ahorro));
      System.out.println("Id_modelo_Ahorro"+ cerrarLibreta.getId_modelo_ahorro());
      cerrarLibreta.setId_tipo_evaluacion(Integer.parseInt(sId_tipo_evaluacion));
      System.out.println("Id_tipo_evaluacion"+ cerrarLibreta.getId_tipo_evaluacion());
      cerrarLibreta.setId_rol(cliente.getId_rol());
      System.out.println("EL ROL -->"+ cerrarLibreta.getId_rol());
      cerrarLibreta.setUlt_usuario(cliente.getId_usuario());
      System.out.println("EL UL USUARIO-->"+ cerrarLibreta.getUlt_usuario());
     
      int iValor = this.mi.setCerrarLibretaPorMateria(cerrarLibreta);
      if (iValor == 1) {
        sMensaje="Se ha cerrado la libreta de calificaciones para la materia seleccionada";
      }
      else {
        sMensaje="No se pudo realizar el cierre de libreta";
      }
    }
    else {
      sMensaje="Datos incompletos"; 
    }
    modelo.put("mensaje", sMensaje);  
    return new ModelAndView("cerrarLibretaPorMateria/Aviso", modelo);
  }
}

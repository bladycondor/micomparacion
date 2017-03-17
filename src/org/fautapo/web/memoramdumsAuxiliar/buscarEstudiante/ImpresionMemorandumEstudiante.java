package org.fautapo.web.memorandumsAuxiliar.buscarEstudiante;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Personas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */
public class ImpresionMemorandumEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    modelo.put("cliente",cliente);
    modelo.put("gestion",request.getParameter("gestion"));
    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) modelo.put("datosInstitucion", datosInstitucion);    
    
    String nombre_completo = request.getParameter("nombre_completo");
    String id_perfil = request.getParameter("id_perfil");  
    String programa  = request.getParameter("programa");  
    String area      = request.getParameter("area");  
    String sigla     = request.getParameter("sigla");  
    String materia   = request.getParameter("materia");  
    String carga_horaria   = request.getParameter("carga_horaria");  
    
    String fecha_i[]      = request.getParameter("fecha_i").split("-");  
    String fecha_f[]      = request.getParameter("fecha_f").split("-");  
    
    int iAnio = Integer.parseInt(fecha_i[0]);
    int iMes = Integer.parseInt(fecha_i[1]);
    int iDia = Integer.parseInt(fecha_i[2]);    
    Date fecha_inicio = new Date(iAnio-1900,iMes-1,iDia);
    
    int fAnio = Integer.parseInt(fecha_f[0]);
    int fMes = Integer.parseInt(fecha_f[1]);
    int fDia = Integer.parseInt(fecha_f[2]);    
    Date fecha_fin = new Date(fAnio-1900,fMes-1,fDia);    
    	
    modelo.put("nombre_completo",nombre_completo);
    modelo.put("id_perfil",id_perfil);
    modelo.put("programa",programa);
    modelo.put("area",area);
    modelo.put("sigla",sigla);
    modelo.put("materia",materia);    
    modelo.put("fecha_i",fecha_inicio);
    modelo.put("fecha_f",fecha_fin);
    modelo.put("carga_horaria",carga_horaria);
    
    return new ModelAndView("memorandumsAuxiliar/buscarEstudiante/Memo",modelo);
  }
}

package org.fautapo.web.administrarConvalidacionManual.autorizarConvalidacion;

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
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2008-05-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2008-05-05
 */


public class EliminarCnvDetalleConvalidacion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    //Datos del Usuario
    int iId_usuario = cliente.getId_usuario();
    String sUsuario = cliente.getNombres();
    
    //Recuperando variables del jsp
    String sId_programa = request.getParameter("id_programa");
    String sId_convalidacion = request.getParameter("id_convalidacion");
    String sId_cnv_detalle   = request.getParameter("id_cnv_detalle");
    String sMensaje="";
    String sAccion = request.getParameter("accion");
    Planes datosPlanes = new Planes();
    //Votamos los datos
    modelo.put("id_programa", sId_programa);
    modelo.put("usuario", sUsuario);
    modelo.put("id_usuario", Integer.toString(iId_usuario));
    modelo.put("id_convalidacion", sId_convalidacion);

    if("".equals(sId_programa)) return new ModelAndView("Error","mensaje", "Seleccione el programa.");  
    if("".equals(sId_convalidacion) ||(sId_convalidacion == null)) return new ModelAndView("Error","mensaje", "No ingreso la Convalidacion.");  
    //Buscamos el programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    //Buscamos la convalidacion
    datosPlanes.setId_convalidacion(Integer.parseInt(sId_convalidacion));
    Planes datosConvalidacion = this.mi.getBuscarConvalidacionManual(datosPlanes);
    modelo.put("datosConvalidacion", datosConvalidacion);
    if((!"".equals(sId_cnv_detalle)) && (sId_cnv_detalle != null) && ("Eliminar".equals(sAccion))) {
     datosPlanes.setId_cnv_detalle(Integer.parseInt(sId_cnv_detalle));
     datosPlanes.setUlt_usuario(cliente.getId_usuario());
     int iResultadoElim = this.mi.setEliminarCnvDetalle(datosPlanes);
     if(iResultadoElim ==0) return new ModelAndView("Error","mensaje", "No se pudo realizar la  Eliminacion del Detalle de Convalidacion.");  
    
    } 
    else{
      return new ModelAndView("Error","mensaje", "No ingreso el Detalle de Convalidacion a Eliminar.");
    }
    
    //Cnv_Detalles
    datosPlanes.setUlt_usuario(cliente.getId_usuario());
    List lCnvDetallesConvalidacionPrograma = this.mi.getListarCnvDetallesConvalidacion(datosPlanes);      
    modelo.put("lCnvDetallesConvalidacionPrograma",lCnvDetallesConvalidacionPrograma);
      
    return new ModelAndView("administrarConvalidacionManual/autorizarConvalidacion/ListarCnvDetallesConvalidacionesPrograma", modelo);
    
  }
}

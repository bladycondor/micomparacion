package org.fautapo.web.administrarConvalidacionManual.autorizarConvalidacion;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2008-05-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2008-05-05
 */


public class ListarConvalidacionesPrograma implements Controller {

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
    String sMensaje="";
    Planes datosPlanes = new Planes();
    //Votamos los datos
    modelo.put("id_programa", sId_programa);
    modelo.put("usuario", sUsuario);
    modelo.put("id_usuario", Integer.toString(iId_usuario));
    modelo.put("id_convalidacion", sId_convalidacion);
    if("".equals(sId_programa)) return new ModelAndView("Error","mensaje", "Seleccione el programa");  
    //Buscamos el programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    //Listamos las convalidaciones realizadas por el usuario
    datosPlanes.setId_programa(datosPrograma.getId_programa());
    datosPlanes.setUlt_usuario(cliente.getId_usuario());
    List lConvalidacionManualPrograma  = this.mi.getListarConvalidacionManualPrograma(datosPlanes);
    modelo.put("lConvalidacionManualPrograma", lConvalidacionManualPrograma);
    
    //Listamos CnvDetalles
    System.out.println("El id_convalidacion -->"+ sId_convalidacion);
    if((!"".equals(sId_convalidacion)) && (sId_convalidacion != null)) {
      //Primero buscar convalidacion
      datosPlanes.setId_convalidacion(Integer.parseInt(sId_convalidacion));
      Planes datosConvalidacion = this.mi.getBuscarConvalidacionManual(datosPlanes);
      modelo.put("datosConvalidacion", datosConvalidacion);
      //Cnv_Detalles
      datosPlanes.setUlt_usuario(cliente.getId_usuario());
      List lCnvDetallesConvalidacionPrograma = this.mi.getListarCnvDetallesConvalidacion(datosPlanes);      
      modelo.put("lCnvDetallesConvalidacionPrograma",lCnvDetallesConvalidacionPrograma);
      
      return new ModelAndView("administrarConvalidacionManual/autorizarConvalidacion/ListarCnvDetallesConvalidacionesPrograma", modelo);
    }	
    
    return new ModelAndView("administrarConvalidacionManual/autorizarConvalidacion/ListarConvalidacionesPrograma", modelo);
    
  }
}

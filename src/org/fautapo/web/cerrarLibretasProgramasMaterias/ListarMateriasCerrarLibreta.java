package org.fautapo.web.cerrarLibretasProgramasMaterias;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

public class ListarMateriasCerrarLibreta implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    //String sUlt_usuario = cliente.getId_usuario() + "|" + cliente.getId_rol();
    String _sNombres = cliente.getNombres();
    int _iId_docente = cliente.getId_usuario();
    int _iId_rol = cliente.getId_rol();    
    
    String sClave = request.getParameter("clave"+ request.getParameter("hora"));
    String sGestionx = request.getParameter("gestion");
    String sPeriodox = request.getParameter("periodo");
    String sRecargado = request.getParameter("recargado");
    String sBandera = request.getParameter("bandera");
    String sBoton = request.getParameter("boton");
    
    
    modelo.put("gestion", sGestionx);
    modelo.put("periodo", sPeriodox);
    
    //Para la primera vez que entre a la pagina
    if (sRecargado == null) {
      if(!"1".equals(request.getParameter("bandera"))){
        if ("".equals(sClave) || ("".equals(sGestionx)) || ("".equals(sPeriodox))) {
          return new ModelAndView("cerrarLibretasProgramasMaterias/Entrada", "cliente", cliente);
        }
    
        // Comprobamos es quien debe, de acuerdo a su clave
        Usuarios datosUsuario = new Usuarios();
        datosUsuario.setId_usuario(cliente.getId_usuario());
        datosUsuario.setClave(request.getParameter("clave"+request.getParameter("hora")));
        if (null == this.mi.getComprobarUsuario(datosUsuario)) {
          return new ModelAndView("cerrarLibretasProgramasMaterias/Entrada", "cliente", cliente);
        }
      }
    }

    // Una vez comprobado la clave del usuario
    //if(!"1".equals(request.getParameter("bandera"))){
      Libretas listarMaterias = new Libretas();
      listarMaterias.setGestion(Integer.parseInt(sGestionx));
      listarMaterias.setPeriodo(Integer.parseInt(sPeriodox));
      List lMaterias = this.mi.getListarMateriasCerrarLibretaIndiv(listarMaterias);
      //lMaterias.setPageSize(lMaterias.getNrOfElements());
      modelo.put("lMateriasCerrarLibreta", lMaterias);
      
    return new ModelAndView("cerrarLibretasProgramasMaterias/ListarMateriasCerrarLibreta", modelo);
  }
}

package org.fautapo.web.administrarCampos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Campos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-20
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-21
*/

public class NuevoCampo implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
  Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String sAccion   = request.getParameter("accion");
    String sId_campo = request.getParameter("id_campo");
    String sId_form  = request.getParameter("id_form"); 
    String sOperacion  = request.getParameter("operacion"); 

    // Listamos los dominios según el acceso
    List lDominios = this.mi.getListarDominiosAcceso(cliente);
    modelo.put("lDominios", lDominios);
     
    //Listamos los tipos de validaciones
    List lTiposValidaciones = this.mi.getListarTiposValidaciones();
    modelo.put("lTiposValidaciones", lTiposValidaciones);
     
    //Buscamos los valores del formulario
    Campos datosFormulario = new Campos();
    datosFormulario.setId_form(Integer.parseInt(sId_form));
    datosFormulario = (Campos) this.mi.getBuscarFormulario(datosFormulario);
    modelo.put("datosFormulario", datosFormulario);

    modelo.put("id_campo", request.getParameter("id_campo"));
    modelo.put("id_form", request.getParameter("id_form"));
    modelo.put("campo", request.getParameter("campo"));
    modelo.put("id_dominio", request.getParameter("id_dominio"));
    modelo.put("columnas", request.getParameter("columnas"));     
    modelo.put("filas", request.getParameter("filas"));          
    modelo.put("caracteres", request.getParameter("caracteres"));          
    modelo.put("referencia", request.getParameter("referencia"));
    modelo.put("operacion", request.getParameter("operacion"));
    modelo.put("nro_columna", request.getParameter("nro_columna"));
    modelo.put("nro_fila", request.getParameter("nro_fila"));    
    modelo.put("id_tipo_validacion", request.getParameter("id_tipo_validacion"));
    modelo.put("formula", request.getParameter("formula"));
    modelo.put("rango1", request.getParameter("rango1"));
    modelo.put("rango2", request.getParameter("rango2"));
    modelo.put("obligatorio", request.getParameter("obligatorio"));

     //Para la primera vez que entra a la pagina
     if ((request.getParameter("id_form") != null) && ("Modificar".equals(sAccion)) && (request.getParameter("recargado") == null)) {
       //Sacamos los datos del campo
       Campos datosCampo = new Campos();
       datosCampo.setId_campo(Integer.parseInt(request.getParameter("id_campo")));
       datosCampo = (Campos) this.mi.getBuscarCampoForm(datosCampo);
       modelo.put("datosCampo", datosCampo);

       modelo.put("id_proceso", Integer.toString(datosFormulario.getId_proceso()));
       modelo.put("id_form", Integer.toString(datosFormulario.getId_form()));
       modelo.put("id_dominio", Integer.toString(datosCampo.getId_dominio()));
       modelo.put("campo", datosCampo.getCampo());
       modelo.put("columnas", Integer.toString(datosCampo.getColumnas()));
       modelo.put("filas", Integer.toString(datosCampo.getFilas()));
       modelo.put("caracteres", Integer.toString(datosCampo.getCaracteres()));
       modelo.put("referencia", Boolean.toString(datosCampo.getReferencia()));
       modelo.put("operacion", Boolean.toString(datosCampo.getOperacion()));       
       modelo.put("id_tipo_validacion", datosCampo.getId_tipo_validacion());
       modelo.put("nro_fila", Integer.toString(datosCampo.getNro_fila()));
       modelo.put("nro_columna", Integer.toString(datosCampo.getNro_columna()));
       modelo.put("rango1", datosCampo.getRango1());
       modelo.put("rango2", datosCampo.getRango2());
       modelo.put("formula", datosCampo.getFormula());
       modelo.put("sw", request.getParameter("sw"));
       modelo.put("obligatorio", Boolean.toString(datosCampo.getObligatorio()));
     }
     modelo.put("accion", sAccion);
     return new ModelAndView("administrarCampos/NuevoCampo", modelo);
   }
}
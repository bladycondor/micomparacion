package org.fautapo.web.administrarLbrTiposNotas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Departamentos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04_03
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-03
*/

public class RegistrarLbrTiposNotas implements Controller {
        
  private MiFacade mi;
 
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
  
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    //Recupero los datos
    int iResultado = 0;
    int iId_facultad = cliente.getInt(request, "id_facultad");
    int iId_departamento = cliente.getInt(request, "id_departamento");
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
    int iId_lbr_tipo_nota = cliente.getInt(request, "id_lbr_tipo_nota");
    int iId_lbr_fase = cliente.getInt(request, "id_lbr_fase");
    String sId_tipo_nota = request.getParameter("id_tipo_nota");
    String sId_lbr_fase = request.getParameter("id_lbr_fase");
    String sAccion = request.getParameter("accion");
    
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("accion", sAccion);
    
    System.out.println("El id_Facultada de lbr_tipo registrar -->"+ Integer.toString(iId_facultad));
    System.out.println("El id_departamento de lbr_tipo  registrar -->"+ Integer.toString(iId_departamento));
    System.out.println("El id_tipo_Evaluacion de lbr_tipo  registrar -->"+ Integer.toString(iId_tipo_evaluacion));
    
    //Buscamos Facultad
    Facultades datosFacultad = new Facultades();
    datosFacultad.setId_facultad(iId_facultad);
    datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
    modelo.put("datosFacultad",datosFacultad);
    
    //Buscamos datos de Departamento
    Departamentos datosDepartamento = new Departamentos();
    datosDepartamento.setId_departamento(iId_departamento);
    datosDepartamento = this.mi.getDptBuscarDepartamento(datosDepartamento);
    modelo.put("datosDepartamento",datosDepartamento);
    
    //Buscar Tipo Evaluacion
    Libretas datosTipoEval = new Libretas();
    datosTipoEval.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosTipoEval = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEval);
    modelo.put("datosTipoEval", datosTipoEval);
    
    //Buscamos datos de la Fase
    Libretas  datosLbrFase = new Libretas();
    datosLbrFase.setId_lbr_fase(iId_lbr_fase);
    datosLbrFase = this.mi.getBuscarLbrFase(datosLbrFase);
    modelo.put("datosLbrFase", datosLbrFase);
    
    if((datosLbrFase == null) ||(datosTipoEval == null) || (datosFacultad == null) || (datosDepartamento == null) 
       || ("".equals(sGestion)) || ("".equals(sPeriodo)) || ("".equals(sId_tipo_nota)))
      return new ModelAndView("Error", "mensaje", "Faltan datos");
    

    //Realizamos la acccion
    Libretas accionLbrTipoNota = new Libretas();
    if("Eliminar".equals(sAccion)){
      accionLbrTipoNota.setId_lbr_tipo_nota(iId_lbr_tipo_nota);
      iResultado = this.mi.setLbrEliminarTipoNota(accionLbrTipoNota);
    }
    else{
      //Registra nuevo o modifica
      accionLbrTipoNota.setId_lbr_tipo_nota(iId_lbr_tipo_nota);
      accionLbrTipoNota.setId_fase(datosLbrFase.getId_fase());  //Sacando todo  de acuerdo a id_lbr_fase
      accionLbrTipoNota.setId_departamento(datosLbrFase.getId_departamento());
      accionLbrTipoNota.setId_tipo_evaluacion(datosLbrFase.getId_tipo_evaluacion());
      accionLbrTipoNota.setGestion(datosLbrFase.getGestion());
      accionLbrTipoNota.setPeriodo(datosLbrFase.getPeriodo());
      accionLbrTipoNota.setId_tipo_nota(Integer.parseInt(sId_tipo_nota));
      accionLbrTipoNota.setId_rol(cliente.getId_rol());
      accionLbrTipoNota.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setLbrRegistrarTipoNota(accionLbrTipoNota);
    }
    
    if(iResultado == 0){
      String sMensaje = "No se realizo el registro";
      modelo.put("mensaje", sMensaje);
    }
    
    //Listamos fases por id_departamento, id_tipo_evaluacion, gestion, periodo    
    Libretas buscarFases = new Libretas();
    buscarFases.setId_departamento(datosDepartamento.getId_departamento());
    System.out.println("El ID_DEPARTAMENTO LISTAR LBR -->"+ Integer.toString(buscarFases.getId_departamento()));
    buscarFases.setId_tipo_evaluacion(datosTipoEval.getId_tipo_evaluacion());
    buscarFases.setGestion(Integer.parseInt(sGestion));  
    buscarFases.setPeriodo(Integer.parseInt(sPeriodo));  
    List lListarFases = this.mi.getLbrListarFases2(buscarFases);
    modelo.put("lListarLbrFases", lListarFases);
    //Si entra id_lbr_fase listamos los tipos notas que existen
    if((!"".equals(sId_lbr_fase)) && (sId_lbr_fase != null)) {
      //Buscamos datos de la Fase
      buscarFases.setId_lbr_fase(iId_lbr_fase);
      datosLbrFase = this.mi.getBuscarLbrFase(buscarFases);
      modelo.put("datosLbrFase", datosLbrFase);
      if(buscarFases ==null) return new ModelAndView("Error", "mensaje", "No existen datos de la  fase seleccionada");
      
      //Listamos los tipos Notas que se tiene
      buscarFases.setId_fase(datosLbrFase.getId_fase());
      List lTiposNotasFase = this.mi.getLbrListarTiposNotasFase(buscarFases);
      modelo.put("lTiposNotasFase", lTiposNotasFase);
    }
    
    modelo.put("cliente",cliente);
    modelo.put("gestion", Integer.toString(datosLbrFase.getGestion()));
    modelo.put("periodo",  Integer.toString(datosLbrFase.getPeriodo()));
    
    return new ModelAndView("administrarLbrTiposNotas/ListarLbrTiposNotas", modelo);
    
  }
}

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

public class NuevoLbrTipoNota implements Controller {
        
  private MiFacade mi;
 
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
  
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    //int id_facultad = cliente.getId_facultad();
    int iId_facultad = cliente.getInt(request, "id_facultad");
    int iId_departamento = cliente.getInt(request, "id_departamento");
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
    
    String sId_lbr_fase = request.getParameter("id_lbr_fase");
    String sAccion = request.getParameter("accion");
    String sId_lbr_tipo_nota = request.getParameter("id_lbr_tipo_nota");
    int iId_lbr_tipo_nota = cliente.getInt(request, "id_lbr_tipo_nota");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    
    System.out.println("El id_Facultada de lbr_tipo nota nuevo -->"+ Integer.toString(iId_facultad));
    System.out.println("El id_departamento de lbr_tipo nota nuevo -->"+ Integer.toString(iId_departamento));
    System.out.println("El id_tipo_Evaluacion de lbr_tipo nuevo -->"+ Integer.toString(iId_tipo_evaluacion));
    System.out.println("El gestion de lbr_tipo nuevo -->"+ sGestion);
    System.out.println("El periodo de lbr_tipo nuevo -->"+ sPeriodo);    
    System.out.println("El periodo de id_lbr_tipo_nota nuevo -->"+ sId_lbr_tipo_nota);    
    System.out.println("El periodo de id_lbr_fase nuevo -->"+ sId_lbr_fase);    
    
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
    datosLbrFase.setId_lbr_fase(Integer.parseInt(sId_lbr_fase));
    datosLbrFase = this.mi.getBuscarLbrFase(datosLbrFase);
    modelo.put("datosLbrFase", datosLbrFase);
    
    if((datosTipoEval == null) || (datosFacultad == null) || (datosDepartamento == null) 
       || ("".equals(sGestion)) || ("".equals(sPeriodo)))
      return new ModelAndView("Error", "mensaje", "Faltan datos");
      
    //Listar Tipos Notas
    List lTiposNotas = this.mi.getListarTiposNotas();   
    modelo.put("lTiposNotas", lTiposNotas);
    
    if(("Modificar".equals(sAccion)) || ("Eliminar".equals(sAccion))) {
      //Buscamos el lbr_tipo_nota
      Libretas datosLbrTipoNota = new Libretas();
      datosLbrTipoNota.setId_lbr_tipo_nota(iId_lbr_tipo_nota);
      datosLbrTipoNota = this.mi.getBuscarLbrTipoNota(datosLbrTipoNota);
      modelo.put("datosLbrTipoNota", datosLbrTipoNota);
      if(datosLbrTipoNota == null)
        return new ModelAndView("Error","mensaje","No existe el Lbr Tipo Nota");
    }
    modelo.put("cliente",cliente);
    modelo.put("accion",sAccion);
    return new ModelAndView("administrarLbrTiposNotas/NuevoLbrTipoNota", modelo);
    
  }
}

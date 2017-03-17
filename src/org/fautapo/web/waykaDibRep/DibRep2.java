package org.fautapo.web.waykaDibRep;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.math.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Dibwayka;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.fautapo.web.waykaDibRep.ListarConsultaPDF;

public class DibRep2 implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    //CONTROL DE SESION
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    String _nombres = cliente.getNombres();

    Dibwayka abm = new Dibwayka();
    Dibwayka aux = new Dibwayka();
    Dibwayka aux1 = new Dibwayka();
    String id_tabla;
    String sql = "";

    String sql_tablas = "";
    String sql_estados = "";
    String sql_campos = "";

    String sql_campos_sum = "";
    String sql_grupo = "";

    String sql_limite="";
    String sql_variables="";
    String sql_condiciones = "";

    String etiquetas  = "";
    String etiquetas_sum  = "";
    String etiquetas_count = "";
    String etiquetas_total = "";
    String sql_total = "";
    String sCampo_calculado = "";
    String sEtiqueta_calculado = "";
    String sSql_grupo_calculado = "";
    String sSql_count_sum = "";
    String[] aOrdenar_campos;
    String sCampos_sql="";
    String sCampos_order_sql="";
    String sCampos_group_sql="";    
    String sEtiquetas_sql="";
    String sCondiciones_sql="";
    String sSql="";

    String sId_consulta = request.getParameter("c");
    
    String sTabla = request.getParameter("t");
    if ((sTabla==null)||(sTabla.equals("0")))
      return new ModelAndView("Aviso", "mensaje","no hay datos definidos datos para Listar");

    int iTabla = Integer.parseInt(sTabla);
    Actividades oProceso = new Actividades();
    oProceso.setId_proceso(iTabla);    
    
    oProceso = this.mi.getBuscarProceso(oProceso);
    Dibwayka oTabla = new Dibwayka();
    oTabla.setProceso(oProceso.getProceso());
    oTabla.setId_proceso(oProceso.getId_proceso());
    oTabla.setTabla("proceso_" + oProceso.getId_proceso());
    oTabla.setId_tabla(oProceso.getId_proceso());
    //int iInsertarTablas=this.mi.setCrearTablasDibWK(oTabla);
    

    String aId_campos[] = request.getParameterValues("id_campos");
    String sOrden = request.getParameter("orden");
    aOrdenar_campos =new String[aId_campos.length];
    if ((aId_campos!=null)&&(sOrden!=null)) {
      for(int i=0; i<aId_campos.length; i++) {
        String sOrden1 = request.getParameter("orden_" + aId_campos[i]);
	sOrden1 =sOrden1.trim();
        if (sOrden1.equals(""))
	  aOrdenar_campos[i]="100000000";
	else 
          aOrdenar_campos[i]=sOrden1;   
      }
      
      for (int i=0; i<aId_campos.length; i++) {
        for (int j=i; j<aId_campos.length; j++) {
	  if (Integer.parseInt(aOrdenar_campos[j])<Integer.parseInt(aOrdenar_campos[i])) {
	    String sApoyo=aOrdenar_campos[i];
	    aOrdenar_campos[i]=aOrdenar_campos[j];
	    aOrdenar_campos[j]=sApoyo;
	    sApoyo=aId_campos[i];
	    aId_campos[i]=aId_campos[j];
	    aId_campos[j]=sApoyo;
	  }
	}
      }
    } 
    
    //MARCA LOS CAMPOS PRINCIPALES
    String headers[] = request.getParameterValues("cabezas");
    String header = "";

    //MARCA LOS CAMPOS PARA LA SUBTOTALES
    String suma_st[] = request.getParameterValues("sumar_st");
    String suma = "";    

          //PARA LOS ENCABEZADOS DE EL REPORTE


    if (aId_campos!=null) {
      for(int i=0; i< aId_campos.length; i++) {
        //PARA LOS ENCABEZADOS DE EL REPORTE 
        if (headers != null) {
          for(int j=0; j< headers.length; j++) {
	    if(headers[j].equals(aId_campos[i])) {
	      headers[j] = Integer.toString(i);
	      break;
	    }
          }
        }
        //PARA LOS SUBTOTALES DE LAS SUMAS EN EL JSP
        if(suma_st != null) {
          for(int j=0; j< suma_st.length; j++) {
	    if(suma_st[j].equals(aId_campos[i])) {
	      if(suma.equals(""))
	        suma = Integer.toString(i);
	      else
	        suma = suma + ":" + Integer.toString(i);;
	        break;
	   }
         }    
       }
       //HASTA AQUI
       Dibwayka oCampo = new Dibwayka();
       oTabla.setId_campo(Integer.parseInt(aId_campos[i]));
       oCampo = this.mi.getBuscarCampoDibWK(oTabla);
       if (sCampos_sql.equals("")) {
         sCampos_sql=oCampo.getCampo();
         sEtiquetas_sql=oCampo.getEtiqueta();
	 sCampos_order_sql = " " + oCampo.getCampo();
	 sCampos_group_sql = " " + oCampo.getCampo();
       } else {
         sCampos_sql+="||'#~~#'||" + oCampo.getCampo();
         sEtiquetas_sql+="@@" + oCampo.getEtiqueta();
	 sCampos_order_sql+=", " + oCampo.getCampo();
	 sCampos_group_sql+=", " + oCampo.getCampo();
       }
     }
   }
   
   if (headers != null) {
     for (int j=0;j<headers.length; j++) {
       for(int k=j;k<headers.length; k++) { 
         try {
           if(Integer.parseInt(headers[j])<Integer.parseInt(headers[k])) {
             String sApoyo=headers[j];
             headers[j]=headers[k];
             headers[k]=sApoyo;
           }
         } catch (Exception ee){}
       }
     } 
   }

   String sPaginacion = request.getParameter("paginacion");
   modelo.put("paginacion",sPaginacion);

    
    List lCamposRevision = this.mi.getListarCamposDibWK(oTabla);
    //System.out.println("*" + lCamposRevision.size());
    for (int i=0; i<lCamposRevision.size();i++) {
      Dibwayka oCampo = new Dibwayka();
      oCampo = (Dibwayka)lCamposRevision.get(i);
      String valor_campo = request.getParameter("campo_" + oCampo.getId_campo());
      String condicion_campo = request.getParameter("condicion_" + oCampo.getId_campo());
      if (valor_campo != null) {
        valor_campo = valor_campo.trim();
        if ((!valor_campo.equals(""))&&(!valor_campo.equals("0"))) {
          String sCombo = request.getParameter("combo_" + oCampo.getId_campo());
          if (sCombo!=null) {
            Dibwayka oTupla = new Dibwayka();
            oTupla.setId_tupla(Integer.parseInt(valor_campo));
            oTupla = this.mi.getBuscarTuplaDibWK(oTupla);
            valor_campo = oTupla.getTupla();
          }
	  if (sCondiciones_sql.equals("")) {
	   sCondiciones_sql = "WHERE " + oCampo.getCampo() + " " + condicion_campo + " '" + valor_campo + "' " ;
          } else { 
	   sCondiciones_sql += "AND " + oCampo.getCampo() + " " + condicion_campo + " '" + valor_campo + "' ";
          }
        }
      }  
    }

    sql = "SELECT (" + sCampos_sql + ")::text as valores From " + oTabla.getTabla() + " ";
    //sql = "SELECT " + sql_campos2 + " FROM " + sql_tablas + " WHERE " + sql_estados;


    String variables[] = request.getParameterValues("variables");
    if (variables != null) {
        for(int i=0; i< variables.length ; i++) {
//	 Abm buscarCampo = this.mi.getBuscarCampo(abm);
//	 Abm buscarTabla = this.mi.getBuscarTabla(buscarCampo);
	 if ("".equals(sql_variables)) {
	   sql_variables= variables[i];  
	 } else {
	   sql_variables = sql_variables + ":" + variables[i];  
	 } 
       }
     }
    
    if (!"".equals(sCondiciones_sql)){
      sql = sql + " AND " + sCondiciones_sql;
      if (!sql_variables.equals("")){
        sql = sql + " ## ";
      }
    } else { 
      if (!sql_variables.equals("")){
        sql = sql + " ### ";
      }
    }

    String sContar_reg = request.getParameter("contar_reg");
    if(sContar_reg==null)
      if (!"".equals(sCampos_group_sql)) {
        if (!sSql_grupo_calculado.equals(""))
          sql = sql + " GROUP BY " + sCampos_group_sql + ", " + sSql_grupo_calculado;
        else
          sql = sql + " GROUP BY " + sCampos_group_sql;
      }
    
    if (!"".equals(sCampos_order_sql)) {
      if (!sSql_grupo_calculado.equals(""))
        sql = sql + " ORDER BY " + sCampos_group_sql +", " + sSql_grupo_calculado;
      else
        sql = sql + " ORDER BY " + sCampos_group_sql;
    } else {
      if (!"".equals(sSql_grupo_calculado)) {
        sql = sql + " ORDER BY " + sSql_grupo_calculado;
      }
    }

    sql = sql + ";";
    System.out.println("guardar SQL:" + abm.getSql());    
    abm.setSql(sql);
    //System.out.println("guardar SQL:" + abm.getSql());

    abm.setEtiquetas(sEtiquetas_sql);
    abm.setId_campos(sql_variables);

    int resultado = 0;

    String glosa[] = request.getParameterValues("glosa");
    if (glosa!=null) {
      abm.setGlosa(true);    
    } else {
      abm.setGlosa(false);
    }
    
    String descripcion = request.getParameter("descripcion");
    descripcion = descripcion.trim();
    if(descripcion.equals(""))
      return new ModelAndView("Aviso", "mensaje","Falta colocar la descripción");
    abm.setDescripcion(descripcion);
    abm.setUlt_usuario(cliente.getId_usuario());
    if (headers!=null)
      for (int kk=0;kk<headers.length;kk++){
      if (header.equals(""))
        header = headers[kk];
      else 
        header = header + ":" + headers[kk];
      }
    abm.setCabezas(header);
    abm.setSumas(suma);
    
    System.out.println("condiciones:" + sql_variables);
    System.out.println("header:" + header);
    System.out.println("suma:" + suma);
    System.out.println("etiquetas:" + sEtiquetas_sql);
    System.out.println("sql:" + sql);
    if (sId_consulta.equals("")) 
      if (!sCampos_sql.equals("")){
        resultado = this.mi.setInsertarConsultaDibWK(abm);
        aux.setId_consulta(resultado);
        return new ModelAndView("Aviso", "mensaje","se grabo correctamente Su Consulta Nro=" + resultado);
      } else {
        return new ModelAndView("Aviso", "mensaje","No tiene Datos bien definidos");
      }
    else
      if (!sCampos_sql.equals("")){
        abm.setId_consulta(Integer.parseInt(sId_consulta));
        resultado = this.mi.setModificarConsultaDibWK(abm);
        aux.setId_consulta(Integer.parseInt(sId_consulta));
        return new ModelAndView("Aviso", "mensaje","se modificó correctamente Su Consulta Nro=" + resultado);
      } else {
        return new ModelAndView("Aviso", "mensaje","No tiene Datos bien definidos");
      }
  }
} 
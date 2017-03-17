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

public class DibRep1 implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    //CONTROL DE SESION
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    String _nombres = cliente.getNombres();

    Instituciones institucion = new Instituciones();
    //institucion.setId_institucion(cliente.id_institucion);
    institucion.setId_institucion(1); //--------------------------ESTATICO
    institucion = this.mi.getBuscarInstitucion(institucion);
    if(institucion !=null) {
      modelo.put("institucion",institucion.getInstitucion());
      modelo.put("logo",institucion.getLogo());
    }

    String sRuta = request.getParameter("ruta");
    String sDescripcion = request.getParameter("descripcion");
    sDescripcion = sDescripcion.replace("\r\n","");

    modelo.put("ruta", sRuta);
    modelo.put("descripcion", sDescripcion);

    String datos[][];
    List tablas_dibrep = new ArrayList();
    boolean t = true;
    String lista_tablas[] = null;
    String sCampos_sql="";
    String sCampos_order_sql="";
    String sCampos_group_sql="";    
    String[] aOrdenar_campos;
    String sEtiquetas_sql="";
    String sCondiciones_sql="";
    String sSql="";
    
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
    int iInsertarTablas=this.mi.setCrearTablasDibWK(oTabla);
    

    String aId_campos[] = request.getParameterValues("id_campos");
    String sOrden = request.getParameter("orden");
    aOrdenar_campos =new String[aId_campos.length];
    if ((aId_campos!=null)&&(sOrden!=null)) {
      for (int i=0;i<aId_campos.length;i++)
        System.out.println("antes aId_campos=" + aId_campos[i]);


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


    //MARCA LOS CAMPOS PARA LA SUBTOTALES
    String suma_st[] = request.getParameterValues("sumar_st");




          //PARA LOS ENCABEZADOS DE EL REPORTE















    if (aId_campos!=null) {
      for(int i=0; i< aId_campos.length; i++) {
      

    //DESDE AQUI 
	  if(headers != null) {
	    for(int j=0; j< headers.length; j++) {
	      if(headers[j].equals(aId_campos[i])) {
	        headers[j] = Integer.toString(i);//buscarCampo.getEtiqueta();
	        break;
	      }
	    }
            //ordenar cabezeras    
            for (int j=0;j<headers.length; j++) {
              for(int k=j;k<headers.length; k++) { 
                try {
                if(Integer.parseInt(headers[j])<Integer.parseInt(headers[k])){
                  String sApoyo=headers[j];
                  headers[j]=headers[k];
                  headers[k]=sApoyo;
                }
                }catch (Exception ee){}
              }
            }
	  }
          
          //PARA LOS SUBTOTALES DE LAS SUMAS EN EL JSP
	  if(suma_st != null) {
	    for(int j=0; j< suma_st.length; j++) {
	      if(suma_st[j].equals(aId_campos[i])) {
	        suma_st[j] = Integer.toString(i);//buscarCampo.getEtiqueta();
	        break;
	      }
	    }    
	  }
    //HASTA AQUI
         Dibwayka oCampo = new Dibwayka();
         oTabla.setId_campo(Integer.parseInt(aId_campos[i]));
         oCampo = this.mi.getBuscarCampoDibWK(oTabla);
         if (sCampos_sql.equals("")){
         sCampos_sql=oCampo.getCampo();
         sEtiquetas_sql=oCampo.getEtiqueta();
	 sCampos_order_sql = " ORDER BY " + oCampo.getCampo();
	 sCampos_group_sql = " GROUP BY " + oCampo.getCampo();
         } else {
         sCampos_sql+="||'#~~#'||" + oCampo.getCampo();
         sEtiquetas_sql+="@@" + oCampo.getEtiqueta();
	 sCampos_order_sql+=", " + oCampo.getCampo();
	 sCampos_group_sql+=", " + oCampo.getCampo();
         }
      }


      sSql = "SELECT (" + sCampos_sql + ")::text as valores From " + oTabla.getTabla() + " ";
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

    String valor[]=sEtiquetas_sql.split("@@");
    
    String aIzq_der[] = new String[valor.length];
    for (int i=0; i<valor.length;i++) {
      aIzq_der[i] = "0"; 
    }

    String aCant_dec[] = new String[valor.length];
    for (int i=0; i<valor.length;i++) {
      aCant_dec[i] = "0"; 
    }

    if(headers != null)
    modelo.put("headers", headers);
    
    if(suma_st != null)
    modelo.put("suma_st", suma_st);


    sSql += " " + sCondiciones_sql;
    
    String sContar_reg = request.getParameter("contar_reg");
    if(sContar_reg==null)
      sSql += " " + sCampos_order_sql;
    else
      sSql += " " +sCampos_group_sql + " " + sCampos_order_sql;

    Abm abm = new Abm();
    abm.setSql(sSql);
    System.out.println("**** " + abm.getSql());
    try {
      List valores = this.mi.getEjecutarListado(abm);
      datos = new String[valores.size()][valor.length];
       for (int i=0; i<valores.size(); i++) { 
         abm = (Abm)valores.get(i);
         String valor1[]= abm.getValores().split("#~~#");
         for (int j=0; j<valor1.length;j++) {
           datos[i][j] = valor1[j];
           for (int k=0; k<aIzq_der.length;k++) {	   
	     if (k==j){
	       try {
	         //aIzq_der[k] =String.valueOf(Float.valueOf(aIzq_der[k]).floatValue() + Float.valueOf(valor1[j]).floatValue());
	         float v = Float.valueOf(valor1[j]).floatValue();
	         aIzq_der[k] = String.valueOf(Integer.parseInt(aIzq_der[k]) + 1);
	     } catch (Exception e1) {	       
	     }
	     }
           }
	   
         }
       }
       
       for(int k=0;k<aIzq_der.length;k++)
         if (!aIzq_der[k].equals("0")) {
           String sDato=datos[0][k];
           String aDato[]=sDato.split("\\.");
           try{aCant_dec[k]=Integer.toString(aDato[1].length());}catch(Exception ep){}
         }

       modelo.put("cant_dec", aCant_dec);
       modelo.put("izq_der", aIzq_der);	 
       modelo.put("valores", valores);      
       modelo.put("datos",datos);
       modelo.put("valor",valor);
    
     } catch(Exception e) {
       System.out.println("ERROR: " + e.getMessage());
       return new ModelAndView("Aviso", "mensaje","no hay datos definidos datos para Listar");
     }

    
    String tipo_texto[] = request.getParameterValues("tipo_texto");
    
    String boton = request.getParameter("op");
    
    if ("PDF".equals(boton)) { 
      
      String a = "esto es una valor que se manda de otra clase";
      ListarConsultaPDF objeto = new ListarConsultaPDF();
      //objeto.a = a;
      return new ModelAndView(objeto, modelo);
    } else
      if ("EXCEL".equals(boton)) {
        String a = "hola esto es prueba";
        ListarConsultaExcel objeto = new ListarConsultaExcel();
        objeto.a = a;
      
        return new ModelAndView(objeto, modelo);
      }
    else
      return new ModelAndView("waykaDibRep/ListarConsulta", modelo);
  }  
}
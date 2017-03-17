/****************************************
 @usuario          :: Dali Aparicio
 @fec_registro     :: 17.10.2005
 @ult_usuario      :: Dali Aparicio
 @fec_modificacion :: 17.10.2005
*****************************************/
package org.fautapo.web.waykaDibRep;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ModificarDibRep implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Abm aux = new Abm();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String _nombres = cliente.getNombres();
    List tablas_dibrep = new ArrayList();
    String t="";
    int id_consulta = Integer.parseInt(request.getParameter("c"));
    Abm abm = new Abm();
    abm.setId_consulta(id_consulta);
    Abm buscarConsulta = this.mi.getBuscarConsulta(abm);

    
    String consulta = buscarConsulta.getConsulta();

    String[] sEtiquetas =buscarConsulta.getEtiquetas().split(":");
    String[] sCabezas =buscarConsulta.getCabezas().split(":");
    String[] sSumas =buscarConsulta.getSumas().split(":");

    List listadoTablas = this.mi.getListarTablas();
    modelo.put("listadoTablas",listadoTablas);

    int a = consulta.indexOf("SELECT (");
    a+=8;
    int b = consulta.indexOf(")::text as valores");
    
   
    //encuentra CAMPOS
    String sCampos = consulta.substring(a,b);
    String[] aCampos = sCampos.split("\\|\\|'#~~#'\\|\\|");
    String[][] aCampos1 = new String[aCampos.length][4]; 
    
    for (int i=0; i<aCampos.length;i++) {
      if((i==0)&&(aCampos[i].equals("count(*)"))) {
        aCampos1[i][0] = "";
        aCampos1[i][1] = aCampos[i];
	aCampos1[i][2] = "a";
	aCampos1[i][3] = Integer.toString(i);
      } else {
        try {
          aCampos1[i][0] = aCampos[i].split("\\.")[0];
          aCampos1[i][1] = aCampos[i].split("\\.")[1];
        } catch (Exception ex) {
          aCampos1[i][0] = "";
          aCampos1[i][1] = aCampos[i];	
	}
	System.out.println("alias[" + i + "][0]=" + aCampos1[i][0] +"         campo=" + aCampos1[i][1]);
        aCampos1[i][3] = Integer.toString(i);
      }
    }
    

    //encuentra TABLAS
    a = consulta.indexOf("FROM");
    a+=4;
    b = consulta.indexOf(" WHERE ");
    if (b==-1)
      b = consulta.indexOf(" ### ");
    if (b==-1)
      b = consulta.indexOf(" GROUP ");
    if (b==-1)
      b = consulta.indexOf(" ORDER ");

    String sTablas = consulta.substring(a,b);
    
    String[] aTablas = sTablas.split(",");
    String[][] aTablas1 = new String[aTablas.length][3];
    for (int i=0; i<aTablas.length;i++) {
      System.out.println("tablas='" + aTablas[i] + "'");
      aTablas1[i][0] = aTablas[i].split(" ")[1]; 
      aTablas1[i][1] = aTablas[i].split(" ")[2];
      Abm tabla = new Abm();
      tabla.setTabla(aTablas1[i][0]);
      tabla = this.mi.getBuscarTabla1(tabla);
      if(t.equals(""))
        t = Integer.toString(tabla.getId_tabla());
      else
        t =t+ ":" + Integer.toString(tabla.getId_tabla());
      tabla.setAlias(aTablas1[i][1]);
      tablas_dibrep.add(tabla);     
      System.out.println("nombre=" + aTablas1[i][0] + " alias=" + aTablas1[i][1]);
    }
    modelo.put("t",t);

    //marca los campos seleccionados
    List campos_marcados = new ArrayList();
    for (int i=0; i<aCampos1.length; i++) {
      for(int j=0; j<aTablas1.length; j++){
        if(aCampos1[i][0].equals(aTablas1[j][1])){
          Abm campo_marcado = new Abm();
	  campo_marcado.setTabla(aTablas1[j][0]);
	  campo_marcado.setCampo(aCampos1[i][1]);
	  aCampos1[i][2]="c";
	  campo_marcado = this.mi.getBuscarCampoTabla1(campo_marcado);
	  campo_marcado.setAlias(aCampos1[i][0]);
          campo_marcado.setOrden(Integer.parseInt(aCampos1[i][3]));	  
	  campos_marcados.add(campo_marcado);
	  break;
	} else {
	  if(aCampos1[i][2] ==null)
	    aCampos1[i][2]="n";
        }
      }
    }

    modelo.put("campos_marcados", campos_marcados);


    //marca los SUM(campos) seleccionados
    List campos_sumas_marcados = new ArrayList();
    for (int i=0; i<aCampos1.length; i++) {
      if(aCampos1[i][2].equals("n")) {
        a=-1;
	String sUnir = aCampos1[i][0] + "." + aCampos1[i][1];
	a = sUnir.indexOf("sum(");
        if (a!=-1)
	  a+=4;
        b = sUnir.indexOf(")");
	if ((a!=-1)&&(b!=-1)) {
	  System.out.println("a=" + a + " b=" + b);
	  try {
	    System.out.println("unir=" + sUnir);	    
	    sUnir = sUnir.substring(a,b); 
	    String[] aUnir=sUnir.split("\\.");
	    System.out.println("aUnir[0]=" + aUnir[0]);
	    System.out.println("aUnir[1]=" + aUnir[1]);	    	    
            aCampos1[i][0] = aUnir[0];
	    aCampos1[i][1] = aUnir[1];
            for(int j=0; j<aTablas1.length; j++) {
              if(aCampos1[i][0].equals(aTablas1[j][1])) {
                Abm campo_marcado = new Abm();
	        campo_marcado.setTabla(aTablas1[j][0]);
	        campo_marcado.setCampo(aCampos1[i][1]);
	        aCampos1[i][2]="s";
	        campo_marcado = this.mi.getBuscarCampoTabla1(campo_marcado);
	        campo_marcado.setAlias(aCampos1[i][0]);
	        campo_marcado.setOrden(Integer.parseInt(aCampos1[i][3]));
	        campos_sumas_marcados.add(campo_marcado);
	        break;
	      }
	    }
	  } catch (Exception ex) {}
	}
      }
    }
    modelo.put("campos_sumas_marcados", campos_sumas_marcados);


    for (int i=0; i<aCampos1.length; i++)
    System.out.println("campo["+i +"]="+ aCampos1[i][0] + "$" + aCampos1[i][1] + "$" + aCampos1[i][2] + "$" + aCampos1[i][3]);

    //carga los campos calculado de una lista
    String sCamposCalculados=""; 
    String sCamposCalculadosEtiquetas="";
    for (int i=0; i<aCampos1.length; i++) {
      if(aCampos1[i][2].equals("n")) {
        if(sCamposCalculados.equals("")){
          sCamposCalculados = aCampos1[i][1];
	  sCamposCalculadosEtiquetas = sEtiquetas[Integer.parseInt(aCampos1[i][3])]; 
        } else {
	  sCamposCalculados = sCamposCalculados + "||'#~~#'||" + aCampos1[i][1];
	  sCamposCalculadosEtiquetas = sCamposCalculadosEtiquetas + ":" + sEtiquetas[Integer.parseInt(aCampos1[i][3])]; 
	}
      }
    }
    
    modelo.put("campos_calculados", sCamposCalculados);
    modelo.put("campos_calculados_etiquetas", sCamposCalculadosEtiquetas);    
    
    //carga los campos cabezas marcados
    if (!sCabezas[0].equals("")){
      for(int i=0; i<sCabezas.length; i++) {
        if ((aCampos1[Integer.parseInt(sCabezas[i])][2].equals("s"))||(aCampos1[Integer.parseInt(sCabezas[i])][2].equals("c"))){
	  for(int j=0; j<aTablas1.length; j++) {
	    if (aTablas1[j][1].equals(aCampos1[Integer.parseInt(sCabezas[i])][0])){
	      Abm campo = new Abm();    
	      campo.setTabla(aTablas1[j][0]);
	      campo.setCampo(aCampos1[Integer.parseInt(sCabezas[i])][1]);
	      System.out.println("tabla=" + aTablas1[j][0]);
	      System.out.println("campo=" + aCampos1[Integer.parseInt(sCabezas[i])][1]);
	      campo = this.mi.getBuscarCampoTabla1(campo);
	      System.out.println("id_campo=" + campo.getId_campo());
              sCabezas[i] = aCampos1[Integer.parseInt(sCabezas[i])][0] + "_" + Integer.toString(campo.getId_campo());  
	      break;
	    }
	  }

	}
      }      
    }
    modelo.put("lista_cabezas", sCabezas);


    //CARGAR LOS CAMPOS SUMA_ST MARCADOS
    if (!sSumas[0].equals("")){
      for(int i=0; i<sSumas.length; i++) {
        if ((aCampos1[Integer.parseInt(sSumas[i])][2].equals("s"))||(aCampos1[Integer.parseInt(sSumas[i])][2].equals("c"))){
	  for(int j=0; j<aTablas1.length; j++) {
	    if (aTablas1[j][1].equals(aCampos1[Integer.parseInt(sSumas[i])][0])){
	      Abm campo = new Abm();    
	      campo.setTabla(aTablas1[j][0]);
	      campo.setCampo(aCampos1[Integer.parseInt(sSumas[i])][1]);
	      System.out.println("tabla=" + aTablas1[j][0]);
	      System.out.println("campo=" + aCampos1[Integer.parseInt(sSumas[i])][1]);
	      campo = this.mi.getBuscarCampoTabla1(campo);
	      System.out.println("id_campo=" + campo.getId_campo());
              sSumas[i] = aCampos1[Integer.parseInt(sSumas[i])][0] + "_" + Integer.toString(campo.getId_campo());  
	      break;
	    }
	  }

	}
      }      
    }

    modelo.put("lista_sumas_st", sSumas);


    //CAMPOS VARIABLES MARCADOS
    List listarCamposVariables = this.mi.getListarCamposCondicion(abm);
    modelo.put("campos_variables_marcados",listarCamposVariables);
    
    String sCount ="";
    if (aCampos1[0][2].equals("a")) {
       sCount="0";
    }
    modelo.put("count",sCount);

    
    
    //CAMPOS CONDICIONES
    a = -1;
    b= -1;
    a = consulta.indexOf("WHERE ");
    if (a !=-1)
      a += 6;
    b = consulta.indexOf(" ## ");
    if (b==-1)
      b = consulta.indexOf(" GROUP ");
    if (b==-1)
      b = consulta.indexOf(" ORDER ");

    
    List campos_condiciones_marcados = new ArrayList();

    if ((b!=-1)&&(a!=-1)){
      String sCondiciones = consulta.substring(a,b);
      System.out.println("espacios a modificar='" + sCondiciones +"'");
      String[] aCondiciones = sCondiciones.split(" AND ");
      for (int i = 0; i< aCondiciones.length; i++) {
        System.out.println("condicion=\"" +  aCondiciones[i] +"\"");
        String[] aValor = aCondiciones[i].split(" ");
        if (aValor.length==3){
          Abm campo = new Abm();
	  String[] aValor1=aValor[0].split("\\."); 
          for(int j=0; j<aTablas1.length; j++) {
            if(aValor1[0].equals(aTablas1[j][1])) {
	        campo.setTabla(aTablas1[j][0]);
	        campo.setCampo(aValor1[1]);
	        campo = this.mi.getBuscarCampoTabla1(campo);
		campo.setCampo(aValor1[0] +"_" + campo.getId_campo());
	        campos_condiciones_marcados.add(campo);
	        break;
	      }
          }
          //campo.setCampo(aValor[0].replace(".","_"));
          campo.setCondicion(aValor[1]);
	  campo.setValores(aValor[2].split("'")[1]);
          System.out.println("$"+campo.getCampo()+"$"+campo.getCondicion()+"$"+campo.getValores());
        }
      }
    }
    modelo.put("campos_condiciones_marcados",campos_condiciones_marcados);

    
    if (tablas_dibrep != null) {    
      if (tablas_dibrep.size() != 0) {
        for (int i = 0; i< tablas_dibrep.size(); i++) {
	  abm = new Abm();
          abm = (Abm) tablas_dibrep.get(i);
          abm.setPermiso("l");
	  List listaCampos = this.mi.getListarCamposTabla(abm);
	  for (int j = 0; j< listaCampos.size(); j++) {
            aux = (Abm) listaCampos.get(j);
	    Abm buscarForaneo = this.mi.getBuscarForanea(aux);
	    aux.setAlias(aTablas1[i][1]);
	    if (buscarForaneo != null) {
	      buscarForaneo.setCampo(buscarForaneo.getId_campo_foraneo() + "#~~#" + buscarForaneo.getCampo_foraneo() + "#~~#''");
              buscarForaneo.setCondicion("");
	      aux.setCombo(this.mi.getListarCombos(buscarForaneo));
	      aux.setId_campo_foraneo(buscarForaneo.getId_campo_foraneo());
	    }
	    listaCampos.set(j, aux);
	  }
	  abm.setCampos_dibrep(listaCampos);
          tablas_dibrep.set(i, abm);
        }
      }
    }
    
    modelo.put("tablas_dibrep", tablas_dibrep);
    modelo.put("atablas",aTablas);
    

    
    //String sCampos[] = sSinSelect[1].split(")::text as valores");
    modelo.put("campos",sCampos);
    modelo.put("tablas",sTablas);    
    
    String titulo = buscarConsulta.getTitulo();
    titulo = titulo.replace("\r\n","");
    buscarConsulta.setTitulo(titulo);

    modelo.put("consulta", buscarConsulta);
    
    modelo.put("id_consulta",request.getParameter("c"));

    return new ModelAndView("dibRep/GeneradorModificar", modelo);
  }  
}
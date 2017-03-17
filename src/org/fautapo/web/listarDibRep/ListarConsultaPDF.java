package org.fautapo.web.listarDibRep;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


import java.util.Date;
import java.util.*;
import java.text.*;
import java.net.*;

import java.awt.Color;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;

//
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
//import org.fautapo.dibRep.*;
import com.lowagie.text.Image;

// definir el Pdf
public class ListarConsultaPDF extends AbstractPdfView {

  private static final Font TITULO_FONT = new Font( Font.HELVETICA, 12, Font.BOLD, Color.black );
  private static final Font CABEZA_COLUMNA_FONT = new Font( Font.HELVETICA, 8, Font.ITALIC, Color.black );
  private static final Font SUMA_COLUMNA_FONT = new Font( Font.HELVETICA, 8, Font.ITALIC, Color.gray);
  private static final Font DATO_FONT = new Font( Font.HELVETICA, 8, Font.ITALIC );
  public String a;
   
// private static final Font DATA_HEAD_FONT = new Font( Font.HELVETICA, 10, Font.ITALIC, Color.black );

  private static final int MARGIN = 32;


  public Image headerImage;
  /** The headertable. */
  public PdfPTable table1;
  public PdfPTable table;
  public List lDatos;
  public List lSubSumas;

  protected void buildPdfDocument(Map model,Document document,PdfWriter writer,HttpServletRequest req,HttpServletResponse resp) throws Exception {
    lDatos=new ArrayList();
    contenidoReporte(document,model);
    String sPaginacion = (String)model.get("paginacion");
    int paginado = Integer.parseInt(sPaginacion);
    int numeroPaginado = lDatos.size()/paginado;
    int fin =0;
    for (int i = 0; i < (numeroPaginado + 1); i++) {
      if ((fin+paginado)<lDatos.size()) 
      fin=fin + paginado;
      else 
      fin=lDatos.size();
      document.newPage();
      cabezeraSuperior(document,writer,model);
      llenarPdf(document, model,paginado*i,fin);
    }  
  }

  void cabezeraSuperior(Document document,PdfWriter writer,Map model) {

         // initialization of the header table
          String sDescripcion = (String)model.get("descripcion"); 
          String sRuta = (String)model.get("ruta"); 

          table1 = new PdfPTable(5);
          table1.getDefaultCell().setBackgroundColor(Color.white);
          table1.getDefaultCell().setBorderWidth(0);
          table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
          try {
            String sLogo = (String)model.get("logo"); 
            headerImage = Image.getInstance(new URL("http://localhost:8080" + sRuta + sLogo));
            table1.addCell(new Phrase(new Chunk(headerImage, 0, 0)));
          } catch (Exception ee){
            table1.addCell("");
          }

          Phrase p = new Phrase();
          Chunk ck;
          try {
            String sInstitucion = (String)model.get("institucion"); 
            ck = new Chunk(sInstitucion + "\n\n\n", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.BLACK));
          } catch (Exception ee){
            ck = new Chunk("               \n\n\n", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.BLACK));
          }
          p.add(ck);
          ck = new Chunk(sDescripcion, new Font(Font.HELVETICA, 16, Font.NORMAL, Color.BLACK));
          p.add(ck);
          table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
          table1.getDefaultCell().setColspan(3);
          table1.addCell(p);            
          Date fecha = new Date();
          SimpleDateFormat formateo = new SimpleDateFormat("dd/MM/yyyy");
          fecha.toString();    
          p = new Phrase();
          ck = new Chunk("Pagina " + writer.getPageNumber() + " \n", new Font(Font.TIMES_ROMAN, 8, Font.BOLDITALIC, Color.BLACK));
          p.add(ck);
          ck = new Chunk(formateo.format(fecha), new Font(Font.TIMES_ROMAN, 8, Font.BOLDITALIC, Color.BLACK));
          p.add(ck);
          table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
          table1.addCell(p);
          table1.setWidthPercentage(100);
          try {
            document.add(table1);
            document.add(new Paragraph(" "));
          } catch(Exception e) {
            throw new ExceptionConverter(e);
          }
  }    

  public void llenarPdf(Document document,Map model, int desde, int hasta) {
    String valor[] = (String[])model.get("valor");
    String aIzq_der[] =(String[])model.get("izq_der");
    cabezeraReporte(model);
    for (int i = desde; i < hasta; i++) {
      Abm tupla = new Abm();
      tupla = (Abm)lDatos.get(i);
      for (int j = 0; j < valor.length; j++) {
        PdfPCell cell = new PdfPCell(new Phrase(tupla.getTuplaDatos()[j],DATO_FONT));
        if (tupla.getSumas().equals("1"))
          cell.setGrayFill(0.8f);
        if (tupla.getSumas().equals("2"))
          cell.setGrayFill(0.9f);
        if (aIzq_der[j].equals("0"))
          cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        else
          cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
      }
    }
    try {
      document.add(table);
    } catch(Exception e) {
      throw new ExceptionConverter(e);
    }
  }

  public void cabezeraReporte(Map model) {
    String valor[] = (String[])model.get("valor");
    table = new PdfPTable(valor.length);
    table.setWidthPercentage(100);
    for (int i = 0; i < valor.length; i++) {
      PdfPCell cell = new PdfPCell(new Phrase(valor[i],CABEZA_COLUMNA_FONT));
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell.setGrayFill(0.7f);
      table.addCell(cell);
    }  
  } 


  public void contenidoReporte(Document document, Map model) {
    String valor[] = (String[])model.get("valor");
    String datos[][] = (String[][])model.get("datos");
    int desc = valor.length-1;
    
    //CABEZAS DENTRO DEL REPORTE
    int cabeza = -1;     
    int cabeza1 = -1;     
    String headers[] = (String[])model.get("headers");
    try {
      cabeza1 = Integer.parseInt(headers[0]);
    } catch(Exception ex){}
    
   //SUBSUMAS DENTRO DEL REPORTE
    int suma = -1;   
    int suma1 = -1;     
    String suma_st[] = (String[])model.get("suma_st");
    try {
      suma1 = Integer.parseInt(suma_st[0]);
    } catch(Exception ex){}
    

    String[][][] subTotal = this.suma_subtotal(model);
    for (int i = 0; i < datos.length; i++) {
      String[] datos1 = new String[valor.length];
      lSubSumas = new ArrayList(); 
      for (int j = 0; j < datos[i].length; j++) {
        //encontrar cabeza
        if (cabeza1 != -1) {
	  for (int k=0; k<headers.length; k++) {
	    int cabeza2 = Integer.parseInt(headers[k]);
	    if (cabeza2==j) {
	      cabeza =j;
	      break;
	    }
	  }
	}
        boolean tt = false;
        if (suma1 != -1) {
	  for (int k=0; k<suma_st.length; k++) {
	    int suma2 = Integer.parseInt(suma_st[k]);
	    if (suma2==j) {
	      tt = true;
	    }
	  }
	}

        //PONER EMCABEZADOS
	if(i==0) {
          datos1[j]=datos[i][j];
	} else {
          try {
	    if (!datos[i][j].equals(datos[i-1][j])) {
              datos1[j]=datos[i][j];
	    } else {
	      if(cabeza==j){
                datos1[j]="";
	      } else {
                datos1[j]=datos[i][j];
	      }
	    }
          } catch (Exception ee){
            datos1[j]="";
          }
	}

        if(((j==(valor.length-1))&&(suma1!=-1))&&(cabeza1!=-1)) {
	  for (int k2=headers.length;k2>0; k2--) {
	    cabeza=-1;
            String[] datos2 = new String[valor.length];
	    for (int k1=0;k1<valor.length; k1++) {
	      int cabeza2 = Integer.parseInt(headers[k2-1]);
	      if (cabeza2==k1) {
	        cabeza =k1;
	        break;
	      }
	    }
	    if (cabeza!=-1) {
	      try {
	        if(!datos[i][cabeza].equals(datos[i+1][cabeza])) {
                    for (int k3=0 ; k3<valor.length; k3++) {
                    datos2[k3] = subTotal[i][cabeza][k3];
		  }
                }
              } catch (Exception ex) {
	        for (int k3=0 ; k3<valor.length; k3++) {
                  datos2[k3] = subTotal[i][cabeza][k3];
                }
	      }
              if(datos2[0]!=null){
                Abm tupla = new Abm();
                tupla.setTuplaDatos(datos2); 
                lSubSumas.add(tupla);
              }    
	    }
          }	 
        } 
      }//columnas
      Abm tupla = new Abm();
      tupla.setTuplaDatos(datos1); 
      tupla.setSumas("0");
      lDatos.add(tupla);
      try {
         Abm tupla1 = (Abm)lSubSumas.get(0);
         String sValor_prueba=tupla1.getTuplaDatos()[0];
         if (sValor_prueba!=null) {
	  for (int kk=lSubSumas.size();kk>0 ;kk--) {
             tupla1 = (Abm)lSubSumas.get(kk-1);
             tupla1.setSumas("1");
             lDatos.add(tupla1);
           }
         }
       } catch (Exception ex){}
    }//filas
    sumaTotal(model);
  }

 public void sumaTotal(Map model) {
    String valor[] = (String[])model.get("valor");
    String aCant_dec[] = (String[])model.get("cant_dec");
    String etiquetas_total[] = (String[])model.get("etiquetas_total");
    String valor_total[] = (String[])model.get("valor_total");
    String[] datos1=new String[valor.length];

    int suma1 = -1;     
    String suma_st[] = (String[])model.get("suma_st");
    try {
      suma1 = Integer.parseInt(suma_st[0]);
    } catch(Exception ex){}

    if((suma_st!=null)||(etiquetas_total != null)) {
      for (int i = 0; i < valor.length; i++) {
         if (i==0) {
           datos1[i]="TOTALES";
	 } else {
           datos1[i]="";
	 }
      }
    Abm tupla = new Abm();
    tupla.setTuplaDatos(datos1);
    tupla.setSumas("0"); 
    lDatos.add(tupla);

      datos1=new String[valor.length];      
      if(etiquetas_total != null)
        for (int i = 0; i < valor.length; i++) {
          boolean t =true;
          for (int j = 0; j< etiquetas_total.length; j++) {
            if(etiquetas_total[j].equals(valor[i])) {
              datos1[i]=valor_total[j];
	      t =false;
	    }
	  }     
	  if(t){
            datos1[i]="";
	  }
	} 
	if (suma1!=-1) {
	  String[] sumasStTotal = this.suma_total(model);
          for (int i = 0; i < sumasStTotal.length; i++) {
             datos1[i]=sumasStTotal[i];
	  }
	}
        tupla = new Abm();
        tupla.setTuplaDatos(datos1); 
        tupla.setSumas("2");
        lDatos.add(tupla);
    }
 }

  public String[][][] suma_subtotal(Map model) {
    String datos[][] = (String[][])model.get("datos");
    String valor[] = (String[])model.get("valor");
    String aCant_dec[] = (String[])model.get("cant_dec");
    String cabezas[] = (String[])model.get("headers");
    String sumas[] = (String[])model.get("suma_st");    
    int fila = datos.length;
    String[][][] resultado = new String[datos.length][valor.length][valor.length];    
    try {
      int prueba1 = Integer.parseInt(cabezas[0]);
      int prueba2 = Integer.parseInt(sumas[0]);    
    
      for (int i=0;i<datos.length; i++) {
        for (int j=0;j<valor.length; j++) {
          for (int k=0;k<valor.length; k++) {
            resultado[i][j][k]="";
          }
        }
      }

      for (int i=0;i<cabezas.length; i++){
        int cabezera = Integer.parseInt(cabezas[i]);
        int num_fila=0; 
        while (num_fila < fila) {
          int cabeza = num_fila;
          for (int k=0;k< sumas.length; k++) {
            num_fila = cabeza;
            float suma = 0;
            boolean t = true;
            while((num_fila<fila)&&(t)) {
              if(datos[cabeza][cabezera].equals(datos[num_fila][cabezera])) {
	        int suma1 = Integer.parseInt(sumas[k]);
                suma = suma + Float.valueOf(datos[num_fila][suma1]).floatValue();
	        num_fila++;
	      } else {
	        t=false;
	      }
            }
	    int sum = Integer.parseInt(sumas[k]);
	    //resultado[num_fila-1][cabezera][cabezera]="SubTotal " + datos[num_fila-1][cabezera];
	    resultado[num_fila-1][cabezera][cabezera]="SubTotal ";
	    resultado[num_fila-1][cabezera][sum]+=redondear(String.valueOf(suma), aCant_dec[sum]);
	    int numcito = num_fila-1;
	  }
        }
      }
    } catch (Exception ex){}    
    return resultado;
  }

  public String[] suma_total(Map model) {
    String datos[][] = (String[][])model.get("datos");
    String aCant_dec[] = (String[])model.get("cant_dec");
    int fila = datos.length;    
    String resultado1[] = new String[datos[0].length];
    for (int i=0;i<resultado1.length;i++)
    resultado1[i]="";
    String sumas[] = (String[])model.get("suma_st");
    for (int k=0;k<sumas.length; k++){
      int inicioFila1=0;
      float suma1=0;
      int sum =Integer.parseInt(sumas[k]);
      while(inicioFila1<fila) {
        suma1 = suma1 + Float.valueOf(datos[inicioFila1][sum]).floatValue();
        inicioFila1++;
      }
      resultado1[sum] = redondear(String.valueOf(suma1),aCant_dec[sum]);
   }
   return resultado1;
 }
  
 public String redondear(String sCantidad,String sDecimales) {
   double dCantidad=Double.valueOf(sCantidad).doubleValue();
   double dDecimales=Double.valueOf(sDecimales).doubleValue();
   double dValor=Math.round(dCantidad * Math.pow(10,dDecimales))/Math.pow(10,dDecimales);
   String sResultado = "" + dValor;
   String aResultado[]=sResultado.split("\\.");
   if (sDecimales.equals("0")){
     sResultado =aResultado[0];
   } else {
     String sApoyo =aResultado[1]; 
     while (sApoyo.length()<Integer.parseInt(sDecimales)){
       sApoyo +="0";
       sResultado = aResultado[0] + "." + sApoyo;
     }
   }
   return sResultado;
 }

}
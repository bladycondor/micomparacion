package org.fautapo.web.reportesTramites.imprimirReporteTramites;

import java.awt.Color;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Tramites;
import java.util.*;
import java.text.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.rtf.*;

public class ListarConsultaPDF extends AbstractPdfView {

  private static final Font TITULO_FONT = new Font( Font.HELVETICA, 12, Font.BOLD, Color.black );
  private static final Font CABEZA_COLUMNA_FONT = new Font( Font.HELVETICA, 8, Font.ITALIC, Color.black );
  private static final Font DATO_FONT = new Font( Font.HELVETICA, 8, Font.NORMAL, Color.black );
  private static final Font DATO_FONT_BOLD = new Font( Font.HELVETICA, 8, Font.BOLD, Color.black );
  private static final int MARGIN = 32;

     protected void buildPdfMetadata(Map model, Document document, HttpServletRequest request) {
       document.setPageSize(PageSize.LEGAL.rotate());
     }


  protected void buildPdfDocument(Map model, Document document, PdfWriter writer, HttpServletRequest req, HttpServletResponse resp) throws Exception {

     java.util.List lTramites = (java.util.List)model.get("lTramites");
     String sTitulo = (String)model.get("sTitulo");
     String sCampos [] = {"Nro HOJA\nDE RUTA", "REFERENCIAS", "ACTIVIDAD", "FECHA", "FIRMA", "FIRMA", "FIRMA"};
     if (sTitulo == null) sTitulo = "Listado del proceso de negocio";
     PdfPCell cell;

     float[] fWidths = {0.05f, 0.30f, 0.25f, 0.09f, 0.11f, 0.11f, 0.11f};
     PdfPTable table = new PdfPTable(fWidths);
     table.setWidthPercentage(100);
     cell = new PdfPCell(new Paragraph(sTitulo));
     cell.setColspan(7);
     table.addCell(cell);
     
     for (int i = 0; i < sCampos.length; i++) {
       cell = new PdfPCell(new Phrase(sCampos[i], CABEZA_COLUMNA_FONT));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       cell.setGrayFill(0.8f);
       table.addCell(cell);
     }  
     table.setHeaderRows(2);     
     for (int i = 0; i < lTramites.size(); i++) {
       String s = "";
       Tramites tramite = (Tramites) lTramites.get(i);	 
       cell = new PdfPCell(new Phrase(Integer.toString(tramite.getId_tramite()), DATO_FONT));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       table.addCell(cell);
       Paragraph p = new Paragraph();
       for (int j = 0; j < tramite.getLista().size(); j++) {
         Tramites tramite1 = (Tramites) tramite.getLista().get(j);	 
         p.add(new Phrase(tramite1.getCampo() + ":", DATO_FONT_BOLD));
         p.add(new Phrase(tramite1.getValor() + "\n", DATO_FONT));
       }
       cell = new PdfPCell(p);
       cell.setHorizontalAlignment(Element.ALIGN_LEFT);
       table.addCell(cell);
       cell = new PdfPCell(new Phrase(tramite.getActividad() + "\n" + tramite.getProceso(), DATO_FONT));
       cell.setHorizontalAlignment(Element.ALIGN_LEFT);
       table.addCell(cell);
       if (tramite.getFec_registro() != null) s = "" + tramite.getFec_registro(); else s = "";
       cell = new PdfPCell(new Phrase(s, DATO_FONT));
       cell.setHorizontalAlignment(Element.ALIGN_LEFT);
       table.addCell(cell);               	  
       cell = new PdfPCell(new Phrase(" ", DATO_FONT));
       table.addCell(cell);               	  
       cell = new PdfPCell(new Phrase(" ", DATO_FONT));
       table.addCell(cell);               	  
       cell = new PdfPCell(new Phrase(" ", DATO_FONT));
       table.addCell(cell);               	  
     }
     if (lTramites.size() == 0) {
       cell = new PdfPCell(new Paragraph(" "));
       cell.setColspan(7);
       table.addCell(cell);
     }
     document.add(table); 
  }
}
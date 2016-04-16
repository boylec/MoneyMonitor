package Core.Services;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import Reporting.Models.HtmlDocument;

public class ReportService implements IReportService {

	@Override
	public void ExportHtmlToPdfFile(HtmlDocument htmlDoc, String fileOutputPath) throws Exception {
		// step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileOutputPath));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, htmlDoc.GetHtmlStream()); 
        //step 5
         document.close();
 
        System.out.println( "PDF Created: " + fileOutputPath );
	}
	
	public void ExportHtmlToHtmlFile(HtmlDocument htmlDoc, String fileOutputPath) throws Exception {
        FileWriter writer = new FileWriter(fileOutputPath);
        String html = htmlDoc.GetHtml();
        writer.write(html); 
        writer.close();
        System.out.println( "PDF Created: " + fileOutputPath );
	}
}

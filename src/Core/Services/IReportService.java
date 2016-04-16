package Core.Services;

import java.io.IOException;
import com.itextpdf.text.DocumentException;
import Reporting.Models.HtmlDocument;

public interface IReportService {
	public void ExportHtmlToPdfFile(HtmlDocument htmlString,String fileOutputPath) throws IOException, DocumentException, Exception ;
	//public boolean ExportHtmlToPdfView(String htmlString) throws FileNotFoundException, DocumentException ;
}

package spark.model.bridge;

import java.io.IOException;

import org.hibernate.search.bridge.StringBridge;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;

import spark.Constant;

public class PdfBridge implements StringBridge {

	@Override
	public String objectToString(Object object) {
		String fileName = object.toString();
		String filePath = Constant.STORAGE_DOCUMENT_FOLDER + fileName +".pdf";
		String content = "";
		
		try {
			PdfReader pdfReader = new PdfReader(filePath);
			PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);
			
			for(int i = 1 ; i <= pdfReader.getNumberOfPages() ; i++) {
				content += pdfReaderContentParser.processContent(i, new SimpleTextExtractionStrategy()).getResultantText();
			}
			
			return content;
		}
		catch(IOException exception) {
			return "";
		}
	}

}

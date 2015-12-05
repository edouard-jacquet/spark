package spark.model.bridge;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.hibernate.search.bridge.StringBridge;

public class PdfBridge implements StringBridge {

	@Override
	public String objectToString(Object object) {
		String path = object.toString();
		
		PDFTextStripper pdfTextStripper = null;
		PDDocument pdDocument = null;
		COSDocument cosDocument = null;
		
		File pdf = new File(path);
		
		try {
			PDFParser pdfParser = new PDFParser(new FileInputStream(pdf));
			pdfParser.parse();
			cosDocument = pdfParser.getDocument();
			pdfTextStripper = new PDFTextStripper();
			pdDocument = new PDDocument(cosDocument);
			String content = pdfTextStripper.getText(pdDocument);
			return content;
		}
		catch(IOException exception) {
			return "";
		}
		finally {
			try {
				pdDocument.close();
			}
			catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

}

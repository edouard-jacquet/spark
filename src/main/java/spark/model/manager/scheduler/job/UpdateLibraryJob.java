package spark.model.manager.scheduler.job;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import spark.Constant;
import spark.model.bean.Source;
import spark.model.connection.Database;
import spark.model.dao.DocumentDAO;
import spark.model.dao.SourceDAO;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.filter.*;

public class UpdateLibraryJob implements Job {
	
    public UpdateLibraryJob() {
    }
    
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        try {
            //Thread.sleep(1000);
        	sync();
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }

	public static void execute() {
		// TODO Auto-generated method stub

			try {
				sync();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	private static void sync() throws NoSuchAlgorithmException{
		/*try {
			sync_aclweb();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		sync_arxiv();
		
	}
	
	private static void sync_aclweb() throws IOException{
		String[] lettres = {"J","Q","P","E","N","D","K","S","A","W","C","H","I","L","Y","O","T"};
		SourceDAO source_dao = new SourceDAO();
		Source source_aclweb = source_dao.getByName("aclweb");
		DocumentDAO doc_dao =  new DocumentDAO();
		EntityManager em = Database.getInstance().getConnection();
		
		// TODO Auto-generated method stub
		for(int i=0; i< lettres.length; i++){			
			String num_ttl;
			
			for(Integer num=79; num < 116; num++){		
				Hashtable<String,spark.model.bean.Document> liste_doc = new Hashtable<String,spark.model.bean.Document>();
				String liste_attachement = "";
				
				if(num > 99 && num < 110){
					Integer annee = num-100;
					num_ttl = "0"+annee.toString();
				}else if(num >= 110){
					Integer annee = num-100;
					num_ttl = annee.toString();
				}else
					num_ttl = num.toString();
				System.out.println("https://aclweb.org/anthology/"+lettres[i]+"/"+lettres[i]+num_ttl+"/");
				try{
					
					URL url = new URL("https://aclweb.org/anthology/"+lettres[i]+"/"+lettres[i]+num_ttl+"/");
					URLConnection urlConnection = url.openConnection();
					InputStream inputStream = urlConnection.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));	    
			    
					String line ;
				    Pattern p1 = Pattern.compile("^(<p><a href=)");   
				    Pattern p2 = Pattern.compile("<i>(.*)</i>"); 
				    Pattern p3 = Pattern.compile("<p><a href=(.*).pdf>"); 
				    Pattern p3_bis = Pattern.compile("<p><a href=(.*).pdf\">"); 
				    Pattern p4 = Pattern.compile("<b>(.*)</b>"); 
				    
				    while ((line= bufferedReader.readLine()) != null) {
				    	Matcher m1 = p1.matcher(line);
				    	if(m1.find()){				    		
				    		Matcher m2 = p2.matcher(line);
				    		Matcher m3 = p3.matcher(line);
				    		Matcher m3_bis = p3_bis.matcher(line);
				    		Matcher m4 = p4.matcher(line);
				    		
				    		if(m2.find() && m3.find() && m4.find()){
				    		
				    			System.out.println("Auteurs : "+m4.group(1));
				    			System.out.println("Attachenement : "+encode(m3.group(1)));
				    			System.out.println("Title : "+m2.group(1).replace("<i>","").replace("</i>",""));
				    			System.out.println("Summary : "+m2.group(1).replace("<i>","").replace("</i>",""));
				    			System.out.println("doc id "+m3.group(1));
				    			

								 spark.model.bean.Document doc = new spark.model.bean.Document();
								   doc.setAttachment(encode(m3.group(1)));
								   doc.setDocumentRef(m3.group(1));
								  // doc.setTitle(m2.group(1).replace("<i>","").replace("</i>","").substring(0, 160));
								   doc.setSummary(m2.group(1).replace("<i>","").replace("</i>",""));
								   doc.setSource(source_aclweb);
								   
								  /* liste_doc.put(encode(m3.group(1)), doc);
								   liste_attachement += encode(m3.group(1))+",";*/

				    		}
				    	}
				    }
				    
				    if(!liste_attachement.equals("")){
				    	List<String> docs_non_present = doc_dao.document_non_presents(liste_attachement);
					
					    for(int k=0; k < docs_non_present.size(); k++){
							spark.model.bean.Document new_doc = liste_doc.get(docs_non_present.get(k));
							//doc_dao.create(new_doc);
							System.out.println("creation  "+new_doc.getAttachment());
							dowloadFile("https://aclweb.org/anthology/"+lettres[i]+"/"+lettres[i]+num_ttl+"/"+new_doc.getDocumentRef()+".pdf", new_doc.getAttachment());
						}
				    }

				}catch(FileNotFoundException e){
					
				}
			    
			}
		}
	}
	
	
	private static void sync_arxiv() throws NoSuchAlgorithmException{
		
		String[] categories = {"cs"};
		int nb_result;
		int start;
		MessageDigest md = MessageDigest.getInstance("MD5");
		SourceDAO source_dao = new SourceDAO();
		DocumentDAO doc_dao =  new DocumentDAO();
		Source source_arxiv = source_dao.getByName("arxiv");
		EntityManager em = Database.getInstance().getConnection();
		
		for(int i=0; i < categories.length; i++){
			nb_result = 1;		
			start = 0;
			
			while(nb_result != 0){
				URL url;
				String page="";
				org.jdom.Document document;
				String liste_attachement = "";
				Element racine;
				Hashtable<String,spark.model.bean.Document> liste_doc = new Hashtable<String,spark.model.bean.Document>();
				
				try {
					
					url = new URL("http://export.arxiv.org/api/query?search_query="+categories[i]+"&start="+start+"&max_results=100");
					URLConnection conn = url.openConnection();
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					System.out.println("req");

						
						SAXBuilder sxb = new SAXBuilder();
						Namespace ns = Namespace.getNamespace("http://www.w3.org/2005/Atom");
						try {
							document = sxb.build(br);
							racine = document.getRootElement();
							List list = racine.getChildren("entry", ns);
							
							for (int j = 0; j < list.size(); j++) {
								   Element node = (Element) list.get(j);
								   String title;
								   if(node.getChildText("title", ns).length() > 200)
									   title = node.getChildText("title", ns).substring(0, 200);
								   else
									   title = node.getChildText("title", ns);
								   
								   spark.model.bean.Document doc = new spark.model.bean.Document();
								   doc.setAttachment(encode(node.getChildText("id", ns)));
								   doc.setTitle(title);
								   doc.setSummary(node.getChildText("summary", ns));
								   doc.setDocumentRef(node.getChildText("id", ns));
								  // doc.setPublicationDate(new Date(node.getChildText("published", ns)));
								  //  doc.setUpdateDate(new Date(node.getChildText("updated", ns)));
								   doc.setSource(source_arxiv);
								   liste_doc.put(encode(node.getChildText("id", ns)), doc);
								  
								   liste_attachement += encode(node.getChildText("id", ns))+",";
								   
							}							
						} catch (JDOMException e) {
							e.printStackTrace();
						}
						
						List<String> docs_non_present = doc_dao.document_non_presents(liste_attachement);
						for(int k=0; k < docs_non_present.size(); k++){
							spark.model.bean.Document new_doc = liste_doc.get(docs_non_present.get(k));
							doc_dao.create(new_doc);
							System.out.println(new_doc.getDocumentRef()+".pdf");
							dowloadFile(new_doc.getDocumentRef().replace("http://arxiv.org/abs/", "http://arxiv.org/pdf/")+".pdf", new_doc.getAttachment());
						}

						
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				start += 100;
			}
		
		}
	}
	
	private static void dowloadFile(String string_url, String non_fichier) throws IOException{
		URL u = new URL(string_url);
		URLConnection uc = u.openConnection();
		String FileType = uc.getContentType();
		int FileLenght = uc.getContentLength();
			if (FileLenght == -1) {
				throw new IOException("Fichier non valide.");
			}
		InputStream in = uc.getInputStream();
		String FileName = u.getFile();
		FileName = FileName.substring(FileName.lastIndexOf('/') + 1);
		
		FileOutputStream WritenFile = new FileOutputStream(Constant.STORAGE_DOCUMENT_FOLDER+non_fichier+".pdf");
		byte[]buff = new byte[1024];
		int l = in.read(buff);
		while(l>0)
		{
		WritenFile.write(buff, 0, l);
		l = in.read(buff);
		}
		WritenFile.flush();
		WritenFile.close();
	}
	
    private static String encode(String password)
    {
        byte[] uniqueKey = password.getBytes();
        byte[] hash      = null;

        try
        {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Error("No MD5 support in this VM.");
        }

        StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1)
            {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            }
            else
                hashString.append(hex.substring(hex.length() - 2));
        }
        return hashString.toString();
    }
    
}

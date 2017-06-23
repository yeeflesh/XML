import javax.servlet.*; 
import javax.servlet.http.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;

//為了儲存XML檔之用
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;

public  class Display extends HttpServlet {
  protected void service(HttpServletRequest req, HttpServletResponse res ) {
    try {

        //1. 載入XML文件      
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      DocumentBuilder builder = factory.newDocumentBuilder();

      //取得XML檔案的正確路徑
      ServletContext context = getServletContext();
      String xml_path_file =  context.getRealPath("lala's_data.xml"); 
      Document doc = builder.parse(new File (xml_path_file) ); //剖析並載入XML文件

      //2. 將XML文件用XSL檔進行XML文件轉換
      res.setContentType("text/html;charset=UTF-8");
      PrintWriter out= res.getWriter();
      TransformerFactory tranFactory = TransformerFactory.newInstance();
      String xsl_path_file =  context.getRealPath("lala's_data.xsl"); 
      StreamSource  xsl = new StreamSource(xsl_path_file) ; 
      Transformer aTransformer = tranFactory.newTransformer(xsl); 
      aTransformer.transform(new DOMSource(doc), new StreamResult(out));

     }
     catch ( Exception e) {
       System.out.println("I go error here!");
       e.printStackTrace();
     }     
   }

  
}
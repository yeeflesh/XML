import javax.servlet.*; 
import javax.servlet.http.*;

import org.apache.xpath.XPathAPI;
import org.apache.xml.serializer.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;
import javax.xml.transform.*; 
import javax.xml.transform.dom.DOMSource; 
import javax.xml.transform.stream.*;
import org.w3c.dom.traversal.*;
import org.w3c.dom.*;
import org.w3c.dom.ls.*;
import org.xml.sax.*;

public  class  Search extends HttpServlet {
  protected void service(HttpServletRequest req, HttpServletResponse res ) {
    try {       
        //0.取得網頁送來的資料           
      req.setCharacterEncoding("UTF-8"); //設定正確的中文編碼

      String lalaDepartment = (String) req.getParameter("department"); //尋找寵物名稱
      //1.載入XML物件
     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
     factory.setNamespaceAware(true);
     DocumentBuilder builder = factory.newDocumentBuilder();   
      //取得XML檔案的正確路徑
      ServletContext context = getServletContext();
      String xml_path_file =  context.getRealPath("lala's_data.xml"); 
      Document doc = builder.parse(new File (xml_path_file) ); //剖析並載入XML文件
      //2.準備查詢
      //2.1 準備好查詢的指令(含名稱空間前置詞)
      String xpath_query="/lala:各系啦啦資料/lala:科系[@系名='"+lalaDepartment+"']"; //查詢名稱的寵物資料 
    //2.2 建立一個含有名稱空間的節點, 以便進行XPATH查詢
    DocumentBuilderFactory factory2  = DocumentBuilderFactory.newInstance();
    factory2.setNamespaceAware(true);
    DocumentBuilder builder2 = factory2.newDocumentBuilder();
    DOMImplementation impl = builder2.getDOMImplementation();
    Document namespaceDoc = impl.createDocument("http://lala's_data.com.tw", "namespaceMapping", null);
    Element namespaceRoot = namespaceDoc.getDocumentElement();
    String q_name ="xmlns:lala";
    String ns ="http://lala's_data.com.tw";
    namespaceRoot.setAttributeNS("http://www.w3.org/2000/xmlns/", q_name, ns);    
    //3.執行查詢
        String result= "<lala:各系啦啦資料 xmlns:lala=\"http://lala's_data.com.tw\">\n" ;
        NodeIterator nl = XPathAPI.selectNodeIterator(doc, xpath_query, namespaceRoot);
        
        //4.將查詢結果轉成字串集合起來       
        Node n;
        while ((n = nl.nextNode())!= null) { 
            result = result + cvtNode2String(n);
        }
        result = result + "\n</lala:各系啦啦資料>";
           
       System.out.println(result);
       //5.利用XSL準備傳回處理結果
      String xslFile =  context.getRealPath("lala's_data.xsl"); 
      res.setContentType("text/html;charset=UTF-8");
      PrintWriter out= res.getWriter();
      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer transformer = tFactory.newTransformer(new StreamSource(xslFile));
      transformer.transform(new StreamSource((Reader)new StringReader(result)),new StreamResult(out));
 }
     catch ( Exception e) {
       System.out.println("I go error here!");
       e.printStackTrace();
     }     
   }
/*將一個XML節點轉成字串: 利用Transformer物件將xml節點轉成字串*/
 public String cvtNode2String(Node doc_node) {
   try {
        StringWriter output = new StringWriter();
        TransformerFactory tranFactory = TransformerFactory.newInstance(); 
        Transformer aTransformer = tranFactory.newTransformer(); 
        Source src = new DOMSource(doc_node); 
        Result dest = new StreamResult(output); 
        aTransformer.transform(src, dest); 
        output.close();
        String tmp = output.toString();
        //移除<?xml ...?>
        String xml_body = tmp.substring(tmp.indexOf("?>")+2);
        return xml_body;

      } catch (Exception e) {
        System.out.println("cvtNode2String Failed!");
        e.printStackTrace();
        return null;
     }  
 }



}
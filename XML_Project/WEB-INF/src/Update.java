import javax.servlet.*; 
import javax.servlet.http.*;

import org.apache.xpath.XPathAPI;
import org.apache.xml.serializer.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.w3c.dom.traversal.*; //NodeIterator

//為了儲存XML檔之用
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.util.*;

public  class  Update extends HttpServlet {
  protected void service(HttpServletRequest req, HttpServletResponse res ) {  
    try {
        //0.取得網頁送來的資料           
      req.setCharacterEncoding("UTF-8"); //設定正確的中文編碼
      String lalaDepartment = (String) req.getParameter("department");
      String lalaCoach = (String) req.getParameter("coach");
      String lalaClothes = (String) req.getParameter("clothes");
      String lalaItems = (String) req.getParameter("items");
      //1.載入XML文件
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      DocumentBuilder builder = factory.newDocumentBuilder();
      //取得XML檔案的正確路徑
      ServletContext context = getServletContext();
      String xml_path_file =  context.getRealPath("lala's_data.xml"); 
      Document doc = builder.parse(new File (xml_path_file) ); //剖析並載入XML文件
     
      System.out.println("Load OK!");
 
        //2.尋找要刪除的節點
        //2.準備查詢 
        //2.1 準備好查詢的指令(含名稱空間前置詞)
        String xpath_query1 = "//lala:科系[@系名='"+lalaDepartment+"']/lala:預算/..";  //查詢要刪除的寵物編號     
        String xpath_query2 = "//lala:科系[@系名='"+lalaDepartment+"']/lala:預算";
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
            NodeIterator n1 = XPathAPI.selectNodeIterator(doc, xpath_query1, namespaceRoot); 
            NodeIterator n2 = XPathAPI.selectNodeIterator(doc, xpath_query2, namespaceRoot); 
            
        String rest=""; //要傳回前端的訊息        
        Node lala_elem=n1.nextNode() ;
        Node old_cost_elem;
        if ((old_cost_elem = n2.nextNode())!= null) { 
          //3.新增含有新資料的節點       
          Element new_cost_elem = doc.createElement("lala:預算")  ; //產生<寵物>
          new_cost_elem.setAttribute("教練預算",lalaCoach);
          new_cost_elem.setAttribute("服裝預算",lalaClothes);
          new_cost_elem.setAttribute("道具預算",lalaItems);
          //new_cost_elem.appendChild(doc.createTextNode("\n\t\t\t\t"));
          //3.2. 修改節點資料
          lala_elem.replaceChild (new_cost_elem,old_cost_elem) ;     
       
          System.out.println("修改預算,OK!");
          rest = "修改預算,OK!" ;                     
         //3.3.儲存 將結果寫入XML文件
            DOMSource source = new DOMSource(doc);
           StreamResult result = new StreamResult(new FileOutputStream(xml_path_file)); 
          ServletContext context2 = getServletContext();
          String xml_dtd =  context2.getRealPath("lala's_data.dtd"); 
           //String xml_dtd= "lala's_data.dtd";
           //System.out.println(xml_dtd);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,xml_dtd);
            // doc.addDocType("lala:各系啦啦資料","SYSTEM","lala's_data.dtd");
            transformer.transform(source, result);
       
          System.out.println("儲存成功!");
        
        }
        //4. 傳遞結果給前端    
         res.setContentType("text/html;charset=UTF-8");
         PrintWriter out = res.getWriter();        
          //send it back to client
         BufferedReader input = new BufferedReader(new StringReader(rest));
         BufferedWriter output = new BufferedWriter( out);
         int ch;
         while ((ch = input.read()) != -1)
             output.write(ch);
         input.close();
         output.close();
        }
         catch ( Exception e) {
           System.out.println("I go error here!");
           e.printStackTrace();
         }     
       }   
}


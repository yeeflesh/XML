import javax.servlet.*; 
import javax.servlet.http.*;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import org.w3c.dom.traversal.*;
import org.apache.xpath.XPathAPI;
import org.apache.xml.serializer.*;
//為了儲存XML檔之用
import javax.xml.validation.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public  class  Add  extends HttpServlet  {
    protected void service(HttpServletRequest req, HttpServletResponse res ) {
    try {
            //0.取得網頁送來的資料           
          req.setCharacterEncoding("UTF-8"); //設定正確的中文編碼
          String lalaDepartment = (String) req.getParameter("department");
          String lalaName = (String) req.getParameter("name");
          String lalaClass = (String) req.getParameter("class");
          String lalaID = (String) req.getParameter("id");
          String lalaPhone = (String) req.getParameter("phone");
          String lalaPlace = (String) req.getParameter("place");
          String lalaSkill = (String) req.getParameter("skill");
          String lalaShoulder = (String) req.getParameter("shoulder");
          String lalaChest = (String) req.getParameter("chest");
          String lalaWaist = (String) req.getParameter("waist");
          String lalaHips = (String) req.getParameter("hips");
          String lalaLegs = (String) req.getParameter("legs");
          String lalaPe = (String) req.getParameter("pe");
          String lalaPe_Name = (String) req.getParameter("pe_name");
          //1.載入XML文件
          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          factory.setNamespaceAware(true);
          //factory.setValidating(true);
          DocumentBuilder builder = factory.newDocumentBuilder();
          //取得XML檔案的正確路徑
          ServletContext context = getServletContext();
          String xml_path_file =  context.getRealPath("lala's_data.xml"); 
          Document doc = builder.parse(new FileInputStream(xml_path_file) ); //剖析並載入XML文件
         
          System.out.println("Load OK!");
          //2.產生各式的節點 ,3.利用appendChild()將新節點插入適當的位置 
          Element lala_elem=doc.createElement("lala:隊員");
          lala_elem.setAttribute("姓名",lalaName); lala_elem.setAttribute("班級",lalaClass); 
          lala_elem.setAttribute("學號",lalaID); lala_elem.setAttribute("電話",lalaPhone);
          lala_elem.appendChild(doc.createTextNode("\n\t\t\t\t"));
          
          Element place_elem=doc.createElement("lala:住宿通勤");           
          Text place_txt1=doc.createTextNode(lalaPlace); place_elem.appendChild(place_txt1);         
          lala_elem.appendChild(place_elem);     
          lala_elem.appendChild(doc.createTextNode("\n\t\t\t\t"));
          
          Element skill_elem=doc.createElement("lala:運動或其他專長");          
          Text skill_txt1=doc.createTextNode(lalaSkill); skill_elem.appendChild(skill_txt1);          
          lala_elem.appendChild(skill_elem);
          lala_elem.appendChild(doc.createTextNode("\n\t\t\t\t"));
          
          Element body_elem=doc.createElement("lala:身材");
          body_elem.setAttribute("肩長",lalaShoulder); body_elem.setAttribute("胸圍",lalaChest);
          body_elem.setAttribute("腰圍",lalaWaist); body_elem.setAttribute("臀圍",lalaHips);
          body_elem.setAttribute("腿長",lalaLegs);        
          lala_elem.appendChild(body_elem);     
          lala_elem.appendChild(doc.createTextNode("\n\t\t\t\t"));
          
          Element pe_elem=doc.createElement("lala:體育課");
          pe_elem.setAttribute("節次",lalaPe);
          Text pe_txt1=doc.createTextNode(lalaPe_Name); pe_elem.appendChild(pe_txt1);     
          lala_elem.appendChild(pe_elem);
          lala_elem.appendChild(doc.createTextNode("\n\t\t\t"));
          
          String xpath_query="/lala:各系啦啦資料/lala:科系[@系名='"+lalaDepartment+"']/lala:隊員資料";
          DocumentBuilderFactory factory2  = DocumentBuilderFactory.newInstance();
          factory2.setNamespaceAware(true);
          DocumentBuilder builder2 = factory2.newDocumentBuilder();
          DOMImplementation impl = builder2.getDOMImplementation();
          Document namespaceDoc = impl.createDocument("http://lala's_data.com.tw", "namespaceMapping", null);
          Element namespaceRoot = namespaceDoc.getDocumentElement();
          String q_name ="xmlns:lala";
          String ns ="http://lala's_data.com.tw";
          namespaceRoot.setAttributeNS("http://www.w3.org/2000/xmlns/", q_name, ns);
          NodeIterator n1 = XPathAPI.selectNodeIterator(doc, xpath_query, namespaceRoot);   
          //Node n=n1.getRoot();
          Node n=n1.nextNode(); //the first call to nextNode() returns the first node in the set.
          n.appendChild(doc.createTextNode("\t"));
          n.appendChild (lala_elem) ;
          n.appendChild(doc.createTextNode("\n\t\t"));
          
          System.out.println("新增一筆隊員資料,OK!");
          //4.儲存 將結果寫入XML文件
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
           //5. 傳遞結果給前端
           res.setContentType("text/html;charset=UTF-8");
             PrintWriter out = res.getWriter();
             String rest = "新增OK" ;         
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


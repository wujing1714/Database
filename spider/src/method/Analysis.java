package method;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Analysis {

	/**解析每个page上的信息*/
	public void analysis(String string){
		try {
			Document document=Jsoup.connect(string).get();
			//Element element1=document.getElementsByClass("column_list_content").first();
			Elements elements=document.getElementsByClass("column_list_content").first().getElementsByTag("table").first().getElementsByTag("tr").first().getElementsByTag("td").first().getElementsByTag("table").first().getElementsByTag("a");
			

			 for(Element element:elements){
			

			String linkHref = "http://www.usst.edu.cn"+element.attr("href");  
				String text = element.text();
               // System.out.println(linkHref);  
				
				
				Document document2=Jsoup.connect(linkHref).get();
			//Element element1=document.getElementsByClass("column_list_content").first();
			Element element2=document2.getElementsByClass("content").first();
				String content1 =  element2.text();
				String content = new String(content1.getBytes(),"GBK").replace('?', ' ').replace('　', ' ');//?转换为空格
				System.out.print(text+"\n");
				System.out.print(content+"\n");	
				
			 }

			//dbHelper.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}

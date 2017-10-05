import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes.Name;

import org.eclipse.jetty.websocket.common.message.MessageWriter;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlUnorderedList;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class SecondSpider implements PageProcessor {
	 private static int page_num=1;
	 private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setUserAgent(Useragnets.getuseragent());

	    @SuppressWarnings("deprecation")
		@Override
	    public void process(Page page) {
	    	
	    	/*page.putField("html", page.getHtml());
	    	List<String> allname=page.getHtml().xpath("//table[@id='table search_']/tbody//tr/td[2]/text()").all();
	    	for(int i=0;i<allname.size();i++)
            {
             	int num=i;
                page.putField("name"+num, allname.get(i));
            }*/
	    	try {
				WebClient webClient = new WebClient(BrowserVersion.CHROME);
				webClient.getOptions().setCssEnabled(false);
				webClient.getOptions().setUseInsecureSSL(true);
				webClient.getOptions().setJavaScriptEnabled(true);
				webClient.setAjaxController(new NicelyResynchronizingAjaxController());
				HtmlPage htmlPage = webClient.getPage("http://www.sse.com.cn/assortment/stock/list/share/");
				webClient.waitForBackgroundJavaScript(10000);
                for(int m=0;m<5;m++){  
                    page.setRawText(htmlPage.asXml());
                    page.setHtml(new Html(htmlPage.asXml()));
                    List<String> allnames=page.getHtml().xpath("//table[@class='table search_']/tbody/tr/td[2]/text()").all();
                    //List<String> allname=htmlPage.getByXPath("//table[@class='table search_']/tbody/tr/td[2]/text()");
                    //List<String> allname=htmlPage.getByXPath("//*[@id='tableData_']/div[2]/table/tbody//tr/td[2]/text()");
                    for(int i=0;i<allnames.size();i++)
                    {
                    	 int num=m*25+i;
                         page.putField("name"+num, allnames.get(i).trim());
                    }
                    /*for(int i=0;i<allname.size();i++)
                    {
                    	 int num=m*25+i;
                         page.putField("nameod"+num, allname.get(i));
                    }*/
                    HtmlTextInput pagenum = (HtmlTextInput)htmlPage.getElementById("ht_codeinput");
                    HtmlSpan go= (HtmlSpan)htmlPage.getElementById("pagebutton");
                    pagenum.setValueAttribute(m+2+"");
                    htmlPage=(HtmlPage)go.click();
                    webClient.waitForBackgroundJavaScript(10000); 
                    /*HtmlUnorderedList divKD=(HtmlUnorderedList)htmlPage.querySelector(".pagination");
                    DomNodeList<HtmlElement> nodeKD=divKD.getElementsByTagName("li");  
                    int nextpos=nodeKD.size()-1;
                    HtmlListItem heLi=(HtmlListItem)nodeKD.get(nextpos); 
                    htmlPage=(HtmlPage)heLi.click();
                    webClient.waitForBackgroundJavaScript(10000); 
                    page.putField("html"+m, htmlPage.asXml());*/
                }  
				webClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
	    	/*List<String> allNames=page.getHtml().xpath("//dl[@class='obu']/dd/a/text()").all();
	    	for(int i=0;i<allNames.size();i++)
	    	{
	    		int temp=(page_num-2)*70+i;
	    		page.putField("name"+temp,allNames.get(i));
	    	}
	        if(page_num<19)
	        {
	        	page.addTargetRequest("https://www.douban.com/people/163296676/rev_contacts?start="+(page_num-1)*70);
		        page_num++;
	        }*/
	    }

	    @Override
	    public Site getSite() {
	        return site;
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Spider.create(new SecondSpider()).addUrl("http://www.sse.com.cn/assortment/stock/list/share/").addPipeline(new ConsolePipeline()).addPipeline(new JsonFilePipeline("D:\\")).thread(1).run();
		  
	}

}

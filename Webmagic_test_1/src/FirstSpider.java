import java.util.List;
import java.util.jar.Attributes.Name;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Selectable;

public class FirstSpider implements PageProcessor {
	 private static int page_num=2;
	 private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
			 .setDomain("www.douban.com")
			 .addCookie("bid", "VG0MnFJ-ljk")
			 .addCookie("ll", "108296")
			 .addCookie("gr_user_id", "f46079e5-ac64-4d6e-89cf-3e82f9cf9197")
			 .addCookie("__yadk_uid", "zWra4PQ81w9SWnswFVkUDyM6Ylfja0m3")
			 .addCookie("viewed", "11613224_5363753_4189495_2044516_2064977")
			 .addCookie("ps", "y")
			 .addCookie("ue", "dayinshine@163.com")
			 .addCookie("_pk_ref.100001.8cb4", "=%5B%22%22%2C%22%22%2C1507030770%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DtwNcLb6aB60hJpVSSLZ2wLs8E_DCkq9ry9WE7DhzV8cvnB_kXpbGDCr4q6HWtwPw%26wd%3D%26eqid%3Dc3c2cd8e0002038e0000000459a664c5%22%5D")
			 .addCookie("__utmt", "1")
			 .addCookie("_vwo_uuid_v2", "3CCFD5BDE7AEA1CE191A3FFBE151F8B2|014a3856d7c7a5f7d5b97621e18245a1")
			 .addCookie("_gat_UA-7019765-1", "1")
			 .addCookie("push_noty_num", "0")
			 .addCookie("push_doumail_num", "0")
			 .addCookie("ap", "1")
			 .addCookie("_pk_id.100001.8cb4", "038248d8e09a7fbd.1489928583.9.1507031220.1507019281.")
			 .addCookie("_pk_ses.100001.8cb4", "*")
			 .addCookie("__utma", "30149280.1972758811.1489928584.1507019253.1507030770.17")
			 .addCookie("__utmb", "30149280.16.10.1507030770")
			 .addCookie("__utmc", "30149280")
			 .addCookie("__utmz", "30149280.1504077124.15.14.utmcsr=baidu|utmccn=(organic)|utmcmd=organic")
			 .addCookie("__utmv", "30149280.16757")
			 .addCookie("_ga", "GA1.2.1972758811.1489928584")
			 .addCookie("_gid", "GA1.2.522355222.1507030838")
			 .addCookie("dbcl2", "167579864:h9ZParU/MUM")
			 .setUserAgent(Useragnets.getuseragent())
			 ;

	    @Override
	    public void process(Page page) {
	    	
	    	List<String> allNames=page.getHtml().xpath("//dl[@class='obu']/dd/a/text()").all();
	    	for(int i=0;i<allNames.size();i++)
	    	{
	    		int temp=(page_num-2)*70+i;
	    		page.putField("name"+temp,allNames.get(i));
	    	}
	        if(page_num<19)
	        {
	        	page.addTargetRequest("https://www.douban.com/people/163296676/rev_contacts?start="+(page_num-1)*70);
		        page_num++;
	        }
	    }

	    @Override
	    public Site getSite() {
	        return site;
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  Spider.create(new FirstSpider()).addUrl("https://www.douban.com/people/163296676/rev_contacts?start=0").addPipeline(new ConsolePipeline()).thread(1).run();
	}

}

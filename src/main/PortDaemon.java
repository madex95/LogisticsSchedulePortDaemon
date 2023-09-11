package main;


import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dao.PortDAO;

public class PortDaemon {
	
	public static void main(String[] args) {
		
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		
        LocalTime now = LocalTime.now(); 
        String URL = "https://www.tradlinx.com/container-terminal-schedule";
		
		try {
			PortDAO portDao = PortDAO.getInstance();
		
			Document doc;
	        doc = Jsoup.connect(URL).get(); 
			Elements el = doc.select("terminal-schedule > div > div.container-percent > div.contents.terminal-content > div > div.schedules > ul > li");
	        
	        boolean flag = true;
            while (true) { 
            	flag = true;
            	now = LocalTime.now();
            	
            	// 매일 08:00에 크롤링
            	if ("0800".equals(String.valueOf(now).replaceAll("[^0-9]","").substring(0,4))) {
            		              
            		// 크롤링 값 세팅 
        			for (int i=0; i<el.size(); i++) {
        				/*항차번호*/    map.put("VOY_NO"      , el.select(".col13").get(i).text());
        				/*포트코드*/    map.put("PORT_CD"     , el.select(".col1").get(i).text());
        				/*터미널코드*/  map.put("TMN_CD"      , el.select(".col2").get(i).text().substring(0,el.select(".col2").get(i).text().indexOf(" Con No.")));
        				/*선박명*/     map.put("VSL_NM"      , el.select(".col3").get(i).text().substring(0,el.select(".col3").get(i).text().indexOf("클릭 시")));
        				/*선박상태*/    map.put("VSL_STTS"    , el.select(".col4").get(i).text());
        				/*화물마감시간*/ map.put("CUT_OFF_DTM" , el.select(".col5").get(i).text().replaceAll("[^0-9]",""));
        				/*도착예정시간*/ map.put("ARV_DTM"     , el.select(".col6").get(i).text().replaceAll("[^0-9]",""));
        				/*출발예정시간*/ map.put("DPT_DTM"     , el.select(".col7").get(i).text().replaceAll("[^0-9]",""));
        				/*선사명*/     map.put("CARRIER_NM"  , el.select(".col8").get(i).text());
                		
                        list.add(map);
                        map = new HashMap<>(); // 초기화
        			}
        			
        			// 트렌잭션
        			portDao.mergePortSchedule(list);
        			list = new ArrayList<>(); // 초기화
        			
        			// 트렌잭션 후 시간이 지나갈때까지 대기
        			while(flag) {
        				now = LocalTime.now();
        				if (!"08".equals(String.valueOf(now).replaceAll("[^0-9]","").substring(0,2))) {
        					flag = false;
        				}
        			}
        			
            	} 
                
            }
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package Project;

import java.io.BufferedInputStream;
import java.net.URL;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class INFO_FOOD {
	private String basic_url = "http://openapi.foodsafetykorea.go.kr/api/8da405b8b1884ebb9c4c/I2790/json/1/100/DESC_KOR=" ;
	private String open_api_code = "I2790";
	public INFO_FOOD() {
		
	}
	
    public JSONArray get_info(String foodname) throws Exception{
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(readUrl(foodname));
        JSONObject Myobject = (JSONObject) jsonObject.get(open_api_code);
        
        // 에러 확인.
        JSONObject result_code = (JSONObject) Myobject.get("RESULT");
        if (!result_code.get("CODE").equals("INFO-000")) {
        	return (JSONArray)result_code.get("CODE");
        }
        
        int count = Integer.parseInt((String) Myobject.get("total_count"));
        JSONArray Result_Row = (JSONArray) Myobject.get("row");
        return Result_Row;
    }
       
    
    private String readUrl(String foodname) throws Exception{
        BufferedInputStream reader = null;
        
        try {
            URL url = new URL(basic_url+foodname);
            
            reader = new BufferedInputStream(url.openStream());
            StringBuffer buffer = new StringBuffer();
            int i = 0;
            byte[] b = new byte[8192];
            while((i = reader.read(b)) != -1){
                buffer.append(new String(b, 0, i));
            }
            return buffer.toString();
            
        } finally{
            if(reader != null) reader.close();
            
        }
        
    }
}

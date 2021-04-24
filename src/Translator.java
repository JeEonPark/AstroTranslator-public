
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Translator {
	
	//네이버 관련 코드
	String clientId = "";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "";//애플리케이션 클라이언트 시크릿값";
    String lang;
    String parsertrans;
    
    BufferedReader br;
    
	
    /*
     * 언어 판별 객체--------------------------------------------------------
     */
	public String whatLanguage(String msg) {
		
		try {
            String query = URLEncoder.encode(msg, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/papago/detectLangs";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "query=" + query;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            String LANGUAGE = response.toString(); //Language에 JSON 넣기
            //JSON 파싱
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(LANGUAGE);
            lang = (String) jsonObject.get("langCode");
		
		
		} catch (Exception e) {
            System.out.println(e);
        }
		
		return lang;
		
	}
	
	
	/*
	 * 번역 객체-----------------------------------------------------------
	 */
	
	public String TransReturnKRtoJP(String msg) { //언어 KR to JP
		
		try {
            String text = URLEncoder.encode(msg, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/language/translate";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=ko&target=ja&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            StringBuffer response = new StringBuffer();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            String translated = response.toString(); //json translated 파싱
            JSONParser jsonParser2 = new JSONParser();
            JSONObject jsonObject2 = (JSONObject) jsonParser2.parse(translated);
            String parsetrans = jsonObject2.get("message").toString();
            JSONObject jsonmsg = (JSONObject) jsonParser2.parse(parsetrans);
            String parseresult = jsonmsg.get("result").toString();
            JSONObject jsonresult = (JSONObject) jsonParser2.parse(parseresult);
            parsertrans = jsonresult.get("translatedText").toString();


        } catch (Exception e) {
            System.out.println(e);
            return "Sorry. Bot have used all the papago daily usage per day. Usage will initialize the next day.\nネイバーパパゴの一日の使用量を超過しました。 使用量は次に日に初期化されます。\n네이버 파파고의 하루 사용량을 초과하였습니다. 사용량은 다음 날 초기화됩니다.\n More information, AstroJonny#0880";
        }
		
		return parsertrans;
		
	}
	
	//---------------------------------------------------------------------
		
	public String TransReturnJPtoKR(String msg) { //언어 JP to KR
		
		try {
            String text = URLEncoder.encode(msg, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/language/translate";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=ja&target=ko&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            StringBuffer response = new StringBuffer();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            String translated = response.toString(); //json translated 파싱
            JSONParser jsonParser2 = new JSONParser();
            JSONObject jsonObject2 = (JSONObject) jsonParser2.parse(translated);
            String parsetrans = jsonObject2.get("message").toString();
            JSONObject jsonmsg = (JSONObject) jsonParser2.parse(parsetrans);
            String parseresult = jsonmsg.get("result").toString();
            JSONObject jsonresult = (JSONObject) jsonParser2.parse(parseresult);
            parsertrans = jsonresult.get("translatedText").toString();


        } catch (Exception e) {
            System.out.println(e);
            return "Sorry. Bot have used all the papago daily usage per day. Usage will initialize the next day.\nネイバーパパゴの一日の使用量を超過しました。 使用量は次に日に初期化されます。\n네이버 파파고의 하루 사용량을 초과하였습니다. 사용량은 다음 날 초기화됩니다.\n More information, AstroJonny#0880";
        }
		
		return parsertrans;
		
	}
	
	
}

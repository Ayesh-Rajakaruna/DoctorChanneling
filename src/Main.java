import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        sendTheWsMassage();
        while (true) {
            if (getCheckAvailable(getHospitalOne()) || getCheckAvailable(getHospitalTwo())) {
                sendTheWsMassage();
            }
        }
    }

    private static boolean getCheckAvailable(String jsonInputString) {
        try {
            URL url = new URL("https://echannelling-apigw.mobitel.lk/echannelling/ext/echannelling/ech-api/ech/v2/doctorSessions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-Ibm-Client-Id", "fbd72ca4-9f75-4cf7-8c1b-4c4b291dfc75");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Cookie", "NSC_bqq.fdiboofmmjoh.ml-MC=ffffffffaf12ed0645525d5f4f58455e445a4a4229a0; cookiesession1=678B288459AAEA163BA8A6AAC0667471");
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                String responseBodyString = response.toString();
                String[] segments = responseBodyString.split("\"remark\":");
                List<String> remarks = new ArrayList<>();
                for (int i = 1; i < segments.length; i++) {
                    int endQuoteIndex = segments[i].indexOf('\"', 1);
                    String remark = segments[i].substring(1, endQuoteIndex);
                    remarks.add(remark);
                }
                System.out.println(remarks);
                return remarks.contains("AVAILABLE");
            }
        } catch (Exception e) {
            sendTheWsMassage();
            return false;
        }
    }

    private static void sendTheWsMassage() {
        try {
            String url = "https://graph.facebook.com/v19.0/342188948968755/messages";
            String postData = "{\n" +
                    "\"messaging_product\": \"whatsapp\",\n" +
                    "\"to\": \"94776331658\",\n" +
                    "\"type\": \"template\",\n" +
                    "\"template\": {\n" +
                    "\"name\": \"hello_world\",\n" +
                    "\"language\": { \"code\": \"en_US\" }\n" +
                    "}\n" +
                    "}";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer EAAWTLyBsqM0BOyui64BDHFfpe6yo5VlVZCn1sXB5S0vsFoEBlpkbKIOKK9l9bZCvGOWWlc2wKnLUNdn5xJTTcJtLSiZC356hdRxuNR7fu8aekgsHmoAYZBISTGhGzHjYg7w17RDLr4WQPf3QEJAPdFa578rqFL8SbFTB8kWwMEj1AFGbBDLhTARlfvU1vknzPYr6bZAXVfTxwLNdHXEsZD");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postData);
            wr.flush();
            wr.close();
            con.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getHospitalOne() {
        return "{"
                + "\"appDate\": \"\","
                + "\"doctorNo\": \"D1292\","
                + "\"hosCode\": \"H03\","
                + "\"offset\": \"\","
                + "\"page\": \"\","
                + "\"price\": \"\","
                + "\"specializationId\": \"\""
                + "}";
    }

    private static String getHospitalTwo() {
        return "{"
                + "\"appDate\": \"\","
                + "\"doctorNo\": \"D1292\","
                + "\"hosCode\": \"H27\","
                + "\"offset\": \"\","
                + "\"page\": \"\","
                + "\"price\": \"\","
                + "\"specializationId\": \"\""
                + "}";
    }
}
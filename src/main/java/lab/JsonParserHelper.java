package lab;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class JsonParserHelper {
    public static String getJSONFromFile(String filename) {
        String jsonText = "";
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(filename));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonText += line + "\n";
            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonText;
    }

    public static String getJSONFromURL(String strUrl) {
        String jsonText = "";

        try {
            URL url = new URL(strUrl);
            InputStream is = url.openStream();

            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonText += line + "\n";
            }

            is.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return jsonText;
    }
}

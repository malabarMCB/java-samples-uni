package crossplatform;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static void writeLine(String text, String filePath) {
        BufferedWriter bw = null;

        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // true = append file
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            bw.write(text);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (bw!=null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    static List<String> readAllLines(String filePath, int startLine) {
        List<String> result = new ArrayList<>();
        BufferedReader br=null;

        try {
            FileInputStream fstream = new FileInputStream(filePath);
            br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;
            int lineCounter=0;

            while ((strLine = br.readLine()) != null)   {
                if(lineCounter>=startLine){
                    result.add(strLine);
                }
                else{
                    lineCounter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(br!=null){
                    br.close();
                }
            } catch (IOException e) {

            }
        }

        return result;
    }
}

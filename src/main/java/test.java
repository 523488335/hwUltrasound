import java.io.*;

/**
 * Created by meifan on 2018/7/3.
 */
public class test {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(("/Users/meifan/Desktop/3d_cloud.xyz")));
        BufferedWriter out = new BufferedWriter(new FileWriter("/Users/meifan/Desktop/3d_cloud^5.xyz"));
        String str = null;
        int count = 0;
        int readcount = 0;
        while ((str = in.readLine()) != null){
            if (readcount % 5 == 0){
                out.write(str);
                out.newLine();
                count++;
            }
            readcount++;
        }
        System.out.println(count);
        out.flush();
        in.close();
        out.close();
    }
}
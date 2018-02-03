import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018-02-02.
 */
public class ImageFetchServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> filenames = new ArrayList<String>();
        String desDir = "D:\\image";
        File fileRootDir = new File(desDir);
        File[] fileArray = fileRootDir.listFiles();

        if (fileArray != null) {
            for (File item : fileArray) {
                if (item.isDirectory()) {
                    filenames.add(item.getName());
                }
            }
        }


        Gson gson = new Gson();
        String s = gson.toJson(filenames);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(s);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String timeStamp = request.getParameter("timeStamp");
        String desDir = "D:\\image\\" + timeStamp;

        ArrayList<Byte> container = new ArrayList();
        byte[] data ;
        FileInputStream fileInputStream ;
        File fileRootDir = new File(desDir);
        File[] fileArray = fileRootDir.listFiles();
        for (File item : fileArray) {
            if (item.isFile()) {
                fileInputStream = new FileInputStream(item.getAbsoluteFile().toString());
                data = new byte[fileInputStream.available()];
                fileInputStream.read(data);
                fileInputStream.close();
                for (byte item1 : data) {
                    container.add(item1);
                }
            }
        }

        int length = container.size();
        byte[] results = new byte[length];
        for (int i = 0; i < length; ++i) {
            results[i] = container.get(i);
        }

        response.setContentType("image/jpeg");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(results);
        outputStream.flush();
        outputStream.close();
    }
}

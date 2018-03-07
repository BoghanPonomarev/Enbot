package ua.nure.ponomarev.web.servlet;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.exception.ValidationException;
import ua.nure.ponomarev.service.WordService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Bogdan_Ponamarev.
 */
@WebServlet(name = "GetFileServlet", urlPatterns = "/PutFile")
public class GetFileServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GetFileServlet.class);
    private WordService wordService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        wordService = (WordService) config.getServletContext().getAttribute("word_service");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<String> errors  = new ArrayList<>();
        try {
            List<FileItem> paramList = upload.parseRequest(request);
            for (FileItem item :paramList) {
                if (!item.isFormField()) {
                    Charset charset = getCharset(request, response,paramList);
                    if (charset == null) {
                        return;
                    }
                    String text = item.getString(charset.name()).substring(1);
                    String topicName = getTopicName(request,response,paramList);
                    if(topicName==null){
                        return;
                    }
                    wordService.insertWords(text,topicName);
                }
            }
        } catch (FileUploadException e) {
           logger.error("File uploader can`t find file");
            forward(request,response,errors);
        } catch (ValidationException e) {
           logger.info("File is invalid");
           errors.add("File is`nt appropriate to pattern");
            forward(request,response,errors);
        } catch (DbException e) {
            errors.add("There some trouble with data holder in server");
            forward(request,response,errors);
        }
       response.sendRedirect("index.jsp");
    }
    private void forward(HttpServletRequest request, HttpServletResponse response, List<String> errors) throws ServletException, IOException {
        request.setAttribute("errors", errors);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request,response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private String getTopicName(HttpServletRequest request, HttpServletResponse response,List<FileItem> paramList ) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        String topicName = null;
        for (FileItem item:paramList){
            if(item.getFieldName()!=null&&item.getFieldName().equals("topic")){
                topicName = item.getString();
                break;
            }
        }
        if(topicName==null){
            errors.add("You have`nt written a topic");
            forward(request,response,errors);
        }
        return  topicName;
    }

    private Charset getCharset(HttpServletRequest request, HttpServletResponse response,List<FileItem> paramList ) throws ServletException, IOException, FileUploadException {
        List<String> errors = new ArrayList<>();
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        String charset=null;
        for (FileItem item:paramList){
            if(item.getFieldName()!=null&&item.getFieldName().equals("charset")){
                charset = item.getString();
                break;
            }
        }
        if (charset == null||charset.matches(" *")) {
            errors.add("You have`nt indicated charset of file");
            forward(request,response,errors);
            return null;
        }
        try {
            return Charset.forName(charset);
        } catch (UnsupportedCharsetException e) {
            errors.add("We have`nt such charset");
            forward(request,response,errors);
            return null;
        }

    }
}

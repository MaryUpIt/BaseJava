package com.urise.webapp.web;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.Config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write(
                "<html>\n" +
                        "<head>\n" +
                        "    <title> DBResumes </title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<header>Application for web-course</a></header>\n" +
                        "<style>\n" +
                        "    table, th, td {\n" +
                        "        border:1px solid black;\n" +
                        "    }\n" +
                        "</style>\n" +
                        "<h1>DateBase Of Resumes</h1>\n" +
                        "<table style=\"width:90%\">\n" +
                        "    <tr>\n" +
                        "        <th> UUID</th>\n" +
                        "        <th>Full Name</th>\n" +
                        "    </tr>\n");

        for (Resume resume : storage.getAllSorted()) {
            writer.write(
                    "    <tr>\n" +
                            "        <th> " + resume.getUuid() + "</th>\n" +
                            "        <th>" + resume.getFullName() + "</th>\n" +
                            "    </tr>\n");
        }

        writer.write(
                "  </table>\n" +
                        "<footer>Practice on Java-education</a></footer>\n" +
                        "</body>\n" +
                        "</html>");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

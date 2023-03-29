package com.urise.webapp.web;

import com.urise.webapp.exceptions.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.Config;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resume", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list_resumes.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "save" -> resume = Resume.EMPTY;
            case "view" -> resume = storage.get(uuid);
            case "edit" -> {
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE, PERSONAL -> {
                            if (section == null) {
                                resume.setSection(type, TextSection.EMPTY);
                            }
                        }
                        case ACHIEVEMENTS, QUALIFICATIONS -> {
                            if (section == null) {
                                resume.setSection(type, ListSection.EMPTY);
                            }
                        }
                        case EXPERIENCE, EDUCATION -> {
                            OrganizationSection resumeOrgSection = (OrganizationSection) section;
                            List<Organization> emptyOrganizations = new ArrayList<>();
                            emptyOrganizations.add(Organization.EMPTY);
                            if (resumeOrgSection != null) {
                                for (Organization organization : resumeOrgSection.getContent()) {
                                    organization.addPeriod(Organization.Period.EMPTY);

                                }
                                emptyOrganizations.addAll(resumeOrgSection.getContent());
                            }
                            resume.setSection(type, new OrganizationSection(emptyOrganizations));
                        }
                    }
                }
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher("view".equals(action) ?
                        "/WEB-INF/jsp/view_resume.jsp" : "/WEB-INF/jsp/edit_resume.jsp").
                forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("full_name");

        Resume resume;
        boolean isNewResume = false;
        try {
            resume = storage.get(uuid);
        } catch (NotExistStorageException e) {
            resume = new Resume(fullName);
            isNewResume = true;
        }
        resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (!HtmlUtil.isEmpty(value)) {
                resume.setContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] titles = request.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && titles.length < 2) {
                resume.getSections().remove(type);
                continue;
            }
            switch (type) {
                case OBJECTIVE, PERSONAL -> resume.setSection(type, new TextSection(value));
                case ACHIEVEMENTS, QUALIFICATIONS -> resume.setSection(type, new ListSection(value.split("\n")));
                case EDUCATION, EXPERIENCE -> {
                    List<Organization> organizations = new ArrayList<>();
                    String[] webSites = request.getParameterValues(type.name() + "url");
                    for (int i = 0; i < titles.length; i++) {
                        String title = titles[i];
                        List<Organization.Period> periods = new ArrayList<>();
                        if (!HtmlUtil.isEmpty(title)) {
                            String counter = type.name() + i;
                            String[] positions = request.getParameterValues(counter + "position");
                            String[] startDates = request.getParameterValues(counter + "dateFrom");
                            String[] endDates = request.getParameterValues(counter + "dateTo");
                            String[] descriptions = request.getParameterValues(counter + "description");
                            for (int j = 0; j < positions.length; j++) {
                                String position = positions[j];
                                if (!HtmlUtil.isEmpty(position)) {
                                    periods.add(new Organization.Period(position, descriptions[j],
                                            DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j])));
                                }
                            }
                            organizations.add(new Organization(title, webSites[i], periods));
                        }
                    }
                    resume.setSection(type, new OrganizationSection(organizations));
                }
            }
        }
        if (isNewResume) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }

        response.sendRedirect("resume");
    }
}

<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/themes/${theme}.css">
    <link rel="stylesheet" href="css/edit_style.css">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Edit resume ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/themes.jsp"/>
<jsp:include page="fragments/header.jsp"/>
<div class="scrollable-panel">
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <table class="profile">
            <td class="profile-empty"></td>
            <td class="profile-photo">
                <img class="photo" src="image_source/female.png" alt="employee">
            </td>
            <td class="profile-content">
                <input class="field-profile" type="text" required placeholder="Name Surname" maxlength="50"
                       name="full_name" size="30" value="${resume.fullName}">

                <c:forEach var="contact" items="<%=ContactType.values()%>">
                    <input class="field-profile" ${HtmlUtil.contactPattern(contact)}
                           name="${contact.name()}" value="${resume.getContact(contact)}">
                </c:forEach>
            </td>
            <td class="profile-empty"></td>
        </table>
        <div class="form-wrapper">
            <c:forEach var="type" items="<%=SectionType.values()%>">
                <c:set var="section" value="${resume.getSection(type)}"/>
                <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>

                <div class="section">${type.title}</div>
                <c:choose>
                    <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                        <textarea class="field" type="text" name="${type}"><%=section%></textarea>
                    </c:when>

                    <c:when test="${type=='ACHIEVEMENTS' || type=='QUALIFICATIONS'}">
                        <textarea class="field" type="text" name="${type}">
                            <%=String.join("\n", ((ListSection) section).getContent())%>
                        </textarea>
                    </c:when>

                    <c:when test="${type=='EDUCATION' || type=='EXPERIENCE'}">
                        <c:forEach var="organization" items="<%=((OrganizationSection)section).getContent()%>"
                                   varStatus="counter">
                            <input type="text" name="${type}" value="${organization.title}">
                            <input type="url" name="${type}url" value="${organization.website}">
                            <c:forEach var="period" items="${organization.periods}">

                                <jsp:useBean id="period" type="com.urise.webapp.model.Organization.Period"/>
                                <input class="field-data" type="date" min="1940-01-01" max="${DateUtil.NOW}"
                                       name="${type}${counter.index}dateFrom"
                                       value="<%=period.getDateFrom()%>">
                                <input class="field-data" type="date" min="1940-01-01" max="${DateUtil.NOW}"
                                       name="${type}${counter.index}dateTo"
                                       value="<%=period.getDateTo()%>">

                                <input type="text" name="${type}${counter.index}position" value="${period.position}">
                                <textarea class="field" type="text" name="${type}${counter.index}description">
                                        ${period.description}
                                </textarea>

                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>
            <button type="submit">SAVE</button>
            <button onclick="history.back()">CANCEL</button>
        </div>

    </form>
</div>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>

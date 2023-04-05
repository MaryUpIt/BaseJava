<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page import="com.urise.webapp.web.Themes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/themes/${theme}.css">
    <link rel="stylesheet" href="css/view_style.css">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Resume ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/themes.jsp"/>
<jsp:include page="fragments/header.jsp"/>
<div class="scrollable-panel">

    <table class="profile">
        <td class="profile-empty"></td>
        <td class="profile-photo">
            <img class="photo" src="image_source/female.png" alt="employee">
        </td>
        <td class="profile-content">
            <div><a title="редактировать"
                    href="resume?uuid=${resume.uuid}&action=edit&theme=${theme}">
                <img class="image" src="image_source/${theme}/edit.png"></a>${resume.fullName}</div>
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                <div title="${contactEntry.key}">
                    <c:if test="${theme == 'dark'}">
                        <%=HtmlUtil.contactToHtml(contactEntry.getKey(), contactEntry.getValue(), "dark")%>
                    </c:if>
                    <c:if test="${theme == 'light'}">
                        <%=HtmlUtil.contactToHtml(contactEntry.getKey(), contactEntry.getValue(), "light")%>
                    </c:if>
                </div>
            </c:forEach><br/>
        </td>
        <td class="profile-empty"></td>
    </table>
    <div class="form-wrapper">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <div class="section">
                    ${type.title}
            </div>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <div class="position">
                            ${resume.getSection(SectionType.OBJECTIVE)}
                    </div>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <div class="descriptions">
                        <th><%=((TextSection) section).getContent()%>
                    </div>
                </c:when>
                <c:when test="${type=='ACHIEVEMENTS' || type=='QUALIFICATIONS'}">
                    <ul class="descriptions">
                        <c:forEach var="content" items="<%=((ListSection)section).getContent()%>">
                            <li>${content}</li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:when test="${type=='EDUCATION' || type=='EXPERIENCE'}">
                    <c:forEach var="organization" items="<%=((OrganizationSection)section).getContent()%>">
                        <div><a class="organization-link" href="${organization.website}">
                            <img title="web-site" class="image" src="image_source/${theme}/site.png">
                                ${organization.title}</a></div>
                        <table class="experience">
                            <c:forEach var="period" items="${organization.periods}">
                                <jsp:useBean id="period" type="com.urise.webapp.model.Organization.Period"/>
                                <tr>
                                    <td class="period"><%=HtmlUtil.periodToHtml(period)%>
                                    </td>
                                    <td>${period.position}</td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td>${period.description}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach><br/>
    </div>
</div>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>
<%@ page import="java.util.List" %>
<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Resume ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2> ${resume.fullName} <a href="resume?uuid=${resume.uuid}&action=edit">EDIT</a></h2>

    <h3>CONTACTS</h3>
    <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
        <dl>
            <dt>${contactEntry.key.title}</dt>
            <dd><%=HtmlUtil.contactToHtml(contactEntry.getKey(), contactEntry.getValue())%></dd>
        </dl>
    </c:forEach><br/>

    <h3>SECTIONS</h3>
    <c:forEach var="sectionEntry" items="${resume.sections}">
    <jsp:useBean id="sectionEntry"
                 type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
    <c:set var="type" value="${sectionEntry.key}"/>
    <c:set var="section" value="${sectionEntry.value}"/>
    <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>


    <c:choose>
    <c:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
    <tr>
        <dl>
            <dt>${type.title}</dt>
            <dd><%=((TextSection) section).getContent()%></dd>
        </dl>
        </c:when>
        <c:when test="${type=='ACHIEVEMENTS' || type=='QUALIFICATIONS'}">
        <dl>
            <dt>${type.title}</dt>
            <dd>
                <ul>
                    <c:forEach var="content" items="<%=((ListSection)section).getContent()%>">
                        <li>${content}</li>
                    </c:forEach>
                </ul>
            </dd>
        </dl>

        </c:when>
        <c:when test="${type=='EDUCATION' || type=='EXPERIENCE'}">
        <dl><dt>${type.title}</dt></dl>

        <c:forEach var="organization" items="<%=((OrganizationSection)section).getContent()%>">
        <h3><a href="${organization.website}">${organization.title}</a></h3>
        <table class="table">
            <c:forEach var="period" items="${organization.periods}">
                <jsp:useBean id="period" type="com.urise.webapp.model.Organization.Period"/>
                <tr>
                    <td><%=HtmlUtil.periodToHtml(period)%></td>
                    <td>${period.position}</td>
                    <td>${period.responsibilities}</td>
                </tr>
            </c:forEach>
        </table>

        </c:forEach>

        </c:when>
        </c:choose>
        </c:forEach><br/>

</section>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>
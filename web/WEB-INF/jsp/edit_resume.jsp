<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Edit resume ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>NAME</dt>
            <dd><input type="text" required placeholder="Name Surname" maxlength="40"
                       name="full_name" size="30" value="${resume.fullName}"></dd>
        </dl>
        <h3>CONTACTS</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input ${HtmlUtil.contactPattern(type)} name="${type.name()}" size="30"
                                                            value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>SECTIONS:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <h3>${type}</h3>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <input type="text" name="${type}" size="75" value="<%=section%>">
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <textarea type="text" name="${type}" cols="75" rows="5"><%=section%></textarea>
                </c:when>
                <c:when test="${type=='ACHIEVEMENTS' || type=='QUALIFICATIONS'}">
                    <textarea type="text" name="${type}" cols="75" rows="5">
                        <%=String.join("\n", ((ListSection) section).getContent())%></textarea>
                </c:when>
                <c:when test="${type=='EDUCATION' || type=='EXPERIENCE'}">
                    <c:forEach var="organization" items="<%=((OrganizationSection)section).getContent()%>"
                               varStatus="counter">
                        <dl>
                            <dt>ORGANIZATION'S TITLE</dt>
                            <dd><input type="text" name="${type}" size="55" value="${organization.title}"></dd>
                        </dl>
                        <dl>
                            <dt>ORGANIZATION'S WEB-SITE</dt>
                            <dd><input type="url" placeholder="https://website.com"
                                       name="${type}url" size="55" value="${organization.website}"></dd>
                        </dl>
                        <c:forEach var="period" items="${organization.periods}">
                            <jsp:useBean id="period" type="com.urise.webapp.model.Organization.Period"/>
                            <dl>
                                <dt>DATE FROM</dt>
                                <dd><input type="text" name="${type}${counter.index}dateFrom" size="15"
                                           value="<%=DateUtil.format(period.getDateFrom())%>" placeholder="MM:yyyy">
                                </dd>
                                <dt style="width: 100px">DATE TO</dt>
                                <dd><input type="text" name="${type}${counter.index}dateTo" size="15"
                                           value="<%=DateUtil.format(period.getDateTo())%>" placeholder="MM:yyyy"></dd>
                            </dl>

                            <dl>
                                <dt>POSITION</dt>
                                <dd><input type="text" name="${type}${counter.index}position" size="55"
                                           value="${period.position}"></dd>
                            </dl>
                            <dl>
                                <dt>RESPONSIBILITIES</dt>
                                <dd><textarea type="text" name="${type}${counter.index}description" cols="55"
                                              rows="5">${period.responsibilities}</textarea>
                                </dd>
                            </dl>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>

        <button type="submit">SAVE</button>
        <button onclick="history.back()">CANCEL</button>
    </form>
</section>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>

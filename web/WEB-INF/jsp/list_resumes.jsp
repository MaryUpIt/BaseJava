<%@ page import="com.urise.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/style.css">
    <title>List resumes</title>

</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<section>
    <h1>RESUME'S STORAGE</h1>
    <table class="table">
        <tr>
            <th>Full Name</th>
            <th>EMAIL</th>
            <th>OBJECTIVE</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a
                        href="resume?uuid=${resume.uuid}&action=view">
                        ${resume.fullName}
                </a></td>
                <td><%=HtmlUtil.contactToHtml(ContactType.EMAIL, resume.getContact(ContactType.EMAIL))%></td>
                <td>${resume.getSection(SectionType.OBJECTIVE)}</td>
                <td style="text-align: center;"><a href="resume?uuid=${resume.uuid}&action=delete">DELETE</a></td>
                <td style="text-align: center;"><a href="resume?uuid=${resume.uuid}&action=edit">EDIT</a></td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3">
            <td colspan="2" style="text-align: center;"><a href="resume?uuid=${resume.uuid}&action=save">ADD RESUME</a>
            </td>
        </tr>
    </table>
    <button></button>
</section>
</body>
<jsp:include page="fragment/footer.jsp"/>
</html>

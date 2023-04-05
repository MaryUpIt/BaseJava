<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/themes/${theme}.css">
    <link rel="stylesheet" href="css/list_style.css">
    <link rel="stylesheet" href="css/style.css">
    <title>List resumes</title>

</head>
<body>
<jsp:include page="fragments/themes.jsp"/>
<jsp:include page="fragments/header.jsp"/>
<div class="scrollable-panel">
        <table class="table_resumes">
            <tr>
                <td colspan="3">
                <td title="добавить резюме" colspan="2">
                    <a class="link-style" href="resume?action=add&theme=${theme}">
                        <img title="добавить резюме" class="image" src="image_source/${theme}/save.png" alt="add">
                    ADD RESUME</a>
                </td>
            </tr>
            <tr>
                <th>FULL NAME</th>
                <th>EMAIL</th>
                <th>POSITION</th>
                <th>EDIT</th>
                <th>DEL</th>
            </tr>
            <c:forEach items="${resume}" var="resume">
                <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>

                <tr>
                    <td><a class="link-style"
                           href="resume?uuid=${resume.uuid}&action=view&theme=${theme}">
                            ${resume.fullName}
                    </a></td>
                    <td title="отправить письмо">

                        <c:if test="${theme == 'dark'}">
                            <%=HtmlUtil.contactToHtml(ContactType.EMAIL, resume.getContact(ContactType.EMAIL), "dark")%>
                        </c:if>
                        <c:if test="${theme == 'light'}">
                            <%=HtmlUtil.contactToHtml(ContactType.EMAIL, resume.getContact(ContactType.EMAIL), "light")%>
                        </c:if>
                    </td>
                    <td>${resume.getSection(SectionType.OBJECTIVE)}</td>
                    <td title="редактировать"><a href="resume?uuid=${resume.uuid}&action=edit&theme=${theme}">
                        <img class="image" src="image_source/${theme}/edit.png" alt="edit"></a></td>
                    <td title="удалить"><a href="resume?uuid=${resume.uuid}&action=delete&theme=${theme}">
                        <img class="image" src="image_source/${theme}/delete.png" alt="del"></a></td>
                </tr>
            </c:forEach>

        </table>
    </div>
</div>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="themes">
    <div class="theme-title">ТЕМА</div>
    <div class="theme-selector">
        <form action="" method="GET">
            <select name="theme" onchange="this.form.submit()">
                <option value="light" ${theme == null || theme == 'light' ? 'selected' : ''}>СВЕТЛАЯ</option>
                <option value="dark" ${theme == 'dark' ? 'selected' : ''}>ТЕМНАЯ</option>
            </select>
        </form>
    </div>
</div>
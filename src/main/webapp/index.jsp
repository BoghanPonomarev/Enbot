<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page isELIgnored="false" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Upload</title>
</head>
<body>
<div style="color: red">
    <c:if test="${requestScope.errors==null}">
        Ok,success
    </c:if>
    <ul>
    <c:forEach items="${requestScope.errors}" var="error">
            <li>
        ${error}
            </li>
    </c:forEach>
        </ul>
</div>
<form action="PutFile" method="post" enctype="multipart/form-data">
    Name of topic: <input name="topic" type="text"><br>
    Charset: <input name="charset"><br>
    File: <input name="data" type="file"><br>
    <input type="submit"><br>
</form>
</body>
</html>
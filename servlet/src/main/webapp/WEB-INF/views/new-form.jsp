<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title></head>
<body><!-- 상대경로사용, [현재 URL이속한계층경로 + /save] -->
<form action="save" method="post"> username:
    <input type="text" name="username"/> age: <input type="text" name="age"/>
    <button type="submit">전송</button>
</form>
</body>
</html>
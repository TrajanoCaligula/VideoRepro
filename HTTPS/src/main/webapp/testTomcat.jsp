<%-- 
    Document   : testTomcat
    Created on : 28 abr 2024, 22:44:42
    Author     : Alvaro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <script>
            <% if (request.getAttribute("Error") != null) { %>
                window.onload = function() {
                    alert("<%= request.getAttribute("Error") %>");
                };
            <% } %>
            <% if (request.getAttribute("Success") != null) { %>
                window.onload = function() {
                    alert("<%= request.getAttribute("Success") %>");
                };
            <% } %>
        </script>
        <h1>HTTPS Register</h1>
        <form action="ServRegUserHttps" method="post">          
            <table>
                <tr>
                    <td><label>Name:</label></td>
                    <td><input type="text" id="name" name="name" placeholder="Maria" maxlength="100" required></td>
                </tr>
                <tr>
                    <td><label>Surname:</label></td>
                    <td><input type="text" id="surname" name="surname" placeholder="Ramí­rez Sierra" maxlength="100" required></td>
                </tr>
                <tr>
                    <td><label>Email:</label></td>
                    <td><input type="text" id="email" name="email" placeholder="maria@example.com" maxlength="200" required></td>
                </tr>
                <tr>
                    <td><label>User Name:</label></td>
                    <td><input type="text" id="userName" name="userName" placeholder="maria123" maxlength="100" required></td>
                </tr>
                <tr>
                    <td><label>Password:</label></td>
                    <td><input type="password" id="password" name="password" placeholder="Contraseña" maxlength="255" required></td>           
                </tr>
                <tr>
                    <td><label>Confirm password:</label></td>
                    <td><input type="password" id="passwordConfirm" name="passwordConfirm" placeholder="Contraseña" maxlength="255" required></td>
                </tr>
            </table>
            <input type="submit" value="Confirm">
        </form>
    </body>
</html>

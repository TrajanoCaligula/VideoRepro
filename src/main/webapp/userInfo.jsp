<%@ page import="java.io.*,java.util.*" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="jakarta.servlet.http.*" %>
<%@ page import="jakarta.servlet.http.*" %>
<%@ page import="isdcm.DTO.VideoDTO" %>
<%@ page import="isdcm.controller.ServListVid" %>
<%
    HttpSession ses = request.getSession();
    
    String param = (String) ses.getAttribute("USER_LOGGED");
    
    if ((param == null || param.isEmpty())) {
%>      
        <script>
            setTimeout(function() {
                window.location.href = "login.jsp?mensaje=You need to login to acces this page"; 
            }, 0);
        </script>
<%
    } else if(param == "false"){
  %>      
        <script>
            setTimeout(function() {
                window.location.href = "login.jsp?mensaje=You need to login to acces this page"; 
            }, 0);
        </script>
<%     
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style.css">
    <title>User Info</title>
</head>
<body class = "bodyList">
    
        <jsp:include page="navbar.jsp"/>
        <br>
        <div class="videoContainer" id="video">
            <label id="jwt"></label>
        </div>
        
        <%
        String paramJWT = (String) ses.getAttribute("JWT");
        %>
    <script>
        // Recupera el JWT de la sesión de JSP
        var param = "<%= paramJWT %>";

        // Actualiza el contenido del elemento HTML con el JWT
        var jwt = document.getElementById('jwt');
        jwt.textContent = param;
    </script>

    
</body>

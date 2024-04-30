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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        <%
        String paramJWT = (String) ses.getAttribute("JWT");
        %>
    var param = "<%= paramJWT %>";

     // Función para obtener la información del servlet
    function obtenerInformacion() {
        var id = '<%= request.getParameter("id") %>';
        $.ajax({
            url: "ServUserProfile",
            type: "GET",
            dataType: "json",
            success: function(data) {
                console.log("Datos recibidos:", data);
                var html = "";
                if(data !== null) {                    
                    html +=  "<table class='tablaUserContainer'><tr>"+
                                "<td class='tablaUserContainerTD'><label style='font-weight: bold;'>Username: </label>" + 
                                data.username + "</td>" +
                                "<td class='tablaUserContainerTD'><label style='font-weight: bold;'>Name: </label>" +
                                data.name + "</td></tr>" +
                            "<tr>"+
                                "<td class='tablaUserContainerTD'><label style='font-weight: bold;'>Surname: </label>" +                                
                                data.surname + "</td>" +
                                "<td class='tablaUserContainerTD'><label style='font-weight: bold;'>Email: </label>" +
                                data.email + "</td></tr>"+
                            "<tr>"+
                                "<td class='tablaUserContainerTD'><label style='font-weight: bold;'>Num uploaded videos: </label>" +                                
                                data.numVidUploaded + "</td></tr>"+ 
                            "<tr>"+
                                "<td class='tablaUserContainerTD'><label style='font-weight: bold;'>This is your JWT: </label>" +                                
                                param + "</td></tr>"+ 
                            "</table>";                                                       
                }
                $("#userdata").html(html); 
            },
            error: function() {
                console.error('Error charging the data from the server');
            }
        });
    }

    // Llamar a la función al cargar la página
    $(document).ready(function() {
        obtenerInformacion();
    });
</script>
</head>
<body class = "bodyList">
    
        <jsp:include page="navbar.jsp"/>
        <br>
        <div class="videoContainer" id="userdata">
        </div>   
</body>

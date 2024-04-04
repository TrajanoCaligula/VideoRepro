<%@ page import="java.io.*,java.util.*" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="jakarta.servlet.http.*" %>
<%@ page import="jakarta.servlet.http.*" %>
<%@ page import="isdcm.DTO.VideoDTO" %>
<%@ page import="isdcm.controller.ServREST" %>
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
    <title>Watch Video</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    // Función para obtener la información del servlet
    function obtenerInformacion() {
        var id = '<%= request.getParameter("id") %>';
        $.ajax({
            url: "ServREST?id=" + id,
            type: "GET",
            dataType: "json",
            success: function(data) {
                console.log("Datos recibidos:", data);
                var html = "";
                if(data !== null) {
                    var fecha = new Date(data.creationDate); // Convertir a objeto de fecha
                    var year = fecha.getFullYear(); // Obtener el año
                    var month = ('0' + (fecha.getMonth() + 1)).slice(-2); // Obtener el mes (agregar 1 porque los meses comienzan en 0)
                    var day = ('0' + fecha.getDate()).slice(-2); // Obtener el día

                    var fechaFormateada = year + '/' + month + '/' + day;
                    
                    html += "<div class='dataToRight'> <label style='font-weight: bold;'>Uploaded by: </label>" + data.userName+"</div>"+
                            "<video class='displayvid' width='640' height='360' controls>\n\
                            <source class='displayvid' src='" +  data.url + "' type='video/"+data.format+"'> Tu navegador no soporta el elemento de video.</video>"+
                            "<table class='tablaVideoContainer'><tr>"+
                                "<td><label style='font-weight: bold;'>T�tle: </label>" + 
                                data.title + "</td>" +
                                "<td><label style='font-weight: bold;'>Author: </label>" +
                                data.author + "</td></tr>" +
                            "<tr>"+
                                "<td><label style='font-weight: bold;'>Creation Date: </label>" +                                
                                fechaFormateada + "</td>" +
                                "<td><label style='font-weight: bold;'>Duration: </label>" +
                                data.duration + "</td></tr>" +
                            "<tr>"+
                                "<td><label style='font-weight: bold;'>Views: </label>" +
                                data.views + "</td>" +   
                                "<td><label style='font-weight: bold;'>Format: </label>" +
                                data.format + "</td>"+
                            "</tr><table>" +
                            "<div class='dataDescription'><label style='font-weight: bold;'>Description: </label>" +
                            data.description + "</div><br>";                                                       
                }
                $("#video").html(html); 
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
        <div class="videoContainer" id="video">

        </div>

    
</body>

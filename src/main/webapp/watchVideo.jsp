<%-- 
    Document   : elementListVid
    Created on : 8 mar 2024, 3:27:21
    Author     : Alvaro
--%>

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
                window.location.href = "login.jsp?mensaje=No tienes permiso para acceder a esta p�gina"; 
            }, 0);
        </script>
<%
    } else if(param == "false"){
  %>      
        <script>
            setTimeout(function() {
                window.location.href = "login.jsp?mensaje=No tienes permiso para acceder a esta p�gina"; 
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
    <title>Listado Videos</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    // Funci�n para obtener la informaci�n del servlet
    function obtenerInformacion() {
        var id = '<%= request.getParameter("id") %>';
        $.ajax({
            url: "ServWatchVid?id=" + id,
            type: "GET",
            dataType: "json",
            success: function(data) {
                console.log("Datos recibidos:", data);
                var html = "";
                if(data !== null) {
                    var fecha = new Date(data.creationDate); // Convertir a objeto de fecha
                    var a�o = fecha.getFullYear(); // Obtener el a�o
                    var mes = ('0' + (fecha.getMonth() + 1)).slice(-2); // Obtener el mes (agregar 1 porque los meses comienzan en 0)
                    var dia = ('0' + fecha.getDate()).slice(-2); // Obtener el d�a

                    var fechaFormateada = a�o + '/' + mes + '/' + dia;
                    
                    html += "<table class='tablaVideoContainer'><tr>"+
                                "<th><label style='font-weight: bold;'>Titulo: </label>" + 
                                data.title + "</th>" +
                                "<th><label style='font-weight: bold;'>Autor: </label>" +
                                data.author + "</th></tr>" +
                            "<tr>"+
                                "<th><label style='font-weight: bold;'>Fecha de Creaci�n: </label>" +                                
                                fechaFormateada + "</th>" +
                                "<th><label style='font-weight: bold;'>Duraci�n: </label>" +
                                data.duration + "</th></tr>" +
                            "<tr>"+
                                "<th><label style='font-weight: bold;'>Visualizaciones: </label>" +
                                data.views + "</th>" +   
                                "<th><label style='font-weight: bold;'>Formato: </label>" +
                                data.format + "</th>"+
                            "</tr><table>" +
                            "<div class='dataDescription'><label style='font-weight: bold;'>Descripci�n: </label>" +
                            data.description + "</div><br>" + 
                            "<div class='dataToRight'> <label style='font-weight: bold;'>Usuario que lo ha subido: </label>" +
                            data.userName+"</div>";                                                       
                }
                $("#video").html(html); // Agregu� el "#" para seleccionar el ID correctamente
            },
            error: function() {
                console.error('Error al obtener datos del servidor');
            }
        });
    }

    // Llamar a la funci�n al cargar la p�gina
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
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

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style.css">
    <title>Listado Videos</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    // Función para obtener la información del servlet
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
                    var año = fecha.getFullYear(); // Obtener el año
                    var mes = ('0' + (fecha.getMonth() + 1)).slice(-2); // Obtener el mes (agregar 1 porque los meses comienzan en 0)
                    var dia = ('0' + fecha.getDate()).slice(-2); // Obtener el día

                    var fechaFormateada = año + '/' + mes + '/' + dia;
                    
                    html += "<div><label style='font-weight: bold;'>Titulo: </label>" + 
                            data.title + "</div><div class = 'listdatadiv'>" +
                            "<label style='font-weight: bold;'>Autor: </label>" +
                            data.author + "</div><div class = 'listdatadiv'>" +
                            "<label style='font-weight: bold;'>Fecha de Creación: </label>" +                                
                            fechaFormateada + "</div><div class = 'listdatadiv'>" +
                            "<label style='font-weight: bold;'>Duración: </label>" +
                            data.duration + "</div><div class = 'listdatadiv'>" +
                            "<label style='font-weight: bold;'>Visualizaciones: </label>" +
                            data.views + "</div><div class = 'listdatadiv'>" +
                            "<label style='font-weight: bold;'>Descripción: </label>" +
                            data.description + "</div><div class = 'listdatadiv'>" +
                            "<label style='font-weight: bold;'>Formato: </label>" +
                            data.format + "</div><div class = 'listdatadiv'>" +
                            "<label style='font-weight: bold;'>Usuario que lo ha subido: </label>" +
                            data.userName + "</div>";                                
                }
                $("#video").html(html); // Agregué el "#" para seleccionar el ID correctamente
            },
            error: function() {
                console.error('Error al obtener datos del servidor');
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

        <div class="contenedor-listado" id="video">

        </div>
    
</body>
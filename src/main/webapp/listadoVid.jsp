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
    <style>
        li {
            background-color: #f9f9f9;
            margin-bottom: 10px;
            padding: 10px;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        li:hover {
            background-color: #e0e0e0;
        }
    </style>
    <script>
        // Funci�n para obtener la informaci�n del servlet
        function obtenerInformacion() {
            $.ajax({
                url: "ServListVid",
                type: "GET",
                dataType: "json",
                success: function(data) {
                    console.log("Datos recibidos:", data);
                    var html = "";
                    for (var i = 0; i < data.length; i++) {
                        var fecha = new Date(data[i].creationDate); // Convertir a objeto de fecha
                        var a�o = fecha.getFullYear(); // Obtener el a�o
                        var mes = ('0' + (fecha.getMonth() + 1)).slice(-2); // Obtener el mes (agregar 1 porque los meses comienzan en 0)
                        var dia = ('0' + fecha.getDate()).slice(-2); // Obtener el d�a

                        var fechaFormateada = a�o + '/' + mes + '/' + dia;
                        
                        html += "<li><div><label style='font-weight: bold;'>Titulo: </label>" + 
                                data[i].title + "</div><div class = 'listdatadiv'>" +
                                "<label style='font-weight: bold;'>Autor: </label>" +
                                data[i].author + "</div><div class = 'listdatadiv'>" +
                                "<label style='font-weight: bold;'>Fecha de Creaci�n: </label>" +                                
                                fechaFormateada + "</div><div class = 'listdatadiv'>" +
                                "<label style='font-weight: bold;'>Duraci�n: </label>" +
                                data[i].duration + "</div><div class = 'listdatadiv'>" +
                                "<label style='font-weight: bold;'>Visualizaciones: </label>" +
                                data[i].views + "</div><div class = 'listdatadiv'>" +
                                "<label style='font-weight: bold;'>Descripci�n: </label>" +
                                data[i].description + "</div><div class = 'listdatadiv'>" +
                                "<label style='font-weight: bold;'>Formato: </label>" +
                                data[i].format + "</div><div class = 'listdatadiv'>" +
                                "<label style='font-weight: bold;'>Usuario que lo ha subido: </label>" +
                                data[i].userName + "</div>" +
                                "<button class='botonVer' id='showVideo' onclick=\"window.location.href = 'watchVideo.jsp?id="+data[i].id+"';\">Ver Video</buton>" +"</li>";
                                
                        
                    }
                    $("#lista-videos").html(html); // Agregu� el "#" para seleccionar el ID correctamente
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
        <div class="rectangulo-detras-h1"></div>
        <h1 class="h1List">Listado de Videos</h1> 
        <br>

        <div class="contenedor-listado">
            <ol class="no-numeros" id="lista-videos">
                
            </ol>
        </div>
    
</body>
</html>
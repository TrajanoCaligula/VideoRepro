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
            .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #333;
        }

        ul {
            list-style-type: none;
            padding: 0;
        }

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
        // Función para obtener la información del servlet
        function obtenerInformacion() {
            $.ajax({
                url: "ServListVid",
                type: "GET",
                dataType: "json",
                success: function(data) {
                    console.log("Datos recibidos:", data);
                    var html = "";
                    for (var i = 0; i < data.length; i++) {
                        html += "<li><label style='font-weight: bold;'>Titulo: </label>" + 
                                data[i].title + "<br>" +
                                "<label style='font-weight: bold;'>Autor: </label>" +
                                data[i].author + "<br>" +
                                "<label style='font-weight: bold;'>Fecha de Creación: </label>" +                                
                                data[i].creationDate + "<br>" +
                                "<label style='font-weight: bold;'>Duración: </label>" +
                                data[i].duration + "<br>" +
                                "<label style='font-weight: bold;'>Visualizaciones: </label>" +
                                data[i].views + "<br>" +
                                "<label style='font-weight: bold;'>Descripción: </label>" +
                                data[i].description + "<br>" +
                                "<label style='font-weight: bold;'>Formato: </label>" +
                                data[i].format + "<br>" +
                                "<label style='font-weight: bold;'>Usuario que lo ha subido: </label>" +
                                data[i].userName +"</li>";
                                
                        
                    }
                    $("#lista-videos").html(html); // Agregué el "#" para seleccionar el ID correctamente
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
<body>
    
        <h1>Listado Videos</h1>
        <ul id="lista-videos"></ul>
    
</body>
</html>
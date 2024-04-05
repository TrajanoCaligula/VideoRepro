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
    <title>Video List</title>
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
                        var fecha = new Date(data[i].creationDate); // Convertir a objeto de fecha
                        var any = fecha.getFullYear(); // Obtener el año
                        var mes = ('0' + (fecha.getMonth() + 1)).slice(-2); // Obtener el mes (agregar 1 porque los meses comienzan en 0)
                        var dia = ('0' + fecha.getDate()).slice(-2); // Obtener el día

                        var fechaFormateada = any + '/' + mes + '/' + dia;
                        
                        html += "<li><div><label style='font-weight: bold;'>Title: </label>" + 
                                data[i].title + "</div><div class = 'listdatadiv'>" +
                                "<label style='font-weight: bold;'>Author: </label>" +
                                data[i].author + "</div><div class = 'listdatadiv'>" +
                                "<label style='font-weight: bold;'>Creation Date: </label>" +                                
                                fechaFormateada + "</div><div class = 'listdatadiv'>" +
                                "<label style='font-weight: bold;'>Duration: </label>" +
                                data[i].duration + "</div><div class = 'listdatadiv'>" +
                                "<label style='font-weight: bold;'>Views: </label>" +
                                data[i].views + "</div><div class = 'listdatadiv'>" +
                                "<label style='font-weight: bold;'>Description: </label>" +
                                data[i].description + "</div><div class = 'listdatadiv'>" +
                                "<label style='font-weight: bold;'>Format: </label>" +
                                data[i].format + "</div><div class = 'listdatadiv'>" +
                                "<label style='font-weight: bold;'>Uploaded by: </label>" +
                                data[i].userName + "</div>" +
                                "<button class='botonVer' id='showVideo' onclick=\"window.location.href = 'watchVideo.jsp?id="+data[i].id+"';\">Watch Video</buton>" +"</li>";
                                
                        
                    }
                    if( data === null||data.length <= 0)html += "<label>There are any video in our repository</label>";
                    $("#lista-videos").html(html);
                },
                error: function() {
                     var html = "";
                     html += "<label>There are any video in our repository</label>";               
                    $("#lista-videos").html(html); 
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
        <script>
        <% if (request.getAttribute("Success") != null) { %>
            window.onload = function() {
                alert("<%= request.getAttribute("Success") %>");
            };
        <% } %>
        <% if (request.getAttribute("Error") != null) { %>
            window.onload = function() {
                alert("<%= request.getAttribute("Error") %>");
            };
        <% } %>
        </script>
        <div class="rectangulo-detras-h1"></div>
        <h1 class="h1List">Video List</h1> 
        <br>

        <div class="contenedor-listado">
            <ol class="no-numeros" id="lista-videos">
                
            </ol>
        </div>
    
</body>
</html>

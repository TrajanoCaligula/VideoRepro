<%@ page import="java.io.*,java.util.*" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="jakarta.servlet.http.*" %>
<%@ page import="jakarta.servlet.http.*" %>
<%@ page import="isdcm.DTO.VideoDTO" %>
<%@ page import="isdcm.controller.ServREST" %>

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
    

</head>
<body class = "bodyList">
    
        <jsp:include page="navbar.jsp"/>
        <div class="rectangulo-detras-h1"></div>
        <h1 class="h1List">Video Searcher</h1> 
        <br>
            <table>
                <tr> 
                    <td><label for="title">Title:</label></td>
                    <td><textarea  type="text" id="title" name="title"></textarea></td>
                    <td><label for="author">Author:</label></td>
                    <td><textarea  type="text" id="author" name="author"></textarea></td>
                    <td><label for="day">Day:</label></td>
                    <td><select id="day" name="day">
                            <option>----</option>
                        </select></td>
                    <td><label for="month">Month:</label></td>
                    <td><span></span></td>
                    <td><select id="month" name="month">
                        <option>----</option>
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                        <option>5</option>
                        <option>6</option>
                        <option>7</option>
                        <option>8</option>
                        <option>9</option>
                        <option>10</option>
                        <option>11</option>
                        <option>12</option>
                    </select></td>
                    <td><label for="year">Year:</label></td>
                    <td><select id="year" name="year">
                            <option>----</option>
                    </select></td>
                    <td><button onclick="obtenerInformacion()">Search</button></td>
                </tr>
            </table>
        <div class="contenedor-listado">
            <ol class="no-numeros" id="lista-videos">
                
            </ol>
        </div>
    
</body>


<script>
     // Obtener el select de días
     var selectDias = document.getElementById("day");

     // Añadir opciones al select
     for (var i = 1; i <= 31; i++) {
         var option = document.createElement("option");
         option.text = i;
         option.value = i;
         selectDias.add(option);
     }
     var selectDias = document.getElementById("year");

     // Añadir opciones al select
     for (var i = 1; i <= 200; i++) {
         var option = document.createElement("option");
         option.text = i+1824;
         option.value = i;
         selectDias.add(option);
     }
 </script>
     <script>
        // FunciÃ³n para obtener la informaciÃ³n del servlet
        function obtenerInformacion() {
            var title = document.getElementById("title").value;
            var author = document.getElementById("author").value;
            var day = document.getElementById("day").value;
            var month = document.getElementById("month").value;
            var year = document.getElementById("year").value;
            
            $.ajax({
                url: "ServREST",
                type: "POST",
                dataType: "json",
                data: {
                    title: title,
                    author: author,
                    day: day,
                    month: month,
                    year: year
                },
                success: function(data) {
                    console.log("Datos recibidos:", data);
                    var html = "";
                    for (var i = 0; i < data.length; i++) {
                        var fecha = new Date(data[i].creationDate); // Convertir a objeto de fecha
                        var any = fecha.getFullYear(); // Obtener el aÃ±o
                        var mes = ('0' + (fecha.getMonth() + 1)).slice(-2); // Obtener el mes (agregar 1 porque los meses comienzan en 0)
                        var dia = ('0' + fecha.getDate()).slice(-2); // Obtener el dÃ­a

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
                                "<button class='botonVer customButton' id='showVideo' data-video-id='"+data[i].id+"'>Watch Video</buton>" +"</li>";
                                
                        
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
        
    function buttonClickHandler(event) {
        var videoId = event.target.getAttribute("data-video-id");
        putView(videoId);
        watchVideo(videoId);
    }
    
     $(document).on("click", ".customButton", buttonClickHandler);
        function putView(videoId) {
            var id = videoId.toString();
            $.ajax({
                url: "ServREST?videoID="+id,
                type: "PUT",
                dataType: "json",
                success: function(data) {
                    console.log("Datos recibidos:", data);
                },
                error: function() {
                    console.log("error");
                }
            });
        }

        // Función para obtener la información del video actualizada después de la actualización
        function watchVideo(videoId) {
            var id = videoId.toString();
            setTimeout(function() {
                // Redireccionar a otra vista (página JSP)
                window.location.href = "reproduccion.jsp?id="+id;
            }, 200);
            
        }

    </script>

</html>

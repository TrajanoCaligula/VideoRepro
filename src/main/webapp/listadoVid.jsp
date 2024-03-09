<%@ page import="java.io.*,java.util.*" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="jakarta.servlet.http.*" %>

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
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listado Din�mico de Datos</title>
    <link rel="stylesheet" href="style.css">
</head>
<body class="bodyList">
    <!--<div id="navbarContainer"></div>-->
    <jsp:include page="navbar.jsp"/>
    <div class="rectangulo-detras-h1"></div>
        <h1 class="h1List">Listado de Videos</h1> <!-- Encabezado dentro del cuerpo del documento -->
        <br>
        <br>
    <div class="contenedor-listado">

        <ol id="listado">
            <!-- Aquí se generará dinámicamente el listado -->
        </ol>
    </div>

    
</body>
<script>
    // Supongamos que tenemos una lista de libros
    var listaVideos = [
        { titulo: "El Quijote", autor: "Miguel de Cervantes", fecha: "1605", numRep: 1000, descripcion: "La historia de Don Quijote y Sancho Panza aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", formato: "Impreso" , duracion:"2:00:03", numRep: 10000},
        { titulo: "Cien años de soledad", autor: "Gabriel García Márquez", fecha: "1967", numRep: 800, descripcion: "La historia de la familia Buendía en Macondo", formato: "Digital",  duracion:"4:00:03", numRep: 300},
        { titulo: "Harry Potter y la piedra filosofal", autor: "J.K. Rowling", fecha: "1997", numRep: 1200, descripcion: "La historia del joven mago Harry Potter", formato: "Audiolibro", duracion:"00:05:03", numRep: 1000000 }
        // Puedes agregar más libros según sea necesario
    ];

    // Obtener el contenedor donde se mostrará el listado de libros
    var contenedor = document.getElementById("listado");

    // Generar el listado de libros dinámicamente
    listaVideos.forEach(function(video) {
        var divVideo = document.createElement("div");
        divVideo.classList.add("video");

        var titulo = document.createElement("div");
        titulo.classList.add("titulo");
        titulo.textContent = video.titulo;

        var autor = document.createElement("div");
        autor.classList.add("autor");
        autor.textContent = "Autor: " + video.autor;

        var formato = document.createElement("div");
        formato.classList.add("formato");
        formato.textContent = "Formato: " + video.formato;

        var numRep = document.createElement("div");
        numRep.classList.add("numRep");
        numRep.textContent = "NumRep: " + video.numRep;

        var fecha = document.createElement("div");
        fecha.classList.add("fecha");
        fecha.textContent = "Fecha: " + video.fecha;

        var duracion = document.createElement("div");
        duracion.classList.add("duracion");
        duracion.textContent = "Duraci�n: " + video.duracion;

        var descripcion = document.createElement("div");
        descripcion.classList.add("descripcion");
        descripcion.textContent = "Descripci�n: " + video.descripcion;
        
        var botonVer = document.createElement("button");
        botonVer.className = "botonVer";
        botonVer.textContent = "Ver video";
        botonVer.onclick = function() {
          window.location.href = "Video.jsp?titulo=" + video.titulo;
        };

        divVideo.appendChild(titulo);
        divVideo.appendChild(fecha);
        divVideo.appendChild(duracion);
        divVideo.appendChild(formato);
        divVideo.appendChild(numRep);
        divVideo.appendChild(autor);
        divVideo.appendChild(fecha);
        divVideo.appendChild(descripcion);
        divVideo.appendChild(botonVer);

        contenedor.appendChild(divVideo);
    });

</script>
</html>

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
                window.location.href = "login.jsp?mensaje=No tienes permiso para acceder a esta página"; 
            }, 0);
        </script>
<%
    } else if(param == "false"){
  %>      
        <script>
            setTimeout(function() {
                window.location.href = "login.jsp?mensaje=No tienes permiso para acceder a esta página"; 
            }, 0);
        </script>
<%     
    }
%>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listado Dinámico de Datos</title>
    <link rel="stylesheet" href="style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
       
    <h1>Listado de Datos</h1>
    <ul>
        <% 
        // Obtener la lista de datos del objeto request
        ArrayList<VideoDTO> listado = (ArrayList<VideoDTO>) request.getAttribute("videoList");
        if (listado != null) {
            for (VideoDTO dato : listado) {
        %>
            <li><%= dato.getTitle() %></li>
            <p>AAAA</p>
        <% 
            }
        }
        %>
    </ul>
</body>
<script>
    // Función para recargar la página automáticamente
    function recargarPagina() {
      setTimeout(function() {
        location.reload();
      }, 2000);
    }

    // Llamar a la función al cargar la página
    recargarPagina();

    // **Llamada al servlet**
    request.getRequestDispatcher("ServListVid").forward(request, response);
</script>
</html>
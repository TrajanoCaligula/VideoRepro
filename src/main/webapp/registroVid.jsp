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
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><title>Regitro Usuario</title>
        <link rel="stylesheet" href="style.css">
       </head>
<body>
    <jsp:include page="navbar.jsp"/>
    <form class="reg-vid-form" action="ServRegVid" method="post" enctype="multipart/form-data">
        <h2>Registar Video</h2>
        <label id = "error" style="color: red;">
            ${requestScope.errorRegVidFail}
        </label>
        <table>
            <tr>
                <td class="columna1"><p>Tí­tulo:</p></td>
                <td><input type="text" id="title" name="title" placeholder="El mejor video" maxlength="100" required></td>
            </tr>
            <tr>
                <td><p>Autor:</p></td>
                <td><input type="text" id="author" name="author" placeholder="Sara" maxlength="100" required></td>
            </tr>
            <tr>
                <td><p>Fecha de creación:</p></td>
                <td><input type="date" id = "creationDate" name="creationDate"  required></td>
            </tr>
            <tr>
                <td><p>Duración:</p></td>
                <td><input type="time" min="00:00:00" max="99:59:59" step="1" id="duration" name="duration" required></td>
            </tr>
            <tr>
                <td class="textarealabel-celda"><p>Descripción:</p></td>
                <td><textarea class= "reg-vid-textarea" id="description" name="description" rows="4" maxlength="255" required></textarea></td>               
            </tr>
            <!--<tr>
                <td><p>Formato:</p></td>
                <td><select id="format" name="format">
                    <option value="mp4">MP4</option>
                    <option value="avi">AVI</option>
                    <option value="mkv">MKV</option>
                    <option value="mov">MOV</option>
                    <option value="wmv">WMV</option>
                    <option value="avchd">AVCHD</option>
                    <option value="flv">FLV</option>
                    <option value="f4v">F4V</option>
                    <option value="swf">SWF</option>
                    <option value="webm">WEBM</option>
                    <option value="h264">H264</option>
                    <option value="ogg">OGG</option>
                    <option value="3gp">3GP</option>
                    <option value="3g2">3G2</option>
                    <option value="m4v">M4V</option>
                    <option value="mpg">MPG</option>
                    <option value="mpeg">MPEG</option>
                    <option value="m2v">M2V</option>
                    <option value="m4v">M4V</option>
                    <option value="vob">VOB</option>
                    <option value="flv">FLV</option> 
                </select>
            </td>
            </tr>-->
            <tr>
                <td><p>Video:</p></td>
                <td><input type="file" id="videoFile" name="videoFile" accept="video/*" required></td>
            </tr>
        </table>
        <input type="submit" value="Subir video">
    </form>
</body> 
<script>
    // Obtener el select
    var select = document.getElementById('format');
    
    // Obtener las opciones
    var opciones = select.getElementsByTagName('option');
    
    // Convertir las opciones en un array para ordenar
    var opcionesArray = Array.prototype.slice.call(opciones, 0);
    
    // Ordenar las opciones alfabÃ©ticamente
    opcionesArray.sort(function(a, b) {
        return a.text.localeCompare(b.text);
    });
    
    // Limpiar el select
    select.innerHTML = '';
    
    // Agregar las opciones ordenadas al select
    opcionesArray.forEach(function(opcion) {
        select.appendChild(opcion);
    });
</script>
</html>

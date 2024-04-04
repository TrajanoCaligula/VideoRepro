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
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><title>Regitro Usuario</title>
        <link rel="stylesheet" href="style.css">
    </head>
<body>
    <jsp:include page="navbar.jsp"/>
    <form class="reg-vid-form" action="ServRegVid" method="post" enctype="multipart/form-data">
        <h2>New Video</h2>
        <label id = "error" style="color: red;">
            ${requestScope.errorRegVidFail}
        </label>
        <table>
            <tr>
                <td class="columna1"><p>Title:</p></td>
                <td><input type="text" id="title" name="title" placeholder="El mejor video" maxlength="100" required></td>
            </tr>
            <tr>
                <td><p>Author:</p></td>
                <td><input type="text" id="author" name="author" placeholder="Sara" maxlength="100" required></td>
            </tr>
            <tr>
                <td><p>Creation Date:</p></td>
                <td><input type="date" id = "creationDate"  min="1900-01-01" max="2024-05-30" name="creationDate"  required></td>
            </tr>
            <tr>
                <td><p>Duration:</p></td>
                <td><input type="time" min="00:00:00" max="99:59:59" step="1" id="duration" name="duration" required></td>
            </tr>
            <tr>
                <td class="textarealabel-celda"><p>Description:</p></td>
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
        <input type="submit" value="Upload video">
    </form>
</body> 
<!--<script>
    var select = document.getElementById('format');
    var opciones = select.getElementsByTagName('option');
    var opcionesArray = Array.prototype.slice.call(opciones, 0);
    
    // Ordenar las opciones alfabéticamente
    opcionesArray.sort(function(a, b) {
        return a.text.localeCompare(b.text);
    });
    select.innerHTML = '';
    opcionesArray.forEach(function(opcion) {
        select.appendChild(opcion);
    });
</script>-->
</html>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><title>Regitro Usuario</title>
    <link rel="stylesheet" href="estilos.css">
</head>
<body>
    <form class="reg-vid-form" action="procesar_login.php" method="post">
        <h2>Registro de Usuario</h2>
        <table>
            <tr>
                <td class="columna1"><p>Título:</p></td>
                <td><input type="text" name="Título" placeholder="El mejor video" required></td>
            </tr>
            <tr>
                <td><p>Autor:</p></td>
                <td><input type="text" name="contraseña" placeholder="Sara" required></td>
            </tr>
            <tr>
                <td><p>Fecha de cración:</p></td>
                <td><input type="date" id = "fecha" name="fecha" placeholder="maria@example.com" required></td>
            </tr>
            <tr>
                <td><p>Duración:</p></td>
                <td><input type="time" min="00:00:00" max="23:59:59" step="1" name="duración" placeholder="maria123" required></td>
            </tr>
            <tr>
                <td class="textarealabel-celda"><p>Descripción:</p></td>
                <td><textarea class= "reg-vid-textarea"id="mensaje" name="mensaje" rows="4"></textarea></td>               
            </tr>
            <tr>
                <td><p>Formato:</p></td>
                <td><select id="formato" name="formato">
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
                    <!-- Agrega más opciones según sea necesario -->
                </select>
            </td>
            </tr>
            <tr>
                <td><p>Video:</p></td>
                <td><input type="file" id="videoFile" name="videoFile" accept="video/*" required></td>
            </tr>
        </table>
        <input type="submit" value="Enviar Registro">
    </form>
</body> 
<script>
    // Obtener el select
    var select = document.getElementById('formato');
    
    // Obtener las opciones
    var opciones = select.getElementsByTagName('option');
    
    // Convertir las opciones en un array para ordenar
    var opcionesArray = Array.prototype.slice.call(opciones, 0);
    
    // Ordenar las opciones alfabéticamente
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
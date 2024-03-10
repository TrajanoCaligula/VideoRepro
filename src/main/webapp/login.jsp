<%-- 
    Document   : login
    Created on : 12 feb 2024, 15:48:15
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script>
    // Obtener el mensaje de advertencia de la URL
    var urlParams = new URLSearchParams(window.location.search);
    var mensaje = urlParams.get('mensaje');
    // Mostrar un cuadro de alerta con el mensaje
    if (mensaje) {
        alert(mensaje);
    }
</script>
 
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Iniciar Sesión</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <jsp:include page="navbar.jsp"/>
        <form class="login-form" action="ServLogUser" method="post">
            <h2>Iniciar Sesión</h2>
            <label id = "error" style="color: red;">
                ${requestScope.errorUserNameInvalid}
            </label>
            <table>
                <tr>
                    <td><p>Usuario:</p></td>
                    <td><input type="text" id="USERNAME" name="USERNAME" placeholder="Usuario" maxlength="100" required></td>
                </tr>
                <tr>
                    <td><p>Password:</p></td>
                    <td><input type="password" id="PASSWORD" name="PASSWORD" placeholder="Contraseña" maxlength="255" required></td>
                    <td><img width="19" height="15" id="eyeIcon" src="eye_slash.png" alt="Ver contraseña"></td>                    
                </tr>
            </table>
            <input type="submit" value="Iniciar Sesión">
        </form>
    </body>
    <script>
        const passwordInput = document.getElementById('PASSWORD');
        const eyeIcon = document.getElementById('eyeIcon');
        const error = document.getElementById('error');

        eyeIcon.addEventListener('click', function() {
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                eyeIcon.src = 'eye_open.png';
            } else {
                passwordInput.type = 'password';
                eyeIcon.src = 'eye_slash.png';
            }
        });
        

    </script>
</html>

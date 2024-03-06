<%-- 
    Document   : login
    Created on : 22 feb 2024, 15:48:15
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

 
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Iniciar Sesión</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <form class="login-form" action="procesar_login.php" method="post">
            <h2>Iniciar Sesión</h2>
            <table>
                <tr>
                    <td><p>Usuario:</p></td>
                    <td><input type="text" id="userName" name="userName" placeholder="Usuario" required></td>
                </tr>
                <tr>
                    <td><p>Password:</p></td>
                    <td><input type="password" id="password" name="password" placeholder="Contraseña" required></td>
                    <td><img width="19" height="15" id="eyeIcon" src="eye_slash.png" alt="Ver contraseña"></td>
                    
                </tr>
            </table>
            <input type="submit" value="Iniciar Sesión">
        </form>
    </body>
    <script>
        const passwordInput = document.getElementById('password');
        const eyeIcon = document.getElementById('eyeIcon');

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

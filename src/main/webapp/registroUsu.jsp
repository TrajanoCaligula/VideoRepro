<%-- 
    Document   : registroUsu
    Created on : 3 mar 2024, 15:48:15
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
 
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Regitro Usuario</title>
    <link rel="stylesheet" href="estilos.css">
</head>
<body>
    <form class= "reg-usu-form"action="procesar_login.php" method="post">
        <h2>Registro de Usuario</h2>
        <table>
            <tr>
                <td><p>Nombre:</p></td>
                <td><input type="text" name="sombre" placeholder="Maria" required></td>
            </tr>
            <tr>
                <td><p>Apellidos:</p></td>
                <td><input type="text" name="apellidos" placeholder="Ramírez Sierra" required></td>
            </tr>
            <tr>
                <td><p>Correo electrónico:</p></td>
                <td><input type="text" name="corre electrónico" placeholder="maria@example.com" required></td>
            </tr>
            <tr>
                <td><p>Nombre de usuario:</p></td>
                <td><input type="text" name="usuario" placeholder="maria123" required></td>
            </tr>
            <tr>
                <td><label for="password">Contraseña:</label></td>
                <td><input type="password" id="password" name="contraseña" placeholder="Contraseña" required></td>
                <td><img width="19" height="15" id="passEye" src="eye_slash.png" alt="Ver contraseña"></td>          
            </tr>
            <tr>
                <td><p>Repetir contraseña:</p></td>
                <td><input type="password" id="repassword" name="recontraseña" placeholder="Contraseña" required></td>
                <td><img width="19" height="15" id="repassEye" src="eye_slash.png" alt="Ver contraseña"></td>
               
            </tr>
        </table>
        <input type="submit" value="Iniciar Sesión">
    </form>
</body> 
<script>
    const passwordInput = document.getElementById('password');
    const repasswordInput = document.getElementById('repassword');
    const eyeIcon = document.getElementById('passEye');
    const eyeIconRe = document.getElementById('repassEye');

    passEye.addEventListener('click', function() {
        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            eyeIcon.src = 'eye_open.png';
        } else {
            passwordInput.type = 'password';
            eyeIcon.src = 'eye_slash.png';
        }
    });
    repassEye.addEventListener('click', function() {
        if (repasswordInput.type === 'password') {
            repasswordInput.type = 'text';
            eyeIconRe.src = 'eye_open.png';
        } else {
            repasswordInput.type = 'password';
            eyeIconRe.src = 'eye_slash.png';
        }
    });
</script>
</html>
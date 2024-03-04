<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Barra de Navegación</title>
    <link rel="stylesheet" href="estilos.css">
</head>
<body>
    <div class="navbar">
        <a href="login.html">Lista de Videos</a> <span class="separador">|</span>
        <a href="login.html">Registrar Video</a> <span class="separador">|</span>
        <a href="login.html">Login</a> <span class="separador">|</span>
        <a href="registroUsu.html">Registro</a> <span class="separador">|</span>
        <span id="usuario"></span> <!-- Espacio para mostrar información del usuario -->
        <a href="javascript:void(0)" onclick="cerrarSesion()">Cerrar Sesión</a> <!-- Botón para cerrar sesión -->
    </div> 
   
</body> 
<script>
    // Aquí puedes agregar lógica para mostrar información del usuario o controlar la sesión
    
    // Ejemplo de función para cerrar sesión
    function cerrarSesion() {
        // Aquí puedes agregar lógica para cerrar la sesión del usuario
        alert("Sesión cerrada");
        // Redirigir a la página de inicio de sesión, por ejemplo:
        window.location.href = "login.html";
    }
    function cambiarCursor() {
        document.getElementById("miTexto").style.cursor = "pointer";
    }
</script>
</html>
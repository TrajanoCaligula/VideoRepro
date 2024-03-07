<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Barra de Navegación</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="navbar">
        <a id="listaVid" href="login.html">Lista de Videos</a> <span id="s1"class="separador">|</span>
        <a id="regVid" href="login.html">Registrar Video</a> <span id="s2" class="separador">|</span>
        <a id="login" href="login.html">Login</a> <span id="s3" class="separador">|</span>
        <a id="regUs" href="registroUsu.html">Registro</a> <span id="s4" class="separador">|</span>
        <a id="nombreUsuario" ></a>  <span id="s5" class="separador">|</span><!-- Espacio para mostrar información del usuario -->
        <a id="closeSession" href="javascript:void(0)" onclick="cerrarSesion()">Cerrar Sesión</a> <!-- Botón para cerrar sesión -->
    </div> 
   
</body> 
<script>
    // Aquí puedes agregar lógica para mostrar información del usuario o controlar la sesión
    var usuarioLogueado = true; // O false, dependiendo de tu lógica de autenticación

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


    var enlaceLogin = document.getElementById('login');
    var enlaceRegistro = document.getElementById('regVid');
    var nombreUsuario = document.getElementById('nombreUsuario');
    var closeSesion = document.getElementById('closeSession');
    var s1 = document.getElementById('s1');
    var s2 = document.getElementById('s2');
    var s3 = document.getElementById('s3');
    var s4 = document.getElementById('s4');
    var s5 = document.getElementById('s5');



    if (usuarioLogueado) {
    // Si el usuario está logueado, ocultamos los enlaces de login y registro y mostramos el nombre de usuario
    enlaceLogin.style.display = 'none';
    enlaceRegistro.style.display = 'none';
    nombreUsuario.style.display = 'inline';
    closeSesion.style.display ='inline';
    nombreUsuario.textContent = "Nombre de usuario";
    s2.style.display='none';
    s3.style.display='none';
    s4.style.display='inline';
    s5.style.display='inline';

  } else {
    // Si el usuario no está logueado, mostramos los enlaces de login y registro y ocultamos el nombre de usuario
    enlaceLogin.style.display = 'inline';
    enlaceRegistro.style.display = 'inline';
    closeSesion.style.display ='none';
    nombreUsuario.style.display = 'none'; // Ocultamos el nombre de usuario
    s2.style.display='inline';
    s3.style.display='inline';
    s4.style.display='none';
    s5.style.display='none';

  }

</script>
</html>

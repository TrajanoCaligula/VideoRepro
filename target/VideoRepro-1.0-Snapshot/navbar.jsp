<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Barra de Navegaci髇</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="navbar">
        <a id="listaVid" href="listadoVid.jsp">Lista de Videos</a> <span id="s1"class="separador">|</span>
        <a id="regVid" href="registroVid.jsp">Registrar Video</a> <span id="s2" class="separador">|</span>
        <a id="login" href="login.jsp">Login</a> <span id="s3" class="separador">|</span>
        <a id="regUs" href="registroUsu.jsp">Registro</a> <span id="s4" class="separador">|</span>
        <a id="nombreUsuario" ></a>  <span id="s5" class="separador">|</span><!-- Espacio para mostrar informaci贸n del usuario -->
        <a id="closeSession" href="javascript:void(0)" onclick="cerrarSesion()">Cerrar Sesi髇</a> <!-- Bot贸n para cerrar sesi贸n -->
    </div> 
   
</body> 
<script>
    // Aqu铆 puedes agregar l贸gica para mostrar informaci贸n del usuario o controlar la sesi贸n
    var usuarioLogueado = false; // O false, dependiendo de tu l贸gica de autenticaci贸n

    // Ejemplo de funci贸n para cerrar sesi贸n
    function cerrarSesion() {
        // Aqu铆 puedes agregar l贸gica para cerrar la sesi贸n del usuario
        alert("Sesi髇 cerrada");
        // Redirigir a la p谩gina de inicio de sesi贸n, por ejemplo:
        window.location.href = "login.jsp";
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
    // Si el usuario est谩 logueado, ocultamos los enlaces de login y registro y mostramos el nombre de usuario
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
    // Si el usuario no est谩 logueado, mostramos los enlaces de login y registro y ocultamos el nombre de usuario
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

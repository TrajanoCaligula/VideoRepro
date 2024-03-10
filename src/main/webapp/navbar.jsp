<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Barra de Navegaci�n</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="navbar">
        <a id="listaVid" href="listadoVid.jsp">Lista de Videos</a> <span id="s1"class="separador">|</span>
        <a id="regVid" href="registroVid.jsp">Registrar Video</a> <span id="s2" class="separador">|</span>
        <a id="login" href="login.jsp">Login</a> <span id="s3" class="separador">|</span>
        <a id="regUs" href="registroUsu.jsp">Registro</a> <span id="s4" class="separador">|</span>
        <a id="nombreUsuario" ></a>  <span id="s5" class="separador">|</span><!-- Espacio para mostrar información del usuario -->
        <a id="closeSession" href="javascript:void(0)" onclick="cerrarSesion()">Cerrar Sesi�n</a> <!-- Botón para cerrar sesión -->
    </div> 
   
</body> 
<script>
    // Aquí puedes agregar lógica para mostrar información del usuario o controlar la sesión
    var ses = '<%= request.getSession(false) %>';
    // Obtener el atributo "USER_LOGGED"
    var userLogged = '<%= session.getAttribute("USER_LOGGED") %>';
    var userName = '<%= session.getAttribute("USERNAME") %>';
     var currentPage = "<%= request.getServletPath() %>";
    // Ejemplo de función para cerrar sesión
    function cerrarSesion() {
        // Aquí puedes agregar lógica para cerrar la sesión del usuario
        alert("Sesi�n cerrada");
        // Redirigir a la página de inicio de sesión, por ejemplo:
        window.location.href = "login.jsp";
    }
    function cambiarCursor() {
        document.getElementById("miTexto").style.cursor = "pointer";
    }

    var listaVid = document.getElementById('listaVid');
    var regVid = document.getElementById('regVid');
    var login = document.getElementById('login');
    var regUs = document.getElementById('regUs');
    var navuserName = document.getElementById('nombreUsuario');
    var closeSesion = document.getElementById('closeSession');
    var s1 = document.getElementById('s1');
    var s2 = document.getElementById('s2');
    var s3 = document.getElementById('s3');
    var s4 = document.getElementById('s4');
    var s5 = document.getElementById('s5');

    listaVid.style.display = 'inline';
    regVid.style.display = 'inline';
    login.style.display = 'inline';
    regUs.style.display = 'inline';
    navuserName.style.display = 'inline';
    closeSesion.style.display ='inline';
    nombreUsuario.textContent = "";
    s1.style.display='inline';
    s2.style.display='inline';
    s3.style.display='inline';
    s4.style.display='inline';
    s5.style.display='inline';


    if (userLogged === "true") {
    // Si el usuario está logueado, ocultamos los enlaces de login y registro y mostramos el nombre de usuario
    listaVid.style.display = 'inline';
    regVid.style.display = 'inline';
    login.style.display = 'none';
    regUs.style.display = 'none';
    navuserName.style.display = 'inline';
    closeSesion.style.display ='inline';
    nombreUsuario.textContent = 'User: '+userName;
    s1.style.display='inline';
    s2.style.display='inline';
    s3.style.display='none';
    s4.style.display='none';
    s5.style.display='inline';

  } else {
    // Si el usuario no está logueado, mostramos los enlaces de login y registro y ocultamos el nombre de usuario
    listaVid.style.display = 'none';
    regVid.style.display = 'none';
    login.style.display = 'inline';
    regUs.style.display = 'inline';
    navuserName.style.display = 'none';
    closeSesion.style.display ='none';
    nombreUsuario.textContent = "";
    s1.style.display='none';
    s2.style.display='none';
    s3.style.display='inline';
    s4.style.display='none';
    s5.style.display='none';
  }
  switch (currentPage) {
            case "//login.jsp" :  // Manejar el caso cuando la URL no contiene informaci�n espec�fica del archivo JSP
                login.style.display = 'none';
                s3.style.display='none';
                break;
            case "/login.jsp":
                login.style.display = 'none';
                s3.style.display='none';
                break;
            case "/registroUsu.jsp":
                regUs.style.display = 'none';
                s3.style.display='none';
                s4.style.display='none';
                break;
            case "/registroVid.jsp":
                regVid.style.display = 'none';
                s2.style.display='none';
                break;
            case "/listadoVid.jsp":
                 listaVid.style.display = 'none';
                 s1.style.display='none';
                break;
            default:
        }
          
</script>
</html>

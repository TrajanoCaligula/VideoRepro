<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Nav bar</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="navbar">
        <a id="sun" href="sunXACML.jsp">Sun XACML Authorizer</a> <span id="s9" class="separador">|</span>
        <a id="balana" href="balanaXACML.jsp">Balana XACML Authorizer</a> <span id="s8" class="separador">|</span>
        <a id="encrypt" href="encryptorXML.jsp">EncryptorXML</a> <span id="s7" class="separador">|</span>
        <a id="searchVid" href="listadoBusqueda.jsp">Search Video</a> <span id="s6" class="separador">|</span>
        <a id="listaVid" href="listadoVid.jsp">Video List</a> <span id="s1"class="separador">|</span>
        <a id="regVid" href="registroVid.jsp">New Video</a> <span id="s2" class="separador">|</span>
        <a id="login" href="login.jsp">Login</a> <span id="s3" class="separador">|</span>
        <a id="regUs" href="registroUsu.jsp">Register</a> <span id="s4" class="separador">|</span>
        <a id="nombreUsuario" href="userInfo.jsp"></a>  <span id="s5" class="separador">|</span>
        <a id="closeSession" href="javascript:void(0)" onclick="cerrarSesion()">Logout</a>
    </div> 
   
</body> 
<script>
    var ses = '<%= request.getSession(false) %>';
    // Obtener el atributo "USER_LOGGED"
    var userLogged = '<%= session.getAttribute("USER_LOGGED") %>';
    var userName = '<%= session.getAttribute("USERNAME") %>';
     var currentPage = "<%= request.getServletPath() %>";
    function cerrarSesion() {
        // 1. AJAX Request (Optional for server-side session termination)
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "ServLogUser", true); // Replace with correct URL and method (use GET for session termination)
        xhr.onload = function() {
          if (xhr.status === 200) {
            // Session terminated successfully on server (handle response if needed)
          } else {
            // Handle errors (e.g., display error message)
            console.error("Error closing session:", xhr.statusText);
          }
        };
        xhr.send();

        // 2. Client-Side Session Management
        sessionStorage.clear(); // Clear client-side session data
        window.location.href = "login.jsp"; // Redirect to login page
    }
    function cambiarCursor() {
        document.getElementById("miTexto").style.cursor = "pointer";
    }

    var searchVid = document.getElementById('searchVid');
    var listaVid = document.getElementById('listaVid');
    var regVid = document.getElementById('regVid');
    var login = document.getElementById('login');
    var regUs = document.getElementById('regUs');
    var navuserName = document.getElementById('nombreUsuario');
    var closeSesion = document.getElementById('closeSession');
    var encrypt = document.getElementById('encrypt');
    var encrypt = document.getElementById('sun');
    var encrypt = document.getElementById('balana');
    var s1 = document.getElementById('s1');
    var s2 = document.getElementById('s2');
    var s3 = document.getElementById('s3');
    var s4 = document.getElementById('s4');
    var s5 = document.getElementById('s5');
    var s6 = document.getElementById('s6');
    var s7 = document.getElementById('s7');
    var s8 = document.getElementById('s8');
    var s9 = document.getElementById('s9');


    listaVid.style.display = 'inline';
    searchVid.style.display = 'inline';
    regVid.style.display = 'inline';
    login.style.display = 'inline';
    regUs.style.display = 'inline';
    navuserName.style.display = 'inline';
    closeSesion.style.display ='inline';
    sun.style.display = 'inline';
    balana.style.display = 'inline';
    nombreUsuario.textContent = "";
    encrypt.style.display = 'inline';
    s1.style.display='inline';
    s2.style.display='inline';
    s3.style.display='inline';
    s4.style.display='inline';
    s5.style.display='inline';
    s6.style.display='inline';
    s7.style.display='inline';
    s8.style.display='inline';
    s9.style.display='inline';



    if (userLogged === "true") {
    // Si el usuario está logueado, ocultamos los enlaces de login y registro y mostramos el nombre de usuario
    listaVid.style.display = 'inline';
    searchVid.style.display = 'inline';
    regVid.style.display = 'inline';
    login.style.display = 'none';
    regUs.style.display = 'none';
    navuserName.style.display = 'inline';
    closeSesion.style.display ='inline';
    nombreUsuario.textContent = 'User: '+userName;    
    encrypt.style.display = 'inline';
    sun.style.display = 'inline';
    balana.style.display = 'inline';
    s6.style.display='inline';
    s1.style.display='inline';
    s2.style.display='inline';
    s3.style.display='none';
    s4.style.display='none';
    s5.style.display='inline';
    s7.style.display='inline';
    s8.style.display='inline';
    s9.style.display='inline';

  } else {
    // Si el usuario no está logueado, mostramos los enlaces de login y registro y ocultamos el nombre de usuario
    listaVid.style.display = 'none';
    searchVid.style.display = 'none';
    regVid.style.display = 'none';
    login.style.display = 'inline';
    regUs.style.display = 'inline';
    navuserName.style.display = 'none';
    closeSesion.style.display ='none';
    nombreUsuario.textContent = "";
    encrypt.style.display = 'none';
    sun.style.display = 'none';
    balana.style.display = 'none';
    s9.style.display='none';
    s8.style.display='none';
    s7.style.display='none';
    s6.style.display='none';
    s1.style.display='none';
    s2.style.display='none';
    s3.style.display='inline';
    s4.style.display='none';
    s5.style.display='none';
  }
  switch (currentPage) {
            case "//login.jsp" :  // Manejar el caso cuando la URL no contiene información específica del archivo JSP
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
            case "/listadoBusqueda.jsp":
                searchVid.style.display = 'none';
                s6.style.display='none';
                break;
            case "/encryptorXML.jsp":
                encrypt.style.display = 'none';
                s7.style.display='none';
                break;
            case "/userInfo.jsp":
                navuserName.style.display = 'none';
                s5.style.display='none';
                break;
            case "/balanaXACML.jsp":
                balana.style.display = 'none';
                s8.style.display='none';
                break;
            case "/sunXACML.jsp":
                sun.style.display = 'none';
                s9.style.display='none';
                break;
            default:
        }
          
</script>
</html>

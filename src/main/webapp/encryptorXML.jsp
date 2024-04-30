<%-- 
    Document   : login
    Created on : 12 feb 2024, 15:48:15
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EncryptorXML</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <jsp:include page="navbar.jsp"/>
        <script>
        <% if (request.getAttribute("Error") != null) { %>
            window.onload = function() {
                alert("<%= request.getAttribute("Error") %>");
            };
        <% } %>
        <% if (request.getAttribute("Success") != null) { %>
            window.onload = function() {
                alert("<%= request.getAttribute("Success") %>");
            };
        <% } %>

        </script>
        <table style="background-color: white; border-radius: 10px; padding: 20px;">
            <tr><td>
            <form action="ServEncrypt" method="post" enctype="multipart/form-data">
                <h2>Encrypt the XML file:</h2>
                <label id = "error" style="color: red;">
                    ${requestScope.errorEncFormatInvalid}
                </label>
                <table>
                    <tr>
                        <td><input type="file" id="xmlFile" name="xmlFile" accept=".xml" /></td>
                        <td><input type="submit" value="Upload"></td>
                    </tr> 

                </table>
            </form></td>
                
        </tr><tr><td>
        <form  action="ServDesencrypt" method="post" enctype="multipart/form-data">
            <h2>Decrypt the XML file:</h2>
            <label>This file has to been encrypted by this web and needs to have the original name that we give to the file</label>
            <label id = "error" style="color: red;">
                ${requestScope.errorDecFormatInvalid}
            </label>
            <table>
                <tr>
                    <td><input type="file" id="xmlEncryptedFile" name="xmlEncryptedFile" accept=".xml" /></td>
                    <td><input type="submit" value="Upload"></td>
                </tr> 
            </table>
        </form>
            </td></tr>
            </table>
        
    </body>
</html>

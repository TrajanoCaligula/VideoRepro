<%@ page import="java.io.*,java.util.*" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="jakarta.servlet.http.*" %>
<%@ page import="jakarta.servlet.http.*" %>
<%@ page import="isdcm.DTO.VideoDTO" %>
<%@ page import="isdcm.controller.ServListVid" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style.css">
    <title>Video List</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        li {
            background-color: #f9f9f9;
            margin-bottom: 10px;
            padding: 10px;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        li:hover {
            background-color: #e0e0e0;
        }
    </style>

</head>
<body class = "bodyList">
    
        <jsp:include page="navbar.jsp"/>
        <div class="rectangulo-detras-h1"></div>
        <h1 class="h1List">Video Searcher</h1> 
        <br>
        <form>
                <table>
                    <tr> 
                        <td class="tdSearcher"><label for="title">Title:</label></td>
                        <td class="tdSearcher"><input  type="text" id="title" name="title"></input></td>
                        <td class="tdSearcher"><label for="author">Author:</label></td>
                        <td class="tdSearcher"><input  type="text" id="author" name="author"></input></td>
                        <td class="tdSearcher"><label for="day">Day:</label></td>
                        <td class="tdSearcher"><select id="day" name="day">
                                <option>----</option>
                            </select></td>
                        <td><label for="month">Month:</label></td>
                        <td><span></span></td>
                        <td><select id="month" name="month">
                            <option>----</option>
                            <option>January</option>
                            <option>February</option>
                            <option>March</option>
                            <option>April</option>
                            <option>May</option>
                            <option>June</option>
                            <option>July</option>
                            <option>August</option>
                            <option>September</option>
                            <option>October</option>
                            <option>November</option>
                            <option>December</option>
                        </select></td>
                        <td><label for="year">Year:</label></td>
                        <td><select id="year" name="year">
                                <option>----</option>
                        </select></td>
                        <td><input class="tdSearcher" type="submit" value="Search"></td>
                    </tr>
                </table>
          </form>
        <div class="contenedor-listado">
            <ol class="no-numeros" id="lista-videos">
                
            </ol>
        </div>
    
</body>


<script>
     // Obtener el select de días
     var selectDias = document.getElementById("day");

     // Añadir opciones al select
     for (var i = 1; i <= 31; i++) {
         var option = document.createElement("option");
         option.text = i;
         option.value = i;
         selectDias.add(option);
     }
     var selectDias = document.getElementById("year");

     // Añadir opciones al select
     for (var i = 1; i <= 200; i++) {
         var option = document.createElement("option");
         option.text = i+1824;
         option.value = i;
         selectDias.add(option);
     }
 </script>

</html>

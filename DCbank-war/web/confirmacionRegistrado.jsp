<%-- 
    Document   : confirmacionRegistrado
    Created on : 04-may-2018, 18:29:55
    Author     : Pedro Avila
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>



<html>
    <style>
        body {
            font-family: Arial,sans-serif;
            background-color: #2ecc71;
        }
    </style>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div align="center">
        <h1>Usuario registrado correctamente</h1>
        El usuario con DNI <b><%= request.getAttribute("dniRegistrado") %></b> se ha añadido correctamente. <br>
        <a  href="empleadoPrincipal.jsp">Regresar a Zona Empleado</a>
        </div> 
                
    </body>
</html>

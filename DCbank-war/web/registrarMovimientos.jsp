<%-- 
    Document   : registrarMovimientos
    Created on : 05-may-2018, 16:01:46
    Author     : Daniel
--%>

<%@page import="java.util.List"%>
<%@page import="dcbank.entity.Cuenta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
        String cuenta = (String)session.getAttribute("idCuentaSeleccionada");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar Movimientos</title>
        <link rel="stylesheet" href="css/login.css" type="text/css">
    </head>    
    
    
        <h1 align="center">Zona Empleado</h1>
        <hr/>
        <p align="right"><a href="empleadoPrincipal.jsp">Volver Atrás</a></p>
        
        <h2>Registrar Movimientos</h2>
<%
        if(cuenta == null){
%>
        <p style="color:red" >Error: primero debe seleccionar la cuenta de un usuario.</p>
<%
        }else{
                
%>
        <ul>
            <li><p><a href="ingresarDinero.jsp">Ingresar Dinero</a></p></li>
            <li><p><a href="retirarDinero.jsp">Retirar Dinero</a></p></li>
            <li><p><a href="transferencia.jsp">Realizar Transferencia</a></p></li>
        </ul>
<%
        }   
%>
    </body>
</html>

<%-- 
    Document   : login
    Created on : 14-abr-2018, 20:35:49
    Author     : Pedro Avila 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    //Recuperación de mensaje de error en caso de que se hubiera producido
    String error;
    error = (String) request.getAttribute("error");
    if(error == null){
        error = "";
    }
    
    //Recuperamos dni si hubo un error para que el usuario pueda detectarlo y corregirlo 
    String dni;
    dni = (String) request.getAttribute("dni");
    if(dni == null){
        dni = "";
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DCbank: Iniciar sesión</title>
        <link rel="stylesheet" href="css/login.css" type="text/css">
    </head>

    <body>
        <div>
	<h1 align="center"> Acceso a DCbank </h1>
        <hr>
	</div>
        <div  class="loginform cf">
          <form  name="login" action="Login" method="post">
            <ul>
		<li>
		<label for="userDni"> DNI </label>
                <input type="text" name="userDni" placeholder="Introduzca su DNI" value='<%= dni %>' maxlength="9" required>
		</li>
	
                <li>
		<label for="userPassword"> Contraseña </label>
		<input type="password" name="userPassword" placeholder="Introduzca su contraseña" required>
		</li>
		
                <li>
		<input type="submit" value="Iniciar sesión">
		</li>
                
                <li>
                <font color="red"><%= error %></font>
                </li>
                
            </ul>
          </form>
        </div>
        
    </body>
</html>


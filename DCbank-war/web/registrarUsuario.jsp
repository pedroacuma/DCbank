<%-- 
    Document   : registrarUsuario
    Created on : 25-abr-2018, 17:46:36
    Author     : Pedro Avila
--%>

<%@page import="dcbank.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
    Usuario loggedUser;
    loggedUser = (Usuario) session.getAttribute("usuario");
    
    //CON ESTO COMPROBAMOS QUE EL USUARIO ES UN ADMIN
    //Un cliente no debe tener acceso a estas páginas de ninguna forma.
    if(loggedUser==null || loggedUser.getRol() != 1){
        response.sendRedirect("login.jsp");
    }
    
    String error,dni,nombre,apellidos,telefono,domicilio;

    error = (String) request.getAttribute("error");
    if(error == null) {
        error = dni = nombre = apellidos = telefono = domicilio = "";
    }else{
        dni = (String) request.getAttribute("dni");
        nombre = (String) request.getAttribute("nombre");
        apellidos = (String) request.getAttribute("apellidos");
        telefono = (String) request.getAttribute("telefono");
        domicilio = (String) request.getAttribute("domicilio");
    }
    
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar usuario</title>
 	<link rel="stylesheet" href="css/altaUsuario.css" type="text/css">
    </head>

    <body>
        <div>
            <h1 align="center"> <a href="empleadoPrincipal.jsp" > Zona Empleado  </a></h1>
        <hr>
	</div>
        <h2 align="left"> Registrar cliente: </h2>

        <div  class="registerform cf">
          <form  name="register" action="RegistrarUsuarioServlet" method="post">
            <ul>
		<li>
		<label for="dniCliente"> DNI </label>
		<input type="text" name="dniCliente" placeholder="DNI del cliente" maxlength="9" value="<%=dni%>" required>
		</li>
                
		<li>
		<label for="nombreCliente"> Nombre </label>
		<input type="text" name="nombreCliente" placeholder="Nombre del cliente" maxlength="50" value="<%=nombre%>" required>
		</li>
                
		<li>
		<label for="apellidosCliente"> Apellidos </label>
		<input type="text" name="apellidosCliente" maxlength="50" placeholder="Apellidos del cliente" value="<%=apellidos%>" required>
		</li>
                
		<li>
		<label for="telefonoCliente"> Telefono </label>
		<input type="text" name="telefonoCliente" maxlength="45" placeholder="Número de teléfono" value="<%=telefono%>" required>
		</li>
                
		<li>
		<label for="domicilioCliente"> Domicilio </label>
		<input type="text" name="domicilioCliente" maxlength="45" placeholder="Domicilio del cliente" value="<%=domicilio%>" required>
		</li>
		<li>
		<!-- Normalmente la contraseña sería proporcionada impresa en un sobre a abrir por el usuario o bien por email
		para nuestra aplicación la establece el empleado para que sea más sencillo y se puedan realizar pruebas -->
		<label for="pwdCliente" > Contraseña </label>
		<input type="password" name="pwdCliente" maxlength="50" placeholder="Contraseña para el cliente" required>
		<input type="password" name="pwdCheck" maxlength="50" placeholder="Repetir contraseña" required>
		</li>
                
                <li>
                    <!-- La Base de datos la crea los usuarios con rol 0 por defecto, pero lo incluimos en el formulario para hacerlo
                    más explícito -->
                    <input type="hidden" name="rolCliente" value="0">
                </li>
                
		<li>
                    <input type="submit" value="Registar">
                </li>
                
                <li>
                    <font color="red"> <%= error %> </font>                    
                </li>
            </ul>
          </form>
        </div>
    </body>
</html>
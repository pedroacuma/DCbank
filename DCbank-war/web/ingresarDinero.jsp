<%-- 
    Document   : ingresardinero
    Created on : 05-may-2018, 20:31:30
    Author     : Daniel
--%>

<%@page import="dcbank.entity.Cuenta"%>
<%@page import="dcbank.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
 
    Usuario user= (Usuario)session.getAttribute("user");
    Cuenta cuenta = (Cuenta)session.getAttribute("cuenta");
    String error = (String)session.getAttribute("errorIngreso");
    if(error == null){
        error = "";
    }

%>
<html>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ingresar Dinero</title>
 	<link rel="stylesheet" href="css/altaUsuario.css" type="text/css">   
    <body>
        <div>
            <h1 align="center"> <a href="empleadoPrincipal.jsp" > Zona Empleado  </a></h1>
        <hr>
        <p align="right"><a href="registrarMovimientos.jsp">Cancelar</a></p>
	</div>
        <h2 align="left"> Ingresar Dinero: </h2>

        <div  class="registerform cf">
          <form  name="ingresarDinero" action="ingresarDineroServlet" method="post">
            <ul>
		<li>
		<label for="nombreUsuario"> Nombre Usuario</label>
                <input type="text" name="nombreUsuario" readonly="readonly" value="<%=user.getNombre()%> <%=user.getApellidos()%> " required>
		</li>
                <li>
		<label for="dni"> DNI</label>
                <input type="text" name="dni" readonly="readonly" value="<%=user.getDni()%>" required>
		</li>
                
		<li>
		<label for="cuentaOrigen"> Cuenta Usuario </label>
		<input type="text" name="cuentaOrigen" readonly="readonly" value="<%=cuenta.getIban() %>" required>
                
		</li>
                
		<li>
		<label for="importe"> Importe a Ingresar</label>
                <input type="text" name="importe" placeholder="Introduzca Cantidad" value="" required>
		</li>
                
		<li>
		<label for="concepto"> Concepto </label>
		<input type="text" name="concepto" placeholder="Introduzca Concepto" value="" required>
		</li>
      
		<li>
                    <input type="submit" value="Ingresar">
                </li> 
                <li>
                    <font align="center" color="red"> <%= error %> </font>                    
                </li>
            </ul>
          </form>
        </div>
                  
    </body>
</html>

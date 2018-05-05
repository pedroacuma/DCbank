<%-- 
    Document   : empleadoPrincipal
    Created on : 20-abr-2018, 18:05:42
    Author     : Pedro Avila
--%>

<%@page import="dcbank.entity.Transferencia"%>
<%@page import="java.util.List"%>
<%@page import="dcbank.entity.Cuenta"%>
<%@page import="dcbank.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Empleado Principal</title>
        <link rel="stylesheet" href="css/usuarioPrincipal.css" type="text/css">
    </head>
    
    <%
           
           Usuario usuario = (Usuario)session.getAttribute("user");
           List<Cuenta> listaCuentas = null;
           String clienteDni = null;
           if (usuario != null){
               listaCuentas = (List<Cuenta>) session.getAttribute("listaCuentas");
               clienteDni = usuario.getDni();
           }
           
    %>    
    
    <body>
        
        <!-- C A B E C E R A -->
        <h1 align="center">Zona Empleado</h1>
        <div class = "opciones">
            <a href="/crearCuenta.jsp?cliente=<%=clienteDni%>">Crear Cuenta</a>     |     
            <a href="/registrarMovimientos.jsp?cuenta=<%=request.getParameter("cuentaUsuario")%>">Registrar Movimiento</a>       |
            <a href="registrarUsuario.jsp">Dar Alta Usuario</a>     |     
            <a href="CerrarSesionServlet">Cerrar Sesión</a>
        </div>
        <form  name="buscadorUsuario" action="EmpleadoServlet" method="post">
            <label for="buscadorUsuario"><b> Buscar Usuario </b></label>
                <input type="text" name="buscadorUsuario" placeholder="Introduzca DNI" maxlength="9">
        </form> 
        
        <div class = "centrar">
        
        <%
            if (usuario != null){
                
        %>
        
            <!-- U S U A R I O -->
            
            
                <table border = "3" class = "tablaUsuario">
            
                    <tr>
                        <th>Nombre y Apellidos</th>
                    </tr>
                    <tr>
                        <td align="center"><%=usuario.getNombre()%> <%=usuario.getApellidos()%></td>
                    </tr>
                    <tr>
                        <th>DNI</th>
                    </tr>
                    <tr>
                        <td align="center"><%=usuario.getDni()%></td>
                    </tr>
                    <tr>
                        <th>Direccion</th>
                    </tr>
                    <tr>
                        <td align="center"><%=usuario.getDomicilio()%></td>
                    </tr>
                </table>
        
                <!-- C U E N T A S -->
        
        
                <table border = "3" class = "tablaCuentas">
                    <tr>
                            <th align = "center"><b>Cuentas</b></th>
                    <tr>
                    <%
                        for (Cuenta c : listaCuentas){
                    %>
                        <tr>
                            <td><a href="ListaMovsCuenta?cuentaUsuario=<%= c.getIdCuenta()%>"> <%=c.getIban()%></a></td>
                        </tr>
                    <%
                        }
                    %>
                </table>
                    
                <!-- M O V I M I E N T O S -->
        
                <table border = "3" class = "tablaMovimientos">
                    <tr>
                        <th align = "center"><b>Movimientos</b></th>
                        <th align = "center"><b><form  name="buscadorMovimiento" action="ListaMovsCuentaYConcepto" method="post">
                            <label for="buscadorMovimiento"> Buscar Movimiento </label>
                            <input type="text" name="concepto" placeholder="Introduzca Concepto">
                           
                        </form> </b></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                    </tr>
                    <tr>
                        <td align = "center">Fecha</td>
                        <td align = "center">Cuenta Origen</td>
                        <td align = "center">Cuenta Destino</td>
                        <td align = "center">Cantidad</td>
                        <td align = "center">Beneficiario</td>
                        <td align = "center">Concepto</td>
                    </tr>
                    
                    <%  
                        if (request.getAttribute("movimientosFiltrados") == null){
                        
                    %>

                                <%  
                                    if (request.getParameter("cuentaUsuario") != null){
                                        List<Transferencia> listaMovimientos = (List<Transferencia>)session.getAttribute("listaMovimientos");
                                %>

                                    <%
                                        for (Transferencia t : listaMovimientos){

                                    %>
                                        <tr>
                                            <td><%=t.getFecha()%></td>
                                            <td><%=t.getCuenta().getIban()%></td>
                                            <td><%=t.getCuentaDestino().getIban()%></td>
                                            <td><%=t.getCantidad()%></td>
                                            <td><%=t.getBeneficiario()%></td>
                                            <td><%=t.getConcepto()%></td>
                                        </tr>
                                    <%
                                        }//endfor
                                    %>
                                <%  
                                    } //endif
                                %>    
                                
                        <%  
                            }else{
                                List<Transferencia> listaBuscados = (List<Transferencia>) request.getAttribute("movimientosFiltrados");
                        %>    
                                    <%
                                        for (Transferencia t : listaBuscados){

                                    %>
                                        <tr>
                                            <td><%=t.getFecha()%></td>
                                            <td><%=t.getCuenta().getIban()%></td>
                                            <td><%=t.getCuentaDestino().getIban()%></td>
                                            <td><%=t.getCantidad()%></td>
                                            <td><%=t.getBeneficiario()%></td>
                                            <td><%=t.getConcepto()%></td>
                                        </tr>
                                    <%
                                        }//endfor
                                    %>
                        
                        <%  
                            }//endelse
                        %>    
                </table>      
                    
        <%
            }//endif
        %>
        </div>
    </body>
</html>

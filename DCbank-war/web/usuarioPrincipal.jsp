<%-- 
    Document   : usuarioPrincipal
    Created on : 26-abr-2018, 11:53:16
    Author     : Jairo
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
        <title>Usuario Principal</title>
        <link rel="stylesheet" href="css/usuarioPrincipal.css" type="text/css">
        
    </head>
    
    
    
    <%
        RequestDispatcher rd;
        Usuario usuario = (Usuario)session.getAttribute("usuario");
        try{
            usuario.equals(null);
        } catch (NullPointerException e){
            rd = this.getServletContext().getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        }
          
        String dni = usuario.getDni();
        List<Cuenta> listaCuentas = (List<Cuenta>) session.getAttribute("listaCuentas");
        
    %>
    
    <body>
        
        <h1 align="center"> Zona Clientes </h1>
        <hr/>
        <div class = "opciones">
            <a href="transferencia.jsp">Realizar Transferencia</a>     |     
            <a href="CerrarSesionServlet">Cerrar Sesión</a>
        </div>
        </br>
        <div class = "centrar">
        <!-- DATOS USUARIO -->
            </br>
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
                    <th align = "center"><b>Saldo</b></th>
            <tr>
            <%
                for (Cuenta c : listaCuentas){
            %>
                <tr>
                    <td><a href="ListaMovsCuenta?cuentaUsuario=<%= c.getIdCuenta()%>" ><%=c.getIban()%></a></td>
                    <td> <%=c.getSaldo()%>€</td>  
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
                                        <td><%=t.getCantidad()%>€</td>
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
                                List<Transferencia> listT = (List<Transferencia>)request.getAttribute("movimientosFiltrados");
                        %> 
                                <%
                                    for (Transferencia t : listT){
                                %>
                                    <tr>
                                        <td><%=t.getFecha()%></td>
                                        <td><%=t.getCuenta().getIban()%></td>
                                        <td><%=t.getCuentaDestino().getIdCuenta()%></td>
                                        <td><%=t.getCantidad()%>€</td>
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
    </div>
    </body>
</html>

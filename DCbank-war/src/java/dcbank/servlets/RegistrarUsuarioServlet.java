/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcbank.servlets;

import dcbank.ejb.CuentaFacade;
import dcbank.ejb.UsuarioFacade;
import dcbank.entity.Usuario;
import dcbank.entity.Cuenta;
import dcbank.utils.GeneradorIBAN;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pedro Avila
 */
@WebServlet(name = "RegistrarUsuarioServlet", urlPatterns = {"/RegistrarUsuarioServlet"})
public class RegistrarUsuarioServlet extends HttpServlet {

    @EJB
    private CuentaFacade cuentaFacade;

    @EJB
    private UsuarioFacade usuarioFacade;
   
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");

        
        RequestDispatcher rd;
        String dni,pwd,pwdCheck,nombre,apellidos,telefono,domicilio,rolAsString;
        
        //Recogemos los datos del formulario
        dni = request.getParameter("dniCliente");
        pwd = request.getParameter("pwdCliente");
        pwdCheck = request.getParameter("pwdCheck");
        nombre = request.getParameter("nombreCliente");
        apellidos = request.getParameter("apellidosCliente");
        telefono = request.getParameter("telefonoCliente");
        domicilio = request.getParameter("domicilioCliente");
        rolAsString = request.getParameter("rolCliente");
        
        //Por defecto en la base de datos ya se crean con rol cliente por defecto, aún así
        //lo realizamos aquí para practicar y porque es más visible
        Short rol;
        rol = Short.parseShort(rolAsString);
       
        //Comprobamos que el usuario no exista
        boolean existeYa = usuarioFacade.find(dni) != null;
        if(existeYa){
            String error;
            error = "El DNI: " + dni + " ya se encuentra registrado";
            request.setAttribute("error", error);
           
            dni = "";
            //En caso de error cargamos algunos atributos de vuelta para que
            //el usuario no tenga que volver a escribirlos y pueda editarlos
            cargaAtributosError(dni,nombre,apellidos,telefono,domicilio,request);
            rd = request.getServletContext().getRequestDispatcher("/registrarUsuario.jsp");    
        }
        else if(!pwd.equals(pwdCheck)){
            String error = "Las contraseñas no coinciden";
            request.setAttribute("error", error);
            cargaAtributosError(dni,nombre,apellidos,telefono,domicilio,request);
            rd = request.getServletContext().getRequestDispatcher("/registrarUsuario.jsp");
        }
        else{
            Usuario usuario = new Usuario();    
            usuario.setDni(dni);
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setTelefono(telefono);
            usuario.setDomicilio(domicilio);
            usuario.setRol(rol);
            usuario.setPassword(pwd);
            
            usuarioFacade.create(usuario);
            
            //Creacion de la cuenta asociada por defecto con saldo 0
            Cuenta cuenta = new Cuenta();
           
            cuenta.setIban(GeneradorIBAN.generadorIBAN());
            cuenta.setSaldo(0);
            cuenta.setPropietario(usuario);
                    
            
            cuentaFacade.create(cuenta);
            
            request.setAttribute("dniRegistrado", dni);
            rd = request.getServletContext().getRequestDispatcher("/confirmacionRegistrado.jsp");
            
        }
        
        rd.forward(request, response);
    }
    
    
    
    private void cargaAtributosError(String dni,String nombre,String apellidos,String telefono,String domicilio,HttpServletRequest request){
        request.setAttribute("dni", dni);
        request.setAttribute("nombre", nombre);
        request.setAttribute("apellidos", apellidos);
        request.setAttribute("telefono", telefono);
        request.setAttribute("domicilio", domicilio );
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    

}

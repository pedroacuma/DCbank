/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcbank.servlets;

import dcbank.ejb.CuentaFacade;
import dcbank.ejb.UsuarioFacade;
import dcbank.entity.Cuenta;
import dcbank.entity.Usuario;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Pedro Avila 
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {
    
    @EJB
    private CuentaFacade cf;
    
    @EJB
    private UsuarioFacade uf;
    
    
            
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
        
        String dni = request.getParameter("userDni");
        String pwd = request.getParameter("userPassword");
        
        RequestDispatcher rd;
        Usuario usuario = uf.find(dni);
        
        /*
        Las passwords no deberían almacenarse en texto plano, sino utilizar
        una función hash y verificar con el hash almacenado. Por no añadir
        complejidad hemos decidido almacenarlas en texto plano.
        */
        
        if(usuario == null)
        {
            String error = "El usuario no existe";
            request.setAttribute("error", error);
            request.setAttribute("dni",dni);
            rd = this.getServletContext().getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        } 
        else if( pwd.equals(usuario.getPassword()))
        {
            HttpSession session = request.getSession();
            //pedro : session.setAttribute(pwd, uf);
            session.setAttribute("usuario", usuario);
            //MySql almacena booleans como tinyint (short en java), mediante esta comparación lo pasamos a boolean
            //Rol == true si es usuario(empleado), rol == false si es usuario(cliente)
            boolean rol = (usuario.getRol() == 0);
            // pedro : String nextPage = rol ? "/usuarioPrincipal.jsp"  : "/empleadoPrincipal.jsp"; 
            String nextPage;
            if (rol) {
                nextPage = "/DatosUsuarioServlet";
                List<Cuenta> listaCuentas = cf.buscarPorPropietario(usuario);
                session.setAttribute("listaCuentas", listaCuentas);
            }else{
                nextPage = "/EmpleadoServlet";
            }
            rd = this.getServletContext().getRequestDispatcher(nextPage);
            rd.forward(request, response);
        } 
        else{
            String error = "Contraseña incorrecta";
            //Cargamos el mensaje de error pero también el dni, que era correcto
            request.setAttribute("error", error);
            request.setAttribute("dni",dni);
            rd = this.getServletContext().getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        }
        
        
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

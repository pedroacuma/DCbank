/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcbank.servlets;

import dcbank.ejb.CuentaFacade;
import dcbank.ejb.TransferenciaFacade;
import dcbank.ejb.UsuarioFacade;
import dcbank.entity.Cuenta;
import dcbank.entity.Transferencia;
import dcbank.entity.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jairo
 */
@WebServlet(name = "DatosUsuarioServlet", urlPatterns = {"/DatosUsuarioServlet"})
public class DatosUsuarioServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     */
    
    @EJB
    private CuentaFacade cf;
    
    @EJB
    private TransferenciaFacade tf;
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        HttpSession session = request.getSession();
        RequestDispatcher rd;
        
        /* CUENTAS */
        
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Cuenta cuenta = null;
       
        List<Cuenta> listaCuentas =  cf.buscarPorPropietario(usuario);
        session.setAttribute("listaCuentas", listaCuentas);
        
        /* MOVIMIENTOS */
        
        String idCuenta = request.getParameter("cuentaUsuario");
        
        if (idCuenta != null){
            cuenta = cf.buscarPorID(Integer.parseInt(idCuenta));
            List<Transferencia> listaMovimientos = tf.buscarPorCuenta(cuenta);
            session.setAttribute("listaMovimientos", listaMovimientos);
        }
        
        if (request.getParameter("buscadorMovimiento") != null){
            String cad = request.getParameter("buscadorMovimiento");
            List<Transferencia> listaT = tf.buscarConcepto(cad);
            session.setAttribute("movimientos", listaT);
        }else{
            session.setAttribute("movimientos", null);
        }
        
        rd = this.getServletContext().getRequestDispatcher("/usuarioPrincipal.jsp");
        rd.forward(request, response);
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

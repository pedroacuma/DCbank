/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcbank.servlets;

import dcbank.ejb.CuentaFacade;
import dcbank.ejb.TransferenciaFacade;
import dcbank.entity.Cuenta;
import dcbank.entity.Transferencia;
import dcbank.entity.Usuario;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author Pedro Avila
 */
@WebServlet(name = "ListaMovsCuentaYConcepto", urlPatterns = {"/ListaMovsCuentaYConcepto"})
public class ListaMovsCuentaYConcepto extends HttpServlet {

    @EJB
    private CuentaFacade cuentaFacade;

    @EJB
    private TransferenciaFacade transferenciaFacade;

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
        
        HttpSession session = request.getSession();
        
        
        String concepto = request.getParameter("concepto");
        String id = (String) session.getAttribute("idCuentaSeleccionada");

        
        if(id!=null && concepto!=null && concepto!=""){
             Integer idCuenta = Integer.parseInt(id);

             Cuenta cuenta = cuentaFacade.buscarPorID(idCuenta);
             List<Transferencia> res;
             res = transferenciaFacade.buscarConceptoEnCuenta(concepto,cuenta);
             System.out.println(res.size());
             request.setAttribute("movimientosFiltrados", res);
        }
        
        //Comprobamos el tipo de usuario para volver a empleadoPrincipal o usuarioPrincipal (el usuario logueado está etiquetado como "usuario" 
        Usuario loggedUser = (Usuario) session.getAttribute("usuario");
        short empleado = (short) loggedUser.getRol();
        
        String nextPage = "/usuarioPrincipal.jsp";
        //Si es empleado rol 1 cambiamos al jsp empleadoPrincipal
        if(empleado == 1) nextPage = "/empleadoPrincipal.jsp";
        
        
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher(nextPage);
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



}

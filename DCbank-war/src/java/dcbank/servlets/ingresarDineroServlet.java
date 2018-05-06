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
import java.io.IOException;

import javax.ejb.EJB;
import java.lang.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Daniel
 */
@WebServlet(name = "ingresarDineroServlet", urlPatterns = {"/ingresarDineroServlet"})
public class ingresarDineroServlet extends HttpServlet {
    
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
        RequestDispatcher rd;
        
        //cogemos los valores del formulario que necesitamos
        
        String importe = request.getParameter("importe");
        String enlace;
        if(Integer.parseInt(importe)>0){
            String concepto = request.getParameter("concepto");
            Cuenta cuenta = (Cuenta)session.getAttribute("cuenta");
            Usuario usuario = (Usuario) session.getAttribute("user");

            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd"); 
            Date fechaAct = new  Date();
            String fecha = formato.format(fechaAct);

            //creo la tranferencia
            Transferencia t = new Transferencia();
            t.setFecha(fecha);
            t.setCantidad(Integer.parseInt(importe));
            t.setCuenta(cuenta);
            t.setCuentaDestino(cuenta);
            t.setBeneficiario(usuario.getNombre());
            t.setConcepto(concepto);
            cuenta.setSaldo(cuenta.getSaldo() + Integer.parseInt(importe));
            cuentaFacade.edit(cuenta);
            transferenciaFacade.crearIngreso(t);
            enlace= "/EmpleadoServlet?buscadorUsuario=" + usuario.getDni();
            

        }else{
            String error = "No se puede ingresar importe negativo";
            session.setAttribute("errorIngreso", error);
            enlace="/ingresarDinero.jsp";
        }
        
        

        rd = this.getServletContext().getRequestDispatcher(enlace);
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

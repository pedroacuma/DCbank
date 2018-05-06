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
 * @author Daniel
 */
@WebServlet(name = "TransferenciaServlet", urlPatterns = {"/TransferenciaServlet"})
public class TransferenciaServlet extends HttpServlet {
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
        String enlace;
        Cuenta cuentaOrigen = (Cuenta)session.getAttribute("cuenta");
        String cnt = (String)request.getParameter("cuentaDestino");
        String importe = request.getParameter("importe");
        String concepto = request.getParameter("concepto");
        Cuenta cuentaDestino = cuentaFacade.buscarPorIban(cnt);
        if(cuentaDestino != null){
            if(Integer.parseInt(importe)>0){
                if(cuentaOrigen.getSaldo()>= Integer.parseInt(importe)){
                    // quitamos de una cuenta
                    cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - Integer.parseInt(importe));
                    cuentaFacade.edit(cuentaOrigen);
                    
                    //ponemos en otra cuenta
                    cuentaDestino.setSaldo(cuentaDestino.getSaldo() + Integer.parseInt(importe));
                    cuentaFacade.edit(cuentaDestino);
                    
                    //creamos las transferencias
                    
                    Transferencia t = new Transferencia();
                    t.setBeneficiario(cuentaDestino.getPropietario().getNombre() + " " + cuentaDestino.getPropietario().getApellidos());
                    t.setCantidad(Integer.parseInt(importe) * (-1));
                    t.setConcepto(concepto);
                    t.setCuenta(cuentaOrigen);
                    t.setCuentaDestino(cuentaDestino);
                    
                    transferenciaFacade.crearIngreso(t);
                    
                    Transferencia t2 = new Transferencia();
                    t2.setBeneficiario(cuentaDestino.getPropietario().getNombre() + " " + cuentaDestino.getPropietario().getApellidos());
                    t2.setConcepto(concepto);
                    t2.setCantidad(Integer.parseInt(importe));
                    t2.setCuenta(cuentaDestino);
                    t2.setCuentaDestino(cuentaOrigen);
                    
                    transferenciaFacade.crearIngreso(t2);
                    
                       enlace= "/EmpleadoServlet?buscadorUsuario=" + cuentaOrigen.getPropietario().getDni();
                } else {
                String error = "Saldo de cuenta origen insuficiente";
                session.setAttribute("errorTransferencia", error); 
                enlace= "/transferencia.jsp";
                }
            }else{
                    String error = "Importe negativo";
                    session.setAttribute("errorTransferencia", error);
                    enlace= "/transferencia.jsp";
            }   
        }else{
            String error = "La cuenta destino no existe";
            session.setAttribute("errorTransferencia", error);
            enlace= "/transferencia.jsp";
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

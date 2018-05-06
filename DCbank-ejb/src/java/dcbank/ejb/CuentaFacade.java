/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcbank.ejb;

import dcbank.entity.Cuenta;
import dcbank.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Pedro Avila
 */
@Stateless
public class CuentaFacade extends AbstractFacade<Cuenta> {

    @PersistenceContext(unitName = "DCbank-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CuentaFacade() {
        super(Cuenta.class);
    }
    
    public List<Cuenta> buscarPorPropietario (Usuario propietario) {
        Query q = this.em.createNamedQuery("Cuenta.findByPropietario",Cuenta.class);
        q.setParameter("propietario", propietario);
                
        List<Cuenta> listaCuentas;

        try{
           listaCuentas = (List<Cuenta>) q.getResultList();
        }catch(EJBException ex){
            listaCuentas = new ArrayList<>();
        }
        
        return  listaCuentas;        
    }
    public Cuenta buscarCuentaEmpleado (Usuario propietario) {
        Query q = this.em.createNamedQuery("Cuenta.findByPropietario",Cuenta.class);
        q.setParameter("propietario", propietario);
        Cuenta cuentaEmpleado;
        cuentaEmpleado = (Cuenta)q.getSingleResult();
        return  cuentaEmpleado;        
    }
    public Cuenta buscarPorIban (String iban) {
        Query q = this.em.createNamedQuery("Cuenta.findByIban", Cuenta.class);
        q.setParameter("iban", iban);
        Cuenta cuenta;
        try{
            cuenta = (Cuenta)q.getSingleResult();
        }catch(NoResultException re){
            cuenta = null;
        }
        
        return  cuenta;        
    }
    
    
      public Cuenta buscarPorID (int id){
        Query q = this.em.createNamedQuery("Cuenta.findByIdCuenta", Cuenta.class);
        q.setParameter("idCuenta", id);
        Cuenta c;
        try{
           c = (Cuenta) q.getSingleResult();
        }catch(NoResultException ex){
           c = null;
        }
        return c;
    }
      
    
}

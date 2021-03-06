/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcbank.ejb;

import dcbank.entity.Cuenta;
import dcbank.entity.Transferencia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Pedro Avila
 */
@Stateless
public class TransferenciaFacade extends AbstractFacade<Transferencia> {

    @PersistenceContext(unitName = "DCbank-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransferenciaFacade() {
        super(Transferencia.class);
    }
    
    public List<Transferencia> buscarPorCuenta (Cuenta cuenta) {
        Query q = this.em.createQuery("select t from Transferencia t where t.cuenta = :cuenta");
        q.setParameter("cuenta", cuenta);
        
        List<Transferencia> listaMovimientos;
        try{
           listaMovimientos = (List<Transferencia>) q.getResultList();
        }catch(Exception ex){
           listaMovimientos = null;
        }
        
        return  listaMovimientos;        
    }
    

    public List<Transferencia> buscarConceptoEnCuenta(String concepto, Cuenta cuenta) {
        
        Query q = this.em.createNamedQuery("Transferencia.findByConceptoCuenta");
        q.setParameter("concepto", "%"+concepto+"%");
        q.setParameter("cuenta", cuenta);
        
        List<Transferencia> listaT;
        try{
           listaT = (List<Transferencia>) q.getResultList();
        }catch(Exception ex){
           listaT = null;
        }
        
        return  listaT;    
    }
    public void crearIngreso (Transferencia t){
        Query q = this.em.createNativeQuery("INSERT INTO transferencia (fecha,cantidad,cuenta,cuentaDestino,beneficiario,concepto) VALUES (CURDATE(),?,?,?,?,?)");
        q.setParameter(1,t.getCantidad());
        q.setParameter(2,t.getCuenta().getIdCuenta());
        q.setParameter(3,t.getCuentaDestino().getIdCuenta());
        q.setParameter(4,t.getBeneficiario());
        q.setParameter(5,t.getConcepto());
        q.executeUpdate();
    }
}

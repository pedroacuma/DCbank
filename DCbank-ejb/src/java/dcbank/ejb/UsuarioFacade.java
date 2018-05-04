/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcbank.ejb;

import dcbank.entity.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Pedro Ávila 
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "DCbank-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    /**
     * Pedro Ávila.
     * Este método realiza una búsqueda de usuarios en mydb.
     *
     * @param dni DNI del usuario a buscar en la base de datos.
     * @return Usuario asociado a @dni. Null si no existe el usuario.
     */
    public Usuario buscarPorDni (String dni) {
        Query q = this.em.createNamedQuery("Usuario.findByDni",Usuario.class);
        q.setParameter("dni", dni);
        
        Usuario u;
        try{
           u = (Usuario) q.getSingleResult();
        }catch(NoResultException ex){
           u = null;
        }
        
        return  u;        
    }
    
}

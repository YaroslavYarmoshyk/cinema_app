package mate.academy.spring.dao.impl;

import javax.persistence.Query;
import mate.academy.spring.dao.AbstractDao;
import mate.academy.spring.dao.RoleDao;
import mate.academy.spring.exception.DataProcessingException;
import mate.academy.spring.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    public RoleDaoImpl(SessionFactory factory) {
        super(factory, Role.class);
    }

    @Override
    public Role add(Role role) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
            return role;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert role " + role + " to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Role getRoleByName(String roleName) {
        try (Session session = factory.openSession()) {
            Query getByName = session.createQuery("from Role"
                    + " where roleName =:name", Role.class);
            getByName.setParameter("name",roleName);
            return (Role) getByName.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Role with name "
                    + roleName + " not found", e);
        }
    }
}

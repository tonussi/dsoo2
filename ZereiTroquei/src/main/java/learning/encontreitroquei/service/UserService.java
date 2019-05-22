package learning.encontreitroquei.service;

import java.util.List;
import learning.encontreitroquei.model.User;
import org.hibernate.Query;

public class UserService extends GenericService {

    @Override
    public Object findById(Class clazz, Integer id) {
        return super.findById(clazz, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List findAll(Class clazz) {
        return super.findAll(clazz); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object obj) {
        super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOrUpdate(Object obj) {
        super.saveOrUpdate(obj); //To change body of generated methods, choose Tools | Templates.
    }

    public User findByEmail(String email) {
        startOperation();
        Query query = session.createQuery("from User where email = :email");
        query.setParameter("email", email);
        List objects = query.list();
        endOperation();
        if (objects.size() > 0) {
            return (User) objects.get(0);
        }
        return null;
    }
}

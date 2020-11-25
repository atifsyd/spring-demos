package com.atifsyd.springintro.springMVC.service;

import com.atifsyd.springintro.springMVC.model.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Service
@Profile({"default", "jpadao"})
public class ProductServiceJPADaoImpl implements ProductService{

    private EntityManagerFactory emf;

    @PersistenceUnit
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<?> listAll() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("from Product", Product.class).getResultList();

    }

    @Override
    public Product getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Product.class, id);
    }

    @Override
    public Product saveOrUpdate(Product modelObject) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Product savedProduct = em.merge(modelObject);
        em.getTransaction().commit();
        return savedProduct;
    }

    @Override
    public void remove(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Product.class, id));
        em.getTransaction().commit();
    }
}

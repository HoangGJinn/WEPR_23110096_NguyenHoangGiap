package giap.hcmute.vn.repository.impl;

import giap.hcmute.vn.config.JPAConfig;
import giap.hcmute.vn.model.CategoryEntity;
import giap.hcmute.vn.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public Optional<CategoryEntity> findById(int cateid) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            CategoryEntity CategoryDTO = em.find(CategoryEntity.class, cateid);
            return Optional.ofNullable(CategoryDTO);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<CategoryEntity> findByName(String catename) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<CategoryEntity> query = em.createQuery(
                "SELECT c FROM CategoryEntity c WHERE c.catename = :catename", CategoryEntity.class);
            query.setParameter("catename", catename);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    @Override
    public List<CategoryEntity> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<CategoryEntity> query = em.createQuery(
                "SELECT c FROM CategoryEntity c ORDER BY c.catename", CategoryEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<CategoryEntity> search(String keyword) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<CategoryEntity> query = em.createQuery(
                "SELECT c FROM CategoryEntity c WHERE c.catename LIKE :keyword", CategoryEntity.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void save(CategoryEntity CategoryDTO) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(CategoryDTO);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(CategoryEntity CategoryDTO) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(CategoryDTO);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(int cateid) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            CategoryEntity CategoryDTO = em.find(CategoryEntity.class, cateid);
            if (CategoryDTO != null) {
                em.remove(CategoryDTO);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(c) FROM CategoryEntity c", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<CategoryEntity> findByUserId(int userId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<CategoryEntity> query = em.createQuery(
                "SELECT c FROM CategoryEntity c WHERE c.user.id = :userId ORDER BY c.catename", CategoryEntity.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}

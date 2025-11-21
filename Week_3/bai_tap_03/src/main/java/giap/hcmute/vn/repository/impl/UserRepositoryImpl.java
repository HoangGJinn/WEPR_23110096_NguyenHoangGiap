package giap.hcmute.vn.repository.impl;

import giap.hcmute.vn.config.JPAConfig;
import giap.hcmute.vn.model.UserEntity;
import giap.hcmute.vn.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

	@Override
	public Optional<UserEntity> findById(int id) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        TypedQuery<UserEntity> query = em.createQuery(
	                "SELECT DISTINCT u FROM UserEntity u " +
	                "LEFT JOIN FETCH u.categories " +
	                "WHERE u.id = :id", UserEntity.class);
	        query.setParameter("id", id);

	        UserEntity user = query.getSingleResult();
	        return Optional.ofNullable(user);
	    } catch (NoResultException e) {
	        return Optional.empty();
	    } finally {
	        em.close();
	    }
	}


	@Override
	public Optional<UserEntity> findByUsername(String username) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        TypedQuery<UserEntity> query = em.createQuery(
	            "SELECT DISTINCT u FROM UserEntity u " +
	            "LEFT JOIN FETCH u.categories " +
	            "WHERE u.username = :username", UserEntity.class);
	        query.setParameter("username", username);
	        UserEntity user = query.getSingleResult();
	        return Optional.ofNullable(user);
	    } catch (NoResultException e) {
	        return Optional.empty();
	    } finally {
	        em.close();
	    }
	}


	@Override
	public Optional<UserEntity> findByEmail(String email) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        TypedQuery<UserEntity> query = em.createQuery(
	            "SELECT DISTINCT u FROM UserEntity u " +
	            "LEFT JOIN FETCH u.categories " +
	            "WHERE u.email = :email", UserEntity.class);
	        query.setParameter("email", email);
	        UserEntity user = query.getSingleResult();
	        return Optional.ofNullable(user);
	    } catch (NoResultException e) {
	        return Optional.empty();
	    } finally {
	        em.close();
	    }
	}


	@Override
	public List<UserEntity> findAll() {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        TypedQuery<UserEntity> query = em.createQuery(
	            "SELECT DISTINCT u FROM UserEntity u " +
	            "LEFT JOIN FETCH u.categories", UserEntity.class);
	        return query.getResultList();
	    } finally {
	        em.close();
	    }
	}


    @Override
    public void save(UserEntity user) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(UserEntity user) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            UserEntity user = em.find(UserEntity.class, id);
            if (user != null) {
                em.remove(user);
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
    public boolean existsByUsername(String username) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM UserEntity u WHERE u.username = :username", Long.class);
            query.setParameter("username", username);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM UserEntity u WHERE u.email = :email", Long.class);
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsByPhone(String phone) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM UserEntity u WHERE u.phone = :phone", Long.class);
            query.setParameter("phone", phone);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    @Override
    public void updatePasswordByEmail(String email, String newPassword) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<UserEntity> query = em.createQuery(
                "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class);
            query.setParameter("email", email);
            UserEntity user = query.getSingleResult();
            
            if (user != null) {
                user.setPassword(newPassword);
                em.merge(user);
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
    public boolean verifyPassword(String username, String password) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<UserEntity> query = em.createQuery(
                "SELECT u FROM UserEntity u WHERE u.username = :username", UserEntity.class);
            query.setParameter("username", username);
            UserEntity user = query.getSingleResult();
            return user != null && password.equals(user.getPassword());
        } catch (NoResultException e) {
            return false;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void updatePassword(int userId, String newPassword) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            UserEntity user = em.find(UserEntity.class, userId);
            if (user != null) {
                user.setPassword(newPassword);
                em.merge(user);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
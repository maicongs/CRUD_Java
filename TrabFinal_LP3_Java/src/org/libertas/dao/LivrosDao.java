package org.libertas.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.libertas.pojo.Livros;

public class LivrosDao {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("ConexaoHibernate");
	EntityManager em = emf.createEntityManager();
	
	public List<Livros> listar(){
		Query query = em.createQuery("SELECT l from Livros l");
		List<Livros> dados = query.getResultList();
		return dados;
	}
	
	public void inserir(Livros l) {
		em.getTransaction().begin();
		em.persist(l);
		em.getTransaction().commit();
	}
	
	public void alterar(Livros l) {
		em.getTransaction().begin();
		em.merge(l);
		em.getTransaction().commit();
	}
	
	public void excluir(Livros l) {
		em.getTransaction().begin();
		em.remove(em.merge(l));
		em.getTransaction().commit();
	}
	
	public Livros consultar(int id) {
		return em.find(Livros.class, id);
		
	}

}

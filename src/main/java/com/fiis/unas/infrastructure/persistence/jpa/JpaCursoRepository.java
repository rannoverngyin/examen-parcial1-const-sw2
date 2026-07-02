package com.fiis.unas.infrastructure.persistence.jpa;

import com.fiis.unas.domain.entity.Curso;
import com.fiis.unas.domain.repository.CursoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class JpaCursoRepository implements CursoRepository {

    private final EntityManager em;

    public JpaCursoRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Curso> findByCiclo(Integer ciclo) {
        TypedQuery<CursoJpaEntity> query = em.createQuery(
                "SELECT c FROM CursoJpaEntity c WHERE c.ciclo = :ciclo", CursoJpaEntity.class);
        query.setParameter("ciclo", ciclo);
        return query.getResultList().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public long countByDocente(String docente) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(c) FROM CursoJpaEntity c WHERE c.docente = :docente", Long.class);
        query.setParameter("docente", docente);
        return query.getSingleResult();
    }

    private Curso toDomain(CursoJpaEntity entity) {
        Curso c = new Curso();
        c.setId(entity.getId());
        c.setCodigo(entity.getCodigo());
        c.setNombre(entity.getNombre());
        c.setCiclo(entity.getCiclo());
        c.setCreditos(entity.getCreditos());
        c.setDocente(entity.getDocente());
        return c;
    }
}

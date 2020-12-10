package pl.wb.security.service;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CDIProducer {

    @Produces
    @PersistenceContext
    public EntityManager entityManager;
}

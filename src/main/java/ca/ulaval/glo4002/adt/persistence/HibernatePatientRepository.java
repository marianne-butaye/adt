package ca.ulaval.glo4002.adt.persistence;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ca.ulaval.glo4002.adt.ServiceLocator;
import ca.ulaval.glo4002.adt.domain.Patient;
import ca.ulaval.glo4002.adt.domain.PatientRepository;

public class HibernatePatientRepository implements PatientRepository {

  private EntityManager entityManager;

  public HibernatePatientRepository() {
    EntityManagerFactory entityManagerFactory = ServiceLocator.INSTANCE
        .resolve(EntityManagerFactory.class);
    entityManager = entityManagerFactory.createEntityManager();
    EntityManagerProvider.setEntityManager(entityManager);

    fillDatabase();
  }

  private void fillDatabase() {
    Patient pierre = new Patient("Pierre");
    persist(pierre);

    Patient marie = new Patient("Marie");
    marie.moveToDepartment("ICU");
    persist(marie);
  }

  @Override
  public void persist(Patient patient) {
    EntityManager em = EntityManagerProvider.getEntityManager();
    entityManager.getTransaction().begin();
    em.persist(patient);
    entityManager.getTransaction().commit();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Collection<Patient> findAll() {
    EntityManager em = EntityManagerProvider.getEntityManager();
    return (Collection<Patient>) em.createQuery("from Patient").getResultList();
  }

  @Override
  public Patient findById(int patientId) {
    EntityManager em = EntityManagerProvider.getEntityManager();
    return em.find(Patient.class, patientId);
  }

}

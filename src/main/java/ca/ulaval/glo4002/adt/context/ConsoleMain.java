package ca.ulaval.glo4002.adt.context;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ca.ulaval.glo4002.adt.applicationservice.PatientService;
import ca.ulaval.glo4002.adt.applicationservice.ServiceLocator;
import ca.ulaval.glo4002.adt.persistence.HibernatePatientRepository;
import ca.ulaval.glo4002.adt.ui.ConsoleUI;

public class ConsoleMain {

  public static void main(String[] args) {
    initializeServiceLocator();

    System.exit(0);
  }

  private static void initializeServiceLocator() {
    ServiceLocator.INSTANCE.register(EntityManagerFactory.class,
        Persistence.createEntityManagerFactory("adt"));
    ServiceLocator.INSTANCE.register(HibernatePatientRepository.class,
        new HibernatePatientRepository());
    ServiceLocator.INSTANCE.register(PatientService.class, new PatientService());
    ServiceLocator.INSTANCE.register(ConsoleUI.class, new ConsoleUI());
  }

}

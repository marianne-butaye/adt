package ca.ulaval.glo4002.adt;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ca.ulaval.glo4002.adt.persistence.HibernatePatientRepository;
import ca.ulaval.glo4002.adt.service.ApplicationService;
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
    ServiceLocator.INSTANCE.register(ApplicationService.class, new ApplicationService());
    ServiceLocator.INSTANCE.register(ConsoleUI.class, new ConsoleUI());
  }

}

package ca.ulaval.glo4002.adt;

import java.util.Collection;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConsoleMain {
	private static Scanner scanner;

	public static void main(String[] args) {
		initializeServiceLocator();
		fillDatabase();

		try (Scanner scanner = new Scanner(System.in)) {
			ConsoleMain.scanner = scanner;
			startCommandPromptLoop();
		}

		System.exit(0);
	}

	private static void initializeServiceLocator() {
		ServiceLocator.INSTANCE.register(HibernatePatientRepository.class, new HibernatePatientRepository());
		ServiceLocator.INSTANCE.register(EntityManagerFactory.class, Persistence.createEntityManagerFactory("adt"));
	}

	private static void fillDatabase() {
		HibernatePatientRepository patientRepository = ServiceLocator.INSTANCE
				.resolve(HibernatePatientRepository.class);

		// TODO find a way to encapsulate this, it is repeated everywhere
		EntityManagerFactory entityManagerFactory = ServiceLocator.INSTANCE.resolve(EntityManagerFactory.class);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityManagerProvider.setEntityManager(entityManager);

		entityManager.getTransaction().begin();

		Patient pierre = new Patient("Pierre");
		patientRepository.persist(pierre);

		Patient marie = new Patient("Marie");
		marie.moveToDepartment("ICU");
		patientRepository.persist(marie);

		entityManager.getTransaction().commit();
	}

	private static void startCommandPromptLoop() {
		System.out.println("Welcome to GLO-4002's ADT!");

		boolean quit = false;

		while (!quit) {
			String option = pickOptionFromMenu();
			System.out.println("\n");
			switch (option) {
			case "1": {
				displayPatientList();
				break;
			}
			case "2": {
				movePatient();
				break;
			}
			case "3": {
				dischargePatient();
				break;
			}
			case "q": {
				quit = true;
				break;
			}
			default: {
				System.out.println("Invalid option");
			}
			}
		}

		System.out.println("Bye bye");

	}

	private static String pickOptionFromMenu() {
		System.out.println("\n");
		System.out.println("-----------------");
		System.out.println("What do you want to do?");
		System.out.println("1) List patients");
		System.out.println("2) Move a patient");
		System.out.println("3) Discharge a patient");

		System.out.print("Choice (or q tp quit) : ");
		return scanner.nextLine();
	}

	private static void displayPatientList() {
		HibernatePatientRepository patientRepository = ServiceLocator.INSTANCE
				.resolve(HibernatePatientRepository.class);

		// TODO find a way to encapsulate this, it is repeated everywhere
		EntityManagerFactory entityManagerFactory = ServiceLocator.INSTANCE.resolve(EntityManagerFactory.class);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityManagerProvider.setEntityManager(entityManager);

		Collection<Patient> patients = patientRepository.findAll();

		for (Patient patient : patients) {
			System.out.println(String.format("%d : %s (status = %s, department = %s)", patient.getId(),
					patient.getName(), patient.getStatus(), patient.getDepartment()));
		}
	}

	private static void movePatient() {
		System.out.println("First, you must select a patient : ");
		displayPatientList();

		// TODO find a way to encapsulate this, it is repeated everywhere
		HibernatePatientRepository patientRepository = ServiceLocator.INSTANCE
				.resolve(HibernatePatientRepository.class);
		EntityManagerFactory entityManagerFactory = ServiceLocator.INSTANCE.resolve(EntityManagerFactory.class);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityManagerProvider.setEntityManager(entityManager);

		System.out.print("Patient ID to move : ");
		int patientId = Integer.parseInt(scanner.nextLine());

		System.out.print("New department : ");
		String newDepartment = scanner.nextLine();

		entityManager.getTransaction().begin();
		Patient patient = patientRepository.findById(patientId);
		patient.moveToDepartment(newDepartment);
		patientRepository.persist(patient);
		entityManager.getTransaction().commit();
	}
	
	private static void dischargePatient() {
		System.out.println("First, you must select a patient : ");
		displayPatientList();

		// TODO find a way to encapsulate this, it is repeated everywhere
		HibernatePatientRepository patientRepository = ServiceLocator.INSTANCE
				.resolve(HibernatePatientRepository.class);
		EntityManagerFactory entityManagerFactory = ServiceLocator.INSTANCE.resolve(EntityManagerFactory.class);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityManagerProvider.setEntityManager(entityManager);

		System.out.print("Patient ID to discharge : ");
		int patientId = Integer.parseInt(scanner.nextLine());

		entityManager.getTransaction().begin();
		Patient patient = patientRepository.findById(patientId);
		patient.discharge();
		patientRepository.persist(patient);
		entityManager.getTransaction().commit();
	}

}

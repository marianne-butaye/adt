package ca.ulaval.glo4002.adt.ui;

import java.util.Collection;
import java.util.Scanner;

import ca.ulaval.glo4002.adt.applicationservice.PatientService;
import ca.ulaval.glo4002.adt.applicationservice.ServiceLocator;
import ca.ulaval.glo4002.adt.domain.Patient;

public class ConsoleUI {

  private Scanner scanner;
  private PatientService applicationService;

  public ConsoleUI() {
    this.applicationService = ServiceLocator.INSTANCE.resolve(PatientService.class);
    try (Scanner scanner = new Scanner(System.in)) {
      this.scanner = scanner;
      startCommandPromptLoop();
    }
  }

  private void startCommandPromptLoop() {
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

  private String pickOptionFromMenu() {
    System.out.println("\n");
    System.out.println("-----------------");
    System.out.println("What do you want to do?");
    System.out.println("1) List patients");
    System.out.println("2) Move a patient");
    System.out.println("3) Discharge a patient");

    System.out.print("Choice (or q tp quit) : ");
    return scanner.nextLine();
  }

  private void displayPatientList() {
    Collection<Patient> patients = applicationService.getPatientList();

    for (Patient patient : patients) {
      System.out.println(String.format("%d : %s (status = %s, department = %s)", patient.getId(),
          patient.getName(), patient.getStatus(), patient.getDepartment()));
    }
  }

  private void movePatient() {
    System.out.println("First, you must select a patient : ");
    displayPatientList();

    System.out.print("Patient ID to move : ");
    int patientId = Integer.parseInt(scanner.nextLine());

    System.out.print("New department : ");
    String newDepartment = scanner.nextLine();

    applicationService.movePatient(patientId, newDepartment);
  }

  private void dischargePatient() {
    System.out.println("First, you must select a patient : ");
    displayPatientList();

    System.out.print("Patient ID to discharge : ");
    int patientId = Integer.parseInt(scanner.nextLine());

    applicationService.dischargePatient(patientId);
  }

}

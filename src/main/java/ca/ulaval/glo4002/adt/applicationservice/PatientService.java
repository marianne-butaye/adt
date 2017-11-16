package ca.ulaval.glo4002.adt.applicationservice;

import java.util.Collection;

import ca.ulaval.glo4002.adt.domain.Patient;
import ca.ulaval.glo4002.adt.domain.PatientRepository;
import ca.ulaval.glo4002.adt.persistence.HibernatePatientRepository;

public class PatientService {

  private PatientRepository patientRepository;

  public PatientService() {
    this.patientRepository = ServiceLocator.INSTANCE.resolve(HibernatePatientRepository.class);
  }

  public Collection<Patient> getPatientList() {
    Collection<Patient> patients = patientRepository.findAll();
    return patients;
  }

  public void movePatient(int patientId, String newDepartment) {
    Patient patient = patientRepository.findById(patientId);
    patient.moveToDepartment(newDepartment);
    patientRepository.persist(patient);
  }

  public void dischargePatient(int patientId) {
    Patient patient = patientRepository.findById(patientId);
    patient.discharge();
    patientRepository.persist(patient);
  }

}

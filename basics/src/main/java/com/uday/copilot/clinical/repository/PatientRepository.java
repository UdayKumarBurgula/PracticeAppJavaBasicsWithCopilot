package com.uday.copilot.clinical.repository;

import com.uday.copilot.clinical.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}

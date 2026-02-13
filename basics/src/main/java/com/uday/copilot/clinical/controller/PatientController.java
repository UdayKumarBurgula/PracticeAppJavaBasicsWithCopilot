package com.uday.copilot.clinical.controller;

import com.uday.copilot.clinical.model.Observation;
import com.uday.copilot.clinical.model.Patient;
import com.uday.copilot.clinical.repository.ObservationRepository;
import com.uday.copilot.clinical.repository.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientRepository patientRepository;
    private final ObservationRepository observationRepository;

    public PatientController(PatientRepository patientRepository, ObservationRepository observationRepository) {
        this.patientRepository = patientRepository;
        this.observationRepository = observationRepository;
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient saved = patientRepository.save(patient);
        return ResponseEntity.created(URI.create("/api/patients/" + saved.getId())).body(saved);
    }

    @GetMapping
    public List<Patient> listPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
        return patientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/observations")
    public ResponseEntity<Observation> addObservation(@PathVariable Long id, @RequestBody Observation observation) {
        return patientRepository.findById(id).map(patient -> {
            observation.setPatient(patient);
            Observation saved = observationRepository.save(observation);
            return ResponseEntity.created(URI.create("/api/patients/" + id + "/observations/" + saved.getId())).body(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/observations")
    public ResponseEntity<List<Observation>> getObservations(@PathVariable Long id) {
        if (!patientRepository.existsById(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(observationRepository.findByPatientId(id));
    }
}

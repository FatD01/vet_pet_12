package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/specialties")
public class SpecialtyController {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyController(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @GetMapping
    public List<Specialty> getAllSpecialties() {
        return specialtyRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialty> getSpecialtyById(@PathVariable Integer id) {
        Optional<Specialty> specialty = specialtyRepository.findById(id);
        return specialty.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Specialty> createSpecialty(@RequestBody Specialty specialty) {
        Specialty savedSpecialty = specialtyRepository.save(specialty);
        return new ResponseEntity<>(savedSpecialty, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialty> updateSpecialty(@PathVariable Integer id, @RequestBody Specialty specialtyData) {
        return specialtyRepository.findById(id)
                .map(specialty -> {
                    specialty.setName(specialtyData.getName());
                    Specialty updatedSpecialty = specialtyRepository.save(specialty);
                    return ResponseEntity.ok(updatedSpecialty);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Integer id) {
        if (specialtyRepository.existsById(id)) {
            specialtyRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

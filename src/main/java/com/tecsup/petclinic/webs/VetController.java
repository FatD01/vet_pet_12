package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.repositories.VetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vets")
public class VetController {
    private final VetRepository vetRepository;

    public VetController(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @GetMapping
    public List<Vet> getAllVets() {
        return vetRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vet> getVetById(@PathVariable Integer id) {
        Optional<Vet> vet = vetRepository.findById(id);
        return vet.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vet> createVet(@RequestBody Vet vet) {
        Vet savedVet = vetRepository.save(vet);
        return new ResponseEntity<>(savedVet, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vet> updateVet(@PathVariable Integer id, @RequestBody Vet vetData) {
        return vetRepository.findById(id)
                .map(vet -> {
                    vet.setFirstName(vetData.getFirstName());
                    vet.setLastName(vetData.getLastName());
                    vet.setSpecialties(vetData.getSpecialties());
                    Vet updatedVet = vetRepository.save(vet);
                    return ResponseEntity.ok(updatedVet);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVet(@PathVariable Integer id) {
        if (vetRepository.existsById(id)) {
            vetRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

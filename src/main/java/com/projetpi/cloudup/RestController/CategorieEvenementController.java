package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.CategorieEvenement;
import com.projetpi.cloudup.entities.Evenement;
import com.projetpi.cloudup.service.CategorieEvenementServiceIMP;
import com.projetpi.cloudup.service.EvenementServiceIMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@Service
@RestController
@RequestMapping("auth/categories")  // Base path for all category-related endpoints
public class CategorieEvenementController {
    @Autowired

    private EvenementServiceIMP ievenementService;
    @Autowired
    private CategorieEvenementServiceIMP categorieEvenementService;

    @PostMapping("/add")  // Endpoint to create a new category
    public ResponseEntity<CategorieEvenement> addCategorieEvenement(@RequestBody CategorieEvenement categorieEvenement) {
        CategorieEvenement savedCategorieEvenement = categorieEvenementService.addCategorieEvenement(categorieEvenement);
        return ResponseEntity.ok(savedCategorieEvenement);  // Return created category with status 200 (OK)
    }

    @GetMapping("/{id}")  // Endpoint to retrieve a category by ID
    public ResponseEntity<CategorieEvenement> retrieveCategorieEvenementById(@PathVariable Long id) {
        CategorieEvenement categorieEvenement = categorieEvenementService.retrieveCategorieEvenementById(id);
        if (categorieEvenement != null) {
            return ResponseEntity.ok(categorieEvenement);
        } else {
            return ResponseEntity.notFound().build();  // Return 404 (Not Found) if category not found
        }
    }


    @PutMapping("/update/{id}")
    public CategorieEvenement updateEvenement(@RequestBody CategorieEvenement evenement, @PathVariable Long id) {
        evenement.setId(id); // Ensure ID is set for update
        return categorieEvenementService.updateCategorieEvenement(evenement);
    }

    @GetMapping("/all")  // Endpoint to retrieve all categories
    public ResponseEntity<List<CategorieEvenement>> retrieveAllCategoriesEvenement() {
        List<CategorieEvenement> categories = categorieEvenementService.retrieveAllCategoriesEvenement();
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/delete/{id}")  // Endpoint to delete a category by ID
    public void deleteCategorieEvenement(@PathVariable Long id) {
        categorieEvenementService.deleteCategorieEvenement(id);
    }
  /*  @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategorieEvenement(@PathVariable Long id) {
        // Find all events (replace if you have filter by category functionality)
        List<Evenement> events = ievenementService.retrieveAllEvenements(); // Or filter by category_id

        // Filter events associated with the category being deleted (if needed)
        List<Evenement> eventsToDelete = new ArrayList<>();
        for (Evenement event : events) {
            if (event.getCategoryId() != null && event.getCategoryId().equals(id)) { // Assuming categoryId field exists
                eventsToDelete.add(event);
            }
        }

        // Delete each event
        for (Evenement event : eventsToDelete) {
            ievenementService.deleteEvenement(event.getId()); // Or similar delete method
        }

        // Now delete the category itself
        categorieEvenementService.deleteCategorieEvenement(id);
        return ResponseEntity.ok().build();
    }*/
}
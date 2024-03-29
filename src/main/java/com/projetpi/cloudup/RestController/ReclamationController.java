package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Reclamation;
import com.projetpi.cloudup.service.IReclamation;
import com.projetpi.cloudup.service.ReclamationServiceIMP;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class ReclamationController {
    private IReclamation iReclamation;

    @PostMapping("/addreclamation")
    public Reclamation AjouterReclamation(@RequestBody Reclamation reclamation) {
    return iReclamation.AjouterReclamation(reclamation);
    }
    @GetMapping("/getallreclams")
    public List<Reclamation> RetrieveAll ()
    {
        return iReclamation.RetrieveAll();
    }
    @GetMapping("/getidreclam/{id}")
    public Reclamation RetrieveById (@PathVariable Integer id)
    {
        return iReclamation.RetrieveById(id);
    }
    @PutMapping("/updatereclam/{reclamation}")
    public Reclamation UpdateReclamation (@RequestBody Reclamation reclamation)
    {
        return iReclamation.UpdateReclamation(reclamation);
    }
    @DeleteMapping("/deletereclam/{id}")
    public void DeleteReclamation (@PathVariable Integer id)
    {
        iReclamation.DeleteReclamation(id);
    }
}
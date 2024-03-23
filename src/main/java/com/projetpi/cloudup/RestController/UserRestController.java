package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.service.IUser;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@NoArgsConstructor
public class UserRestController {
    public IUser iUser;

    @Autowired
    public UserRestController(IUser iUser) {
        this.iUser = iUser;
    }

    @PostMapping("/addUser")
    public User addU (@RequestBody User u) {
        return iUser.addU(u);
    }

    @GetMapping("/retrieveAllPtoUser")
    public List<Publication> retrieveAllP(){
        return iUser.retrieveAllP();
    }

    @GetMapping("/retrieveAllCtoUser")
    public List<Commentary> retrieveAllC(){
        return iUser.retrieveAllC();
    }

}

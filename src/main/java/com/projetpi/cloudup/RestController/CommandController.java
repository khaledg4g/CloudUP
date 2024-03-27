package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.dto.CommandDto;
import com.projetpi.cloudup.service.ICommandService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands")
@AllArgsConstructor
public class CommandController {
    private ICommandService commandService;
    @PostMapping()
    public ResponseEntity<CommandDto> create(@RequestBody CommandDto commandDto){

        try {

            return new ResponseEntity<>(commandService.create(commandDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}

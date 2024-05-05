package com.projetpi.cloudup.service.Impl;

import com.projetpi.cloudup.dto.CommandDto;
import com.projetpi.cloudup.dto.CommandProductDto;
import com.projetpi.cloudup.entities.Command;
import com.projetpi.cloudup.entities.CommandProduct;
import com.projetpi.cloudup.repository.ICommandProductRepository;
import com.projetpi.cloudup.repository.ICommandRepository;
import com.projetpi.cloudup.repository.UserRepository;
import com.projetpi.cloudup.service.ICommandService;
import lombok.AllArgsConstructor;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class CommandServiceImpl implements ICommandService {
    private Mapper mapper;
    private ICommandRepository commandRepository;
    private UserRepository userRepository;
    private ICommandProductRepository commandProductRepository;
    @Override
    public CommandDto create(CommandDto commandDto) {
        Command command = mapper.map(commandDto, Command.class);
        command.setUser(userRepository.findUserByEmail(commandDto.getUser().getEmail()).orElse(null));
        if(command.getUser() == null){
            return null;
        }
        Set<CommandProduct> savedCommandProducts = new HashSet<>();
        for (CommandProductDto commandProductDto : commandDto.getCommandProducts()) {
            CommandProduct commandProduct = mapper.map(commandProductDto, CommandProduct.class);
            savedCommandProducts.add(commandProductRepository.save(commandProduct));
        }
        command.setCommandProducts(savedCommandProducts);
        Command savedCommand = commandRepository.save(command);
        return mapper.map(savedCommand, CommandDto.class);
    }
}

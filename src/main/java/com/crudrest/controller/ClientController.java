package com.crudrest.controller;

import com.crudrest.dto.ClientDTO;
import com.crudrest.dto.ResponseMessage;
import com.crudrest.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAllClients(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "3") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clientService.findAllClients(pageRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
        return clientService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> saveNewClient(@RequestBody ClientDTO clientDTO){
        return clientService.saveNewClient(clientDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO){
        return clientService.updateClient(id, clientDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteClient(@PathVariable Long id){
        return clientService.deleteClient(id);
    }

}

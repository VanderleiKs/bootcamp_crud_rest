package com.crudrest.service;

import com.crudrest.dto.ClientDTO;
import com.crudrest.dto.ResponseMessage;
import com.crudrest.entity.Client;
import com.crudrest.exception.ClientException;
import com.crudrest.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    //Find All clients
    public ResponseEntity<Page<ClientDTO>> findAllClients(PageRequest pageRequest){
        return ResponseEntity.ok().body(clientRepository.findAll(pageRequest).map(ClientDTO::new));
    }

    //Find by id
    public ResponseEntity<ClientDTO> findById(Long id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientException("Client not found"));
        return ResponseEntity.ok().body(new ClientDTO(client));
    }

    //Update client
    public ResponseEntity<ResponseMessage> updateClient(Long id, ClientDTO clientDTO){
        Client client = verifyIfExistClient(id);
        clientRepository.save(dtoToClient(clientDTO, client));
        return ResponseEntity.ok().body(new ResponseMessage("Save with success!"));
    }

    //Save new Client
    public ResponseEntity<ResponseMessage> saveNewClient(ClientDTO clientDTO){
        if(clientRepository.findByName(clientDTO.getName()) != null){
            throw new ClientException("Client already registered");
        }
        Client client = clientRepository.save(dtoToClient(clientDTO, new Client()));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseMessage("Save with success!"));
    }

    //Delete client
    public ResponseEntity<ResponseMessage> deleteClient(Long id){
        verifyIfExistClient(id);
        clientRepository.deleteById(id);
        return ResponseEntity.ok().body(new ResponseMessage("Excluded with success!"));
    }

    //Convert clientDTO to client
    private Client dtoToClient(ClientDTO clientDTO, Client client){
        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setIncome(clientDTO.getIncome());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setChildren(clientDTO.getChildren());
        return client;
    }

    //Verify if exist client
    private Client verifyIfExistClient(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ClientException("Client not found"));
    }
}

package com.sample.controllers;

import com.sample.Entities.SampleEntity;
import com.sample.dao.SampleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SampleController {
	@Autowired
    private SampleDAO dao;

    @PostMapping(path = "post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SampleEntity> post(@RequestBody SampleEntity s){
        //TODO: do more validations than just this
        if(dao.findById(s.getId()).isPresent())
            return new ResponseEntity<SampleEntity>(new SampleEntity(), HttpStatus.BAD_REQUEST);

        SampleEntity created = dao.save(s);
        return new ResponseEntity<SampleEntity>(created, created!=null ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @GetMapping(path = "get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SampleEntity> get(@PathVariable String id){
        try{ //try to parse the id as an int
            Optional<SampleEntity> response = dao.findById(Long.parseLong(id));
            if(response.isPresent())
                return new ResponseEntity<SampleEntity>(response.get(), HttpStatus.OK);
        }
        catch (final Exception e){
            //TODO: log this
        }
        return new ResponseEntity<SampleEntity>(new SampleEntity(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SampleEntity>> getAll(){
        List<SampleEntity> response = dao.findAll();
        return new ResponseEntity<List<SampleEntity>>(response, response!=null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "put", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SampleEntity> put(@RequestBody SampleEntity s){
        //TODO: do more validations than just this
        if(!dao.findById(s.getId()).isPresent())
            return new ResponseEntity<SampleEntity>(new SampleEntity(), HttpStatus.BAD_REQUEST);

        SampleEntity response = dao.save(s);
        return new ResponseEntity<SampleEntity>(response, response!=null ? HttpStatus.NO_CONTENT : HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        try{ //try to parse the id as an int
            dao.deleteById(Long.parseLong(id));
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        catch (final NumberFormatException e){
            //TODO: log this
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        catch (final IllegalArgumentException e){
            //TODO: log this
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}

package com.satoru.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

public class GenericService<Model, IDType extends Serializable, T extends MongoRepository<Model, IDType>> {
	
	protected Sort sort = new Sort(Direction.ASC, "id");
	
    @Autowired
    private T repository;
        
    @Transactional
    public Model save(Model entity) {
        return repository.save(entity);
    }
    
    public List<Model> listAll() {
        return repository.findAll(sort);
    }
    
    public Model findOne(IDType id) {
		return repository.findOne(id);
	}
	
    @Transactional
    public void delete(Model model) {
		repository.delete(model);
	}
    
    protected T getRepository() {
    	return repository;
    }
	
}
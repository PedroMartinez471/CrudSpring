package com.crud.models;

import org.springframework.data.repository.CrudRepository;

public interface UserCrud extends CrudRepository<User, Long>{
    User findUserByName(String name);
}

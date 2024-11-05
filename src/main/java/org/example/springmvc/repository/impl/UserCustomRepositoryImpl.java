package org.example.springmvc.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.springmvc.entity.User;
import org.example.springmvc.repository.UserCustomRepository;

public class UserCustomRepositoryImpl implements UserCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;



}

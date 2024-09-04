package com.aurionpro.model.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurionpro.model.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{

}

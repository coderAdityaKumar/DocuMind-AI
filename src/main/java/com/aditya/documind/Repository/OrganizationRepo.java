package com.aditya.documind.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aditya.documind.Entity.Organization;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization,Long> {

    
}

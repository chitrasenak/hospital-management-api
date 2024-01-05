package com.mirna.hospitalmanagementapi.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirna.hospitalmanagementapi.domain.entities.Doctor;

/**
 * Repository interface for retrieving and manipulating all Doctor objects using their unique Long identifier.
*
 * @author Mirna Gama
* @version 1.0
*/
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
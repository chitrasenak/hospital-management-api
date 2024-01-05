package com.mirna.hospitalmanagementapi.unit.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.mirna.hospitalmanagementapi.HospitalManagementApiApplication;
import com.mirna.hospitalmanagementapi.application.services.DoctorServiceImpl;
import com.mirna.hospitalmanagementapi.domain.dtos.AddressDTO;
import com.mirna.hospitalmanagementapi.domain.dtos.DoctorDTO;
import com.mirna.hospitalmanagementapi.domain.dtos.DoctorPublicDataDTO;
import com.mirna.hospitalmanagementapi.domain.entities.Doctor;
import com.mirna.hospitalmanagementapi.domain.enums.Specialty;
import com.mirna.hospitalmanagementapi.domain.repositories.DoctorRepository;

import jakarta.validation.ConstraintViolationException;

/**
 * 
 * @author Mirna Gama
 * @version 1.0
 */
@SpringBootTest(classes = HospitalManagementApiApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class DoctorServiceTest {

	@Autowired
	private DoctorServiceImpl doctorService;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@BeforeAll
	public void init() {
		DoctorDTO doctorDTO1 = new DoctorDTO("test1", "test1@gmail.com", "123456", "99999999", Specialty.ORTHOPEDICS,
				new AddressDTO("TEST STREET", "NEIGHBORHOOD", "12345678", "CITY", "ST", null, null));
				
		DoctorDTO doctorDTO2 = new DoctorDTO("test2", "test2@gmail.com", "789101", "99999999", Specialty.ORTHOPEDICS,
				new AddressDTO("TEST STREET", "NEIGHBORHOOD", "12345678", "CITY", "ST", null, null));
		
		DoctorDTO doctorDTO3 = new DoctorDTO("test3", "test3@gmail.com", "112131", "99999999", Specialty.ORTHOPEDICS,
				new AddressDTO("TEST STREET", "NEIGHBORHOOD", "12345678", "CITY", "ST", null, null));
	
		doctorRepository.save(new Doctor(doctorDTO1));
		doctorRepository.save(new Doctor(doctorDTO2));
		doctorRepository.save(new Doctor(doctorDTO3));
	}
	
	@AfterAll
	public void terminate() {
		doctorRepository.deleteAll();
	}
	
	/**
	 * Create a valid doctor.
	 */
	@Test
	@DisplayName("Should add valid doctor")
	public void testAddValidDoctor() throws Exception {
		DoctorDTO doctorDTO = new DoctorDTO("test", "test@gmail.com", "123456", "99999999", Specialty.ORTHOPEDICS,
				new AddressDTO("TEST STREET", "NEIGHBORHOOD", "12345678", "CITY", "ST", null, null));
		
		Doctor doctor = doctorService.addDoctor(doctorDTO);
		
		assertNotNull(doctor.getId());
	}
	
	/**
	 * Avoid creating a doctor with invalid parameter. Example: name being blank
	 */
	@Test
	@DisplayName("Should not add doctor with invalid parameter")
	public void testAddInvalidDoctor() throws Exception {
		DoctorDTO doctorDTO = new DoctorDTO("", "test@gmail.com", "123456", "99999999", Specialty.ORTHOPEDICS,
				new AddressDTO("TEST STREET", "NEIGHBORHOOD", "12345678", "CITY", "ST", null, null));
		
		assertThrows(ConstraintViolationException.class, () ->  doctorService.addDoctor(doctorDTO));
	}
	
	/**
	 * Finds doctors stored in the database with pagination
	 */
	@Test
	@DisplayName("Should find doctors with pagination")
	public void testFindDoctors() throws Exception {
		Pageable pageable = PageRequest.of(0, 3);
		
		Page<DoctorPublicDataDTO> doctors = doctorService.findDoctors(pageable);
		
		assertEquals(doctors.getSize(), 3);
	}
}
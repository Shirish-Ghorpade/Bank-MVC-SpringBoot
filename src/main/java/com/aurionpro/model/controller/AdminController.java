package com.aurionpro.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.model.dto.AdminDataDto;
import com.aurionpro.model.dto.AdminDto;
import com.aurionpro.model.dto.PageResponse;
import com.aurionpro.model.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bankapp")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin")
	public ResponseEntity<AdminDto> addNewAdmin(@Valid @RequestBody AdminDataDto adminDataDto) {
		return ResponseEntity.ok(adminService.addNewAdmin(adminDataDto));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/{adminId}")
	public ResponseEntity<AdminDto> getAdminById(@PathVariable int adminId) {
		return ResponseEntity.ok(adminService.getAdminById(adminId));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/admins")
	ResponseEntity<PageResponse<AdminDto>> viewAdmins(
			@RequestParam(defaultValue = "0", required = false) int pageNo,
			@RequestParam(defaultValue = "5", required = false) int pageSize,
			@RequestParam(defaultValue = "adminId", required = false) String sortBy,
			@RequestParam(defaultValue = "asc", required = false) String sortDir) {
		return ResponseEntity.ok(adminService.getAllAdmins(pageNo, pageSize, sortBy, sortDir));
	}


	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/admin/{adminId}")
	public ResponseEntity<AdminDto> updateAdminById(@PathVariable int adminId,
			@Valid @RequestBody AdminDataDto adminDataDto) {
		return ResponseEntity.ok(adminService.updateAdminById(adminId, adminDataDto));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/admin/{adminId}")
	public ResponseEntity<String> deleteAdminById(@PathVariable int adminId) {
		return ResponseEntity.ok(adminService.deleteAdminById(adminId));
	}

}

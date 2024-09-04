package com.aurionpro.model.service;

import com.aurionpro.model.dto.AdminDataDto;
import com.aurionpro.model.dto.AdminDto;
import com.aurionpro.model.dto.PageResponse;

public interface AdminService {
	
	AdminDto addNewAdmin(AdminDataDto customerDataDto);

	AdminDto getAdminById(int id);
	
	PageResponse<AdminDto> getAllAdmins(int pageNo, int pageSize, String sortBy, String sortDir);
	
	AdminDto updateAdminById(int id, AdminDataDto customerDataDto);

	String deleteAdminById(int id);
}

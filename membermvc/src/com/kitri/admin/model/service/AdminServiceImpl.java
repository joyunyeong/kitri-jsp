package com.kitri.admin.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kitri.member.model.MemberDetailDto;

public class AdminServiceImpl implements AdminService {

	private static AdminService adminService;
	
	static {
		adminService = new AdminServiceImpl();
	}
	
	private AdminServiceImpl() {}
	
	public static AdminService getAdminService() {
		return adminService;
	}

	@Override
	public String getMemberList(String key, String word) {
		// DB에 가서 가져와야함
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", key);
		map.put("word", word);
		List<MemberDetailDto> list = AdminServiceImpl.getadmind
		return null;
	}

}

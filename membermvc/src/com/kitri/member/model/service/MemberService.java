package com.kitri.member.model.service;

import com.kitri.member.model.*;

public interface MemberService {

	String idCheck(String id);
	String zipSearch(String doro);
	int registerMember(MemberDetailDto memberDetailDto);
	MemberDto loginMember(String id, String pass);
	
	MemberDetailDto getMember(String id);
	int modifyMember(MemberDetailDto memberDetailDto);
	int deleteMember(String id);
	
}

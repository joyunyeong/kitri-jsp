package com.kitri.member.model.dao;

import java.util.List;
import java.util.Map;

import com.kitri.member.model.*;

public interface MemberDao {

	int idCheck(String id);
	List<ZipcodeDto> zipSearch(String doro);
	int registerMember(MemberDetailDto memberDetailDto);
	MemberDto loginMember(Map<String, String> map);
	
	MemberDetailDto getMember(String id);
	int modifyMember(MemberDetailDto memberDetailDto);
	int deleteMember(String id);
	
}

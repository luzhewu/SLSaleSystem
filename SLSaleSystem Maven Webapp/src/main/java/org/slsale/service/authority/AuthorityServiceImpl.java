package org.slsale.service.authority;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.slsale.dao.authority.AuthorityMapper;
import org.slsale.dao.function.FunctionMapper;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	@Resource
	private AuthorityMapper authorityMapper;
	@Resource
	private FunctionMapper functionMapper;
	private Logger logger = Logger.getLogger(AuthorityServiceImpl.class);

	@Override
	public int addAuthority(Authority authority) throws Exception {
		return authorityMapper.addAuthority(authority);
	}

	@Override
	public int modifyAuthority(Authority authority) throws Exception {
		return authorityMapper.modifyAuthority(authority);
	}

	@Override
	public int deleteAuthority(Authority authority) throws Exception {
		return authorityMapper.deleteAuthority(authority);
	}

	@Override
	public List<Authority> getAuthorityList(Authority authority)
			throws Exception {
		return authorityMapper.getAuthorityList(authority);
	}

	@Override
	public Authority getAuthority(Authority authority) throws Exception {
		return authorityMapper.getAuthority(authority);
	}

	@Override
	public boolean lu_addAuthority(String[] ids, String createdBy)
			throws Exception {
		Authority authority = new Authority();
		authority.setRoleId(Integer.parseInt(ids[0]));
		// 删除角色下的功能
		authorityMapper.deleteAuthority(authority);
		String idsSql = "";
		for (int i = 1; i < ids.length; i++) {
			idsSql += Integer.parseInt(ids[i]) + ",";
		}
		if (idsSql != null && idsSql.contains(",")) {
			idsSql = idsSql.substring(0, idsSql.lastIndexOf(","));
			List<Function> fList = functionMapper.getFunctionListByIn(idsSql);
			if (null != fList && fList.size() > 0) {
				for (Function function : fList) {
					authority.setFunctionId(function.getId());
					authority.setCreatedBy(createdBy);
					authority.setCreationTime(new Date());
					authorityMapper.addAuthority(authority);
				}
			}
		}
		return true;
	}

	@Override
	public boolean lu_delAddAuthority(Authority authority, String checkFuncList)
			throws Exception {
		String[] funcList = null;
		authorityMapper.deleteAuthority(authority);
		if (null != checkFuncList && !"".equals(checkFuncList)) {
			funcList = checkFuncList.split(",");
			for (int i = 0; i < funcList.length; i++) {
				authority.setFunctionId(Integer.parseInt(funcList[i]));
				authorityMapper.addAuthority(authority);
			}
		}
		return true;
	}

}

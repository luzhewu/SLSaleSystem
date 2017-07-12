package org.slsale.service.function;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.function.FunctionMapper;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;
import org.springframework.stereotype.Service;

@Service
public class FunctionServiceImpl implements FunctionService {
	@Resource
	private FunctionMapper functionMapper;

	@Override
	public List<Function> getMainFunctionList(Authority authority)
			throws Exception {
		return functionMapper.getMainFunctionList(authority);
	}

	@Override
	public List<Function> getSubFunctionList(Function function)
			throws Exception {
		return functionMapper.getSubFunctionList(function);
	}

	@Override
	public List<Function> getFunctionListByIn(String idsSql) throws Exception {
		return functionMapper.getFunctionListByIn(idsSql);
	}

	@Override
	public List<Function> getSubFuncList(Function function) throws Exception {
		return functionMapper.getSubFuncList(function);
	}

	@Override
	public List<Function> getFunctionsByRoleId(Authority authority)
			throws Exception {
		return functionMapper.getFunctionsByRoleId(authority);
	}

	@Override
	public Function getFunctionById(Function function) throws Exception {
		return functionMapper.getFunctionById(function);
	}

}

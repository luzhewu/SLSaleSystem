package org.slsale.dao.function;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;

public interface FunctionMapper {
	/**
	 * getMainFunctionList
	 * @param authority
	 * @return
	 * @throws Exception
	 */
	List<Function> getMainFunctionList(Authority authority) throws Exception;

	/**
	 * getMainFunctionList
	 * @param function
	 * @return
	 * @throws Exception
	 */
	List<Function> getSubFunctionList(Function function) throws Exception;

	/**
	 * getFunctionListByIn
	 * @param idsSql
	 * @return
	 * @throws Exception
	 */
	List<Function> getFunctionListByIn(@Param(value = "idsSql") String idsSql)
			throws Exception;

	/**
	 * getSubFuncList
	 * @param function
	 * @return
	 * @throws Exception
	 */
	List<Function> getSubFuncList(Function function) throws Exception;

	/**
	 * getFunctionsByRoleId
	 * @param authority
	 * @return
	 * @throws Exception
	 */
	List<Function> getFunctionsByRoleId(Authority authority) throws Exception;

	/**
	 * getFunctionById
	 * @param function
	 * @return
	 * @throws Exception
	 */
	Function getFunctionById(Function function) throws Exception;
}

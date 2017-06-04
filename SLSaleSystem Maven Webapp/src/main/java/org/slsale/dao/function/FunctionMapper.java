package org.slsale.dao.function;

import java.util.List;

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
}

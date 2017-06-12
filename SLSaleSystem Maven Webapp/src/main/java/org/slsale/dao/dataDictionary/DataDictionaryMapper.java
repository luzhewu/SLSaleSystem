package org.slsale.dao.dataDictionary;

import java.util.List;

import org.slsale.pojo.DataDictionary;

public interface DataDictionaryMapper {
	/**
	 * getDataDictionaries
	 * @param dataDictionary
	 * @return
	 * @throws Exception
	 */
	List<DataDictionary> getDataDictionaries(DataDictionary dataDictionary)
			throws Exception;
}

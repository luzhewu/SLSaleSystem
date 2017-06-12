package org.slsale.service.user.datadictionary;

import java.util.List;

import org.slsale.pojo.DataDictionary;

public interface DataDictionaryService {
	/**
	 * getDataDictionaries
	 * @param dataDictionary
	 * @return
	 * @throws Exception
	 */
	List<DataDictionary> getDataDictionaries(DataDictionary dataDictionary)
			throws Exception;
}

package org.slsale.service.user.datadictionary;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.dataDictionary.DataDictionaryMapper;
import org.slsale.pojo.DataDictionary;
import org.springframework.stereotype.Service;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {
	@Resource
	private DataDictionaryMapper dataDictionaryMapper;

	@Override
	public List<DataDictionary> getDataDictionaries(
			DataDictionary dataDictionary) throws Exception {
		return dataDictionaryMapper.getDataDictionaries(dataDictionary);
	}

}

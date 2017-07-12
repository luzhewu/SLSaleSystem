package org.slsale.dao.authority;

import java.util.List;

import org.slsale.pojo.Authority;

/**
 * 权限表dao层
 * @author lzw
 * 2017-7-11
 * 每个人都有自己的梦想。努力拼搏吧!不要让自己后悔!
 */
public interface AuthorityMapper {
	/**
	 * addAuthority
	 * @param authority
	 * @return
	 */
	int addAuthority(Authority authority) throws Exception;

	/**
	 * modifyAuthority
	 * @param authority
	 * @return
	 */
	int modifyAuthority(Authority authority) throws Exception;

	/**
	 * deleteAuthority
	 * @param authority
	 * @return
	 */
	int deleteAuthority(Authority authority) throws Exception;

	/**
	 * getAuthorityList
	 * @return
	 */
	List<Authority> getAuthorityList(Authority authority) throws Exception;

	/**
	 * getAuthority
	 * @param authority
	 * @return
	 */
	Authority getAuthority(Authority authority) throws Exception;
}

/**
 * @ Dattatray Bodhale
 * Jan 27, 2020
 */
package com.ZioSet.Service;
import java.util.List;
import java.util.Optional;

import com.ZioSet.dto.UserDto;
import com.ZioSet.model.Role;
import com.ZioSet.model.UserInfo;


public interface UserServices {

	UserDto loginUser(UserInfo user);
	UserInfo getUserById(int userId);
	void saveUser(UserInfo userInfo);
	List<UserInfo> getAllUsers();
	List<Role> getAllRoles();
	void deleteUser(UserInfo userInfo);
	List<UserInfo> getUserByLimit(int page_no, int item_per_page);
	List<UserInfo> getUserByLimitAndSearch(String searchText, int pageNo, int perPage);
	int getUserCountAndSearch(String searchText);
	int getUserCount();
	Optional<UserInfo> getUserByUserName(String userName);
	String generateRandomCode();
	

}

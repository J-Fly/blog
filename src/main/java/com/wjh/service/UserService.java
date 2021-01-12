package com.wjh.service;

import com.wjh.po.User;

public interface UserService {
	User checkUser(String username,String password);
}

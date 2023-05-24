package com.anime.service;

import com.anime.entity.Role;
import com.anime.service.base.BaseService;

public interface RoleService extends BaseService<Role> {
	Role getByName(String name);
}

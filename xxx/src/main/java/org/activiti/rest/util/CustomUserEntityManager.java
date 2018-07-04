package org.activiti.rest.util;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.cms_cloudy.user.dao.UserDao;
import com.cms_cloudy.user.dao.UserGroupDao;
import com.cms_cloudy.user.pojo.HrGroup;
import com.cms_cloudy.user.pojo.HrUser;
@Component
public class CustomUserEntityManager extends UserEntityManager{

        @Resource(name="UserDao")
	    private UserDao userDao;//自己实现的获取用户数据的Service
        @Resource(name="UserGroupDao")
	    private UserGroupDao userGroupDao;//自己实现的获取用户数据的Service
	    @Override
	    public UserEntity findUserById(final String userCode) {
	        if (userCode == null)
	            return null;

	        try {
	            UserEntity userEntity = null;
	            List<HrUser> bUserList = userDao.selectUser((Integer.valueOf(userCode)));
	            HrUser bUser = bUserList.get(0);
	            userEntity = ActivitiUtils.toActivitiUser(bUser);
	            return userEntity;
	        } catch (EmptyResultDataAccessException e) {
	            return null;
	        }
	    }

	    @Override
	    public List<Group> findGroupsByUser(final String userCode) {
	        if (userCode == null)
	            return null;

	        List<HrGroup> bGroups = userGroupDao.selectGroupByUserId(Integer.valueOf(userCode));
	        List<Group> gs = null;
	        gs = ActivitiUtils.toActivitiGroups(bGroups);
	        return gs;

	    }

	    @Override
	    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
	        throw new RuntimeException("not implement method.");
	    }

	    @Override
	    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId,
	                                                         String key) {
	        throw new RuntimeException("not implement method.");
	    }

	    @Override
	    public List<String> findUserInfoKeysByUserIdAndType(String userId,
	                                                        String type) {
	        throw new RuntimeException("not implement method.");
	    }

	    @Override
	    public long findUserCountByQueryCriteria(UserQueryImpl query) {
	        throw new RuntimeException("not implement method.");
	    }

}

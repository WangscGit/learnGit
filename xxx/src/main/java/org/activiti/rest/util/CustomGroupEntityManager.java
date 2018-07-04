package org.activiti.rest.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import com.cms_cloudy.user.dao.IHrGroupDao;
import com.cms_cloudy.user.dao.UserGroupDao;
import com.cms_cloudy.user.pojo.HrGroup;

@Component
public class CustomGroupEntityManager extends GroupEntityManager {

    @Autowired
    private IHrGroupDao groupDao;
    @Autowired
    private UserGroupDao userGroupDao;
    public GroupEntity findGroupById(final String groupCode) {
        if (groupCode == null)
            return null;

        try {
        	Map<String, Object> map= new HashMap<String,Object>();
        	map.put("groupId",Integer.valueOf(groupCode));
        	HrGroup bGroup = groupDao.selectOneGroup(map);
            GroupEntity e = new GroupEntity();
            e.setRevision(1);

            // activiti��3��Ԥ����������ͣ�security-role��assignment��user  
            // ���ʹ��Activiti  
            // Explorer����Ҫsecurity-role���ܿ���manageҳǩ����Ҫassignment����claim����  
            e.setType("assignment");

            e.setId(bGroup.getGroupId()+"");
            e.setName(bGroup.getGroupName());
            return e;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public List<Group> findGroupsByUser(final String userCode) {
        if (userCode == null)
            return null;

        List<HrGroup> bGroupList = userGroupDao.selectGroupByUserId(Integer.valueOf(userCode));

        List<Group> gs = new ArrayList<Group>();
        GroupEntity g;
        for (HrGroup bGroup : bGroupList) {
            g = new GroupEntity();
            g.setRevision(1);
            g.setType("assignment");

            g.setId(bGroup.getGroupId()+"");
            g.setName(bGroup.getGroupName());
            gs.add(g);
        }
        return gs;
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        throw new RuntimeException("not implement method.");
    }
}

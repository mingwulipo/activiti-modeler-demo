package com.test.activiti.service;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lipo
 * @date 2020/7/28
 */
@Service
public class MyIdentityService {
    @Autowired
    private IdentityService identityService;

    public void createUserRole() {
        syncRole("yuangong", "员工");
        syncUser("1", "张三", "yuangong");

        syncRole("jingli", "经理");
        syncUser("2", "李四经理", "jingli");

        syncRole("zongjian", "总监");
        syncUser("3", "王吴总监", "zongjian");

        syncRole("hr", "人力");
        syncUser("4", "小丽人力", "hr");


    }

    public void syncRole(String roleCode, String roleName) {
        Group group = identityService.newGroup(roleCode);
        group.setName(roleName);
        identityService.deleteGroup(roleCode);
        identityService.saveGroup(group);
    }

    public void syncUser(String userId, String userName, String roleCode) {
        User user = identityService.newUser(userId);
        user.setFirstName(userName);
        user.setLastName("");
        identityService.deleteUser(userId);
        identityService.deleteMembership(userId, roleCode);
        identityService.saveUser(user);
        identityService.createMembership(userId, roleCode);
    }

}

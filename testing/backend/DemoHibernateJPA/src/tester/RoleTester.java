/*
 * Copyright 2017 Le Cao Nguyen
 */
package tester;

import dao.RoleManager;
import java.util.ArrayList;
import java.util.List;
import model.Role;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author Le Cao Nguyen
 */
public class RoleTester extends TransactionPerformer implements Runnable {
    private final RoleManager roleManager = new RoleManager();
    
    @Override
    public void run() {
        System.out.println("Creating roles... " + (createRoles() ? "Success!" : "Failed!"));
        System.out.println("Updating roles... " + (updateRoles() ? "Success!" : "Failed!"));
        System.out.println("Updating roles... " + (deleteRoles() ? "Success!" : "Failed!"));
    }

    public boolean createRoles() {
        boolean success = true;
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("admin", "Administrator"));
        roles.add(new Role("testmaster", "Test Master"));
        roles.add(new Role("student", "Student"));
        for (Role role : roles) {
            success &= roleManager.saveRole(role);
        }
        return success;
    }

    public boolean updateRoles() {
        boolean success = true;
        Role adminRole = roleManager.getRole("admin");
        adminRole.setDescription("Ba chu thien ha");
        success &= roleManager.updateRole(adminRole);
        return success;
    }

    public boolean deleteRoles() {
        boolean success = true;
        Role testmasterRole = roleManager.getRole("testmaster");
        success &= roleManager.deleteRole(testmasterRole);
        List<Role> roles = roleManager.getAllRoles();
        for (Role role : roles) {
            success &= roleManager.deleteRole(role);
        }
        return success;
    }

}

package com.MyMiniProject.MiniProject.utilities;

import com.MyMiniProject.MiniProject.Entities.Permission;
import com.MyMiniProject.MiniProject.Entities.Role;
import com.MyMiniProject.MiniProject.Entities.User;
import com.MyMiniProject.MiniProject.Repositories.PermissionRepository;
import com.MyMiniProject.MiniProject.Repositories.RoleRepository;
import com.MyMiniProject.MiniProject.Repositories.UserRepository;
import com.MyMiniProject.MiniProject.Services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class DatabaseUtility {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public void initDatabase() {
        Logger.getLogger("Database utility").info("Seeding database ...");
        initUsers();
        //initRoles();
        Logger.getLogger("Database utility").info("Database seeding complete");
    }

    public void initUsers() {
        //        Check table is empty
        if (userRepository.count() > 0) return;

        User user1 = User.builder()
                .username("amine")
                .password("1212")
                .build();
        User user2 = User.builder()
                .username("ahmad")
                .password("1212")
                .build();

        userRepository.saveAll(Arrays.asList(
                user1,
                user2
        ));
    }

    public void initRoles() {
        // Check if roles already exist. If yes, no need to add them again.
        if (roleRepository.count() > 0)
            return;

        // Create individual permission objects for different actions.
        Permission readPermissionAdmin = Permission.builder().namePermission("ADMIN_READ").build();
        Permission updatePermissionAdmin = Permission.builder().namePermission("ADMIN_UPDATE").build();
        Permission createPermissionAdmin = Permission.builder().namePermission("ADMIN_CREATE").build();
        Permission deletePermissionAdmin = Permission.builder().namePermission("ADMIN_DELETE").build();

        Permission readPermissionModerator = Permission.builder().namePermission("MOD_READ").build();
        Permission updatePermissionModerator = Permission.builder().namePermission("MOD_UPDATE").build();
        Permission createPermissionModerator = Permission.builder().namePermission("MOD_CREATE").build();
        Permission deletePermissionModerator = Permission.builder().namePermission("MOD_DELETE").build();

        Permission readPermissionUser = Permission.builder().namePermission("USER_READ").build();
        Permission updatePermissionUser = Permission.builder().namePermission("USER_UPDATE").build();
        Permission createPermissionUser = Permission.builder().namePermission("USER_CREATE").build();
        Permission deletePermissionUser = Permission.builder().namePermission("USER_DELETE").build();

        // Save all the permissions to the database and retrieve their IDs.
        List<Permission> savedPermissions = permissionRepository.saveAll(Arrays.asList(
                readPermissionAdmin,
                updatePermissionAdmin,
                createPermissionAdmin,
                deletePermissionAdmin,
                readPermissionModerator,
                updatePermissionModerator,
                createPermissionModerator,
                deletePermissionModerator,
                readPermissionUser,
                createPermissionUser,
                updatePermissionUser,
                deletePermissionUser
        ));

        // Create individual role objects and assign them role names.

        Role admin = Role.builder().roleName("ADMIN").build();
        Role moderator = Role.builder().roleName("MOD").build();
        Role user = Role.builder().roleName("USER").build();
        roleRepository.saveAll(Arrays.asList(
                admin,
                moderator,
                user
        ));

        // Assign permissions to roles using retrieved permission IDs.

        // Assign permissions to the "ADMIN" role.
        roleService.addPermissionToRole(admin.getId(), savedPermissions.get(0).getId());
        roleService.addPermissionToRole(admin.getId(), savedPermissions.get(1).getId());
        roleService.addPermissionToRole(admin.getId(), savedPermissions.get(2).getId());
        roleService.addPermissionToRole(admin.getId(), savedPermissions.get(3).getId());

        // Assign permissions to the "MANAGER" role.
        roleService.addPermissionToRole(moderator.getId(), savedPermissions.get(4).getId());
        roleService.addPermissionToRole(moderator.getId(), savedPermissions.get(5).getId());
        roleService.addPermissionToRole(moderator.getId(), savedPermissions.get(6).getId());
        roleService.addPermissionToRole(moderator.getId(), savedPermissions.get(7).getId());

        // Assign permissions to the "USER" role.
        roleService.addPermissionToRole(user.getId(), savedPermissions.get(8).getId());
        roleService.addPermissionToRole(user.getId(), savedPermissions.get(9).getId());
        roleService.addPermissionToRole(user.getId(), savedPermissions.get(10).getId());
        roleService.addPermissionToRole(user.getId(), savedPermissions.get(11).getId());

        List<Role> roles = new ArrayList<>();
        roles.add(admin);

        // Create and save the admin user.
        User adminUser = User.builder()
                .username("wiame") // Set the username here
                .build();
        userRepository.save(adminUser);
        User u = User.builder().roles(roles).password(passwordEncoder.encode("1212")).username(adminUser.getUsername()).build();
        userRepository.save(u);
    }
}

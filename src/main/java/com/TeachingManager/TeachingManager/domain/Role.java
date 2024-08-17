package com.TeachingManager.TeachingManager.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
        PRESIDENT("ROLE_PRESIDENT"),
        TEACHER("ROLE_TEACHER"),
        ADMIN ("ROLE_ADMIN,ROLE_TEACHER,ROLE_PRESIDENT");

        private final String roles;
        public static String getIncludingRoles(String role){
            return Role.valueOf(role).getRoles();
        }
        public static String addRole(Role role, String addRole){
            String priorRoles = role.getRoles();
            priorRoles += ","+addRole;
            return priorRoles;
        }

        public static String addRole(String roles, Role role){
            return roles + "," + role.getRoles();
        }
}


package com.ra34.projecte2.DTO;

public class UserRolesDTO {
    private Long userId;
    private java.util.List<Long> roleIds;

    public UserRolesDTO() {
    }

    public UserRolesDTO(Long userId, java.util.List<Long> roleIds) {
        this.userId = userId;
        this.roleIds = roleIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public java.util.List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(java.util.List<Long> roleIds) {
        this.roleIds = roleIds;
    }


}

package me.weyye.hipermission;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class PermissonItem implements Serializable {
    public String PermissionName;
    public String Permission;
    public int PermissionIconRes;

    public PermissonItem(String permission, String permissionName, int permissionIconRes) {
        Permission = permission;
        PermissionName = permissionName;
        PermissionIconRes = permissionIconRes;
    }

    public PermissonItem(String permission) {
        Permission = permission;
    }
}

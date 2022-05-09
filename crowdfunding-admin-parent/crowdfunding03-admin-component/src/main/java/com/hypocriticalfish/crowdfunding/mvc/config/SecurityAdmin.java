package com.hypocriticalfish.crowdfunding.mvc.config;

import com.hypocriticalfish.crowdfunding.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/4 22:34
 * @Description
 */
// config包下
public class SecurityAdmin extends User {

    private static final long serialVersionUID = -8467066231074784765L;
    // 原始的 Admin 对象， 包含 Admin 对象的全部属性
    private Admin originalAdmin;

    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities) {
        super(originalAdmin.getLogin(), originalAdmin.getUserPswd(), authorities);
        this.originalAdmin = originalAdmin;

        // 将原始 Admin 对象中的密码擦除，密码已经传给父类User构造器，因此这里擦除不影响
        this.originalAdmin.setUserPswd(null);
    }

    // 对外提供的获取原始 Admin 对象的 getXxx()方法
    public Admin getOriginalAdmin() {
        return originalAdmin;
    }

}

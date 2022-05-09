package com.hypocriticalfish.crowdfunding;

import com.hypocriticalfish.crowdfunding.constant.CrowdConstant;
import com.hypocriticalfish.crowdfunding.entity.Admin;
import com.hypocriticalfish.crowdfunding.entity.Role;
import com.hypocriticalfish.crowdfunding.exception.LoginAcctAlreadyInUseException;
import com.hypocriticalfish.crowdfunding.mapper.AdminMapper;
import com.hypocriticalfish.crowdfunding.mapper.RoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/3 17:50
 * @Description
 */
@SpringJUnitConfig(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void testRoleSave() {
        for (int i = 0; i < 235; i++) {
            roleMapper.insertSelective(new Role(null, "role" + i));
        }
    }


}

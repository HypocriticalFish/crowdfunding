package com.hypocriticalfish.crowdfunding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hypocriticalfish.crowdfunding.constant.CrowdConstant;
import com.hypocriticalfish.crowdfunding.entity.Admin;
import com.hypocriticalfish.crowdfunding.entity.AdminExample;
import com.hypocriticalfish.crowdfunding.entity.Role;
import com.hypocriticalfish.crowdfunding.exception.LoginAcctAlreadyInUseException;
import com.hypocriticalfish.crowdfunding.exception.LoginFailedException;
import com.hypocriticalfish.crowdfunding.mapper.AdminMapper;
import com.hypocriticalfish.crowdfunding.service.AdminService;
import com.hypocriticalfish.crowdfunding.util.CrowdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author Hypocritical Fish
 * @Create 2022/4/26 17:46
 * @Description
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void update(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public void saveAdminRoleRelationship(String adminId, List<Integer> roleIdList) {
        // 删除旧数据
        adminMapper.deleteOldRelationship(adminId);

        // 加入新数据
        if (roleIdList != null && roleIdList.size() > 0) {
            adminMapper.insertNewRelationship(adminId, roleIdList);
        }
    }

    @Override
    public Admin getAdminByLoginAcct(String username) {
        AdminExample example = new AdminExample();

        AdminExample.Criteria criteria = example.createCriteria();

        criteria.andLoginEqualTo(username);

        return adminMapper.selectByExample(example).get(0);
    }

    @Override
    public void saveAdmin(Admin admin) {
        // 1.密码加密
        /*String userPswd = admin.getUserPswd();
        userPswd = CrowdUtil.md5(userPswd);
        admin.setUserPswd(userPswd);*/
        String userPswd = admin.getUserPswd();
        admin.setUserPswd(passwordEncoder.encode(userPswd));

        // 2.生成创建时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        admin.setCreateTime(createTime);

        // 3.执行保存
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            logger.info("异常全类名=" + e.getClass().getName());
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
        //throw new RuntimeException();
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        // 1.根据登录账号查询Admin对象
        // 1.1创建AdminExample对象
        AdminExample adminExample = new AdminExample();

        // 1.2创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();

        // 1.3在criteria对象中封装插叙条件
        criteria.andLoginEqualTo(loginAcct);

        // 1.4调用AdminMapper的方法执行查询
        List<Admin> list = adminMapper.selectByExample(adminExample);

        // 2.判断admin对象是否为空
        if (list == null || list.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        if (list.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        Admin admin = list.get(0);

        // 3.如果Admin对象为空则抛出异常
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }


        // 4.如果Admin对象不为空则将数据库密码从Admin对象中取出

        String userPswdDB = admin.getUserPswd();

        // 5.将表单提交的明文密码进行加密
        String userPswdForm = CrowdUtil.md5(userPswd);

        // 6.对密码进行比较
        if (!Objects.equals(userPswdDB, userPswdForm)) {
            // 7.如果比较结果不一致则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 8.如果一致则返回admin对象
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 1.调用PageHelper的静态方法开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        // 2.执行查询
        List<Admin> list = adminMapper.selectAdminByKeyWord(keyword);

        // 3.封装到PageInfo对象中
        return new PageInfo<>(list);
    }
}

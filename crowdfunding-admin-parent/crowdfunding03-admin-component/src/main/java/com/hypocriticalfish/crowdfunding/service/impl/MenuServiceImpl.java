package com.hypocriticalfish.crowdfunding.service.impl;

import com.hypocriticalfish.crowdfunding.entity.Menu;
import com.hypocriticalfish.crowdfunding.entity.MenuExample;
import com.hypocriticalfish.crowdfunding.mapper.MenuMapper;
import com.hypocriticalfish.crowdfunding.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/3 19:53
 * @Description
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        List<Menu> menuList = menuMapper.selectByExample(new MenuExample());
        return menuList;
    }

    @Override
    public void removeMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }
}

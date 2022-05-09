package com.hypocriticalfish.crowdfunding.service;

import com.hypocriticalfish.crowdfunding.entity.Menu;

import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/3 19:53
 * @Description
 */
public interface MenuService {
    List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenu(Integer id);
}

package com.hypocriticalfish.crowdfunding.mvc.handler;

import com.hypocriticalfish.crowdfunding.entity.Menu;
import com.hypocriticalfish.crowdfunding.service.MenuService;
import com.hypocriticalfish.crowdfunding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/3 19:54
 * @Description
 */
@RestController
public class MenuHandler {
    @Autowired
    private MenuService menuService;


    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTreeNew() {

        // 1.查询全部的Menu对象
        List<Menu> menuList = menuService.getAll();

        // 2.声明一个变量来存储找到的根节点
        Menu root = null;

        // 3.创建 Map 对象用来存储 id 和 Menu 对象的对应关系便于查找父节点
        Map<Integer, Menu> menuMap = new HashMap<>();

        // 4.遍历 menuList 填充 menuMap
        for (Menu menu : menuList) {

            Integer id = menu.getId();

            menuMap.put(id, menu);
        }

        // 5.再次遍历 menuList 查找根节点、组装父子节点
        for (Menu menu : menuList) {

            // 5.1 获取当前对象pid
            Integer pid = menu.getPid();

            // 5.2 检查pid是否为null
            if (pid == null) {
                // 将当前对象赋给root
                root = menu;
                continue;
            }
            // 5.3 如果pid不为null，说明当前节点有父节点，找到父节点就可以进行组装
            menuMap.get(pid).getChildren().add(menu);
        }
        return ResultEntity.successWithData(root);
    }

    @RequestMapping("/menu/save.json")
    public ResultEntity<String> saveMenu(Menu menu) {

        menuService.saveMenu(menu);

        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/menu/update.json")
    public ResultEntity<String> updateMenu(Menu menu) {

        menuService.updateMenu(menu);

        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/menu/remove.json")
    public ResultEntity<String> removeMenu(@RequestParam("id") Integer id) {

        menuService.removeMenu(id);
        return ResultEntity.successWithoutData();
    }
}

package com.hypocriticalfish.crowdfunding.test;

import com.hypocriticalfish.crowdfunding.entity.po.MemberPO;
import com.hypocriticalfish.crowdfunding.entity.vo.DetailProjectVO;
import com.hypocriticalfish.crowdfunding.entity.vo.PortalTypeVO;
import com.hypocriticalfish.crowdfunding.mapper.MemberPOMapper;
import com.hypocriticalfish.crowdfunding.mapper.ProjectPOMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/7 20:31
 * @Description
 */
@SpringBootTest
public class MybatisTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Autowired
    private ProjectPOMapper projectPOMapper;

    final private Logger logger = LoggerFactory.getLogger(MybatisTest.class);

    @Test
    public void testLoadTypeData(){
        List<PortalTypeVO> portalTypeVOS = projectPOMapper.selectProtalTypeVOList();
        portalTypeVOS.forEach(System.out::println);
    }

    @Test
    public void testLoadDetailProjectVO() {

        Integer projectId = 1;
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(projectId);
        if (detailProjectVO == null)
            logger.warn("项目不存在，项目编号："+ projectId);
        else
            logger.info(detailProjectVO.getProjectName());
    }

    @Test
    public void testMapper() {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String source = "123123";
        String encode = passwordEncoder.encode(source);

        MemberPO memberPO = new MemberPO(null, "jack", encode, "杰克", "jack@qq.com", 1, 1, "杰克", "123123", 2);

        memberPOMapper.insert(memberPO);
    }

    @Test
    public void testConnection() throws SQLException {

        Connection connection = dataSource.getConnection();

        logger.debug(connection.toString());
    }
}

package com.baomidou.mybatisplus.test.h2;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.test.h2.config.H2Db;
import com.baomidou.mybatisplus.test.h2.entity.enums.AgeEnum;
import com.baomidou.mybatisplus.test.h2.entity.persistent.H2User;
import com.baomidou.mybatisplus.test.h2.service.IH2UserService;

/**
 * <p>
 * Mybatis Plus H2 Junit Test
 * </p>
 *
 * @author Caratacus
 * @since 2017/4/1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:h2/spring-test-h2.xml"})
public class H2UserTest extends BaseTest {

    @Autowired
    protected IH2UserService userService;

    @BeforeClass
    public static void initDB() throws SQLException, IOException {
        H2Db.initH2User();
    }

    @Test
    public void testInsertMy() {
        String name = "自定义insert";
        int version = 1;
        int row = userService.myInsert(name, version);
        Assert.assertEquals(1, row);
    }

    @Test
    public void testInsertObjectWithParam() {
        String name = "自定义insert带Param注解";
        int version = 1;
        int row = userService.myInsertWithParam(name, version);
        Assert.assertEquals(1, row);
    }

    @Test
    public void testInsertObjectWithoutParam() {
        String name = "自定义insert带Param注解";
        int version = 1;
        int row = userService.myInsertWithoutParam(name, version);
        Assert.assertEquals(1, row);
    }

    @Test
    public void testEntityWrapperSelectSql() {
        QueryWrapper<H2User> ew = new QueryWrapper<>();
        ew.select("test_id as testId, name, age");
        List<H2User> list = userService.list(ew);
        for (H2User u : list) {
            Assert.assertNotNull(u.getTestId());
            Assert.assertNotNull(u.getName());
            Assert.assertNull(u.getPrice());
        }
    }

    @Test
    public void testQueryWithParamInSelectStatement() {
        Map<String, Object> param = new HashMap<>();
        String nameParam = "selectStmtParam";
        param.put("nameParam", nameParam);
        param.put("ageFrom", 1);
        param.put("ageTo", 100);
        List<H2User> list = userService.queryWithParamInSelectStatememt(param);
        Assert.assertNotNull(list);
        for (H2User u : list) {
            Assert.assertEquals(nameParam, u.getName());
            Assert.assertNotNull(u.getTestId());
        }
    }

    @Test
    public void testQueryWithParamInSelectStatement4Page() {
//        Map<String, Object> param = new HashMap<>();
//        String nameParam = "selectStmtParam";
//        param.put("nameParam", nameParam);
//        param.put("ageFrom", 1);
//        param.put("ageTo", 100);
//        Page<H2User> page = userService.queryWithParamInSelectStatememt4Page(param, new Page<H2User>(0, 10));
//        Assert.assertNotNull(page.getRecords());
//        for (H2User u : page.getRecords()) {
//            Assert.assertEquals(nameParam, u.getName());
//            Assert.assertNotNull(u.getId());
//        }
//        Assert.assertNotEquals(0, page.getTotal());
    }

    @Test
    public void testSelectCountWithParamInSelectItems() {
        Map<String, Object> param = new HashMap<>();
        String nameParam = "selectStmtParam";
        param.put("nameParam", nameParam);
        param.put("ageFrom", 1);
        param.put("ageTo", 100);
        int count = userService.selectCountWithParamInSelectItems(param);
        Assert.assertNotEquals(0, count);
    }

    @Test
    public void testUpdateByIdWithOptLock() {
        Long id = 991L;
        H2User user = new H2User();
        user.setTestId(id);
        user.setName("991");
        user.setAge(AgeEnum.ONE);
        user.setPrice(BigDecimal.TEN);
        user.setDesc("asdf");
        user.setTestType(1);
        user.setVersion(1);
        userService.save(user);

        H2User userDB = userService.getById(id);
        Assert.assertEquals(1, userDB.getVersion().intValue());

        userDB.setName("992");
        userService.updateById(userDB);
        Assert.assertEquals("updated version value should be updated to entity", 2, userDB.getVersion().intValue());

        userDB = userService.getById(id);
        Assert.assertEquals(2, userDB.getVersion().intValue());
        Assert.assertEquals("992", userDB.getName());
    }

    @Test
    public void testUpdateByEwWithOptLock() {
        H2User userInsert = new H2User();
        userInsert.setName("optLockerTest");
        userInsert.setAge(AgeEnum.THREE);
        userInsert.setPrice(BigDecimal.TEN);
        userInsert.setDesc("asdf");
        userInsert.setTestType(1);
        userInsert.setVersion(99);
        userService.save(userInsert);

        QueryWrapper<H2User> ew = new QueryWrapper<>();
        ew.ge("age", AgeEnum.TWO.getValue());
        Long id99 = null;
        for (H2User u : userService.list(ew)) {
            System.out.println(u.getName() + "," + u.getAge() + "," + u.getVersion());
            if (u.getVersion() != null && u.getVersion() == 99) {
                id99 = u.getTestId();
            }
        }
        userService.update(new H2User().setPrice(BigDecimal.TEN).setVersion(99), ew);
        System.out.println("============after update");
        ew = new QueryWrapper<>();
        ew.ge("age", AgeEnum.TWO.getValue());
        for (H2User u : userService.list(ew)) {
            System.out.println(u.getName() + "," + u.getAge() + "," + u.getVersion());
            if (id99 != null && u.getTestId().equals(id99)) {
                Assert.assertEquals("optLocker should update version+=1", 100, u.getVersion().intValue());
            }
        }
    }

    @Test
    public void testOptLocker4WrapperIsNull() {
        H2User userInsert = new H2User();
        userInsert.setName("optLockerTest");
        userInsert.setAge(AgeEnum.THREE);
        userInsert.setPrice(BigDecimal.TEN);
        userInsert.setDesc("asdf");
        userInsert.setTestType(1);
        userInsert.setVersion(99);
        userService.save(userInsert);

        QueryWrapper<H2User> ew = new QueryWrapper<>();
        ew.ge("age", AgeEnum.TWO.getValue());
        Long id99 = null;
        Map<Long, BigDecimal> idPriceMap = new HashMap<>();
        for (H2User u : userService.list(ew)) {
            System.out.println(u.getName() + "," + u.getAge() + "," + u.getVersion());
            idPriceMap.put(u.getTestId(), u.getPrice());
            if (u.getVersion() != null && u.getVersion() == 99) {
                id99 = u.getTestId();
            }
        }
        userService.update(new H2User().setPrice(BigDecimal.TEN).setVersion(99), null);
        System.out.println("============after update");
        ew = new QueryWrapper<>();
        ew.ge("age", AgeEnum.TWO.getValue());
        for (H2User u : userService.list(ew)) {
            System.out.println(u.getName() + "," + u.getAge() + "," + u.getVersion());
            if (id99 != null && u.getTestId().equals(id99)) {
                Assert.assertEquals("optLocker should update version+=1", 100, u.getVersion().intValue());
            } else {
                Assert.assertEquals("other records should not be updated", idPriceMap.get(u.getTestId()), u.getPrice());
            }
        }
        userService.update(new H2User().setPrice(BigDecimal.ZERO), null);
        for (H2User u : userService.list(new QueryWrapper<>())) {
            System.out.println(u.getName() + "," + u.getAge() + "," + u.getVersion());
            Assert.assertEquals("all records should be updated", u.getPrice().setScale(2, RoundingMode.HALF_UP).intValue(), BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP).intValue());
        }

    }

    @Test
    public void testEntityWrapperSelectSqlExcludeColumn() {
        QueryWrapper<H2User> ew = new QueryWrapper<>();
        ew.excludeColumns(H2User.class, "age", "price", null);
        List<H2User> list = userService.list(ew);
        for (H2User u : list) {
            Assert.assertNotNull(u.getTestId());
            Assert.assertNotNull(u.getName());
            Assert.assertNull(u.getPrice());
        }
        ew = new QueryWrapper<>(null, "test_id", "name", "age", "price");
        ew.excludeColumns(H2User.class, "age", "price", null);
        list = userService.list(ew);
        for (H2User u : list) {
            Assert.assertNotNull(u.getTestId());
            Assert.assertNotNull(u.getName());
            Assert.assertNull(u.getPrice());
        }
        ew = new QueryWrapper<>(new H2User());
        ew.excludeColumns("age", "price", null);
        for (H2User u : userService.list(ew)) {
            Assert.assertNotNull(u.getTestId());
            Assert.assertNotNull(u.getName());
            Assert.assertNull(u.getPrice());
        }
        Wrapper<H2User> wrapper = new QueryWrapper<H2User>().lambda().select().excludeColumns(H2User.class, H2User::getAge, H2User::getPrice);
        List<H2User> list2 = userService.list(wrapper);
        for (H2User u : list2) {
            Assert.assertNotNull(u.getTestId());
            Assert.assertNotNull(u.getName());
            Assert.assertNull(u.getPrice());
        }
        wrapper = new QueryWrapper<>(new H2User()).lambda().select().excludeColumns(H2User::getAge, H2User::getPrice);
        list2 = userService.list(wrapper);
        for (H2User u : list2) {
            Assert.assertNotNull(u.getTestId());
            Assert.assertNotNull(u.getName());
            Assert.assertNull(u.getPrice());
        }
        wrapper = new QueryWrapper<H2User>().lambda().select(H2User::getTestId, H2User::getName, H2User::getTestDate, H2User::getPrice, H2User::getAge).excludeColumns(H2User.class, H2User::getAge, H2User::getTestDate, H2User::getVersion, H2User::getPrice);
        list2 = userService.list(wrapper);
        for (H2User u : list2) {
            Assert.assertNotNull(u.getName());
            Assert.assertNull(u.getPrice());
        }
    }

    @Test
    public void testBatchTransactional(){
        try {
            userService.testBatchTransactional();
        }catch (Exception e){
            List<H2User> list = userService.list(new QueryWrapper<H2User>().like("name", "batch"));
            Assert.assertTrue(CollectionUtils.isEmpty(list));
        }
    }

    @Test
    public void testSimpleTransactional(){
        try {
            userService.testSimpleTransactional();
        }catch (Exception e){
            List<H2User> list = userService.list(new QueryWrapper<H2User>().like("name", "simple"));
            Assert.assertTrue(CollectionUtils.isEmpty(list));
        }
    }
}

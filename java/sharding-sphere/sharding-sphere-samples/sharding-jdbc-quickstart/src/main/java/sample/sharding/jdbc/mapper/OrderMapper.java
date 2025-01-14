package sample.sharding.jdbc.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderMapper {

    /**
     * 新增订单
     * @param price  订单价格
     * @param userId 用户id
     * @param status 订单状态
     * @return
     */
    @Insert("insert into t_order(price,user_id,status) value(#{price},#{userId},#{status})")
    int insertOrder(@Param("price") BigDecimal price, @Param("userId") Long userId, @Param("status") String status);

    /**
     * 根据id列表查询多个订单
     * @param orderIds 订单id列表
     * @return
     */
    @Select({"<script>" +
            "select " +
            " * " +
            " from t_order t" +
            " where t.order_id in " +
            "<foreach collection='orderIds' item='id' open='(' separator=',' close=')'>" +
            " #{id} " +
            "</foreach>" +
            "</script>"})
    List<Map<String, Object>> selectOrderbyIds(@Param("orderIds") List<Long> orderIds);
}

package mybatis.mapper;

import mybatis.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}

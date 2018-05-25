package com.example.spiderjava.dao;

import com.example.spiderjava.model.Market;
import com.example.spiderjava.model.MarketExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MarketMapper {
    long countByExample(MarketExample example);

    int deleteByExample(MarketExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Market record);

    int insertSelective(Market record);

    List<Market> selectByExampleWithRowbounds(MarketExample example, RowBounds rowBounds);

    List<Market> selectByExample(MarketExample example);

    Market selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Market record, @Param("example") MarketExample example);

    int updateByExample(@Param("record") Market record, @Param("example") MarketExample example);

    int updateByPrimaryKeySelective(Market record);

    int updateByPrimaryKey(Market record);
}
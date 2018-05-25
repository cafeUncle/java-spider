package com.example.spiderjava.dao;

import com.example.spiderjava.model.NoticeDetail;
import com.example.spiderjava.model.NoticeDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface NoticeDetailMapper {
    long countByExample(NoticeDetailExample example);

    int deleteByExample(NoticeDetailExample example);

    int deleteByPrimaryKey(Integer noticeId);

    int insert(NoticeDetail record);

    int insertSelective(NoticeDetail record);

    List<NoticeDetail> selectByExampleWithBLOBsWithRowbounds(NoticeDetailExample example, RowBounds rowBounds);

    List<NoticeDetail> selectByExampleWithBLOBs(NoticeDetailExample example);

    List<NoticeDetail> selectByExampleWithRowbounds(NoticeDetailExample example, RowBounds rowBounds);

    List<NoticeDetail> selectByExample(NoticeDetailExample example);

    NoticeDetail selectByPrimaryKey(Integer noticeId);

    int updateByExampleSelective(@Param("record") NoticeDetail record, @Param("example") NoticeDetailExample example);

    int updateByExampleWithBLOBs(@Param("record") NoticeDetail record, @Param("example") NoticeDetailExample example);

    int updateByExample(@Param("record") NoticeDetail record, @Param("example") NoticeDetailExample example);

    int updateByPrimaryKeySelective(NoticeDetail record);

    int updateByPrimaryKeyWithBLOBs(NoticeDetail record);

    int updateByPrimaryKey(NoticeDetail record);
}
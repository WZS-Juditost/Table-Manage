package com.ck.mybatis.demo.mapper;

import com.ck.mybatis.demo.entity.B_Table;
import java.util.List;
import java.util.Map;


public interface B_TableMapper {

    int insert(B_Table b_table);
    int delete(Long id);
    int update(B_Table b_table);
    List<B_Table> find(String value);
    List<B_Table> getAll();
    int deleteByC(String cValue);
    int findCountByColumnName(Map<String, Object> params);
    List<String> findDistinctB();

}

package com.ck.mybatis.demo.mapper;

import com.ck.mybatis.demo.entity.A_Table;
import java.util.List;
import java.util.Map;


public interface A_TableMapper {

    int insert(A_Table a_table);
    int delete(Long id);
    int update(A_Table a_table);
    List<A_Table> find(String value);
    int findCountByColumnName(Map<String, Object> params);
    List<String> findDistinctB();

}

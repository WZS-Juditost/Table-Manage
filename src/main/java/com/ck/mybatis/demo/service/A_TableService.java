package com.ck.mybatis.demo.service;

import com.ck.mybatis.demo.entity.A_Table;

import java.util.List;
import java.util.Map;

public interface A_TableService {

    public int insert(A_Table a_table);

    public int delete(Long id);

    public int update(A_Table a_table);

    public List<A_Table> find(String value);

    public int findCountByColumnName(String columnName, String value);

    public Map<String, Integer> getCountsForColumns(String value, List<String> columnNames);

    public List<String> findDistinctB();

}

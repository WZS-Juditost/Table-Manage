package com.ck.mybatis.demo.service;

import com.ck.mybatis.demo.entity.B_Table;

import java.util.List;
import java.util.Map;

public interface B_TableService {

    public int insert(B_Table b_table);

    public int delete(Long id);

    public int update(B_Table b_table);

    public List<B_Table> find(String value);

    List<B_Table> getAll();

    int deleteByC(String cValue);

    public int findCountByColumnName(String columnName, String value);

    public Map<String, Integer> getCountsForColumns(String value, List<String> columnNames);

    public List<String> findDistinctB();

}

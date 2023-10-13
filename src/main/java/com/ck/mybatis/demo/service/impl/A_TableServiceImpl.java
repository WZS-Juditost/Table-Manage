package com.ck.mybatis.demo.service.impl;

import com.ck.mybatis.demo.entity.A_Table;
import com.ck.mybatis.demo.mapper.A_TableMapper;
import com.ck.mybatis.demo.service.A_TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class A_TableServiceImpl implements A_TableService {

    @Autowired
    private A_TableMapper a_tableMapper;

    public int insert(A_Table a_table) {
        return a_tableMapper.insert(a_table);
    }

    public int delete(Long id) {
        return a_tableMapper.delete(id);
    }

    public int update(A_Table a_table) {
        return a_tableMapper.update(a_table);
    }

    public List<A_Table> find(String value) {
        return a_tableMapper.find(value);
    }

    public int findCountByColumnName(String columnName, String value) {
        Map<String, Object> params = new HashMap<>();
        params.put("columnName", columnName);
        params.put("value", value);
        return a_tableMapper.findCountByColumnName(params);
    }

    public Map<String, Integer> getCountsForColumns(String value, List<String> columnNames) {
        Map<String, Integer> result = new HashMap<>();
        for (String columnName : columnNames) {
            int count = findCountByColumnName(columnName, value);
            result.put(columnName, count);
        }
        return result;
    }

    public List<String> findDistinctB() {
        return a_tableMapper.findDistinctB();
    }
}

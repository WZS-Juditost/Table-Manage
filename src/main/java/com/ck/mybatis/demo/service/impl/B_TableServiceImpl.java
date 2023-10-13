package com.ck.mybatis.demo.service.impl;

import com.ck.mybatis.demo.entity.B_Table;
import com.ck.mybatis.demo.mapper.B_TableMapper;
import com.ck.mybatis.demo.service.B_TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class B_TableServiceImpl implements B_TableService {

    @Autowired
    private B_TableMapper b_tableMapper;

    private static final Logger logger = LoggerFactory.getLogger(B_TableServiceImpl.class);

    public int insert(B_Table b_table) {
        try {
            return b_tableMapper.insert(b_table);
        } catch (DuplicateKeyException e) {
            logger.error("尝试保存 A = {} 和 C = {} 的重复的 B_Table", b_table.getA(), b_table.getC());
            throw e;
        }
    }

    public int delete(Long id) {
        return b_tableMapper.delete(id);
    }

    public int update(B_Table b_table) {
        try{
            return b_tableMapper.update(b_table);
        } catch (DuplicateKeyException e) {
            logger.error("尝试更新 A = {} 和 C = {} 的重复的 B_Table", b_table.getA(), b_table.getC());
            throw e;
        }
    }

    public List<B_Table> find(String value) {
        return b_tableMapper.find(value);
    }

    @Override
    public List<B_Table> getAll() {
        return b_tableMapper.getAll();
    }

    public int deleteByC(String cValue) {
        return b_tableMapper.deleteByC(cValue);
    }

    public int findCountByColumnName(String columnName, String value) {
        Map<String, Object> params = new HashMap<>();
        params.put("columnName", columnName);
        params.put("value", value);
        return b_tableMapper.findCountByColumnName(params);
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
        return b_tableMapper.findDistinctB();
    }

}
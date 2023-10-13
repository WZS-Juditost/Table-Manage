package com.ck.mybatis.demo.mapper;

import com.ck.mybatis.demo.entity.C_Table;

import java.util.List;

public interface C_TableMapper {

    int insert(C_Table c_table);
    int update(C_Table c_table);
    List<C_Table> find(String value);
    List<C_Table> getAll();
    void deleteAll();
}


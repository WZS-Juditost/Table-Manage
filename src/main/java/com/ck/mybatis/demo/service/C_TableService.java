package com.ck.mybatis.demo.service;

import com.ck.mybatis.demo.entity.C_Table;

import java.util.List;
public interface C_TableService {
    public void calculate(String value, Integer seqNo);

    public void calculateForDistinctB();

    public List<C_Table> findByB(String b);

    List<C_Table> getAll();

}

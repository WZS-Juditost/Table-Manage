package com.ck.mybatis.demo.service.impl;

import com.ck.mybatis.demo.entity.C_Table;
import com.ck.mybatis.demo.mapper.C_TableMapper;
import com.ck.mybatis.demo.service.C_TableService;
import com.ck.mybatis.demo.service.A_TableService;
import com.ck.mybatis.demo.service.B_TableService;
import com.ck.mybatis.demo.utils.CalculationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class C_TableServiceImpl implements C_TableService {
    @Autowired
    private C_TableMapper c_tableMapper;

    @Autowired
    private A_TableService a_tableService;

    @Autowired
    private B_TableService b_tableService;

    public void calculate(String value, Integer seqNo) {
        List<String> columnNamesA = Arrays.asList("aa", "bb", "cc", "dd", "ee");
        Map<String, Integer> countsA = a_tableService.getCountsForColumns(value, columnNamesA);

        List<String> columnNamesB = Arrays.asList("aa", "true_aa", "bb", "true_bb", "cc", "true_cc", "dd", "true_dd", "ee", "true_ee");
        Map<String, Integer> countsB = b_tableService.getCountsForColumns(value, columnNamesB);

        C_Table c_table = new C_Table();

        c_table.setSeqNo(seqNo);
        c_table.setB(value);
        c_table.setAas(getRatioOrNA(countsB.get("true_aa"), countsA.get("aa")));
        c_table.setBbs(getRatioOrNA(countsB.get("true_bb"), countsA.get("bb")));
        c_table.setCcs(getRatioOrNA(countsB.get("true_cc"), countsA.get("cc")));
        c_table.setAaa(getRatioOrNA(countsB.get("true_aa"), countsB.get("aa")));
        c_table.setBba(getRatioOrNA(countsB.get("true_bb"), countsB.get("bb")));
        c_table.setCca(getRatioOrNA(countsB.get("true_cc"), countsB.get("cc")));
        c_table.setAass(getRatioOrNA(countsB.get("true_ee"), countsA.get("ee")));
        c_table.setAac(getRatioOrNA(countsB.get("true_aa") + countsB.get("true_ee"), countsA.get("aa") + countsA.get("ee")));
        c_table.setDds(getRatioOrNA(countsB.get("true_dd"), countsA.get("dd")));

        c_tableMapper.insert(c_table);

    }

    private String getRatioOrNA(Integer numerator, Integer denominator) {
        if (denominator == null || denominator == 0) {
            return "NA";
        }
        return CalculationUtils.calculateRatioAndFormat(numerator, denominator);
    }

    public void calculateForList(List<String> values) {
        int seqNo = 1;
        for (String value : values) {
            calculate(value, seqNo);
            seqNo += 1;
        }
    }

    public void calculateForDistinctB() {
        List<String> distinctB_A_Table = a_tableService.findDistinctB();
        List<String> distinctB_B_Table = b_tableService.findDistinctB();

        List<String> combinedList = new ArrayList<>(distinctB_A_Table);
        combinedList.addAll(distinctB_B_Table);

        List<String> distinctValues = combinedList.stream().filter(s -> s != null && !s.trim()
                .isEmpty()).map(String::trim).distinct().collect(Collectors.toList());


        c_tableMapper.deleteAll();
        calculateForList(distinctValues);
    }

    public List<C_Table> findByB(String b) {
        return c_tableMapper.find(b);
    }

    @Override
    public List<C_Table> getAll() {
        return c_tableMapper.getAll();
    }
}

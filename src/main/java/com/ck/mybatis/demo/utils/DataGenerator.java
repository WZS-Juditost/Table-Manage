package com.ck.mybatis.demo.utils;

import com.ck.mybatis.demo.entity.A_Table;
import com.ck.mybatis.demo.service.A_TableService;
import com.ck.mybatis.demo.entity.B_Table;
import com.ck.mybatis.demo.service.B_TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataGenerator {

    @Autowired
    private A_TableService a_tableService;

    @Autowired
    private B_TableService b_tableService;

    public void createRandomDataForA() {
        Random random = new Random();
        int totalRecords = 20000;

        for (int i = 0; i < 20000; i++) {
            A_Table a_table = new A_Table();

            a_table.setSeqNo(i + 1);
            a_table.setA(randomFormattedString(random));
            a_table.setB(randomString(random, 1));
            a_table.setC(randomString(random, 1));
            a_table.setD(randomString(random, 1));
            a_table.setE(randomString(random, 1));
            a_table.setF(randomString(random, 1));
            a_table.setAa(random.nextBoolean());
            a_table.setBb(random.nextBoolean());
            a_table.setCc(random.nextBoolean());
            a_table.setDd(random.nextBoolean());
            a_table.setEe(random.nextBoolean());

            a_tableService.insert(a_table);
        }

        System.out.println("共生成数据: " + totalRecords);
    }

    public void createRandomDataForB() {
        Random random = new Random();
        int totalRecords = 20000;

        for (int i = 0; i < 20000; i++) {
            B_Table b_table = new  B_Table();

            b_table.setSeqNo(i + 1);
            b_table.setA(randomFormattedString(random));
            b_table.setB(randomString(random, 1));
            b_table.setC(randomString(random, 1));
            b_table.setD(randomString(random, 1));
            b_table.setE(randomString(random, 1));
            b_table.setF(randomString(random, 1));
            b_table.setAa(random.nextBoolean());
            b_table.setTaa((random.nextBoolean()));
            b_table.setBb(random.nextBoolean());
            b_table.setTbb((random.nextBoolean()));
            b_table.setCc(random.nextBoolean());
            b_table.setTcc((random.nextBoolean()));
            b_table.setDd(random.nextBoolean());
            b_table.setTdd((random.nextBoolean()));
            b_table.setEe(random.nextBoolean());
            b_table.setTee((random.nextBoolean()));

            b_tableService.insert(b_table);
        }

        System.out.println("共生成数据: " + totalRecords);
    }

    public String randomFormattedString(Random random) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int indexStart = random.nextInt(characters.length());
        int indexEnd = random.nextInt(characters.length());

        int random4DigitNum = random.nextInt(10000);

        return characters.charAt(indexStart) + String.format("%04d", random4DigitNum) + characters.charAt(indexEnd);
    }

    public String randomString(Random random, int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }
}

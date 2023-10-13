package com.ck.mybatis.demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class A_Table implements Serializable {
    private Long id;
    private Integer seqNo;
    private String A;
    private String B;
    private String C;
    private String D;
    private String E;
    private String F;
    private Boolean aa;
    private Boolean bb;
    private Boolean cc;
    private Boolean dd;
    private Boolean ee;
    private static final long serialVersionUID = 1L;
}
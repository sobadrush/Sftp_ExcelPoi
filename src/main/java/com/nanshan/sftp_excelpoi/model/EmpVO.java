package com.nanshan.sftp_excelpoi.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author RogerLo
 * @date 2023/5/28
 */

@Data
@Builder
public class EmpVO implements Serializable {

    public static void main(String[] args) {
        Stream.of(
            EmpVO.builder().empNo(1001).empName("Roger").empJob("SA").empHireDate(new Date(new java.util.Date().getTime())).build(),
            EmpVO.builder().empNo(1001).empName("Kelly").empJob("PM").empHireDate(new Date(new java.util.Date().getTime())).build(),
            EmpVO.builder().empNo(1001).empName("Ken").empJob("SD").empHireDate(new Date(new java.util.Date().getTime())).build(),
            EmpVO.builder().empNo(1001).empName("Terry").empJob("PG").empHireDate(new Date(new java.util.Date().getTime())).build(),
            EmpVO.builder().empNo(1001).empName("Billy").empJob("QA").empHireDate(new Date(new java.util.Date().getTime())).build()
        ).collect(Collectors.toList()).forEach(System.out::println);
    }

    private static final long serialVersionUID = 1L;

    private Integer empNo;
    private java.sql.Date empHireDate;
    private String empName;
    private String empJob;

}

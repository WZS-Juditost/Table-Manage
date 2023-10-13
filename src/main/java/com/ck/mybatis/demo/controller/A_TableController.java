package com.ck.mybatis.demo.controller;

import com.ck.mybatis.demo.common.Result;
import com.ck.mybatis.demo.entity.A_Table;
import com.ck.mybatis.demo.service.A_TableService;
import com.ck.mybatis.demo.utils.ExcelUtils;
import com.ck.mybatis.demo.utils.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;


@RestController
@RequestMapping("/a")
public class A_TableController {

    @Autowired
    private A_TableService a_tableService;

    @Autowired
    private DataGenerator dataGenerator;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody A_Table a_table) {
        int insertCount = a_tableService.insert(a_table);
        if (insertCount > 0) {
            return new ResponseEntity<>("创建成功.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("创建失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable("id") Long id) {
        return Result.ok(a_tableService.delete(id) > 0);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody A_Table a_table) {
        a_table.setId(id);
        int updateCount = a_tableService.update(a_table);
        if (updateCount > 0) {
            return new ResponseEntity<>("更新成功.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("更新失败.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/import")
    public ResponseEntity<String> importExcelFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("请选择一个文件", HttpStatus.OK);
        }

        try (InputStream in = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(in)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            int rowNum = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                rowNum++;

                if (rowNum <= 2) {
                    continue;
                }

                A_Table a_table = new A_Table();

                Integer seqNo = ExcelUtils.getCellNumericValue(currentRow.getCell(0));
                if (seqNo != null) {
                    a_table.setSeqNo(seqNo);
                } else {
                    continue;
                }
                a_table.setA(ExcelUtils.getCellStringValue(currentRow.getCell(1)));
                a_table.setB(ExcelUtils.getCellStringValue(currentRow.getCell(2)));
                a_table.setC(ExcelUtils.getCellStringValue(currentRow.getCell(3)));
                a_table.setD(ExcelUtils.getCellStringValue(currentRow.getCell(4)));
                a_table.setE(ExcelUtils.getCellStringValue(currentRow.getCell(5)));
                a_table.setF(ExcelUtils.getCellStringValue(currentRow.getCell(6)));
                a_table.setAa(ExcelUtils.getCellBooleanValue(currentRow.getCell(7)));
                a_table.setBb(ExcelUtils.getCellBooleanValue(currentRow.getCell(8)));
                a_table.setCc(ExcelUtils.getCellBooleanValue(currentRow.getCell(9)));
                a_table.setDd(ExcelUtils.getCellBooleanValue(currentRow.getCell(10)));
                a_table.setEe(ExcelUtils.getCellBooleanValue(currentRow.getCell(11)));

                a_tableService.insert(a_table);
            }

            return new ResponseEntity<>("导入成功", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("发送错误，请确认后重试", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search/{value}")
    public ResponseEntity<List<A_Table>> find(@PathVariable String value) {
        return ResponseEntity.ok(a_tableService.find(value));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> findCountByColumnName(
            @RequestParam String columnName,
            @RequestParam String value) {
        int count = a_tableService.findCountByColumnName(columnName, value);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/generateData")
    public String generateData() {
        dataGenerator.createRandomDataForA();
        return "数据生成完毕";
    }
}




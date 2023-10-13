package com.ck.mybatis.demo.controller;

import com.ck.mybatis.demo.entity.C_Table;
import com.ck.mybatis.demo.service.C_TableService;
import com.ck.mybatis.demo.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/c")
public class C_TableController {

    @Autowired
    private C_TableService c_tableService;

    @PostMapping("/calculate")
    public ResponseEntity<String> calculate() {
        try {
            c_tableService.calculateForDistinctB();
            return new ResponseEntity<>("计算成功.", HttpStatus.CREATED);
        } catch (ArithmeticException e) {
            return new ResponseEntity<>("出现错误." + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search/{value}")
    public ResponseEntity<List<C_Table>> find(@PathVariable String value) {
        return ResponseEntity.ok(c_tableService.findByB(value));
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportExcelFile() {
        try (
                Workbook workbook = new XSSFWorkbook();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ) {
            Sheet sheet = workbook.createSheet("C_Table data");

            List<C_Table> data = c_tableService.getAll();

            Row headerRow = sheet.createRow(0);
            String[] columns = {"SeqNo", "B", "aaS", "bbS", "ccS", "aaA", "bbA", "ccA", "aaSS", "aaC", "ddS"};
            for (int i = 0; i < columns.length; i++) {
                Cell headerCell = headerRow.createCell(i);
                headerCell.setCellValue(columns[i]);
            }

            int rowNum = 1;
            for (C_Table item : data) {
                Row row = sheet.createRow(rowNum++);
                ExcelUtils.setCellValue(row.createCell(0), item.getSeqNo());
                ExcelUtils.setCellValue(row.createCell(1), item.getB());
                ExcelUtils.setCellValue(row.createCell(2), item.getAas());
                ExcelUtils.setCellValue(row.createCell(3), item.getBbs());
                ExcelUtils.setCellValue(row.createCell(4), item.getCcs());
                ExcelUtils.setCellValue(row.createCell(5), item.getAaa());
                ExcelUtils.setCellValue(row.createCell(6), item.getBba());
                ExcelUtils.setCellValue(row.createCell(7), item.getCca());
                ExcelUtils.setCellValue(row.createCell(8), item.getAass());
                ExcelUtils.setCellValue(row.createCell(9), item.getAac());
                ExcelUtils.setCellValue(row.createCell(10), item.getDds());
            }

            workbook.write(bos);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=c_table_data.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(bos.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("发送错误，请确认后重试", HttpStatus.BAD_REQUEST);
        }
    }
}


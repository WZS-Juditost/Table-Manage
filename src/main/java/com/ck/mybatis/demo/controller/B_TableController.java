package com.ck.mybatis.demo.controller;

import com.ck.mybatis.demo.common.Result;
import com.ck.mybatis.demo.entity.B_Table;
import com.ck.mybatis.demo.service.B_TableService;
import com.ck.mybatis.demo.utils.ExcelUtils;
import com.ck.mybatis.demo.utils.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*;
import org.springframework.dao.DuplicateKeyException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;



@RestController
@RequestMapping("/b")
public class B_TableController {

    @Autowired
    private B_TableService b_tableService;

    @Autowired
    private DataGenerator dataGenerator;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody B_Table b_table) {
        try {
            int insertCount = b_tableService.insert(b_table);
            if (insertCount > 0) {
                return new ResponseEntity<>("创建成功.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("创建失败", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>("创建失败，存在重复的记录", HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable("id") Long id) {
        return Result.ok(b_tableService.delete(id) > 0);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody B_Table b_table) {
        try {
            b_table.setId(id);
            int updateCount = b_tableService.update(b_table);
            if (updateCount > 0) {
                return new ResponseEntity<>("更新成功.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("更新失败.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (DuplicateKeyException e) {
            return new ResponseEntity<>("更新失败，存在重复的记录", HttpStatus.CONFLICT);
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

                B_Table b_table = new B_Table();

                Integer seqNo = ExcelUtils.getCellNumericValue(currentRow.getCell(0));
                if (seqNo != null) {
                    b_table.setSeqNo(seqNo);
                } else {
                    continue;
                }
                b_table.setA(ExcelUtils.getCellStringValue(currentRow.getCell(1)));
                b_table.setB(ExcelUtils.getCellStringValue(currentRow.getCell(2)));
                b_table.setC(ExcelUtils.getCellStringValue(currentRow.getCell(3)));
                b_table.setD(ExcelUtils.getCellStringValue(currentRow.getCell(4)));
                b_table.setE(ExcelUtils.getCellStringValue(currentRow.getCell(5)));
                b_table.setF(ExcelUtils.getCellStringValue(currentRow.getCell(6)));
                b_table.setAa(ExcelUtils.getCellBooleanValue(currentRow.getCell(7)));
                b_table.setTaa(ExcelUtils.getCellBooleanValue(currentRow.getCell(8)));
                b_table.setBb(ExcelUtils.getCellBooleanValue(currentRow.getCell(9)));
                b_table.setTbb(ExcelUtils.getCellBooleanValue(currentRow.getCell(10)));
                b_table.setCc(ExcelUtils.getCellBooleanValue(currentRow.getCell(11)));
                b_table.setTcc(ExcelUtils.getCellBooleanValue(currentRow.getCell(12)));
                b_table.setDd(ExcelUtils.getCellBooleanValue(currentRow.getCell(13)));
                b_table.setTdd(ExcelUtils.getCellBooleanValue(currentRow.getCell(14)));
                b_table.setEe(ExcelUtils.getCellBooleanValue(currentRow.getCell(15)));
                b_table.setTee(ExcelUtils.getCellBooleanValue(currentRow.getCell(16)));

                b_tableService.insert(b_table);
            }

            return new ResponseEntity<>("导入成功", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("发送错误，请检查是否存在重复的记录后重试", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/search/{value}")
    public ResponseEntity<List<B_Table>> find(@PathVariable String value) {
        return ResponseEntity.ok(b_tableService.find(value));
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportExcelFile() {
        try (
                Workbook workbook = new XSSFWorkbook();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ) {
            Sheet sheet = workbook.createSheet("B_Table data");

            List<B_Table> data = b_tableService.getAll();

            Row headerRow = sheet.createRow(0);
            String[] columns = {"SeqNo", "A", "B", "C", "D", "E", "F", "Aa", "Taa", "Bb", "Tbb", "Cc", "Tcc", "Dd", "Tdd", "Ee", "Tee"};
            for (int i = 0; i < columns.length; i++) {
                Cell headerCell = headerRow.createCell(i);
                headerCell.setCellValue(columns[i]);
            }

            int rowNum = 1;
            for (B_Table item : data) {
                Row row = sheet.createRow(rowNum++);
                ExcelUtils.setCellValue(row.createCell(0), item.getSeqNo());
                ExcelUtils.setCellValue(row.createCell(1), item.getA());
                ExcelUtils.setCellValue(row.createCell(2), item.getB());
                ExcelUtils.setCellValue(row.createCell(3), item.getC());
                ExcelUtils.setCellValue(row.createCell(4), item.getD());
                ExcelUtils.setCellValue(row.createCell(5), item.getE());
                ExcelUtils.setCellValue(row.createCell(6), item.getF());
                ExcelUtils.setCellValue(row.createCell(7), item.getAa());
                ExcelUtils.setCellValue(row.createCell(8), item.getTaa());
                ExcelUtils.setCellValue(row.createCell(9), item.getBb());
                ExcelUtils.setCellValue(row.createCell(10), item.getTbb());
                ExcelUtils.setCellValue(row.createCell(11), item.getCc());
                ExcelUtils.setCellValue(row.createCell(12), item.getTcc());
                ExcelUtils.setCellValue(row.createCell(13), item.getDd());
                ExcelUtils.setCellValue(row.createCell(14), item.getTdd());
                ExcelUtils.setCellValue(row.createCell(15), item.getEe());
                ExcelUtils.setCellValue(row.createCell(16), item.getTee());
            }

            workbook.write(bos);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=b_table_data.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(bos.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("发送错误，请确认后重试", HttpStatus.BAD_REQUEST);
        }
    }

    // In your Controller class
    @DeleteMapping("/deleteByC/{cValue}")
    public ResponseEntity<String> deleteByC(@PathVariable String cValue) {
        int deleteCount = b_tableService.deleteByC(cValue);
        if (deleteCount > 0) {
            return new ResponseEntity<>("删除的数据量: " + deleteCount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("没有找到匹配的数据.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> findCountByColumnName(
            @RequestParam String columnName,
            @RequestParam String value) {
        int count = b_tableService.findCountByColumnName(columnName, value);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/uniqueB")
    public List<String> getUniqueBValues() {
        return b_tableService.findDistinctB();
    }

    @GetMapping("/generateData")
    public String generateData() {
        dataGenerator.createRandomDataForB();
        return "数据生成完毕";
    }
}


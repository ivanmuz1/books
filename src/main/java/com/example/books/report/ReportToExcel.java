package com.example.books.report;

import com.example.books.entities.Form;
import com.example.books.repositories.FormRepository;
import com.example.books.repositories.PersonRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class ReportToExcel {

    @Autowired
    FormRepository formRepository;

    @Autowired
    PersonRepository personRepository;

    public void generateReport(String SpecifiedDate) throws IOException, ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = dateFormat.parse(SpecifiedDate);
        Date date = new Date(parsedDate.getTime());

        System.out.println(date);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Отчет");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Имя пользователя");
        header.createCell(1).setCellValue("Название книги");
        header.createCell(2).setCellValue("Дней просрочки на этот месяц");
        header.createCell(3).setCellValue("Пенни на месяц");

        System.out.println(SpecifiedDate);

        int rowNumber = 1;
        List<Form> formList = formRepository.findUserByMonth(date);

        for(Form form: formList){
            Row row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(form.getReaderId());
            row.createCell(1).setCellValue(form.getBookId());
            row.createCell(2).setCellValue(form.getDateReceipt());
            row.createCell(3).setCellValue(form.getPenalties());
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream("отчет.xls")){
            workbook.write(fileOutputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        workbook.close();
    }
}

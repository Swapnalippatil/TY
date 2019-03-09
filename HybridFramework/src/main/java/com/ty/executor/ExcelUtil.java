package com.ty.executor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	//Constructor is for creating object with file Path
	//Other programs can only use this constructor during Object creation
	//This constructor will make sure FilePath is set before calling any methods below
	//by object
	public ExcelUtil(String filePath) {
		this.filePath = filePath;
	}
	//static is removed to make it object oriented 
	//main method is removed for not executing by itself, it must be executed through other classes 

	String filePath;

	public int getRowCount(String sheetName) {
		FileInputStream fis = null;

		int rowCount = -1;
		try {
			// just to open file in read mode
			fis = new FileInputStream(filePath);
			// create workbook for file opened
			Workbook wb = WorkbookFactory.create(fis);

			// it gets Sheet object pointing to required sheet
			Sheet sh = wb.getSheet(sheetName);
			// if sheet is not present then sh will be null

			if (sh != null) {
				// if sheet is present then get last row num as count
				rowCount = sh.getLastRowNum();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rowCount;

	}

	public Object getCellData(String sheetName, int rowNum, int colNum) {
		FileInputStream fis = null;

		Object data = null;
		try {
			// just to open file in read mode
			fis = new FileInputStream(filePath);
			// create workbook for file opened
			Workbook wb = WorkbookFactory.create(fis);

			// it gets Sheet object pointing to required sheet
			Sheet sh = wb.getSheet(sheetName);
			// if sheet is not present then sh will be null

			if (sh != null) {
				// if sheet is present then get into row
				Row r = sh.getRow(rowNum);
				if (r != null) {
					// if row is present then get into cell
					Cell c = r.getCell(colNum);
					if (c != null) {
						// if cell is present then get data
						switch (c.getCellTypeEnum()) {
						case NUMERIC:
							data = c.getNumericCellValue();
							break;
						case STRING:
							data = c.getStringCellValue();
							break;
						default:
							break;
						}

					}
				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}

	public void writeToCell(String sheetName, int rowNum, int colNum, String data) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			// just to open file in read mode
			fis = new FileInputStream(filePath);
			// create workbook for file opened
			Workbook wb = WorkbookFactory.create(fis);

			// it gets Sheet object pointing to required sheet
			Sheet sh = wb.getSheet(sheetName);
			// if sheet is not present then sh will be null

			if (sh != null) {
				// if sheet is present then get into row
				Row r = sh.getRow(rowNum);
				if (r != null) {
					Cell c = r.createCell(colNum);
					c.setCellValue(data);
					// open file again for write
					fos = new FileOutputStream(filePath);
					// write data in memory to file
					wb.write(fos);
				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

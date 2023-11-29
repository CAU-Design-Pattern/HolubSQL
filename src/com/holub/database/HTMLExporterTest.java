package com.holub.database;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

class HTMLExporterTest {

	@Test
	void test_RawName_Empty() throws IOException{
		String tableName = "RawName Empty Table";
		String[] tableRow = { "","",""};
		String[][] row = { 
				{ "Hello", "Allen", "1" },
				{ "Flintstone", "Wilma", "2" }
		};
		
		Table people = TableFactory.create(tableName, tableRow);
		for(int i=0;i<row.length;i++)
		{
			people.insert(row[i]);
		}
		
		File htmlFile = new File("c:\\dp2023\\People_RawName_Empty.html"); 
		Writer out = new FileWriter(htmlFile);
		HTMLExporter exporter = new HTMLExporter(out);
		people.export(exporter);
		out.close();
		
		byte[] htmlByte = Files.readAllBytes(Paths.get(htmlFile.getPath()));
		String lines = new String(htmlByte,StandardCharsets.UTF_8);
		//over jdk11
		//String lines = Files.readString(Paths.get(htmlFile.getPath()));
		Document doc = Jsoup.parseBodyFragment(lines);
		Element table = doc.select("table").get(0);

		for(int i=0;i<row.length;i++)
		{
			Element rowOut = table.select("tr").get(i+1);
			Elements colsOut = rowOut.select("td");
			for(int j=0;j<colsOut.size();j++)
			{
				assertEquals(row[i][j], colsOut.get(j).text());
			}
		}
	}

	@Test
	void test_Data_Empty() throws IOException{
		String tableName = "Empty Table";
		String[] tableRow = {"last","first","addrId"};
		String[][] row = { 
				{"","","Seoul"},
				{"","",""},
				{"","Kim",""}				
		};
		
		Table people = TableFactory.create(tableName, tableRow);
		for(int i=0;i<row.length;i++)
		{
			people.insert(row[i]);
		}
		
		File htmlFile = new File("c:\\dp2023\\People_Data_Empty.html"); 
		Writer out = new FileWriter(htmlFile);
		HTMLExporter exporter = new HTMLExporter(out);
		people.export(exporter);
		out.close();

		byte[] htmlByte = Files.readAllBytes(Paths.get(htmlFile.getPath()));
		String lines = new String(htmlByte,StandardCharsets.UTF_8);
		//String lines = Files.readString(Paths.get(htmlFile.getPath()));
		Document doc = Jsoup.parseBodyFragment(lines);
		Element table = doc.select("table").get(0);

		for(int i=0;i<row.length;i++)
		{
			Element rowOut = table.select("tr").get(i+1);
			Elements colsOut = rowOut.select("td");
			for(int j=0;j<colsOut.size();j++)
			{
				assertEquals(row[i][j], colsOut.get(j).text());
			}
		}
	}
	
	@Test
	void test_HTML_Raw() throws IOException{
		String tableName = "HTML Raw Test";
		String[] tableRow = {"last","first","addrId"};
		String[] row1 = { "Hello", "Allen", "1" };
		String[] row2 = { "Flintstone", "Wilma", "2" };
		String[] row3 = { "New String", "Hello", "2" };
		String[] row4 = { "Flintstone","Fred","2" };
		
		Table people = TableFactory.create(tableName, tableRow);
		people.insert(row1);
		people.insert(row2);
		people.insert(row3);
		people.insert(row4);
		
		File htmlFile = new File("c:\\dp2023\\People_Test_Raw.html"); 
		Writer out = new FileWriter(htmlFile);
		HTMLExporter exporter = new HTMLExporter(out);
		people.export(exporter);
		out.close();
		
		String HTMLTestString = 
				"<html><body><table border='1'>\n"
				+ "HTML Raw Test\n"
				+ "<tr><th>last</th><th>first</th><th>addrId</th></tr>\n"
				+ "<tr><td>Hello</td><td>Allen</td><td>1</td></tr>\n"
				+ "<tr><td>Flintstone</td><td>Wilma</td><td>2</td></tr>\n"
				+ "<tr><td>New String</td><td>Hello</td><td>2</td></tr>\n"
				+ "<tr><td>Flintstone</td><td>Fred</td><td>2</td></tr>\n"
				+ "</table></body></html>\n";

		byte[] htmlByte = Files.readAllBytes(Paths.get(htmlFile.getPath()));
		String lines = new String(htmlByte,StandardCharsets.UTF_8);
		//String lines = Files.readString(Paths.get(htmlFile.getPath()));
		assertEquals(HTMLTestString, lines);
		
	}
	
	@Test
	void test_HTML_Jsoup() throws IOException{

		String tableName = "HTML Jsoup Test";
		String[] tableRow = {"last","first","addrId"};
		String[][] row = {
				{ "Hello", "Allen", "1" },
				{ "Flintstone", "Wilma", "2" },
				{ "New String", "Hello", "2" },
				{ "Flintstone","Fred","2" }
		};
		
		Table people = TableFactory.create(tableName, tableRow);
		for(int i=0;i<row.length;i++)
		{
			people.insert(row[i]);
		}
		
		File htmlFile = new File("c:\\dp2023\\People_Test_Jsoup.html"); 
		Writer out = new FileWriter(htmlFile);
		HTMLExporter exporter = new HTMLExporter(out);
		people.export(exporter);
		out.close();

		byte[] htmlByte = Files.readAllBytes(Paths.get(htmlFile.getPath()));
		String lines = new String(htmlByte,StandardCharsets.UTF_8);
		//String lines = Files.readString(Paths.get(htmlFile.getPath()));
		Document doc = Jsoup.parseBodyFragment(lines);
		Element table = doc.select("table").get(0);

		for(int i=0;i<row.length;i++)
		{
			Element rowOut = table.select("tr").get(i+1);
			Elements colsOut = rowOut.select("td");

			for(int j=0;j<colsOut.size();j++)
			{
				assertEquals(row[i][j], colsOut.get(j).text());
			}
		}
	}
	
	/*
	 * 실패하는 테스트
	 * 해당 테스트를 통과하기 위해 옳바른 출력이 뭔지 정의해야 한다(HTMLExporter 보고서 참조)
	 */
	@Test
	void test_Tag_Data() throws IOException{
		String tableName = "People_Test_tag";
		String[] tableRow = { "last","first","addrId" };
		String[][] row = {
				{ "Hello", "Allen", "1" },
				{ "Flintstone", "Wilma", "2" },
				{ "</html>", "Hello", "2" },
				{ "Flintstone","Fred","2" }
		};
		
		Table people = TableFactory.create(tableName, tableRow);
		for(int i=0;i<row.length;i++)
		{
			people.insert(row[i]);
		}
		
		File htmlFile = new File("c:\\dp2023\\People_Test_Tag.html"); 
		Writer out = new FileWriter(htmlFile);
		HTMLExporter exporter = new HTMLExporter(out);
		people.export(exporter);
		out.close();

		byte[] htmlByte = Files.readAllBytes(Paths.get(htmlFile.getPath()));
		String lines = new String(htmlByte,StandardCharsets.UTF_8);
		//String lines = Files.readString(Paths.get(htmlFile.getPath()));
		Document doc = Jsoup.parseBodyFragment(lines);
		Element table = doc.select("table").get(0);

		for(int i=0;i<row.length;i++)
		{
			Element rowOut = table.select("tr").get(i+1);
			Elements colsOut = rowOut.select("td");

			for(int j=0;j<colsOut.size();j++)
			{
				assertEquals(row[i][j], colsOut.get(j).text());
			}
		}
	}

}

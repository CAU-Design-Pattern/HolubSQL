package com.holub.database;

import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class XMLExporterTest {
	@Test
	public void XMLExporterTest() {
		Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });
		Table address = TableFactory.create("address", new String[] { "addrId", "street", "city", "state", "zip" });
		try {

			people.insert(new Object[] { "Holub", "Allen", "1" });
			people.insert(new Object[] { "Flintstone", "Wilma", "2" });
			people.insert(new String[] { "addrId", "first", "last" }, new Object[] { "2", "Fred", "Flintstone" });

			address.insert(new Object[] { "1", "123 MyStreet", "Berkeley", "CA", "99999" });

			List l = new ArrayList();
			l.add("2");
			l.add("123 Quarry Ln.");
			l.add("Bedrock ");
			l.add("XX");
			l.add("12345");
			assert (address.insert(l) == 1);

			l.clear();
			l.add("3");
			l.add("Bogus");
			l.add("Bad");
			l.add("XX");
			l.add("12345");

			List c = new ArrayList();
			c.add("addrId");
			c.add("street");
			c.add("city");
			c.add("state");
			c.add("zip");
			assert (address.insert(c, l) == 1);

		} catch (Throwable t) {
			System.out.println("Insert Fail.");
			t.printStackTrace();
			System.exit(1);
		}
		try {
			System.out.println("---- People Table Write. ----\n");
			Writer peopeWriter = new FileWriter("people");
			people.export(new XMLExporter(peopeWriter));
			peopeWriter.close();

			Path peopleFilePath = Paths.get("people");
			String peopleFileString = Files.readString(peopleFilePath);

			String peopleTestString = """
					<?xml version="1.0" encoding="UTF-8"?>
					<people>
						<row>
							<last>Holub</last>
							<first>Allen</first>
							<addrId>1</addrId>
						</row>
						<row>
							<last>Flintstone</last>
							<first>Wilma</first>
							<addrId>2</addrId>
						</row>
						<row>
							<last>Flintstone</last>
							<first>Fred</first>
							<addrId>2</addrId>
						</row>
					</people>
					""";

			Assert.assertEquals(peopleFileString, peopleTestString);
			System.out.println("\n\n");

			System.out.println("---- Adress Table Write. ----\n");
			Writer addressWriter = new FileWriter("address");
			address.export(new XMLExporter(addressWriter));
			addressWriter.close();

			Path addressFilePath = Paths.get("address");
			String addressFileString = Files.readString(addressFilePath);

			String addressTestString = """
					<?xml version="1.0" encoding="UTF-8"?>
					<address>
						<row>
							<addrId>1</addrId>
							<street>123 MyStreet</street>
							<city>Berkeley</city>
							<state>CA</state>
							<zip>99999</zip>
						</row>
						<row>
							<addrId>2</addrId>
							<street>123 Quarry Ln.</street>
							<city>Bedrock </city>
							<state>XX</state>
							<zip>12345</zip>
						</row>
						<row>
							<addrId>3</addrId>
							<street>Bogus</street>
							<city>Bad</city>
							<state>XX</state>
							<zip>12345</zip>
						</row>
					</address>
										""";

			Assert.assertEquals(addressFileString, addressTestString);
			System.out.println("\n\n");

		} catch (Throwable t) {
			System.out.println("Export Fail.");
			t.printStackTrace();
			System.exit(1);
		}
	}
}

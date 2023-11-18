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
	public void xMLExporterTest() {
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
            String peopleFileString = new String(Files.readAllBytes(peopleFilePath));
			

            String peopleTestString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            		"<people>\n" +
            	    "\t<columns>\n" +
            	    "\t\t<column>last</column>\n" +
            	    "\t\t<column>first</column>\n" +
            	    "\t\t<column>addrId</column>\n" +
            	    "\t</columns>\n" +
            	    "\t<row>\n" +
            	    "\t\t<last>Holub</last>\n" +
            	    "\t\t<first>Allen</first>\n" +
            	    "\t\t<addrId>1</addrId>\n" +
            	    "\t</row>\n" +
            	    "\t<row>\n" +
            	    "\t\t<last>Flintstone</last>\n" +
            	    "\t\t<first>Wilma</first>\n" +
            	    "\t\t<addrId>2</addrId>\n" +
            	    "\t</row>\n" +
            	    "\t<row>\n" +
            	    "\t\t<last>Flintstone</last>\n" +
            	    "\t\t<first>Fred</first>\n" +
            	    "\t\t<addrId>2</addrId>\n" +
            	    "\t</row>\n" +
            	    "</people>\n";




			Assert.assertEquals(peopleFileString, peopleTestString);
			System.out.println("\n\n");

			System.out.println("---- Adress Table Write. ----\n");
			Writer addressWriter = new FileWriter("address");
			address.export(new XMLExporter(addressWriter));
			addressWriter.close();

			Path addressFilePath = Paths.get("address");
			String addressFileString = new String(Files.readAllBytes(addressFilePath));


			String addressTestString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				    "<address>\n" +
				    "\t<columns>\n" +
				    "\t\t<column>addrId</column>\n" +
				    "\t\t<column>street</column>\n" +
				    "\t\t<column>city</column>\n" +
				    "\t\t<column>state</column>\n" +
				    "\t\t<column>zip</column>\n" +
				    "\t</columns>\n" +
				    "\t<row>\n" +
				    "\t\t<addrId>1</addrId>\n" +
				    "\t\t<street>123 MyStreet</street>\n" +
				    "\t\t<city>Berkeley</city>\n" +
				    "\t\t<state>CA</state>\n" +
				    "\t\t<zip>99999</zip>\n" +
				    "\t</row>\n" +
				    "\t<row>\n" +
				    "\t\t<addrId>2</addrId>\n" +
				    "\t\t<street>123 Quarry Ln.</street>\n" +
				    "\t\t<city>Bedrock </city>\n" +
				    "\t\t<state>XX</state>\n" +
				    "\t\t<zip>12345</zip>\n" +
				    "\t</row>\n" +
				    "\t<row>\n" +
				    "\t\t<addrId>3</addrId>\n" +
				    "\t\t<street>Bogus</street>\n" +
				    "\t\t<city>Bad</city>\n" +
				    "\t\t<state>XX</state>\n" +
				    "\t\t<zip>12345</zip>\n" +
				    "\t</row>\n" +
				    "</address>\n";



			Assert.assertEquals(addressFileString, addressTestString);
			System.out.println("\n\n");

		} catch (Throwable t) {
			System.out.println("Export Fail.");
			t.printStackTrace();
			System.exit(1);
		}
	}
}

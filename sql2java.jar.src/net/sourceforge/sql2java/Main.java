/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/

package net.sourceforge.sql2java;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.*;

public class Main {

	private static Properties prop;

	public static void main(String argv[]) {
		main(argv, null);
	}

	public static void main(String argv[], Map overideFileProperties) {
		if (argv == null || argv.length < 1) {
			System.err.println("Usage: java net.sourceforge.sql2java.Main <properties filename>");
			System.exit(1);
		}
		prop = new Properties();
		try {
			prop.load(new FileInputStream(argv[0]));
			CodeWriter cw = new CodeWriter(null, prop);
			cw.log("database properties initialization");
			Database db = new Database();
			db.setDriver(getProperty("jdbc.driver"));
			db.setUrl(getProperty("jdbc.url"));
			db.setUsername(getProperty("jdbc.username"));
			db.setPassword(getProperty("jdbc.password"));
			db.setCatalog(getProperty("jdbc.catalog"));
			db.setSchema(getProperty("jdbc.schema"));
			db.setTableNamePattern(getProperty("jdbc.tablenamepattern"));
			db.setActiveConnections(getProperty("jdbc.active", "10"));
			db.setIdleConnections(getProperty("jdbc.idle", "5"));
			db.setMaxWait(getProperty("jdbc.maxwait", "120000"));
			CodeWriter writer = new CodeWriter(db, prop);
			if (overideFileProperties != null)
				prop.putAll(overideFileProperties);
			if ("false".equalsIgnoreCase(getProperty("jdbc.oracle.retrieve.remarks")))
				db.setOracleRetrieveRemarks(false);
			else
				db.setOracleRetrieveRemarks(true);
			String tt = getProperty("jdbc.tabletypes", "TABLE");
			StringTokenizer st = new StringTokenizer(tt, ",; \t");
			ArrayList al = new ArrayList();
			for (; st.hasMoreTokens(); al.add(st.nextToken().trim()));
			db.setTableTypes((String[]) (String[]) al.toArray(new String[al.size()]));
			db.load();
			if (argv.length > 1)
				writer.setDestinationFolder(argv[1]);
			writer.process();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static String getProperty(String property) {
		String res = ConfigHelper.getGlobalProperty(property);
		if (res != null) {
			return res.trim();
		} else {
			String s = prop.getProperty(property);
			return s == null ? s : s.trim();
		}
	}

	public static String getProperty(String property, String default_val) {
		String s = getProperty(property);
		if (s == null)
			return default_val;
		else
			return s;
	}

	public static boolean isInArray(String ar[], String code) {
		if (ar == null)
			return false;
		for (int i = 0; i < ar.length; i++)
			if (code.equalsIgnoreCase(ar[i]))
				return true;

		return false;
	}
}
package com.company;

import com.company.config.AppConfig;
import com.company.dao.TestDao;
import com.company.export.TestExport;
import com.company.export.SecondXmlWriter;
import com.company.export.FirstXmlWriter;
import com.company.util.Parsing2xml;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;
import java.sql.DriverManager;

@Slf4j
public class Main {
    public static final String firstDestinationFilename = "xml\\1.xml";
    public static final String secondDestinationFilename = "xml\\2.xml";

    public static void main(String[] args) throws Exception {
        if (args.length == 5) {
            AppConfig.INSTANCE.setDriverClassName(args[0]);
            AppConfig.INSTANCE.setJdbcUrl(args[1]);
            AppConfig.INSTANCE.setUserName(args[2]);
            AppConfig.INSTANCE.setPassword(args[3]);
            AppConfig.INSTANCE.setCountField(Integer.parseInt(args[4]));
        }

        initDBI(AppConfig.INSTANCE.getDriverClassName(),
                AppConfig.INSTANCE.getJdbcUrl(),
                AppConfig.INSTANCE.getUserName(),
                AppConfig.INSTANCE.getPassword());

        // save DB test
        TestExport testExport = new TestExport();

        testExport.process(AppConfig.INSTANCE.getCountField(), 1000);

        // save 1.xml
        FirstXmlWriter noNSWriter = new FirstXmlWriter();

        TestDao testDao = DBIProvider.getDao(TestDao.class);
        noNSWriter.writeToXml(Paths.get(firstDestinationFilename), testDao.getAll());

        // save 2.xml
        SecondXmlWriter export2xml = new SecondXmlWriter();
        export2xml.setFile(secondDestinationFilename);
        export2xml.save();

        // parsing 2.xml and output arithmetic sum of the values ​​of all attributes
        System.out.println("арифметическая сумму = " + Parsing2xml.getArithmeticSumOfValues());
    }

    public static void initDBI(String dbDriver, String dbUrl, String dbUser, String dbPassword) {
        DBIProvider.init(() -> {
            try {
                Class.forName(dbDriver);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("PostgreSQL driver not found", e);
            }
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        });
    }
}

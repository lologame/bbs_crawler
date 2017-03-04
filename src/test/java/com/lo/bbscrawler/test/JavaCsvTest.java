package com.lo.bbscrawler.test;

import com.csvreader.CsvWriter;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2017/2/22.
 */
public class JavaCsvTest {

    @Test
    public void testWrite() throws IOException {
        CsvWriter wr = new CsvWriter("./info.csv", ',', Charset.forName("GBK"));
        String[] contents = {"Lily", "五一", "90", "女"};
        wr.writeRecord(contents);
        wr.close();
    }

}

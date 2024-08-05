package com.dhee.mine;


import com.dhee.mine.Utils.MainApplication;
import com.dhee.mine.Utils.enty.Table;
import com.dhee.mine.Utils.payload.PayloadColumn;
import com.dhee.mine.Utils.payload.PayloadSchema;
import com.dhee.mine.Utils.payload.PayloadTable;
import com.dhee.mine.Utils.enty.Database;
import com.dhee.mine.Utils.spider.SpiderHandle;
import lombok.extern.java.Log;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


@Log
public class AppTest {

    @Test
    public void testApp() {
        List<Database> run = new MainApplication().run();
        for (Database database : run){
            System.out.println("--"+database.getDatabase_name());
            for (Table table : database.getTables()){
                System.out.println("----------"+table.getTable_name());
            }
        }
    }

    @Test
    public void payloadText() {
        PayloadSchema payloadSchema = new PayloadSchema();
        PayloadTable payloadTable = new PayloadTable();
        PayloadColumn payloadColumn = new PayloadColumn();

        System.out.println(payloadSchema.getPayload("1'", 1));
        System.out.println(payloadTable.getPayload("1'",1,"asd"));
        System.out.println(payloadColumn.getPayload("1'", 1,"asd","das"));
    }
    @Test
    public void href() throws URISyntaxException, IOException {
        SpiderHandle spiderHandle = new SpiderHandle();
        List<String> urlList = spiderHandle.getUrlList("https://baidu.com");
        System.out.println(urlList);
    }
}






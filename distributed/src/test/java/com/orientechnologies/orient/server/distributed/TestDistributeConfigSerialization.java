package com.orientechnologies.orient.server.distributed;

import junit.framework.Assert;

import org.junit.Test;

import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.serialization.serializer.record.string.ORecordSerializerSchemaAware2CSV;
import com.orientechnologies.orient.core.sql.query.OSQLAsynchQuery;

public class TestDistributeConfigSerialization extends AbstractServerClusterTest {

  @Test
  public void test() throws Exception {
    init(2);
    execute();
  }

  @Override
  protected void executeTest() throws Exception {
    ODatabaseDocumentTx db = new ODatabaseDocumentTx("plocal:target/server0/databases/" + getDatabaseName());
    try {
      db.setSerializer(new ORecordSerializerSchemaAware2CSV());
      db.create();
      db.getMetadata().getSchema().createClass("TestMessaging");
      db.save(new ODocument("TestMessaging").field("test", "test"));
      db.query(new OSQLAsynchQuery("select from TestMessaging "));
      db.close();
    } catch (OException e) {
      e.printStackTrace();
      Assert.fail("error creating a csv database in distributed environment");
    }
  }

  protected String getDatabaseName() {
    return "testConfigSerialization";
  }
}

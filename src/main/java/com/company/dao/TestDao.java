package com.company.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import com.company.DBIProvider;
import com.company.model.Test;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.unstable.BindIn;

import java.util.List;

@UseStringTemplate3StatementLocator
@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class TestDao implements AbstractDao {

    @SqlQuery("SELECT nextval('test_seq')")
    abstract int getNextVal();

    @Transaction
    public int getSeqAndSkip(int step) {
        int id = getNextVal();
        DBIProvider.getDBI().useHandle(h -> h.execute("ALTER SEQUENCE test_seq RESTART WITH " + (id + step)));
        return id;
    }

    //   http://stackoverflow.com/questions/13223820/postgresql-delete-all-content
    @SqlUpdate("TRUNCATE test")
    @Override
    public abstract void clean();

    @SqlBatch("INSERT INTO test (id, field) VALUES (:id, :field)")
    public abstract int[] insertBatch(@BindBean List<Test> test, @BatchChunkSize int chunkSize);

    @SqlQuery("SELECT * FROM test")
    public abstract List<Test> getAll();

    @SqlQuery("SELECT * FROM test WHERE field in (<nameList>)")
    public abstract List<Test> getBatch(@BindIn("nameList") List<Integer> nameList);
}

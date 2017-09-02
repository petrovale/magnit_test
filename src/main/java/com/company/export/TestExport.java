package com.company.export;

import com.company.DBIProvider;
import com.company.dao.TestDao;
import com.company.model.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestExport {

    private TestDao testDao = DBIProvider.getDao(TestDao.class);
    private static final int NUMBER_THREADS = 4;
    private ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_THREADS);

    public String process(int countField, int chunkSize) throws Exception {
        testDao.clean();

        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                List<Future<int[]>> futures = new ArrayList<>();

                int id = testDao.getSeqAndSkip(chunkSize);

                List<Test> chunk = new ArrayList<>(chunkSize);

                for (int i = 1; i <= countField; i++) {
                    final Test test = new Test(id++, i);
                    chunk.add(test);
                    if (chunk.size() == chunkSize) {
                        futures.add(submit(chunk));
                        chunk = new ArrayList<>(chunkSize);
                        id = testDao.getSeqAndSkip(chunkSize);
                    }
                }
                if (!chunk.isEmpty()) {
                    futures.add(submit(chunk));
                }

                futures.forEach(f -> {
                    try {
                        f.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                System.out.println();
                executorService.shutdown();

                return "successfully";
            }

            private Future<int[]> submit(List<Test> chunk) {
                return executorService.submit(() -> testDao.insertBatch(chunk, chunk.size()));
            }
        }.call();
    }
}

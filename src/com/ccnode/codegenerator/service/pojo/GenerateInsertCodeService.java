//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.service.pojo;

import com.ccnode.codegenerator.database.DatabaseComponenent;
import com.ccnode.codegenerator.dialog.InsertDialogResult;
import com.ccnode.codegenerator.dialog.InsertFileProp;
import com.ccnode.codegenerator.dialog.InsertFileType;
import com.ccnode.codegenerator.genCode.GenDaoService;
import com.ccnode.codegenerator.genCode.GenMapperService;
import com.ccnode.codegenerator.genCode.GenServiceInterfaceService;
import com.ccnode.codegenerator.genCode.GenServiceService;
import com.ccnode.codegenerator.genCode.GenSqlService;
import com.ccnode.codegenerator.log.Log;
import com.ccnode.codegenerator.log.LogFactory;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class GenerateInsertCodeService {
    private static Log log = LogFactory.getLogger(GenerateInsertCodeService.class);

    public GenerateInsertCodeService() {
    }

    public static void generateInsert(InsertDialogResult insertDialogResult) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Map<InsertFileType, InsertFileProp> fileProps = insertDialogResult.getFileProps();
        ExecutorService executorService = Executors.newFixedThreadPool(fileProps.size());

        try {
            List<Future> futures = Lists.newArrayList();
            Iterator var5 = fileProps.keySet().iterator();

            label73:
            while(true) {
                if (!var5.hasNext()) {
                    var5 = futures.iterator();

                    while(true) {
                        if (!var5.hasNext()) {
                            break label73;
                        }

                        Future future = (Future)var5.next();

                        try {
                            future.get();
                        } catch (InterruptedException var13) {
                            throw new RuntimeException(var13);
                        } catch (ExecutionException var14) {
                            log.error("generate insert file catch exception", var14);
                            throw new RuntimeException(var14);
                        }
                    }
                }

                InsertFileType fileType = (InsertFileType)var5.next();
                Future<Void> future = executorService.submit(() -> {
                    generateFiles(fileType, fileProps, insertDialogResult);
                    return null;
                });
                futures.add(future);
            }
        } catch (Exception var15) {
            throw var15;
        } finally {
            executorService.shutdown();
        }

        log.info("generate files cost in milli seconds:" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " the use db is:" + DatabaseComponenent.currentDatabase());
    }

    private static void generateFiles(InsertFileType type, Map<InsertFileType, InsertFileProp> propMap, InsertDialogResult insertDialogResult) {
        switch(type) {
            case SQL:
                GenSqlService.generateSqlFile((InsertFileProp)propMap.get(type), insertDialogResult.getPropList(), insertDialogResult.getPrimaryProp(), insertDialogResult.getTableName(), insertDialogResult.getMultipleColumnIndex(), insertDialogResult.getMultipleColumnUnique());
                break;
            case DAO:
                GenDaoService.generateDaoFileUsingFtl((InsertFileProp)propMap.get(type), insertDialogResult.getSrcClass());
                break;
            case MAPPER_XML:
                GenMapperService.generateMapperXmlUsingFtl((InsertFileProp)propMap.get(type), insertDialogResult.getPropList(), insertDialogResult.getSrcClass(), (InsertFileProp)propMap.get(InsertFileType.DAO), insertDialogResult.getTableName(), insertDialogResult.getPrimaryProp());
                break;
            case SERVICE:
                GenServiceService.generateServiceUsingFtl((InsertFileProp)propMap.get(type), insertDialogResult.getSrcClass(), (InsertFileProp)propMap.get(InsertFileType.DAO), (InsertFileProp)propMap.get(InsertFileType.SERVICE_INTERFACE));
                break;
            case SERVICE_INTERFACE:
                GenServiceInterfaceService.generateServiceIntefaceUsingFtl((InsertFileProp)propMap.get(type), insertDialogResult.getSrcClass());
        }

    }
}

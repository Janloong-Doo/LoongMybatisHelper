//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.genmethodxml;

import com.ccnode.codegenerator.database.DatabaseComponenent;
import com.ccnode.codegenerator.dialog.GenerateResultMapDialog;
import com.ccnode.codegenerator.dialog.MethodExistDialog;
import com.ccnode.codegenerator.execute.MessageEnum;
import com.ccnode.codegenerator.execute.StopContextException;
import com.ccnode.codegenerator.methodnameparser.QueryParseDto;
import com.ccnode.codegenerator.methodnameparser.QueryParser;
import com.ccnode.codegenerator.methodnameparser.buidler.ParamInfo;
import com.ccnode.codegenerator.methodnameparser.buidler.QueryInfo;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.ParsedFind;
import com.ccnode.codegenerator.methodnameparser.tag.XmlTagAndInfo;
import com.ccnode.codegenerator.pojo.DomainClassInfo;
import com.ccnode.codegenerator.pojo.FieldToColumnRelation;
import com.ccnode.codegenerator.pojo.GeneratedMethodDTO;
import com.ccnode.codegenerator.pojo.MethodXmlPsiInfo;
import com.ccnode.codegenerator.util.DateUtil;
import com.ccnode.codegenerator.util.GenCodeUtil;
import com.ccnode.codegenerator.util.MyBatisPlusUtils;
import com.ccnode.codegenerator.util.MyPsiXmlUtils;
import com.ccnode.codegenerator.util.PsiClassUtil;
import com.ccnode.codegenerator.util.PsiDocumentUtils;
import com.ccnode.codegenerator.util.PsiElementUtil;
import com.ccnode.codegenerator.util.PsiSearchUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intellij.ide.util.ClassFilter;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.Icon;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GenMethodXmlInvoker {
    private List<String> methodNameList;
    private Project myProject;
    private PsiElement element;
    private TextRange myTextRange;

    public GenMethodXmlInvoker(List<String> methodName, Project project, TextRange textRange, PsiElement element) {
        this.methodNameList = methodName;
        this.element = element;
        this.myProject = project;
        this.myTextRange = textRange;
    }

    @Nullable
    public GenMethodResult invoke() {
        Module srcModule = ModuleUtilCore.findModuleForPsiElement(this.element);
        if (srcModule == null) {
            return null;
        } else {
            PsiClass srcClass = PsiElementUtil.getContainingClass(this.element);
            if (srcClass == null) {
                return null;
            } else {
                DomainClassInfo domainClassInfo = PsiClassUtil.getDomainClassInfo(srcClass);
                if (domainClassInfo != null && domainClassInfo.getDomainClass() != null) {
                    PsiClass pojoClass = domainClassInfo.getDomainClass();
                    String srcClassName = srcClass.getName();
                    XmlFile psixml = null;
                    List<XmlFile> xmlFiles = PsiSearchUtils.searchMapperXml(this.myProject, ModuleUtilCore.findModuleForPsiElement(this.element), srcClass.getQualifiedName());
                    if (xmlFiles.size() == 0) {
                        Messages.showErrorDialog("can't find xml file for namespace " + srcClassName, "xml file not found error");
                        return null;
                    } else {
                        if (xmlFiles.size() == 1) {
                            psixml = (XmlFile)xmlFiles.get(0);
                        }

                        List<String> props = PsiClassUtil.extractProps(pojoClass);
                        XmlTag rootTag = psixml.getRootTag();
                        if (rootTag == null) {
                            return null;
                        } else {
                            String tableName = MyPsiXmlUtils.findTableNameFromRootTag(rootTag);
                            if (tableName == null) {
                                tableName = MyBatisPlusUtils.extractTableNameByAnnotation(domainClassInfo);
                            }

                            if (StringUtils.isEmpty(tableName)) {
                                String s = Messages.showInputDialog(this.myProject, "can't find table name from your " + psixml.getName() + "\nplease add a correct insert method into the file\nlike\n'<insert id=\"insert\">\n        INSERT INTO user ....\n</insert>\nso we can extract the table name 'user' from it", "can't extract table name", (Icon)null);
                                if (!StringUtils.isNotBlank(s)) {
                                    return null;
                                }

                                tableName = s;
                            }

                            FieldToColumnRelation relation = MyPsiXmlUtils.findFieldToColumnRelation(rootTag, pojoClass.getQualifiedName(), props);
                            PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(this.myProject);
                            if (!relation.getHasFullRelation()) {
                                if (relation.getHasJavaTypeResultMap()) {
                                    Messages.showErrorDialog("please check with your resultMap\ndose it contain all the property of " + pojoClass.getQualifiedName() + "? ", "proprety in resultMap is not complete");
                                    return null;
                                }

                                GenerateResultMapDialog generateResultMapDialog = new GenerateResultMapDialog(this.myProject, props, pojoClass.getQualifiedName());
                                boolean b = generateResultMapDialog.showAndGet();
                                if (!b) {
                                    return null;
                                }

                                FieldToColumnRelation relation1 = generateResultMapDialog.getRelation();
                                MyPsiXmlUtils.buildAllColumnMap(this.myProject, psiDocumentManager.getDocument(psixml), rootTag, psiDocumentManager, relation1, pojoClass.getQualifiedName());
                                relation = MyPsiXmlUtils.convertToRelation(relation1);
                            }

                            String allColumnName = MyPsiXmlUtils.findAllColumnName(rootTag, relation.getFiledToColumnMap());
                            String newDaoTextBuider;
                            if (allColumnName == null) {
                                newDaoTextBuider = MyPsiXmlUtils.buildAllColumn(relation.getFiledToColumnMap());
                                XmlTag sql = rootTag.createChildTag("sql", "", newDaoTextBuider, false);
                                sql.setAttribute("id", "Base_Column_List");
                                rootTag.addSubTag(sql, true);
                                allColumnName = "Base_Column_List";
                            }

                            newDaoTextBuider = "";
                            List<XmlTag> newGeneratedTag = Lists.newArrayList();
                            Set<String> allImportList = Sets.newHashSet();
                            Document document = psiDocumentManager.getDocument(srcClass.getContainingFile());
                            MethodXmlPsiInfo methodInfo = new MethodXmlPsiInfo();
                            methodInfo.setTableName(tableName);
                            methodInfo.setRelation(relation);
                            methodInfo.setAllColumnName(allColumnName);
                            methodInfo.setTableName(tableName);
                            methodInfo.setPsiClassFullName(pojoClass.getQualifiedName());
                            methodInfo.setPsiClassName(pojoClass.getName());
                            methodInfo.setFieldMap(PsiClassUtil.buildFieldMapWithConvertPrimitiveType(pojoClass));
                            methodInfo.setProject(this.myProject);
                            methodInfo.setMybatisXmlFile(psixml);
                            methodInfo.setSrcClass(srcClass);
                            List<GeneratedMethodDTO> serviceMethods = Lists.newArrayList();
                            List<GeneratedMethodDTO> serviceInterfaceMethod = Lists.newArrayList();
                            TreeJavaClassDialogWithSkipButton chooser1;
                            PsiClass aClass2;
                            if (DatabaseComponenent.getProfile().getGenerateMethodInService()) {
                                chooser1 = new TreeJavaClassDialogWithSkipButton("choose serviceClass", this.myProject, GlobalSearchScope.projectScope(this.myProject), new ClassFilter() {
                                    public boolean isAccepted(PsiClass aClass) {
                                        return aClass.getParent() instanceof PsiFile && !aClass.isInterface();
                                    }
                                }, srcClass);
                                chooser1.selectDirectory(pojoClass.getContainingFile().getContainingDirectory());
                                chooser1.showDialog();
                                aClass2 = chooser1.getSelected();
                                methodInfo.setServiceClass(aClass2);
                            }

                            if (DatabaseComponenent.getProfile().getGenerateMethodInServiceInterface()) {
                                chooser1 = new TreeJavaClassDialogWithSkipButton("choose service interface Class", this.myProject, GlobalSearchScope.projectScope(this.myProject), new ClassFilter() {
                                    public boolean isAccepted(PsiClass aClass) {
                                        return aClass.getParent() instanceof PsiFile && aClass.isInterface();
                                    }
                                }, srcClass);
                                chooser1.selectDirectory(pojoClass.getContainingFile().getContainingDirectory());
                                chooser1.showDialog();
                                aClass2 = chooser1.getSelected();
                                methodInfo.setServiceInterfaceClass(aClass2);
                            }

                            Iterator var35 = this.methodNameList.iterator();

                            while(var35.hasNext()) {
                                String methodName = (String)var35.next();
                                methodInfo.setMethodName(methodName);
                                XmlTag existTag = MyPsiXmlUtils.methodAlreadyExist(psixml, methodInfo.getMethodName());
                                MethodExistDialog exist;
                                if (existTag != null) {
                                    exist = new MethodExistDialog(this.myProject, existTag.getText());
                                    boolean b = exist.showAndGet();
                                    if (!b) {
                                        return null;
                                    }

                                    WriteCommandAction.runWriteCommandAction(this.myProject, () -> {
                                        existTag.delete();
                                        PsiDocumentUtils.commitAndSaveDocument(psiDocumentManager, document);
                                    });
                                }

                                rootTag = psixml.getRootTag();
                                exist = null;

                                QueryParseDto parseDto;
                                try {
                                    parseDto = QueryParser.parse(props, methodInfo);
                                } catch (Exception var29) {
                                    if (var29 instanceof StopContextException) {
                                        StopContextException stopContextException = (StopContextException)var29;
                                        if (stopContextException.isShouldNotifyMessage()) {
                                            if (stopContextException.getMessageEnum().equals(MessageEnum.ERROR)) {
                                                Messages.showErrorDialog(this.myProject, stopContextException.getMessage(), "error");
                                            } else {
                                                Messages.showInfoMessage(this.myProject, stopContextException.getMessage(), "info");
                                            }
                                        }

                                        return null;
                                    }

                                    throw var29;
                                }

                                XmlTagAndInfo choosed = null;
                                if (parseDto == null) {
                                    Messages.showErrorDialog(this.myProject, "the text must start with find or delete or count or update", "parse error");
                                    return null;
                                }

                                String content;
                                if (!parseDto.getHasMatched()) {
                                    if (!parseDto.getDisplayErrorMsg()) {
                                        return null;
                                    }

                                    content = "";
                                    List<String> errorMsg = parseDto.getErrorMsg();

                                    for(int i = 0; i < errorMsg.size(); ++i) {
                                        content = content + (String)errorMsg.get(i) + "\n";
                                    }

                                    Messages.showErrorDialog(content, "can't parse the methodName");
                                    return null;
                                }

                                QueryInfo queryInfos = parseDto.getQueryInfo();
                                choosed = MyPsiXmlUtils.generateTag(rootTag, queryInfos, methodInfo.getMethodName());
                                content = this.generateAddedDaoStringForMethod(methodName, choosed);
                                newDaoTextBuider = newDaoTextBuider + content;
                                GeneratedMethodDTO servceInterfaceMethod;
                                if (methodInfo.getServiceClass() != null) {
                                    servceInterfaceMethod = this.generateServiceMethod(srcClassName, methodName, choosed);
                                    serviceMethods.add(servceInterfaceMethod);
                                }

                                if (methodInfo.getServiceInterfaceClass() != null) {
                                    servceInterfaceMethod = this.generateServiceInterfaceMethod(methodName, choosed);
                                    serviceInterfaceMethod.add(servceInterfaceMethod);
                                }

                                newGeneratedTag.add(choosed.getXmlTag());
                                Set<String> importList = choosed.getInfo().getImportList();
                                allImportList.addAll(importList);
                            }

                            WriteCommandAction.runWriteCommandAction(this.myProject, () -> {
                                document.replaceString(this.myTextRange.getStartOffset(), this.myTextRange.getEndOffset(), newDaoTextBuider);
                                PsiDocumentUtils.commitAndSaveDocument(psiDocumentManager, document);
                            });
                            XmlFile finalPsiXml = psixml;
                            Document xmlDocument = psiDocumentManager.getDocument(psixml);
                            Iterator var40 = newGeneratedTag.iterator();

                            while(var40.hasNext()) {
                                XmlTag tag = (XmlTag)var40.next();
                                WriteCommandAction.runWriteCommandAction(this.myProject, () -> {
                                    PsiDocumentUtils.commitAndSaveDocument(psiDocumentManager, xmlDocument);
                                    finalPsiXml.getRootTag().addSubTag(tag, false);
                                    PsiDocumentUtils.commitAndSaveDocument(psiDocumentManager, xmlDocument);
                                    XmlTag[] newSubTags = finalPsiXml.getRootTag().getSubTags();
                                    XmlTag newSubTag = newSubTags[newSubTags.length - 1];
                                    xmlDocument.insertString(newSubTag.getTextOffset(), "\n<!--auto generated by codehelper on " + DateUtil.formatLong(new Date()) + "-->\n" + "\t");
                                    PsiDocumentUtils.commitAndSaveDocument(psiDocumentManager, xmlDocument);
                                });
                            }

                            PsiFile psiFile = PsiDocumentManager.getInstance(this.myProject).getPsiFile(document);
                            if (psiFile != null && psiFile instanceof PsiJavaFile) {
                                PsiDocumentUtils.addImportToFile(psiDocumentManager, (PsiJavaFile)psiFile, document, allImportList);
                            }

                            if (methodInfo.getServiceClass() != null) {
                                PsiDocumentUtils.addMethodToClass(this.myProject, methodInfo.getServiceClass(), psiDocumentManager, serviceMethods, methodInfo.getServiceInterfaceClass() != null);
                            }

                            if (methodInfo.getServiceInterfaceClass() != null) {
                                PsiDocumentUtils.addMethodToClass(this.myProject, methodInfo.getServiceInterfaceClass(), psiDocumentManager, serviceInterfaceMethod, false);
                            }

                            XmlTag[] subTags = psixml.getRootTag().getSubTags();
                            if (subTags.length > 0) {
                                GenMethodResult result = new GenMethodResult();
                                result.setHasCursor(true);
                                result.setCursorFile(psixml);
                                result.setCursorElement(subTags[subTags.length - 1]);
                                return result;
                            } else {
                                return null;
                            }
                        }
                    }
                } else {
                    Messages.showErrorDialog("please provide an insert method with corresponding database class as parameter in this class\n like 'insert(User user)'\nwe need the 'User' class to parse your method", "can't find the class for the database table");
                    return null;
                }
            }
        }
    }

    @NotNull
    private GeneratedMethodDTO generateServiceInterfaceMethod(String methodName, XmlTagAndInfo choosed) {
        boolean pageQuery = checkIsPageQuery(choosed);
        Set<String> imports = new HashSet();
        imports.add(choosed.getInfo().getReturnClass());
        imports.add("java.util.List");
        if (pageQuery) {
            imports.add("com.github.pagehelper.Page");
        }

        String methodNext = "";

        for(int i = 0; i < choosed.getInfo().getParamInfos().size(); ++i) {
            ParamInfo info = (ParamInfo)choosed.getInfo().getParamInfos().get(i);
            methodNext = methodNext + info.getParamType() + " " + info.getParamValue();
            imports.add(info.getParamFullType());
            if (i != choosed.getInfo().getParamInfos().size() - 1) {
                methodNext = methodNext + ",";
            }
        }

        String methodReturnType = choosed.getInfo().getMethodReturnType();
        if (pageQuery) {
            if (choosed.getInfo().getParamInfos().size() > 0) {
                methodNext = methodNext + ", int page, int pageSize";
            } else {
                methodNext = methodNext + "int page, int pageSize";
            }

            methodReturnType = "Page<" + PsiClassUtil.extractLast(choosed.getInfo().getReturnClass()) + ">";
        }

        String methodText = "\n\n\t" + methodReturnType + " " + methodName + "(" + methodNext + ");";
        GeneratedMethodDTO var10000 = GeneratedMethodDTO.builder().methodText(methodText).imports(imports).build();
        if (var10000 == null) {
            $$$reportNull$$$0(0);
        }

        return var10000;
    }

    private static boolean checkIsPageQuery(XmlTagAndInfo choosed) {
        if (choosed.getInfo().getParseQuery() instanceof ParsedFind) {
            ParsedFind find = (ParsedFind)choosed.getInfo().getParseQuery();
            if (find.getWithPage()) {
                return true;
            }
        }

        return false;
    }

    @NotNull
    private GeneratedMethodDTO generateServiceMethod(String srcClassName, String methodName, XmlTagAndInfo choosed) {
        boolean pageQuery = checkIsPageQuery(choosed);
        Set<String> imports = new HashSet();
        imports.add(choosed.getInfo().getReturnClass());
        imports.add("java.util.List");
        String methodNext = "";
        String params = "";

        for(int i = 0; i < choosed.getInfo().getParamInfos().size(); ++i) {
            ParamInfo info = (ParamInfo)choosed.getInfo().getParamInfos().get(i);
            methodNext = methodNext + info.getParamType() + " " + info.getParamValue();
            params = params + info.getParamValue();
            imports.add(info.getParamFullType());
            if (i != choosed.getInfo().getParamInfos().size() - 1) {
                methodNext = methodNext + ",";
                params = params + ",";
            }
        }

        String methodReturnType = choosed.getInfo().getMethodReturnType();
        String methodBody = "";
        if (pageQuery) {
            imports.add("com.github.pagehelper.PageHelper");
            imports.add("com.github.pagehelper.Page");
            if (choosed.getInfo().getParamInfos().size() > 0) {
                methodNext = methodNext + ", int page, int pageSize";
            } else {
                methodNext = methodNext + "int page, int pageSize";
            }

            methodReturnType = "Page<" + PsiClassUtil.extractLast(choosed.getInfo().getReturnClass()) + ">";
            methodBody = "\t\tPageHelper.startPage(page, pageSize);\n\t\treturn (" + methodReturnType + ")" + GenCodeUtil.getLowerCamel(srcClassName) + "." + methodName + "(" + params + ");\n";
        } else {
            methodBody = "\t\t return " + GenCodeUtil.getLowerCamel(srcClassName) + "." + methodName + "(" + params + ");\n";
        }

        String methodText = "\tpublic " + methodReturnType + " " + methodName + "(" + methodNext + "){\n" + methodBody + "\t}\n\n";
        GeneratedMethodDTO var10000 = GeneratedMethodDTO.builder().methodText(methodText).imports(imports).build();
        if (var10000 == null) {
            $$$reportNull$$$0(1);
        }

        return var10000;
    }

    @NotNull
    private String generateAddedDaoStringForMethod(String methodName, XmlTagAndInfo choosed) {
        String insertNext = "(";
        if (choosed.getInfo().getParamInfos() != null) {
            for(int i = 0; i < choosed.getInfo().getParamInfos().size(); ++i) {
                ParamInfo info = (ParamInfo)choosed.getInfo().getParamInfos().get(i);
                insertNext = insertNext + "@Param(\"" + info.getParamAnno() + "\")" + info.getParamType() + " " + info.getParamValue();
                if (i != choosed.getInfo().getParamInfos().size() - 1) {
                    insertNext = insertNext + ",";
                }
            }
        }

        insertNext = insertNext + ");";
        String var10000 = choosed.getInfo().getMethodReturnType() + " " + methodName + insertNext + "\n\n\t";
        if (var10000 == null) {
            $$$reportNull$$$0(2);
        }

        return var10000;
    }
}

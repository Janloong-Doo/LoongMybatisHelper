//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser;

import com.ccnode.codegenerator.methodnameparser.buidler.QueryBuilder;
import com.ccnode.codegenerator.methodnameparser.parsedresult.count.ParsedCountDto;
import com.ccnode.codegenerator.methodnameparser.parsedresult.delete.ParsedDeleteDto;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.ParsedFindDto;
import com.ccnode.codegenerator.methodnameparser.parsedresult.update.ParsedUpdateDto;
import com.ccnode.codegenerator.methodnameparser.parser.CountParser;
import com.ccnode.codegenerator.methodnameparser.parser.DeleteParser;
import com.ccnode.codegenerator.methodnameparser.parser.FindParser;
import com.ccnode.codegenerator.methodnameparser.parser.UpdateParser;
import com.ccnode.codegenerator.pojo.MethodXmlPsiInfo;
import java.util.List;

public class QueryParser {
    public QueryParser() {
    }

    public static QueryParseDto parse(List<String> props, MethodXmlPsiInfo info) {
        String methodLower = info.getMethodName().toLowerCase();
        if (methodLower.startsWith("find")) {
            ParsedFindDto parse = (new FindParser(methodLower, props)).parse();
            return QueryBuilder.buildFindResult(parse.getParsedFinds(), parse.getParsedFindErrors(), info);
        } else if (methodLower.startsWith("update")) {
            ParsedUpdateDto dto = (new UpdateParser(methodLower, props)).parse();
            return QueryBuilder.buildUpdateResult(dto.getUpdateList(), dto.getErrorList(), info);
        } else if (methodLower.startsWith("delete")) {
            ParsedDeleteDto parse = (new DeleteParser(methodLower, props)).parse();
            return QueryBuilder.buildDeleteResult(parse.getParsedDeletes(), parse.getErrors(), info);
        } else if (methodLower.startsWith("count")) {
            ParsedCountDto parse = (new CountParser(methodLower, props)).parse();
            return QueryBuilder.buildCountResult(parse.getParsedCounts(), parse.getErrors(), info);
        } else {
            return null;
        }
    }
}

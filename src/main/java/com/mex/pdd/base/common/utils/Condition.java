package com.mex.pdd.base.common.utils;

import com.mex.pdd.base.common.exception.RRException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 列表页面搜索
 */
public class Condition {
    private String groupOp;
    private List<Rule> rules;

    public Condition dealConditon() {
        if (StringUtils.isEmpty(groupOp) || CollectionUtils.isEmpty(rules)) {
            throw new RRException("列表参数有误");
        }
//        setGroupOp("OR");
        List<Rule> res = rules.stream().filter(rule -> !StringUtils.isEmpty(rule.getData()))
                .filter(rule -> !StringUtils.isEmpty(rule.getField())).peek(rule -> {
                    OPMapper op = OPMapper.lookupOP(rule.getOp());
                    switch (op) {
                        case like:
                            rule.setOp(OPMapper.like.value);
                            rule.setData("%" + rule.getData() + "%");
                            break;
                        case eq:
                            rule.setOp(OPMapper.eq.value);
                            rule.setData(rule.getData());
                            break;
                        case le:
                            rule.setOp(OPMapper.le.value);
                            rule.setData(rule.getData());
                            break;
                        case ge:
                            rule.setOp(OPMapper.ge.value);
                            rule.setData(rule.getData());
                            break;
                        case in:
                            rule.setOp(OPMapper.in.value);
//                            rule.setData("(" + rule.getData() + ")");
                            rule.setData("ios_11");
                            break;
                        default:
                            rule.setOp(OPMapper.like.value);
                            rule.setData("%" + rule.getData() + "%");
                            break;
                    }
                }).collect(Collectors.toList());
        setRules(res);
        return this;
    }

    public String generateCondition() {
        StringBuilder condition = new StringBuilder("");
        if (CollectionUtils.isNotEmpty(rules)) {
            rules.forEach(rule -> {
                if (!StringUtils.isEmpty(rule.getData())) {
                    OPMapper op = OPMapper.lookupOP(rule.getOp());
                    condition.append(" ").append(groupOp).append(" ").append(rule.getField()).append(" ").append(op.value).append(" ");
                    switch (op) {
                        case like:
                            condition.append("'%").append(rule.getData()).append("%'");
                            break;
                        default:
                            condition.append("'").append(rule.getData()).append("'");
                            break;
                    }
                }
            });
        }
        String result = condition.toString();
        if (!StringUtils.isEmpty(result)) {
            result = Stream.of(" (", result, " ) ").collect(Collectors.joining());
        }
        return result.replaceFirst(groupOp, "");
    }

//    public static void main(String[] args) {
//        String condition = "{\"groupOp\":\"OR\",\"rules\":[{\"field\":\"a.id\",\"op\":\"like\",\"data\":\"1\"},{\"field\":\"a.pIdNameList\",\"op\":\"eq\",\"data\":\"1\"},{\"field\":\"adver.pIdNameList\",\"op\":\"eq\",\"data\":\"1\"}]}";
////        String condition = "{\"groupOp\":\"OR\",\"rules\":[{\"field\":\"a.id\",\"op\":\"like\",\"data\":\"\"},{\"field\":\"a.pIdNameList\",\"op\":\"eq\",\"data\":\"\"},{\"field\":\"adver.pIdNameList\",\"op\":\"eq\",\"data\":\"\"}]}";
//        PageCondition pageCondition = JSON.parseObject(condition, PageCondition.class);
//        System.out.println(pageCondition.generateCondition());
//    }

    public static class Rule {
        private String field;
        private String op;
        private String data;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getOp() {
            return op;
        }

        public void setOp(String op) {
            this.op = op;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public enum OPMapper {
        like("LIKE"), eq("="), ne("<>"), le("<="), lt("<"), gt(">"), ge(">="), in("in");
        public String value;

        public static final Map<String, OPMapper> lookupTable;

        static {
            lookupTable = Arrays.stream(values()).collect(Collectors.toMap(OPMapper::name, o -> o));
        }

        OPMapper(String value) {
            this.value = value;
        }

        public static String lookup(String name) {
            return lookupTable.get(name).value;
        }

        public static String lookup(OPMapper opMapper) {
            return lookup(opMapper.name());
        }

        public static OPMapper lookupOP(String name) {
            return lookupTable.get(name);
        }
    }

    public String getGroupOp() {
        return groupOp;
    }

    public void setGroupOp(String groupOp) {
        this.groupOp = groupOp;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public void addRules(List<Rule> addtionalRules) {
        rules.addAll(addtionalRules);
    }

    public void addRule(Rule r) {
        rules.add(r);
    }
}

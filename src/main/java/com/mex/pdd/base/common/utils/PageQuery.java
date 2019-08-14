package com.mex.pdd.base.common.utils;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mex.pdd.base.common.entity.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 查询参数
 *
 * @author david
 */
@ApiModel("列表参数")
@Data
@Accessors(chain = true)
public class PageQuery<T> extends BaseBean {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("当前页码")
    private int page = 1;
    @ApiModelProperty("每页条数")
    private int limit = 10;
    @ApiModelProperty("排序字段")
    private String sidx;
    @ApiModelProperty("排序方式")
    private Sort order;
    @ApiModelProperty("过滤条件")
    private PageCondition<T> pageCondition = new PageCondition<>();

    @Data
    @ApiModel("过滤条件")
    @Accessors(chain = true)
    public static class PageCondition<T> {
        @ApiModelProperty("各项规则之前的逻辑")
        private Logic groupOp = Logic.AND;
        @ApiModelProperty("搜索规则")
        private List<Rule> rules = Lists.newArrayList();

        public EntityWrapper<T> buildWrapper() {
            EntityWrapper<T> wrapper = new EntityWrapper<>();
            Logic logic = getGroupOp();
            List<Rule> rules = getRules();
            for (int i = 0; i < rules.size(); i++) {
                Rule rule = rules.get(i);
                String field = rule.getField();
                Object data = rule.getData();
                if (!ImmutableList.of(Op.isNull, Op.notNull).contains(rule.getOp()) && (Objects.isNull(data)
                        || (data instanceof String && StringUtils.isBlank((String) data))
                        || (data instanceof JSONArray && ((JSONArray) data).isEmpty()))) continue;
                if (i > 0) {
                    logic.buildWrapper(wrapper);
                }
                rule.getOp().buildWrapper(wrapper, field, data);
            }
            return wrapper;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @ApiModel("搜索规则")
    @Accessors(chain = true)
    @Data
    public static class Rule extends BaseBean {
        @ApiModelProperty("字段")
        private String field;
        @ApiModelProperty("op")
        private Op op;
        @ApiModelProperty("数据")
        private Object data;
    }

    public int getOffset() {
        return page * limit;
    }

    public Wrapper<T> buildWrapper() {
        EntityWrapper<T> wrapper = new EntityWrapper<>();
        if (Objects.nonNull(pageCondition) && CollectionUtils.isNotEmpty(pageCondition.getRules()))
            wrapper = pageCondition.buildWrapper();
        wrapper.isWhere(true);
        return addSort(wrapper);
    }

    public Wrapper<T> addSort(Wrapper<T> wrapper) {
        if (Objects.nonNull(this.order) && StringUtils.isNotBlank(sidx)) {
            wrapper.orderBy(sidx, !Objects.equals(Sort.DESC, order));
        } else {
            wrapper.orderBy("id", false);
        }
        return wrapper;
    }

    public enum Sort {
        ASC, DESC
    }

    public enum Logic {
        AND {
            @Override
            <T> Wrapper<T> buildWrapper(Wrapper<T> wrapper) {
                return wrapper.and();
            }
        }, OR {
            @Override
            <T> Wrapper<T> buildWrapper(Wrapper<T> wrapper) {
                return wrapper.or();
            }
        };

        abstract <T> Wrapper<T> buildWrapper(Wrapper<T> wrapper);
    }

    public enum Op {
        like("like") {
            @Override
            <T> Wrapper<T> buildWrapper(Wrapper<T> wrapper, String field, Object data) {
                return wrapper.like(field, String.valueOf(data));
            }
        }, eq("=") {
            @Override
            <T> Wrapper<T> buildWrapper(Wrapper<T> wrapper, String field, Object data) {
                return wrapper.eq(field, data);
            }
        }, ne("<>") {
            @Override
            <T> Wrapper<T> buildWrapper(Wrapper<T> wrapper, String field, Object data) {
                return wrapper.ne(field, data);
            }
        }, le("<=") {
            @Override
            <T> Wrapper<T> buildWrapper(Wrapper<T> wrapper, String field, Object data) {
                return wrapper.le(field, data);
            }
        }, lt("<") {
            @Override
            <T> Wrapper<T> buildWrapper(Wrapper<T> wrapper, String field, Object data) {
                return wrapper.lt(field, data);
            }
        },
        gt(">") {
            @Override
            <T> Wrapper<T> buildWrapper(Wrapper<T> wrapper, String field, Object data) {
                return wrapper.gt(field, data);
            }
        }, ge(">=") {
            @Override
            <T> Wrapper<T> buildWrapper(Wrapper<T> wrapper, String field, Object data) {
                return wrapper.ge(field, data);
            }
        }, in("in") {
            @Override
            <T> Wrapper<T> buildWrapper(Wrapper<T> wrapper, String field, Object data) {
                if (data instanceof JSONArray) {
                    List<Object> list = new ArrayList<>(((JSONArray) data));
                    return wrapper.in(field, list);
                } else if (data instanceof Collection) {
                    return wrapper.in(field, (Collection) data);
                } else return wrapper;
            }
        }, isNull("isNull") {
            @Override
            <T> Wrapper<T> buildWrapper(Wrapper<T> wrapper, String field, Object data) {
                return wrapper.eq(field, "");
            }
        }, notNull("notNull") {
            @Override
            <T> Wrapper<T> buildWrapper(Wrapper<T> wrapper, String field, Object data) {
                return wrapper.isNotNull(field).and().ne(field, "");
            }
        };
        public String value;

        public static final Map<String, Op> lookupTable;

        static {
            lookupTable = Arrays.stream(values()).collect(Collectors.toMap(Op::name, o -> o));
        }

        Op(String value) {
            this.value = value;
        }

        public static String lookup(String name) {
            return lookupTable.get(name).value;
        }

        public static String lookup(Op opMapper) {
            return lookup(opMapper.name());
        }

        public static Op lookupOP(String name) {
            return lookupTable.get(name);
        }

        abstract <T> Wrapper<T> buildWrapper(Wrapper<T> wrapper, String field, Object data);
    }
}

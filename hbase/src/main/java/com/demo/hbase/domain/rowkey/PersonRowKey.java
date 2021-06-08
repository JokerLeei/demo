package com.demo.hbase.domain.rowkey;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: lijiawei04
 * @date: 2021/5/24 7:31 下午
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PersonRowKey implements RowKey {

    private Long userId;

    @Override
    public String parse() {
        return String.valueOf(userId);
    }

    @Override
    public PersonRowKey from(String rowKey) {
        if (StringUtils.isBlank(rowKey)) {
            return null;
        }
        String[] split = rowKey.split(getSeparator());
        return new PersonRowKey(Long.parseLong(split[0]));
    }

}

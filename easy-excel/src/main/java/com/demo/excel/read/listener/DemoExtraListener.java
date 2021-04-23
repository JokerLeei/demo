package com.demo.excel.read.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson.JSON;
import com.demo.excel.read.data.DemoExtraData;

import lombok.extern.slf4j.Slf4j;

/**
 * 读取单元格的批注
 *
 * @author: lijiawei04
 * @date: 2021/2/26 5:49 下午
 */
@Slf4j
public class DemoExtraListener extends AnalysisEventListener<DemoExtraData> {

    @Override
    public void invoke(DemoExtraData data, AnalysisContext context) {
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info("读取到了一条额外信息:{}", JSON.toJSONString(extra));
        switch (extra.getType()) {
            case COMMENT:
                log.info("额外信息是批注,在rowIndex:{},columnIndex:{},内容是:{}", extra.getRowIndex(), extra.getColumnIndex(), extra.getText());
                break;
            case HYPERLINK:
                log.info("额外信息是超链接,在rowIndex:{},columnIndex:{},内容是:{}", extra.getRowIndex(), extra.getColumnIndex(), extra.getText());
                break;
            case MERGE:
                log.info("额外信息是合并单元格,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex:{},lastRowIndex:{},lastColumnIndex:{}",
                        extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(), extra.getLastColumnIndex());
                break;
            default:
        }
    }
}
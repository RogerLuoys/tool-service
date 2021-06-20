package com.luoys.upgrade.toolservice.controller.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.controller.vo.HttpRequest;
import com.luoys.upgrade.toolservice.controller.vo.ParamVO;
import com.luoys.upgrade.toolservice.controller.vo.ToolDetailVO;
import com.luoys.upgrade.toolservice.dao.po.ToolPO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransformTool {

    public static ToolDetailVO transformPO2VO(ToolPO po) {
        if (po == null) {
            return null;
        }
        ToolDetailVO vo = new ToolDetailVO();
        vo.setDescription(po.getDescription());
        vo.setToolId(po.getToolId());
        vo.setStatus(1);
        vo.setTitle(po.getTitle());
        vo.setType(po.getType());
        vo.setPermission(po.getPermission());
        // {"name":"name1", "value":""};{"name":"name2", "value":"value1"}
        vo.setParamList(toParamVO(po));
        // 模板转换
        switch (po.getType()) {
            case 1:
                vo.setSqlList(toSql(po));
                break;
            case 2:
                vo.setHttpRequest(toHttpRequest(po));
            case 3:
                vo.setRpcProvider(po.getTemplate());
        }
//        vo.setHttpType();
//        vo.setHttpHeaderList();
        // httpURL={""};
//        vo.setHttpURL();
        // {"name":"header","value":"http://"}
//        vo.setHttpHeaderList();
        // {"name":"sql", "value":"select"} &&
        return vo;
    }

    private static List<ParamVO> toParamVO(ToolPO po){
        String[] params = po.getParams().split(";");
        List<ParamVO> paramVOList = new ArrayList<>();
        for (String param : params) {
            paramVOList.add((ParamVO) JSON.parse(param));
        }
        return paramVOList;
    }
    private static HttpRequest toHttpRequest(ToolPO po) {
        return (HttpRequest) JSON.parse(po.getTemplate());
    }
    private static List<String> toSql(ToolPO po) {
        return Arrays.asList(po.getTemplate().split(" &&& "));
    }
}

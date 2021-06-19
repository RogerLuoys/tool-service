package com.luoys.upgrade.toolservice.controller.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.controller.vo.ParamVO;
import com.luoys.upgrade.toolservice.controller.vo.ToolDetailVO;
import com.luoys.upgrade.toolservice.dao.po.ToolPO;

public class TransformTool {

    public static ToolDetailVO transformpo2vo(ToolPO po) {
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
        vo.setParamList();
        // 模板转换
        vo.setHttpBody();
        vo.setHttpType();
        vo.setHttpHeaderList();
        // httpURL={""};
        vo.setHttpURL();
        // {"name":"header","value":"http://"}
        vo.setHttpHeaderList();
        // {"name":"sql", "value":"select"} &&
        vo.setSqlList();
        vo.setRpcProvider();
        return vo;
    }

    void test(ToolPO po){
        String[] params = po.getParams().split(";");
        for (String param : params) {
            ParamVO paramVO = (ParamVO) JSON.parse(param);
        }
    }
}

package com.luoys.upgrade.toolservice.service.common;

import com.luoys.upgrade.toolservice.dao.po.AutoStepPO;
import com.luoys.upgrade.toolservice.service.client.DBClient;
import com.luoys.upgrade.toolservice.service.client.UIClient;
import com.luoys.upgrade.toolservice.service.dto.JdbcDTO;
import com.luoys.upgrade.toolservice.service.dto.UiDTO;

/**
 * 步骤执行器，参考外观模式
 */
public class StepExecutor {

    private DBClient sql = new DBClient();
    private UIClient ui = new UIClient();

    public void init() {};

    public String execute(AutoStepPO autoStepPO) {
        String varName;
        return sql.execute(autoStepPO);
    }
}

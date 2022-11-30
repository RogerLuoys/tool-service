package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.dao.po.AutoStepPO;
import com.luoys.upgrade.toolservice.web.vo.AutoStepVO;

/**
 * 步骤执行器，参考外观模式
 */
public class StepExecutor {

    private DBClient sql = new DBClient();
    private UIClient ui = new UIClient();
    private HTTPClient http = new HTTPClient();
    private RPCClient rpc = new RPCClient();
    private UtilClient util = new UtilClient();
    private AssertionClient assertion = new AssertionClient(ui);

    public void init() {
        ui.initChromeWeb();
    };

    public void close() {
        ui.quit();
    }

    public String execute(AutoStepPO autoStepPO) {
        String varName;
        return sql.execute(autoStepPO);
    }


    public String execute(AutoStepVO autoStepVO) {
        String varName;
//        return sql.execute(autoStepPO);
        return null;
    }
}

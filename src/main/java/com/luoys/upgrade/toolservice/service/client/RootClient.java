package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.service.dto.CaseDTO;
import com.luoys.upgrade.toolservice.service.enums.ConfigTypeEnum;

public class RootClient {

    public SqlClient sql = new SqlClient();
    public UiClient ui = new UiClient();
    public HttpClient http = new HttpClient();
    public RpcClient rpc = new RpcClient();
    public UtilClient util = new UtilClient();
    public AssertionClient assertion = new AssertionClient(ui);
    public Boolean isBeforeSuiteDone = false;
    public Boolean suiteError = false;

    public RootClient(CaseDTO caseDTO) {
        // 非UI自动化，不用额外初始化
        if (caseDTO.getUiType() == null) {
            return;
        }
        // 是UI自动化，需要初始化对应的webdriver
        switch (ConfigTypeEnum.fromCode(caseDTO.getUiType())) {
            case CHROME:
                ui.initChrome(caseDTO.getUiArgument());
                break;
            case FIREFOX:
                // todo init
                break;
            case ANDROID:
                ui.initAndroid();
                break;
        }
    }
}

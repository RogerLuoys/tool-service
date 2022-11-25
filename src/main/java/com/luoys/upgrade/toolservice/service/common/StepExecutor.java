package com.luoys.upgrade.toolservice.service.common;

import com.luoys.upgrade.toolservice.service.client.Client;
import com.luoys.upgrade.toolservice.service.client.DBClient;
import com.luoys.upgrade.toolservice.service.dto.JdbcDTO;

/**
 * 步骤执行器，参考外观模式
 */
public class StepExecutor {

    private Client<JdbcDTO> sql = new DBClient();

    public String executeSql(JdbcDTO jdbcDTO) {
        String varName;
        return sql.execute(jdbcDTO);
    }
}

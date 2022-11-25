package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.service.dto.JdbcDTO;

import java.util.ArrayList;

/**
 * 步骤执行器，参考外观模式
 */
public class RootClient {

    private Client<JdbcDTO> sql = new DBClient();

    public String executeSql(JdbcDTO jdbcDTO) {
        String varName;
        return sql.execute(jdbcDTO);
    }
}

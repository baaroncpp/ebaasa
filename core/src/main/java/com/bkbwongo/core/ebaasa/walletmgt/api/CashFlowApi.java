package com.bkbwongo.core.ebaasa.walletmgt.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bkaaron
 * @created on 15/06/2021
 * @project ebaasa-sms
 */
@Tag(name = "Cash flow", description = "Manage moving cash between accounts")
@RestController
@RequestMapping("/api")
public class CashFlowApi {
}

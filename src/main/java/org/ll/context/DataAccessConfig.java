package org.ll.context;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:transaction-context.xml")
public class DataAccessConfig {

}

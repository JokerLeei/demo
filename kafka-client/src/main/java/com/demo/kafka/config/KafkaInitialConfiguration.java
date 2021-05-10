package com.demo.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 不手动创建topic，在执行代码kafkaTemplate.send("topic1", normalMessage)发送消息时，kafka会帮我们自动完成topic的创建工作，
 * 但这种情况下创建的topic默认只有一个分区，分区也没有副本。所以，我们可以在项目中新建一个配置类专门用来初始化topic
 *
 * @author: lijiawei04
 * @date: 2021/4/29 5:08 下午
 */
//@Configuration
public class KafkaInitialConfiguration {

    /**
     * 创建一个名为testtopic的Topic并设置分区数为8，分区副本数为2
     * 第三个参数，分区副本数必须>=broker数
     */
    @Bean
    public NewTopic initialTopic() {
        return new NewTopic("test-topic", 8, (short) 1);
    }

    /**
     * 如果要修改分区数，只需修改配置值重启项目即可
     * 修改分区数并不会导致数据的丢失，但是分区数只能增大不能减小
     */
    @Bean
    public NewTopic updateTopic() {
        return new NewTopic("test-topic", 10, (short) 1);
    }
}
package com.flinkpump.model;

import com.flinkpump.codegen.JaninoUtils;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
@Builder(toBuilder = true)
public class DynamicProducerRule implements Evaluable {

    private String condition;

    private String targetTopic;

    private Evaluable evaluable;

    public void init(Long id) {
        try {
            Class<Evaluable> clazz = JaninoUtils.genCodeAndGetClazz(id, targetTopic, condition);
            this.evaluable = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean eval(ClientLogSource clientLogSource) {
        return this.evaluable.eval(clientLogSource);
    }

    public static void main(String[] args) throws Exception {
        String condition = "String.valueOf(sourceModel.getId())==\"1\"";

        DynamicProducerRule dynamicProducerRule = DynamicProducerRule
                .builder()
                .condition(condition)
                .targetTopic("t")
                .build();

        dynamicProducerRule.init(1L);

        boolean b = dynamicProducerRule.eval(ClientLogSource.builder().id(1).build());

        System.out.println();
    }

}

package io.jus.hopegaarden.util;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

/*
    Mock 객체 주입시 사용

    JpaMetamodelMappingContext 주입
        - JPA에서 엔티티의 메타데이터를 Mock으로 대체
        - BaseEntity를 사용해 자동으로 시간을 넣을 예정이므로 이를 대체하기 위해 주입
 */
@MockBean(JpaMetamodelMappingContext.class)
public class MockBeanInjection {

//    @MockBean
//    protected Service service;

}

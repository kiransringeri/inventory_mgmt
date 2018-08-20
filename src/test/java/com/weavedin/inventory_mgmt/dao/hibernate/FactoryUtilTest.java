package com.weavedin.inventory_mgmt.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class FactoryUtilTest {

  @Test
  void Should_CreateSessionFactory() {
    SessionFactory factory = FactoryUtil.getSessionFactory();
    assertNotNull(factory);
  }
}

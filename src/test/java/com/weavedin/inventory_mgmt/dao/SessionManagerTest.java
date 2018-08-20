package com.weavedin.inventory_mgmt.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

class SessionManagerTest {

  @Test
  void Should_GetSession_When_getSessionIsInvoked() {
    Session session = null;
    try {
      session = SessionManager.getSession();
      assertNotNull(session);
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }
}

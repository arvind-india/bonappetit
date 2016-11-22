CREATE TABLE ITEM_ORDER (
  ITEM_ORDER_ID   BIGINT       NOT NULL AUTO_INCREMENT,
  DELIVER_TO      VARCHAR(255) NOT NULL,
  NOTE            VARCHAR(255),
  ORDER_TIME      TIMESTAMP    NOT NULL,
  STATUS          INTEGER      NOT NULL,
  ITEM_ID         BIGINT       NOT NULL,
  STAFF_MEMBER_ID BIGINT       NOT NULL,
  PRIMARY KEY (ITEM_ORDER_ID),
  FOREIGN KEY (ITEM_ID) REFERENCES ITEM (ITEM_ID),
  FOREIGN KEY (STAFF_MEMBER_ID) REFERENCES STAFF_MEMBER (STAFF_MEMBER_ID)
);

CREATE TABLE OPTION_ORDER (
  OPTION_ORDER_TYPE VARCHAR(255) NOT NULL,
  OPTION_ORDER_ID   BIGINT      NOT NULL AUTO_INCREMENT,
  VALUE             INTEGER,
  CHECKED           BOOLEAN,
  OPTION_ID         BIGINT,
  RADIO_ITEM_ID     BIGINT,
  ITEM_ORDER_ID     BIGINT,
  PRIMARY KEY (OPTION_ORDER_ID),
  FOREIGN KEY (ITEM_ORDER_ID) REFERENCES ITEM_ORDER (ITEM_ORDER_ID),
  FOREIGN KEY (OPTION_ID) REFERENCES ITEM_OPTION (OPTION_ID),
  FOREIGN KEY (RADIO_ITEM_ID) REFERENCES RADIO_ITEM (RADIO_ITEM_ID)
);

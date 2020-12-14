
CREATE SEQUENCE user_account_id_seq;

CREATE TABLE USER_ACCOUNT (
                USER_ID INTEGER NOT NULL DEFAULT nextval('user_account_id_seq'),
                EMAIL VARCHAR(320) NOT NULL,
                NICKNAME VARCHAR(25) NOT NULL,
                PASSWORD VARCHAR(25) NOT NULL,
                APPLICATION_BALANCE NUMERIC NOT NULL,
                CONSTRAINT user_account_pk PRIMARY KEY (USER_ID)
);


ALTER SEQUENCE user_account_id_seq OWNED BY USER_ACCOUNT.USER_ID;

CREATE UNIQUE INDEX user_idx
 ON USER_ACCOUNT
 ( EMAIL ASC );

CLUSTER user_idx ON USER_ACCOUNT;

CREATE UNIQUE INDEX useridentification_idx
 ON USER_ACCOUNT
 ( EMAIL ASC );

CLUSTER useridentification_idx ON USER_ACCOUNT;

CREATE TABLE FRIENDS_NETWORK (
                USER_ID INTEGER NOT NULL,
                FRIEND_ID INTEGER NOT NULL,
                CONSTRAINT friends_network_pk PRIMARY KEY (USER_ID, FRIEND_ID)
);






CREATE SEQUENCE credit_bank_details_id_seq;

CREATE TABLE CREDIT_BANK_DETAILS (
                CREDIT_ID INTEGER NOT NULL DEFAULT nextval('credit_bank_details_id_seq'),
                USER_ID INTEGER NOT NULL,
                HOLDER_NAME VARCHAR(105) NOT NULL,
                IBAN VARCHAR(27) NOT NULL,
                BIC VARCHAR(11) NOT NULL,
                CONSTRAINT credit_bank_details_pk PRIMARY KEY (CREDIT_ID)
);

ALTER SEQUENCE credit_bank_details_id_seq OWNED BY CREDIT_BANK_DETAILS.CREDIT_ID;

CREATE INDEX creditbankdetails_idx
 ON CREDIT_BANK_DETAILS
 ( USER_ID ASC );

CLUSTER creditbankdetails_idx ON CREDIT_BANK_DETAILS;








CREATE SEQUENCE payment_transaction_id_seq;

CREATE TABLE PAYMENT_TRANSACTION (
                PAYMENT_ID INTEGER NOT NULL DEFAULT nextval('payment_transaction_id_seq'),
                ISSUER_ID INTEGER NOT NULL,
                RECIPIENT_ID INTEGER NOT NULL,
                DESCRIPTION VARCHAR(120),
                AMOUNT NUMERIC NOT NULL,
                CONSTRAINT payment_transaction_pk PRIMARY KEY (PAYMENT_ID)
);


ALTER SEQUENCE payment_transaction_id_seq OWNED BY PAYMENT_TRANSACTION.PAYMENT_ID;

CREATE INDEX transaction_idx
 ON PAYMENT_TRANSACTION
 ( ISSUER_ID ASC, RECIPIENT_ID ASC );

CLUSTER transaction_idx ON PAYMENT_TRANSACTION;








CREATE SEQUENCE debit_bank_details_id_seq;

CREATE TABLE DEBIT_BANK_DETAILS (
                DEBIT_ID INTEGER NOT NULL DEFAULT nextval('debit_bank_details_id_seq'),
                USER_ID INTEGER NOT NULL,
                HOLDER_NAME VARCHAR(105) NOT NULL,
                CARD_NUMBER NUMERIC(16) NOT NULL,
                EXPIRATION_DATE NUMERIC(4) NOT NULL,
                CVV NUMERIC(3) NOT NULL,
                CONSTRAINT debit_bank_details_pk PRIMARY KEY (DEBIT_ID)
);


ALTER SEQUENCE debit_bank_details_id_seq OWNED BY DEBIT_BANK_DETAILS.DEBIT_ID;

CREATE INDEX holderbankaccount_idx
 ON DEBIT_BANK_DETAILS
 ( USER_ID ASC );

CLUSTER holderbankaccount_idx ON DEBIT_BANK_DETAILS;








CREATE SEQUENCE transfer_transaction_id_seq;

CREATE TABLE TRANSFER_TRANSACTION (
                TRANSFER_ID INTEGER NOT NULL DEFAULT nextval('transfer_transaction_id_seq'),
                USER_ID INTEGER NOT NULL,
                CREDIT_ID INTEGER DEFAULT NULL,
                DEBIT_ID INTEGER DEFAULT NULL,
                AMOUNT NUMERIC NOT NULL,
                CREDIT BOOLEAN DEFAULT true NOT NULL,
                CONSTRAINT transfer_transaction_pk PRIMARY KEY (TRANSFER_ID)
);


ALTER SEQUENCE transfer_transaction_id_seq OWNED BY TRANSFER_TRANSACTION.TRANSFER_ID;

CREATE INDEX new_tabletransfertransaction_idx
 ON TRANSFER_TRANSACTION
 ( USER_ID ASC );

CLUSTER new_tabletransfertransaction_idx ON TRANSFER_TRANSACTION;

ALTER TABLE DEBIT_BANK_DETAILS ADD CONSTRAINT user_bankdetails_fk
FOREIGN KEY (USER_ID)
REFERENCES USER_ACCOUNT (USER_ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE PAYMENT_TRANSACTION ADD CONSTRAINT issuer_transaction_fk
FOREIGN KEY (ISSUER_ID)
REFERENCES USER_ACCOUNT (USER_ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE PAYMENT_TRANSACTION ADD CONSTRAINT recipient_transaction_fk1
FOREIGN KEY (RECIPIENT_ID)
REFERENCES USER_ACCOUNT (USER_ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE CREDIT_BANK_DETAILS ADD CONSTRAINT user_creditbankdetails_fk
FOREIGN KEY (USER_ID)
REFERENCES USER_ACCOUNT (USER_ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE TRANSFER_TRANSACTION ADD CONSTRAINT useraccount_new_tabletransfertransaction_fk
FOREIGN KEY (USER_ID)
REFERENCES USER_ACCOUNT (USER_ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE FRIENDS_NETWORK ADD CONSTRAINT user_account_friends_network_fk
FOREIGN KEY (USER_ID)
REFERENCES USER_ACCOUNT (USER_ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE FRIENDS_NETWORK ADD CONSTRAINT user_account_friends_network_fk1
FOREIGN KEY (FRIEND_ID)
REFERENCES USER_ACCOUNT (USER_ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE TRANSFER_TRANSACTION ADD CONSTRAINT credit_bank_details_transfer_transaction_fk
FOREIGN KEY (CREDIT_ID)
REFERENCES CREDIT_BANK_DETAILS (CREDIT_ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE TRANSFER_TRANSACTION ADD CONSTRAINT debit_bank_details_transfer_transaction_fk
FOREIGN KEY (DEBIT_ID)
REFERENCES DEBIT_BANK_DETAILS (DEBIT_ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

/*==============================================================*/
/* Table that holds the documents                               */
/*==============================================================*/
create table TEMPORAL_DATA (
    ID                INTEGER          not null,
    NAME              VARCHAR(128)     not null,
    DATE              DATE,
    TYPE              VARCHAR(128)     not null,
    CONTENT           VARCHAR(2048)    not null,
    constraint PK_UNI_TEMPORAL_DATA primary key (ID),
    constraint UNIQUE_TEMPORAL_DATA unique (NAME, DATE, TYPE)
);

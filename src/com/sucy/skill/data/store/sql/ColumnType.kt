package com.sucy.skill.data.store.sql

enum class ColumnType(val key: String) {
    STRING_16("VARCHAR(16)"),
    STRING_32("VARCHAR(32)"),
    STRING_64("VARCHAR(64)"),
    STRING_128("VARCHAR(128)"),
    STRING_255("VARCHAR(255)"),
    TEXT("TEXT"),
    INT("INT"),
    LONG("BIGINT"),
    FLOAT("FLOAT(24)"),
    DOUBLE("FLOAT(53)"),
    INCREMENT("INT KEY AUTO_INCREMENT"),
    DATE_TIME("DATETIME")
}
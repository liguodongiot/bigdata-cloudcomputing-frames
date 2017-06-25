package com.lgd.es.util;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/6/22 17:20
 */
public class DocumentParam<T> {

    private String indexName;
    private String typeName;
    private String documentId;
    private T document;

    public DocumentParam(String indexName, String typeName, String documentId, T document) {
        this.indexName = indexName;
        this.typeName = typeName;
        this.documentId = documentId;
        this.document = document;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public T getDocument() {
        return document;
    }

    public void setDocument(T document) {
        this.document = document;
    }
}

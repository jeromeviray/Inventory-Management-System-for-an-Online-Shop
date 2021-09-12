package com.project.inventory.store.product.model;

public class FileImageDto {
    private String id;
    private String fileName;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FileImageDto{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}

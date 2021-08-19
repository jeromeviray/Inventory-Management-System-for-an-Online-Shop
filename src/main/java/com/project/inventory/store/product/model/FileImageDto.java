package com.project.inventory.store.product.model;

public class FileImageDto {
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FileImageDto{" +
                "fileName='" + fileName + '\'' +
                '}';
    }
}

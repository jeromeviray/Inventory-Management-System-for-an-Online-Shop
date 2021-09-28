package com.project.inventory.store.product.model;

public class FileImageDto {
    private String id;
    private String fileName;
    public String path;

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

    public String getPath() {
        return path;
    }

    public void setPath( String path ) {
        this.path = path;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

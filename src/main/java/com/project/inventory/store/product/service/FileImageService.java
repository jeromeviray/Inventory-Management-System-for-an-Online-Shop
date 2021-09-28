package com.project.inventory.store.product.service;

import com.project.inventory.store.product.model.FileImage;
import com.project.inventory.store.product.model.FileImageDto;

import java.io.File;
import java.util.List;

public interface FileImageService {

    void saveFileImage( FileImage fileImage );

    void deleteFileImage( int id );

    FileImage getFileImageById( int id );

    FileImage getFileImages( String[] fileName );

    FileImage updateFileImage( FileImage fileImage, int id );

    FileImage getFileImageByFileNameAndProductId(String fileName, int productId);

    List<FileImage> saveFileImages( List<FileImage> fileImages );

    FileImageDto convertEntityToDto(FileImage fileImage);

}

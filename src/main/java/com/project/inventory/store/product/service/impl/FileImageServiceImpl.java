package com.project.inventory.store.product.service.impl;

import com.project.inventory.store.product.model.FileImage;
import com.project.inventory.store.product.model.FileImageDto;
import com.project.inventory.store.product.repository.FileImageRepository;
import com.project.inventory.store.product.service.FileImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileImageServiceImpl implements FileImageService {

    @Autowired
    private FileImageRepository fileImageRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public void saveFileImage( FileImage fileImage ) {
        fileImageRepository.save( fileImage );
    }

    @Override
    public void deleteFileImage( int id ) {

    }

    @Override
    public FileImage getFileImageById( int id ) {
        return null;
    }

    @Override
    public FileImage getFileImages( String[] fileName ) {
        return null;
    }

    @Override
    public FileImage updateFileImage( FileImage fileImage, int id ) {
        return null;
    }

    @Override
    public List<FileImage> saveFileImages( List<FileImage> fileImages ) {
        return fileImageRepository.saveAllAndFlush( fileImages );
    }

    @Override
    public FileImage getFileImageByFileNameAndProductId( String fileName, int productId ) {
        return fileImageRepository.findByFileNameAndProductId(fileName, productId);
    }

    @Override
    public FileImageDto convertEntityToDto( FileImage fileImage ) {
        return mapper.map( fileImage, FileImageDto.class );
    }
}

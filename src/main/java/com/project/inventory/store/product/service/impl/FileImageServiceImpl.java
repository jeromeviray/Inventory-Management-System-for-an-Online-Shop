package com.project.inventory.store.product.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.product.model.FileImage;
import com.project.inventory.store.product.model.FileImageDto;
import com.project.inventory.store.product.repository.FileImageRepository;
import com.project.inventory.store.product.service.FileImageService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

@Service
public class FileImageServiceImpl implements FileImageService {
    Logger logger = LoggerFactory.getLogger( FileImageServiceImpl.class );


    @Autowired
    private FileImageRepository fileImageRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public void saveFileImage( FileImage fileImage ) {
        fileImageRepository.save( fileImage );
    }

    @Override
    public void deleteFileImage( FileImage fileImage, Path path, int productId ) throws Exception {
        try {
            // Delete file or directory
            fileImageRepository.deleteByFileNameAndProductId( fileImage.getId(), productId );
            Files.delete( path );
            logger.info( "File or directory deleted successfully" );
        } catch( NoSuchFileException ex ) {
            throw new NoSuchFileException( "No such file or directory: +" + path );
        } catch( DirectoryNotEmptyException ex ) {
            throw new DirectoryNotEmptyException( "Directory %s is not empty " + path );
        } catch( IOException ex ) {
            throw new IOException( ex.getMessage() );
        }
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
        return fileImageRepository.findByFileNameAndProductId( fileName, productId ).orElseThrow(() -> new NotFoundException(String.format( "File Not Found %s", fileName )) );
    }

    @Override
    public FileImageDto convertEntityToDto( FileImage fileImage ) {
        return mapper.map( fileImage, FileImageDto.class );
    }
}

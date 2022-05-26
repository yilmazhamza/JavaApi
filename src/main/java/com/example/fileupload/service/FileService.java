package com.example.fileupload.service;

import com.example.fileupload.dto.request.FileRequest;
import com.example.fileupload.dto.response.FileResponse;
import com.example.fileupload.entity.FileEntity;
import com.example.fileupload.repository.FileRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class FileService {
    private final FileRepository fileRepository;
    private final Path path = Paths.get("C:\\Users\\sahabt\\library-automation-system\\file-upload\\uploads");
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileResponse save(MultipartFile multipartFile) {
        var extension =multipartFile.getOriginalFilename().split("\\.")[1];
        if(extension.equalsIgnoreCase("png")||extension.equalsIgnoreCase("jpeg")||extension.equalsIgnoreCase("jpg")||extension.equalsIgnoreCase("docx")||extension.equalsIgnoreCase("pdf") ||extension.equalsIgnoreCase("xlsx")) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
            }
            try {
                Files.copy(multipartFile.getInputStream(), path.resolve(multipartFile.getOriginalFilename()));
            } catch (IOException e) {
            }
            long fileSizeInMb = multipartFile.getSize();

            var fileEntity = fileRepository.save(new FileEntity(multipartFile.getOriginalFilename(), path.toString(), extension, fileSizeInMb));
            return new FileResponse(fileEntity.getId(), fileEntity.getFileName(), fileEntity.getFilePath(), fileEntity.getFileExtension(), fileEntity.getSize());
        }
        throw new IllegalArgumentException("It is not a supported file format you have to use one of these extensions png, jpeg, jpg, docx, pdf, xlsx ");
    }

    public List<FileEntity> getAll() {
        return fileRepository.findAll();
    }

    public byte[] getFile(long id) throws IOException {
        var fileEntity = fileRepository.findById(id).get();
        Path path = Paths.get(fileEntity.getFilePath()+"/"+fileEntity.getFileName());
        System.err.println(fileEntity.getFilePath()+"/"+fileEntity.getFileName());

        System.err.println(Arrays.toString(Files.readAllBytes(path)));
        File file = new File(path.toString());
        InputStream inputStream = new FileInputStream(file);
        return inputStream.readAllBytes();

    }

    public void deleteFile(long id) {
        var file = fileRepository.existsById(id);
        if(!file)
            throw new IllegalArgumentException("Böyle bir dosya bulunamadı");

        fileRepository.deleteById(id);
    }

    public FileResponse updateFile(long id, MultipartFile file) {
        var fileUpdate = fileRepository.existsById(id);
        var fileNewUpdate = fileRepository.findById(id);
        if(!fileUpdate)
            throw new IllegalArgumentException("Böyle bir dosya bulunamadı");

        FileEntity newFile= new FileEntity();
        newFile.setFileName(fileNewUpdate.get().getFileName());
        var response = fileRepository.save(newFile);
        return new FileResponse(response.getId(),response.getFileName(),response.getFilePath(),response.getFileExtension(),response.getSize());

    }
}

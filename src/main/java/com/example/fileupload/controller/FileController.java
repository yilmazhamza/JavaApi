package com.example.fileupload.controller;

import com.example.fileupload.dto.request.FileRequest;
import com.example.fileupload.dto.response.FileResponse;
import com.example.fileupload.entity.FileEntity;
import com.example.fileupload.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequestMapping
public class FileController {

    private final FileService fileService;
    private final Path path = Paths.get("C:\\Users\\sahabt\\library-automation-system\\file-upload\\uploads");

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @GetMapping
    public List<FileEntity> getAll(){
        return fileService.getAll();
    }

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileResponse addFile (@RequestPart("multipartFile")MultipartFile multipartFile){
        return fileService.save(multipartFile);
    }
    @GetMapping(value = "/{getById}")
    public byte[] getAll(@PathVariable("getById") long id) throws IOException {
        return fileService.getFile(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFile(@PathVariable long id){

        fileService.deleteFile(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public FileResponse updateFile(@PathVariable long id, @RequestBody MultipartFile file){
        return fileService.updateFile(id,file);
    }

}

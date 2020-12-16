package iRide.service.File;

import iRide.service.File.model.output.FileListOutput;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class FileService {

    public void uploadFile(MultipartFile file, String category) {
        if (file.isEmpty()) {
            throw new NotFoundException("File not included");
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String categoryRoot = "files/" + category;
        String stringPath = categoryRoot + "/" + filename;

        File f = new File(stringPath);
        if (f.exists()) {
            throw new DataExistsException("File exists");
        }

        if (!Files.exists(Paths.get(categoryRoot))) {
            new File(categoryRoot).mkdir();
        }

        try {
            Files.createDirectories(Paths.get(categoryRoot));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Path copyLocation = Paths
                    .get(categoryRoot + File.separator + filename);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<FileListOutput> filesList(List<String> categories) {
        List<FileListOutput> files = new ArrayList<>();

        for (String category : categories) {
            File directory = new File("files/" + category);
            List<File> dirFiles;

            try {
                dirFiles = Arrays.asList(directory.listFiles());
            }
            catch (NullPointerException e){
                dirFiles = new ArrayList<>();
            }

            List<String> filename = new ArrayList<>();
            for (File dirFile: dirFiles){
                filename.add(dirFile.getName());
                FileListOutput fileListOutput = new FileListOutput();
                fileListOutput.setCategory(category);
                fileListOutput.setFile(dirFile.getName());

                files.add(fileListOutput);
            }
        }
        return files;
    }

    public InputStreamResource getFile(String category, String filename) {
        String categoryRoot = "files/" + category;
        String stringPath = categoryRoot + "/" + filename;

        File f = new File(stringPath);

        try {
            return new InputStreamResource(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            throw new NotFoundException("File not found");
        }
    }

    public void deleteFile(String category, String filename){
        String categoryRoot = "files/" + category;
        String stringPath = categoryRoot + "/" + filename;

        File f = new File(stringPath);

        if(!f.exists()){
            throw new NotFoundException("File not found");
        }
        f.delete();
    }
}

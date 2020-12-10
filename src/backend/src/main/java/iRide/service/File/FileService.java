package iRide.service.File;

import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    public void uploadFile(MultipartFile file, String category){
        if (file.isEmpty()){
            throw new NotFoundException("File not included");
        }
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        String stringPath = "./files/" + category + "/" + filename;
        File f = new File(stringPath);
        if (f.exists()){
            throw new DataExistsException("File exists");
        }

        try {
            Path path = Paths.get(stringPath);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Model getUploadFile(){

    }
}

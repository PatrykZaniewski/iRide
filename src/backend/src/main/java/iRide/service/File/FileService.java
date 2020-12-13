package iRide.service.File;

import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
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
        String categoryRoot = "files/" + category;
        String stringPath = categoryRoot + "/" + filename;

        File f = new File(stringPath);
        if (f.exists()){
            throw new DataExistsException("File exists");
        }

        if(!Files.exists(Paths.get(categoryRoot))){
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

    public Model getUploadFile(){
        return null;
    }
}

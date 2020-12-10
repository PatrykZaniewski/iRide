package iRide.controller;

import iRide.service.File.FileService;
import iRide.service.File.model.input.FileAddInput;
import iRide.utils.exception.DataExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping(value = "/file")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String uploadFile(@ModelAttribute FileAddInput fileAddInput, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        try {
            this.fileService.uploadFile(file, fileAddInput.getCategory());
            redirectAttributes.addFlashAttribute("code", 101);
        }
        catch (DataExistsException e){
            redirectAttributes.addFlashAttribute("code", 201);
        }
        return "redirect:/file";
    }

    @GetMapping("/upload")
    public String getUploadFile(Model model){

    }
}

package iRide.controller;

import iRide.config.AuthenticationUserDetails;
import iRide.service.Category.CategoryService;
import iRide.service.File.FileService;
import iRide.service.File.model.input.FileAddInput;
import iRide.service.File.model.output.FileListOutputAdmin;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/file")
public class FileController {

    private final FileService fileService;
    private final CategoryService categoryService;

    @Autowired
    public FileController(FileService fileService, CategoryService categoryService) {
        this.fileService = fileService;
        this.categoryService = categoryService;
    }

    @PostMapping("/upload")
    public String uploadFile(@ModelAttribute FileAddInput fileAddInput, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            this.fileService.uploadFile(file, fileAddInput.getCategory());
            redirectAttributes.addFlashAttribute("code", 102);
        } catch (DataExistsException e) {
            redirectAttributes.addFlashAttribute("code", 201);
        }
        return "redirect:/file";
    }

    @GetMapping("/upload")
    public String getUploadFile(Model model, @AuthenticationPrincipal AuthenticationUserDetails authenticationUserDetails) {
        List<String> categories = this.categoryService.getCategoriesByGroupAndId(authenticationUserDetails.getId());
        model.addAttribute("categories", categories);
        return "admin/file_add";
    }

    @GetMapping("")
    public String getFilesList(Model model, @AuthenticationPrincipal AuthenticationUserDetails authenticationUserDetails) {
        List<String> categories = this.categoryService.getCategoriesByGroupAndId(authenticationUserDetails.getId());
        List<FileListOutputAdmin> fileListOutputAdmins = this.fileService.filesList(categories);

        if (model.asMap().get("code") != null) {
            Integer code = (Integer)model.asMap().get("code");
            switch (code) {
                case 101:
                    model.addAttribute("infoMessage", "Wybrany plik został usunięty.");
                    break;
                case 102:
                    model.addAttribute("infoMessage", "Plik został dodany.");
                    break;
                case 201:
                    model.addAttribute("infoError", "Plik o wybranej nazwie istnieje.");
                    break;
                case 202:
                    model.addAttribute("infoError", "Wybrany plik nie istnieje.");
                    break;
                default:
                    model.addAttribute("infoError", "Wystąpił nieznany błąd.");
            }
        }
        model.addAttribute("fileListOutputAdmins", fileListOutputAdmins);
        return "admin/file_list";
    }

    @GetMapping("/{category}")
    public ResponseEntity<Resource> getFile(Model model, @PathVariable(value = "category") String category, @RequestParam(value = "filename") String filename) {

        Resource file = this.fileService.getFile(category, filename);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Disposition", "attachment; filename=" + filename);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).headers(responseHeaders).body(file);
    }

    @DeleteMapping("/{category}")
    public String deleteFile(Model model, RedirectAttributes redirectAttributes, @PathVariable(value = "category") String category, @RequestParam(value = "filename") String filename) {
        try {
            this.fileService.deleteFile(category, filename);
            redirectAttributes.addFlashAttribute("code", 101);
        }
        catch (NotFoundException e){
            redirectAttributes.addFlashAttribute("code", 202);
        }
        return "redirect:/file";
    }
}

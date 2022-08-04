package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import toyproject.board.domain.Files;
import toyproject.board.service.FilesService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final FilesService filesService;

    @GetMapping("/images/new")
    public String createImage(Model model) {

        return "imageFile/createImage";
    }

//    @PostMapping("/uploadImage")
//    public String uploadImage

    @PostMapping("/uploadImage")
    public String uploadImage(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception{
        Files file = new Files();

        String sourceFileName = files.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
        File destinationFile;
        String destinationFileName;
        String fileUrl = "C:\\Users\\didrl\\OneDrive - 홍익대학교\\바탕 화면\\board\\src\\main\\resources\\static\\img\\";

        do {
            destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
            destinationFile = new File(fileUrl + destinationFileName);
        } while (destinationFile.exists());

        destinationFile.getParentFile().mkdirs();
        files.transferTo(destinationFile);

        file.setFilename(destinationFileName);
        file.setFileOriName(sourceFileName);
        file.setFileurl(fileUrl);
        filesService.join(file);

        return "redirect:/";
    }

    @GetMapping("/images")
    public String showIamges(Model model) {

        return "imageFile/showImage";
    }
}

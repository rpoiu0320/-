package com.blog.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blog.domain.AttachVo;
import com.blog.domain.BoardVo;
import com.blog.domain.ReplyVo;
import com.blog.service.BoardService;
import com.blog.service.CommentService;
import com.blog.util.Criteria;
import com.blog.util.FileUpload;
import com.blog.util.PageVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/port/*")
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
@Log4j2
public class BoardController
{
	private final BoardService service;
	private final CommentService comService;
	private final FileUpload fileUpload;
	private final String uploadDir = "C:/upload";
	
	@GetMapping("/write.do")
	//@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@PreAuthorize("isAuthenticated()")
	public String writeBoardFrom(Principal pc, Model model)
	{
		if (pc != null)
			model.addAttribute("user", pc.getName());
		
		return "/portfolio/write";
	}
	
	@PostMapping("/save.do")
	public String saveBoard(
			BoardVo vo, 
			@RequestParam("fileName") MultipartFile fileName,
			@RequestParam("uploadFile") List<MultipartFile> uploadFile)
	{
		List<AttachVo> attachList = fileUpload.uploadFiles(fileName, uploadFile);
		vo.setAttachList(attachList);
		service.register(vo);
		
		return "redirect:/port/list.do";
	}
	
//	@GetMapping("/list.do")
//	public String listBoard(Model model)
//	{
//		model.addAttribute("list", service.getList());
//
//		return "/portfolio/list";
//	}
	
	@GetMapping("/list.do")
	public String listBoard(Model model, Criteria ct/*, @RequestParam("files") MultipartFile[] files*/)
	{
		model.addAttribute("list", service.getListPaging(ct));
		model.addAttribute("pageMaker", new PageVO(ct, service.getTotal(ct)));

		return "/portfolio/list";
	}
	
	@GetMapping("/view.do")				// @RequestParam 없어도 변수명만 같으면 됨(thymleaf는 있어야함)
	public String getRead(Model model, @RequestParam("bno") Long bno, @ModelAttribute("ct") Criteria ct, Principal pc)
	{																	// 매개변수 바로 addAttribute
		// model.addAttribute("ct", ct);
		model.addAttribute("view", service.getRead(bno));
		//model.addAttribute("replyList", service.getListReply(bno));
		model.addAttribute("comments", comService.getComments(bno));
		model.addAttribute("commentCount", comService.getCommentCount(bno));		
		model.addAttribute("next", service.getNext(bno));
		model.addAttribute("prev", service.getPrev(bno));
		
		/*
		 * if (pc != null) model.addAttribute("username", pc.getName());
		 */
		
		return "/portfolio/view";
	}
	
	@GetMapping("/delete.do")
//	public String deletePort(Model model, Long bno)
//	{
//		if (service.deletePort(bno) != 1)
//			model.addAttribute("deleteError", "오류가 있어 삭제하지 못했습니다.");
//
//		return "redirect:/port/list.do";
//	}
//								1회성 어트리뷰트
	public String deletePort(RedirectAttributes rttr, @RequestParam("bno") Long bno, Criteria ct)
	{
		
		if (service.deletePort(bno) != 1)
		{
			rttr.addFlashAttribute("msg", "오류가 있어 삭제하지 못했습니다.");
			rttr.addFlashAttribute("pageNum", ct.getPageNum());
			rttr.addFlashAttribute("amout", ct.getAmount());			

			return "redirect:/port/list.do";
		}

		rttr.addFlashAttribute("msg", "삭제되었습니다.");

		return "redirect:/port/list.do";
	}
	
	@GetMapping("/update.do")
	public String modify(@RequestParam("bno") Long bno, Model model)
	{
		model.addAttribute("board", service.getRead(bno));		// xml에서 resultMap을 사용해 조인하면 하나만 사용해도 가능
		model.addAttribute("attach", service.getAttach(bno));		

		return "/portfolio/modify";
	}
	
	@PostMapping("/update.do")
	public String update(BoardVo boardVo,
			@RequestParam("fileName") MultipartFile fileName,
			@RequestParam("uploadFile") List<MultipartFile> uploadFile)
	{
		List<AttachVo> attachList = fileUpload.uploadFiles(fileName, uploadFile);
		service.update(boardVo, attachList);
		
		return "redirect:/port/list.do";
	}
	
	@GetMapping("/reply.do")
	public String insertReply(Long bno, String username, ReplyVo vo)
	{
		
		System.out.println(bno);
		System.out.println(username);
		System.out.println(vo.getBno());
		System.out.println(vo.getId());
		System.out.println(vo.getRegdate());
		System.out.println(vo.getReply_num());
		System.out.println(vo.getContent());
		service.insertReply(bno, username, vo);
		
		return "/port/view?bno=" + vo.getBno();
	}
	
	@GetMapping("/download.do")
    public ResponseEntity<Resource> downloadFile(@RequestParam("filename") String filename) 
	{
      //ResponseEntity: Spring에서 HTTP 응답을 만들 때 사용하는 클래스입니다. HTTP 상태 코드, 헤더, 바디를 모두 설정할 수 있습니다.
      //Resource: Spring의 Resource는 파일과 같은 리소스를 다루는 인터페이스입니다. 이를 통해 파일을 읽고 응답으로 전달할 수 있습니다
      //@RequestParam: HTTP 요청에서 URL 파라미터를 메서드의 파라미터에 바인딩하는 어노테이션입니다
        try 
        {
            filename = filename.replace("\\", "/");
            //파일 경로에 포함된 백슬래시(\)를 슬래시(/)로 변경하는 코드입니다
            
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            //Paths.get(uploadDir): uploadDir 변수에 저장된 디렉토리 경로를 기반으로 Path 객체를 생성합니다.
          //resolve(filename): filename을 기존 경로에 결합합니다. 예를 들어, C:/uploads와 image.jpg를 결합하면 C:/uploads/image.jpg가 됩니다.
            //normalize(): 경로에 포함된 ..(상위 디렉터리로 이동)나 .(현재 디렉터리) 같은 특수 문자를 처리해 정규화된 경로를 반환합니다. 예를 들어, C:/uploads/../image.jpg는 C:/image.jpg로 변환됩니다.
        
            Resource resource = new UrlResource(filePath.toUri());
            //Resource는 파일과 같은 리소스를 다루는 인터페이스입니다. 이를 통해 파일을 읽고 응답으로 전달할 수 있습니다.
            //filePath.toUri(): Path 객체를 URI로 변환하여 UrlResource에 전달합니다. 이는 파일 경로를 URL로 변환하는 과정입니다.
            
            if (!resource.exists() || !resource.isReadable()) 
            {
               //resource.isReadable(): 리소스가 읽을 수 있는지 확인하는 메서드입니다. 파일이 존재하고 읽을 수 있으면 true, 아니면 false를 반환합니다.
                throw new RuntimeException("파일을 찾을 수 없거나 읽을 수 없습니다.");
            }

            String contentDisposition = "attachment; filename=\"" + resource.getFilename() + "\"";
            //Content-Disposition 헤더: 이 HTTP 헤더는 파일을 다운로드할 때 브라우저에게 파일을 다운로드로 처리하도록 지시합니다.
            //attachment; filename=\"" + resource.getFilename() + "\"": 파일을 다운로드할 때 브라우저가 보여줄 파일 이름을 설정하는 부분입니다. 
            //예를 들어, 파일 이름이 image.jpg라면, attachment; filename="image.jpg"가 됩니다.
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
            //ResponseEntity.ok(): HTTP 200 OK 상태 코드로 응답을 반환하겠다는 의미입니다
            //HttpHeaders.CONTENT_DISPOSITION: HTTP 헤더 중 Content-Disposition 헤더를 설정합니다. 이 헤더는 브라우저에게 응답이 첨부파일임을 알려주고, 파일 이름을 설정합니다.
            //contentDisposition에 설정된 값이 Content-Disposition 헤더로 전달되어 다운로드할 파일 이름이 지정됩니다.
            //body(resource): 응답 본문에 실제 파일 리소스를 담아서 클라이언트에게 전달합니다.
        } 
        catch (MalformedURLException e) 
        {
           //MalformedURLException: URL이 잘못된 형식일 때 발생하는 예외입니다. 이 예외가 발생하면, 파일 경로가 잘못되었음을 알리고 처리합니다.
            throw new RuntimeException("파일 경로를 읽을 수 없습니다.", e);
        }
    }
}

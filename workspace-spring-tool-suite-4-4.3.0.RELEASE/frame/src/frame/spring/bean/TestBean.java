package frame.spring.bean;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import frame.spring.vo.TestVO;

@Controller
@RequestMapping("/test/")
public class TestBean {
	
	@Autowired
	private SqlSessionTemplate sql = null;
	
	@RequestMapping("form.do")
	public String form() {
		System.out.println(sql);
		return "/test/form";
	}
	@RequestMapping(value="formPro.do" , method=RequestMethod.POST)
	public String formPro(MultipartHttpServletRequest request, String id,String pw,int age,String name) {
		MultipartFile mf =request.getFile("img"); 
		String path = request.getRealPath("imgs");
		String org = mf.getOriginalFilename();
		String ext = org.substring(org.lastIndexOf("."));
		String img = id+ext;
		File f = new File(path+"//"+img);
		try {
			mf.transferTo(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(id);
		TestVO vo = new TestVO();
		vo.setAge(age);
		vo.setId(id);
		vo.setImg(img);
		vo.setName(name);
		vo.setPw(pw);
		// Mybatis insert ����
		System.out.println(vo);
		sql.insert("test.insertTest" , vo);
		
		return "/test/formPro";
		}
	@RequestMapping("main.do")
	public String loginmain(HttpSession session,Model model) {
		String id = (String)session.getAttribute("memId");
		TestVO vo = (TestVO)sql.selectOne("test.selectUser", id);
		model.addAttribute("vo",vo);
		
		return "/test/main";
	}
	
	@RequestMapping("loginForm.do")
	public String logform() {
		return "/test/loginForm";
	}
	
	@RequestMapping("loginPro.do")
	public String logPro(TestVO vo, HttpSession session,Model model) {
		int c = (Integer)sql.selectOne("test.loginCheck", vo);
		if(c==1) {
			session.setAttribute("memId",vo.getId());
		}
		model.addAttribute("c", c);
		return "/test/loginPro";
	}
	@RequestMapping("modifyForm.do")
	public String loginModifyForm(HttpSession session,Model model) {
		String id = (String)session.getAttribute("memId");
		TestVO vo = (TestVO)sql.selectOne("test.selectUser", id);
		model.addAttribute("vo",vo);
		return "/test/modifyForm";
	}
	
	@RequestMapping("modifyPro.do")
	public String loginModifyPro(HttpSession session,Model model,MultipartHttpServletRequest request
			,String pw, int age, String name, String org) {
		String id = (String)session.getAttribute("memId");
		MultipartFile mf = request.getFile("img");
		String orgname = mf.getOriginalFilename();
		TestVO vo = new TestVO();
		vo.setAge(age);
		vo.setName(name);
		vo.setPw(pw);
		vo.setId(id);
		if(orgname.equals("")) {
			vo.setImg(org);
		}else {
			String path = request.getRealPath("imgs");
			String ext = orgname.substring(orgname.lastIndexOf("."));
			String img = id+ext;
			File f = new File(path+"//"+img);
			try{mf.transferTo(f);}
			catch(Exception e) {
				e.printStackTrace();
			}
			vo.setImg(img);
		}
		
		sql.update("test.updateModify" , vo);
		return "/test/modifyPro";
	}
	
	@RequestMapping("deleteForm.do")
	public String deleteForm(HttpSession session,Model model) {
		String id = (String)session.getAttribute("memId");
		TestVO vo = (TestVO)sql.selectOne("test.selectUser", id);
		model.addAttribute("vo",vo);
		return "/test/deleteForm";
	}
	
	@RequestMapping("deletePro.do")
	public String logindeleteform(HttpSession session, MultipartHttpServletRequest request,String pw,String id,int age,String org,String name) {
		
		
		return "/test/deletePro";
	}
	@RequestMapping("logout.do")
	public String loginlogout(HttpSession session) {
		session.invalidate();
		return "/test/logout";
	}
}


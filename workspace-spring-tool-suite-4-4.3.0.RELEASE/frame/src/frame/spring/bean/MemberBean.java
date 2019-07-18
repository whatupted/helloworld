package frame.spring.bean;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import frame.spring.vo.LogonDBBean;
import frame.spring.vo.LogonDataBean;

	@Controller
	@RequestMapping("/member/")
public class MemberBean {
		
		@Autowired
		private LogonDBBean member = null;
		@Autowired
		private LogonDataBean person = null;
		
		@RequestMapping("main.do")
		public String main() {
			
			return "/member/main";
		}
		@RequestMapping("loginForm.do")
		public String loginForm() {
			return "/member/loginForm";
		}
		@RequestMapping("logout.do")
		public String loginlogout(HttpSession session, HttpServletRequest request) {
			session.invalidate();
			return "/member/logout";
		}
		@RequestMapping("loginPro.do")
		public String Pro(String id, String passwd, HttpSession session, Model model) {
			try {
				int check = member.userCheck(id, passwd);
				if(check==1) {
					session.setAttribute("memId",id);
				}
				model.addAttribute("check",check);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "/member/loginPro";
		}
		@RequestMapping("confirmId.do")
		public String comfirId(String id, Model model) {
			try {
				int check = member.confirmId(id);
				model.addAttribute("check",check);
				model.addAttribute("id",id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "/member/confirmId";
		}
		@RequestMapping("deleteForm.do")
		public String deleteForm() {
			return "/member/deleteForm";
		}
		@RequestMapping("deletePro.do")
		public String deletePro(String passwd, Model model,HttpSession session) {
			String id = (String) session.getAttribute("memId");
			try {
				int check = member.deleteMember(id, passwd);
				model.addAttribute("passwd",passwd);
				model.addAttribute("check",check);
				if(check==1) {
					session.invalidate();
				}
				}	 
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "/member/deletePro";
		}
		@RequestMapping("inputForm.do")
		public String inputForm() {
			return "/member/inputForm";
		}
		@RequestMapping("inputPro.do")
		public String inputPro(HttpServletRequest request, HttpServletResponse response) {
			try {
				person = new LogonDataBean();
				person.setId(request.getParameter("id"));
				person.setPasswd(request.getParameter("passwd"));
				person.setBlog(request.getParameter("blog"));
				person.setEmail(request.getParameter("email"));
				person.setJumin1(request.getParameter("jumin1"));
				person.setJumin2(request.getParameter("jumin2"));
				person.setName(request.getParameter("name"));
				person.setReg_date(new Timestamp(System.currentTimeMillis()) );
				member.insertMember(person);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "/member/inputPro";
		}
		@RequestMapping("modify.do")
		public String modify() {
			return "/member/modify";
		}
		@RequestMapping("modifyForm.do")
		public String modifyForm(HttpSession session, Model model) {
			String id = (String) session.getAttribute("memId");
			try {
				Object sql = member.getMember(id);
				model.addAttribute("c",sql);
			}catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "/member/modifyForm";
		}
		@RequestMapping("modifyPro.do")
		public String modifyPro(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
			String id = (String) session.getAttribute("memId");
			try {
				person = new LogonDataBean();
				person.setId(id);
				person.setName(request.getParameter("name"));
				person.setPasswd(request.getParameter("passwd"));
				person.setEmail(request.getParameter("email"));
				person.setBlog(request.getParameter("blog"));
				member.updateMember(person);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "/member/modifyPro";
		}
	}

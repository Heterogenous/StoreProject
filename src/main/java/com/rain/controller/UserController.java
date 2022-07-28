package com.rain.controller;

import com.rain.controller.ex.*;
import com.rain.entity.User;
import com.rain.service.IUserService;
import com.rain.service.ex.InsertException;
import com.rain.service.ex.UsernameDuplicatedException;
import com.rain.util.Code;
import com.rain.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 注册请求
     * @param user
     * @return
     */
    @RequestMapping("/reg")
    public JsonResult<Void> reg(User user){
        //创建响应结果对象
        userService.reg(user);
        return new JsonResult<>(Code.REG_OK,"注册成功!");
    }


    /**
     * 登陆请求
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public JsonResult<User> login(String username,
                                  String password,
                                  HttpSession session){
        User user = userService.login(username, password);
        //将对象设置到session对象中
        session.setAttribute("uid",user.getUid());
        session.setAttribute("username",user.getUsername());
        System.out.println("<--- "+username+" 登陆网站 "+new Date() +" --->");
        return new JsonResult<>(Code.LOGIN_OK, "登陆成功!", user);
    }

    @RequestMapping("/logout")
    public JsonResult<Void> logout(HttpSession session){
        //登出,消除session
        System.out.println("<--- "+getUsernameFromSession(session)+" 退出登陆 "+new Date() +" --->");
        session.invalidate();
        return new JsonResult<>(Code.LOGOUT_OK,"退出登陆!");
    }

    /**
     * 修改密码
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @param session session对象
     * @return 返回修改信息
     */
    @RequestMapping("/change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session
                                           ){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(Code.UPDATE_OK,"修改成功!");
    }

//    @RequestMapping("/clear_session")
//    public void clearSession(HttpSession session){
//        session.setAttribute("uid",null);
//        session.setAttribute("username",null);
//        session.invalidate();
//        System.out.println(session);
//    }

    /**
     * 根据登陆信息，获取对象信息
     * @param session 登陆信息
     * @return 对应的信息
     */
    @RequestMapping("/get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User result = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(Code.GET_OK,"获取对象成功!",result);
    }

    /**
     * 修改用户信息,phone,email,gender
     * @param user 被修改者的信息
     * @param session 登录者信息
     * @return 被修改者的状况
     */
    @RequestMapping("/change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        //获取登录者的uid和username
        Integer loginUid = getUidFromSession(session);
        String loginUsername = getUsernameFromSession(session);
        //调用业务逻辑，将登录者信息以及被修改者信息传入
        //System.out.println("--------gender--------->"+user.getGender());
        userService.changeInfo(loginUid,loginUsername,user);
        return new JsonResult<>(Code.UPDATE_OK,"修改成功!");
    }


//    @Value("${spring.servlet.multipart.max-file-size}")
//    public String test;
    //文件上传的最大值
    public static final Integer AVATAR_MAX_SIZE = 2*1024*1024;
    //限制上传文件的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    {
        AVATAR_TYPE.add("image/jpeg");
        //AVATAR_TYPE.add("image/jpg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }
    //文件上传路径
    public static final String AVATAR_PATH = "/upload/";

    /**
     * MultipartFile接口是SpringMvc提供的一个接口，这个接口为我们包装了获取文件类型的数据
     * 可以接收任何类型的file，Springboot整合了mvc,只需在处理请求的方法参数上声明一个类型为
     * MultipartFile的参数，然后SpringBoot自动传递给服务的文件数据值赋值给这个参数
     * @param session
     * @param file
     * @return
     */
    @RequestMapping("/change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           @RequestParam("file") MultipartFile file
                                            ){
        //System.out.println("----------------------->"+file.getSize());
        //判断文件是否为空
        if (file.isEmpty()){
            throw new FileEmptyException("图片不能为空!",Code.FILE_UPLOAD_EMPTY);
        }
        if(file.getSize() > AVATAR_MAX_SIZE){
            throw new FileSizeException("图片超出 "+(AVATAR_MAX_SIZE/1024/1024)+"mb 限制!请重新上传!",Code.FILE_UPLOAD_SIZE);
        }
        //判断是否符合类型
        String contentType = file.getContentType();
        if(!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("图片类型不支持上传!",Code.FILE_UPLOAD_TYPE);
        }
        //上传的文件 ../upload/文件.png
        //String path = session.getServletContext().getRealPath(AVATAR_PATH);


        //获取jar包所在目录
        ApplicationHome h = new ApplicationHome(getClass());
        File jarFile = h.getSource();
        //在jar包目录下生成一个上传图片保存的文件夹
        String path = jarFile.getParentFile().toString() + AVATAR_PATH;

        //File对象指向这个路径，File是否存在，不存在则创建
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();//创建当前目录
        }
        //获取文件的名称,使用UUID生成图片的文件名
        String originalFilename = file.getOriginalFilename();
        //测试
//        System.out.println("----------------->"+originalFilename);
//        System.out.println(test.lastIndexOf("m"));
        int pointIndex = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(pointIndex);
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        //目标文件,在dir文件下，创建filename文件名的文件
        File dest = new File(dir,filename);
        //将file写入到该文件中
        try {
            file.transferTo(dest);
        }catch (FileStateException e){
            throw new FileStateException("文件状态异常!",Code.FILE_UPLOAD_STATE);
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常!",Code.FILE_UPLOAD_IO);
        }

        Integer uid = getUidFromSession(session);//被修改的uid
        String username = getUsernameFromSession(session);//修改者的username
        //返回头像路径
        String avatar = AVATAR_PATH + filename;
        //调用业务逻辑
        userService.changeAvatar(uid,username,avatar);
        //返回用户头像路径给前端，用于头像展示使用
        //拼接jar包所在路径
        //avatar = path + filename;
        return new JsonResult<>(Code.UPDATE_OK,"头像修改成功!",avatar);
    }

}

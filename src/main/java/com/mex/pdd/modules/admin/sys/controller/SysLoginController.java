package com.mex.pdd.modules.admin.sys.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.mex.pdd.base.common.controller.utils.ShiroUtils;
import com.mex.pdd.base.common.entity.LoginVM;
import com.mex.pdd.base.common.enums.Status;
import com.mex.pdd.modules.admin.sys.entity.SysUser;
import com.mex.pdd.modules.admin.sys.entity.SysUserToken;
import com.mex.pdd.modules.admin.sys.service.SysUserService;
import com.mex.pdd.modules.admin.sys.service.SysUserTokenService;
import io.swagger.annotations.Api;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
@Api(description = "登录管理", tags = {"登录相关"})
public class SysLoginController {
    @Autowired
    private Producer producer;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;

    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody LoginVM loginVM) {
//        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//        if (!captcha.equalsIgnoreCase(kaptcha)) {
//            return ResponseBean.error("验证码不正确");
//        }
        //用户信息
        SysUser user = sysUserService.queryByUserName(loginVM.getUsername());
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(loginVM.getPassword(), user.getSalt()).toHex())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("账号或密码不正确");
        }
        //账号锁定
        if (user.getStatus() == Status.disable) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("账号已被锁定,请联系管理员");
        }
        //生成token，并保存到数据库
        SysUserToken token = sysUserTokenService.createToken(user.getUserId());
        return ResponseEntity.ok(token.getToken());
    }

    /**
     * 验证是否登录
     */
    @GetMapping(value = "/sys/auth")
    public ResponseEntity<Void> auth() {
        return ResponseEntity.ok(null);
    }

    /**
     * 退出
     */
    @RequestMapping(value = "/sys/logout", method = RequestMethod.DELETE)
    public ResponseEntity<Void> logout() {
        sysUserTokenService.logout(ShiroUtils.getUserId().orElseThrow(() -> new RuntimeException("当前用户不存在")));
        return ResponseEntity.ok(null);
    }

}

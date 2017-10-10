package com.chunyang.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.chunyang.service.UserService;
import com.chunyang.util.AesCbcUtil;

import net.sf.json.JSONObject;

/**
 * 用户登录认证Contorller
 * 
 * @author qinxuegang
 *
 */
@Controller
public class LoginContorller {

	@Value("${chunyang.wx-xcx.wxspAppid}")
	private String wxspAppid;
	@Value("${chunyang.wx-xcx.wxspSecret}")
	private String wxspSecret;
	@Value("${chunyang.wx-xcx.grant_type}")
	private String grant_type;

	private RestTemplate restTemplate = new RestTemplate();

	
/*	@RequestMapping(value="/")
	public String login() {
		Log log = LogFactory.getLog(LoginContorller.class);
		log.info("登录成功");
		return "login";
	}*/

	/**
	 * 小程序登录认证 解密用户敏感数据
	 * 
	 * @param code
	 *            用户允许登录后，回调内容会带上 code（有效期五分钟），开发者需要将 code 发送到开发者服务器后台，使用code 换取
	 *            session_key api，将 code 换成 openid 和 session_key
	 * @param iv
	 *            加密算法的初始向量
	 * @param encryptedData
	 *            明文,加密数据
	 * @return
	 */
	@ResponseBody // 该标签控制方法返回的数据
	@RequestMapping(value = "/decodeUserInfo", method = RequestMethod.POST)
	public Map<String, Object> decodeUserInfo(@RequestParam("code") String code, @RequestParam("iv") String iv,
			@RequestParam("encryptedData") String encryptedData) {

		Map<String, Object> map = new HashMap<String, Object>();

		// 登录凭证不能为空
		if (code == null || code.length() == 0) {
			map.put("status", 0);
			map.put("msg", "code 不能为空");
			return map;
		}

		/* 1、 向微信服务器 使用登录code 获取 session_key 和 openid */
		// 下面是使用了spring中的RestTemplat的get请求方法请求微信服务器的，而且使用了rest的风格进行的访问
		// getForObject()方法 第一个参数：访问的地址 第二个参数： 请求返回的数据类型 之后的参数为请求路径中包含的参数
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid={wxspAppid}&secret={wxspSecret}&js_code={code}&grant_type={grant_type}";
		// 发送请求
		String str = restTemplate.getForObject(url, String.class, wxspAppid, wxspSecret, code, grant_type);
		// 解析相关内容(转换成json)
		JSONObject json = JSONObject.fromObject(str);
		// 获取会话秘钥
		String session_key = json.getString("session_key");
		// 获取用户的唯一标识
		// String openid = json.getString("openid");

		/* 2、 对encryptedData加密数据进行AES解密 */
		try {
			String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
			if (null != result && result.length() > 0) {
				map.put("status", 1);
				map.put("msg", "解密成功");

				JSONObject userInfoJSON = JSONObject.fromObject(result);
				Map<String, Object> userInfo = new HashMap<String, Object>();
				userInfo.put("openId", userInfoJSON.get("openId"));
				userInfo.put("nickName", userInfoJSON.get("nickName"));
				userInfo.put("gender", userInfoJSON.get("gender"));
				userInfo.put("city", userInfoJSON.get("city"));
				userInfo.put("province", userInfoJSON.get("province"));
				userInfo.put("country", userInfoJSON.get("country"));
				userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
				userInfo.put("unionId", userInfoJSON.get("unionId"));
				map.put("userInfo", userInfo);
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("status", 0);
		map.put("msg", "解密失败");
		return map;
	}

}

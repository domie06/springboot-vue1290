package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.JiashutanwangEntity;
import com.entity.view.JiashutanwangView;

import com.service.JiashutanwangService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 家属探望
 * 后端接口
 * @author 
 * @email 
 * @date 2023-05-13 12:25:08
 */
@RestController
@RequestMapping("/jiashutanwang")
public class JiashutanwangController {
    @Autowired
    private JiashutanwangService jiashutanwangService;


    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JiashutanwangEntity jiashutanwang,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date tanwangshijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date tanwangshijianend,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("laoren")) {
			jiashutanwang.setLaorenbianhao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<JiashutanwangEntity> ew = new EntityWrapper<JiashutanwangEntity>();
                if(tanwangshijianstart!=null) ew.ge("tanwangshijian", tanwangshijianstart);
                if(tanwangshijianend!=null) ew.le("tanwangshijian", tanwangshijianend);

		PageUtils page = jiashutanwangService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiashutanwang), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,JiashutanwangEntity jiashutanwang, 
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date tanwangshijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date tanwangshijianend,
		HttpServletRequest request){
        EntityWrapper<JiashutanwangEntity> ew = new EntityWrapper<JiashutanwangEntity>();
                if(tanwangshijianstart!=null) ew.ge("tanwangshijian", tanwangshijianstart);
                if(tanwangshijianend!=null) ew.le("tanwangshijian", tanwangshijianend);

		PageUtils page = jiashutanwangService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiashutanwang), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( JiashutanwangEntity jiashutanwang){
       	EntityWrapper<JiashutanwangEntity> ew = new EntityWrapper<JiashutanwangEntity>();
      	ew.allEq(MPUtil.allEQMapPre( jiashutanwang, "jiashutanwang")); 
        return R.ok().put("data", jiashutanwangService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(JiashutanwangEntity jiashutanwang){
        EntityWrapper< JiashutanwangEntity> ew = new EntityWrapper< JiashutanwangEntity>();
 		ew.allEq(MPUtil.allEQMapPre( jiashutanwang, "jiashutanwang")); 
		JiashutanwangView jiashutanwangView =  jiashutanwangService.selectView(ew);
		return R.ok("查询家属探望成功").put("data", jiashutanwangView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        JiashutanwangEntity jiashutanwang = jiashutanwangService.selectById(id);
        return R.ok().put("data", jiashutanwang);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        JiashutanwangEntity jiashutanwang = jiashutanwangService.selectById(id);
        return R.ok().put("data", jiashutanwang);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody JiashutanwangEntity jiashutanwang, HttpServletRequest request){
    	jiashutanwang.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(jiashutanwang);
        jiashutanwangService.insert(jiashutanwang);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody JiashutanwangEntity jiashutanwang, HttpServletRequest request){
    	jiashutanwang.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(jiashutanwang);
        jiashutanwangService.insert(jiashutanwang);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody JiashutanwangEntity jiashutanwang, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jiashutanwang);
        jiashutanwangService.updateById(jiashutanwang);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        jiashutanwangService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	









}

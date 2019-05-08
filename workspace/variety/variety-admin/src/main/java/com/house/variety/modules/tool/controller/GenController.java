package com.house.variety.modules.tool.controller;//package com.hsl.frame.modules.tool.controller;
//
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.hsl.frame.commons.controller.BaseController;
//import com.hsl.frame.modules.system.entity.SysConfig;
//import com.hsl.generator.domain.GeneratorVo;
//import com.hsl.generator.domain.TableInfo;
//import com.hsl.generator.service.IGenService;
//import com.hsl.pile.core.annotation.Log;
//import com.hsl.pile.core.enums.BusinessType;
//import com.hsl.pile.core.pojo.HslResponse;
//import com.hsl.pile.core.utils.str.Convert;
//import org.apache.commons.io.IOUtils;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
///**
// * 描述  代码生成 操作处理<br/>
// * 创建人  HuangChao <br/>
// * 创建时间  2018/11/8 10:41
// */
//@Controller
//@RequestMapping("/tool/gen")
//public class GenController extends BaseController {
//    private String prefix = "tool/gen";
//
//    @Reference
//    private IGenService genService;
//
//    @RequiresPermissions("tool:gen:view")
//    @GetMapping()
//    public String gen() {
//        return prefix + "/gen";
//    }
//
//    @RequiresPermissions("tool:gen:list")
//    @PostMapping("/list")
//    @ResponseBody
//    public HslResponse<IPage<TableInfo>> list(TableInfo tableInfo) {
//        return HslResponse.ok(genService.selectTableList(getPage(), tableInfo));
//    }
//
//    /**
//     * 生成代码
//     */
//    @RequiresPermissions("tool:gen:code")
//    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
//    @GetMapping("/genCode")
//    public void genCode(HttpServletResponse response,  GeneratorVo generatorVo) throws IOException {
//        byte[] data = genService.generatorCode(generatorVo);
//        response.reset();
//        response.setHeader("Content-Disposition", "attachment; filename=\"frame.zip\"");
//        response.addHeader("Content-Length", "" + data.length);
//        response.setContentType("application/octet-stream; charset=UTF-8");
//
//        IOUtils.write(data, response.getOutputStream());
//    }
//
//    /**
//     * 批量生成代码
//     */
//    @RequiresPermissions("tool:gen:code")
//    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
//    @GetMapping("/batchGenCode")
//    @ResponseBody
//    public void batchGenCode(HttpServletResponse response, GeneratorVo generatorVo, String tables) throws IOException {
//        String[] tableNames = Convert.toStrArray(tables);
//        byte[] data = genService.generatorCode(generatorVo,tableNames);
//        response.reset();
//        response.setHeader("Content-Disposition", "attachment; filename=\"frame.zip\"");
//        response.addHeader("Content-Length", "" + data.length);
//        response.setContentType("application/octet-stream; charset=UTF-8");
//
//        IOUtils.write(data, response.getOutputStream());
//    }
//}

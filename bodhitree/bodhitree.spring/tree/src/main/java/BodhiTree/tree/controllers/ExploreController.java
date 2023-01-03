
package BodhiTree.tree.controllers;

import BodhiTree.tree.models.Subject;
import BodhiTree.tree.services.models.ExploreVO;
import BodhiTree.tree.lib.JSON;
import BodhiTree.tree.lib.Result;
import BodhiTree.tree.models.common.Link;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
@RequestMapping(path="/explore")
public class ExploreController {

    static Logger logger = LoggerFactory.getLogger(ExploreController.class);

    private static ObjectMapper mapper = JSON.getMapper();

    @GetMapping(value = {"", "/"})
    Result explore () {

        ExploreVO data = new ExploreVO()
            // banners
            .addBanner(new Subject(null,
                "buddha",
                "佛陀（觉悟者）",
                "",
                "bodhidharma.jpeg",
                new Link("觉悟之路", "http://www.quanxue.cn/CT_FoJia/JueWuZhiLIndex.html")
            ))
            .addBanner(new Subject(null,
                "laotse",
                "老子",
                "",
                "laotse.jpeg",
                new Link("孔子问道于老子", "https://mp.weixin.qq.com/s/_v5x56Qo6uIccHD3bFJwhQ")
            ))
            .addBanner(new Subject(null,
                "confucius",
                "孔子",
                "",
                "confucius.jpeg",
                new Link("至圣先师孔子", "http://www.quanxue.cn/CT_RuJia/LiDai/LiDai02.html")
            ))
            // categories
            .addCategory(new Subject(null,
                "bodhidharma", "佛陀教育",
                "",
                "bodhidharma.jpeg",
                new Link("觉悟之路", "http://www.quanxue.cn/CT_FoJia/JueWuZhiLIndex.html")
            ))
            .addCategory(new Subject(null,
                "chinese.classics", "国学",
                "",
                "chinese-classics.png",
                new Link("论语", "http://www.quanxue.cn/CT_RuJia/LunYu/LunYu01.html")
            ))
            .addCategory(new Subject(null,
                "chinese", "语文",
                "",
                "chinese.png",
                new Link("觉悟之路", "http://www.quanxue.cn/CT_FoJia/JueWuZhiLIndex.html")
            ))
            .addCategory(new Subject(null,
                "math", "数学",
                "",
                "math.jpeg",
                new Link("觉悟之路", "http://www.quanxue.cn/CT_FoJia/JueWuZhiLIndex.html")
            ))
            .addCategory(new Subject(null,
                "english", "英语",
                "",
                "english.jpeg",
                new Link("觉悟之路", "http://www.quanxue.cn/CT_FoJia/JueWuZhiLIndex.html")
            ))
            .addCategory(new Subject(null,
                "history", "历史",
                "",
                "history.jpeg",
                new Link("觉悟之路", "http://www.quanxue.cn/CT_FoJia/JueWuZhiLIndex.html")
            ));

        return new Result()
            .code(Result.SUCCESS)
            .data(data);
    }

}


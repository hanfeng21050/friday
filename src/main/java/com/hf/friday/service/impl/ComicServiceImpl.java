package com.hf.friday.service.impl;

import com.hf.friday.base.Constants;
import com.hf.friday.base.ResponseCode;
import com.hf.friday.util.TokenUtil;
import com.hf.friday.vo.*;
import com.hf.friday.base.Results;
import com.hf.friday.dao.*;
import com.hf.friday.model.*;
import com.hf.friday.service.ComicService;
import com.hf.friday.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author CoolWind
 * @Date 2020/4/16 16:48
 */
@Service
@Transactional
@Slf4j
public class ComicServiceImpl implements ComicService {

    @Value("${comic.path}")
    private String comicPath;
    @Autowired
    private ComicDAO comicDAO;
    @Autowired
    private ImageDAO imageDAO;
    @Autowired
    private ComicConfigDAO comicConfigDAO;
    @Autowired
    private ChapterDAO chapterDAO;
    @Autowired
    private TagDAO tagDAO;
    @Autowired
    private TypeDAO typeDAO;
    @Autowired
    private ComicTagDAO comicTagDAO;
    @Autowired
    private ComicTypeDAO comicTypeDAO;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private UserCommentDAO userCommentDAO;
    @Autowired
    private UserCollectionDAO userCollectionDAO;
    @Autowired
    private CardDAO cardDAO;
    @Autowired
    SwiperImgDAO swiperImgDAO;


    @Override
    public Results<Comic> listByPage(Integer offset, Integer limit) {
        ComicExample example = new ComicExample();
        example.setLimit(limit);
        example.setOffset((long)offset);
        List<Comic> comicList = comicDAO.selectByExample(example);
        int count = (int) comicDAO.countByExample(new ComicExample());
        return Results.success(count,comicList);
    }

    @Override
    public List<Comic> selectAll()
    {
        ComicExample comicExample = new ComicExample();
        comicExample.createCriteria().andStatusEqualTo(Constants.VALID);
        return comicDAO.selectByExample(comicExample);
    }

    @Override
    public Comic selectById(int id) {
        return comicDAO.selectByPrimaryKey(id);
    }

    @Override
    public Results insert(Comic comic) {
        //生成编号
        ComicConfig comicConfig = comicConfigDAO.selectByPrimaryKey(Constants.CONFIGID);
        comicConfig.setNum(comicConfig.getNum() + 1);
        comicConfigDAO.updateByPrimaryKey(comicConfig);

        String no = StringUtil.genNO(comicConfig.getNum());
        comic.setNo(no);
        comic.setStatus(0);
        comic.setCreateTime(new Date());
        comic.setLikeNum(0);
        comic.setCount(0);
        comic.setFire(0);

        return comicDAO.insertSelective(comic) > 0 ? Results.success() : Results.failure();
    }

    @Override
    public Results addComic(Comic comic, List<Integer> tagIdList, Integer typeId) {
        //保存comic
        ComicConfig comicConfig = comicConfigDAO.selectByPrimaryKey(Constants.CONFIGID);
        comicConfig.setNum(comicConfig.getNum() + 1);
        comicConfigDAO.updateByPrimaryKey(comicConfig);
        String no = StringUtil.genNO(comicConfig.getNum());
        comic.setNo(no);
        comic.setStatus(0);
        comic.setCreateTime(new Date());
        comic.setLikeNum(0);
        comic.setCount(0);
        comic.setFire(0);
        //返回主键
        comicDAO.insertSelective(comic);

        int comicId = comic.getId();
        //保存type
        ComicType comicType = new ComicType();
        comicType.setComicId(comicId);
        comicType.setTypeId(typeId);
        comicType.setCreateTime(new Date());
        comicTypeDAO.insertSelective(comicType);

        //保存标签
        for (Integer tagId : tagIdList) {
            ComicTag comicTag = new ComicTag();
            comicTag.setComicId(comicId);
            comicTag.setTagId(tagId);
            comicTag.setCreateTime(new Date());
            comicTagDAO.insertSelective(comicTag);
        }
        return Results.success();
    }

    @Override
    public Results upload(ImageVO imageVO) throws IOException {
        Comic comic = comicDAO.selectByPrimaryKey(imageVO.getId());
        //漫画编号
        String no = comic.getNo();
        //封面名
        String imgName = no + "0000";

        MultipartFile img = imageVO.getFile();
        String extName = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
        String url = comicPath+no+"/"+imgName+extName;

        File file = new File(url);
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }

        FileCopyUtils.copy(img.getInputStream(),new FileOutputStream(file));
        comic.setCoverImg("/comic/" + no+"/"+imgName+extName);
        int x = comicDAO.updateByPrimaryKey(comic);

        return x == 1 ? Results.success() : Results.failure();
    }


    /**
     * 获取全部章节
     * @param id
     * @return
     */
    @Override
    public Results<Chapter> getChapter(int id) {
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.setOrderByClause("chapter.index ASC");
        chapterExample.createCriteria().andComicIdEqualTo(id).andStatusEqualTo(1);
        //List<Chapter> chapters = chapterDAO.listByComicId(id);
        List<Chapter> chapters = chapterDAO.selectByExample(chapterExample);
        return Results.success("success",chapters.size(),chapters);
    }


    /**
     * 返回一个章节页面的图片 分页
     * @return
     */
    @Override
    public Results<DetailVO> list(HtpRquest request) {

        //返回图片
        request.countOffset();
        List<Image> images = imageDAO.listByChapterId(request.getId(),request.getOffset(),request.getLimit());
        //返回当前章节图片的总数
        ImageExample example = new ImageExample();
        example.createCriteria().andTargetIdEqualTo(request.getId());
        long count = imageDAO.countByExample(example);
        ComicConfig comicConfig = comicConfigDAO.selectByPrimaryKey(Constants.CONFIGID);

        Chapter chapter = chapterDAO.selectByPrimaryKey(request.getId());
        DetailVO detailVO = new DetailVO();
        detailVO.setChapter(chapter);
        detailVO.setImageList(images);
        detailVO.setCount((int) count);
        detailVO.setHost(comicConfig.getHost());
        return Results.success("success",detailVO);
    }


    /**
     * app index页面所需数据
     * @return
     */
    @Override
    public Results<ComicVO> getHotComic(HtpRquest request) {
        //type表示取出什么类型的漫画
        if(request.getType() == 1)
        {
            ComicConfig comicConfig = comicConfigDAO.selectByPrimaryKey(Constants.CONFIGID);
            ComicExample comicExample = new ComicExample();
            comicExample.setOffset((long) request.getOffset());
            comicExample.setLimit(request.getLimit());
            comicExample.createCriteria().andStatusEqualTo(1);
            comicExample.setOrderByClause("comic.fire desc");
            List<Comic> comicList = comicDAO.selectByExample(comicExample);

            List<ComicVO> list = new ArrayList<>();
            for (Comic comic1 : comicList) {
                ComicVO dto = new ComicVO();
                //最新章节
                Chapter chapter = chapterDAO.selectNew(comic1.getId());
                dto.setChapter(chapter);
                dto.setComic(comic1);
                dto.setHost(comicConfig.getHost());
                list.add(dto);
            }

            //当前类型漫画总数量
            int count = (int) comicDAO.countByExample(new ComicExample());

            return Results.success("success",count,list);
        }
        return Results.failure();
    }

    /**
     * app ComicDetail页面所需数据
     * @return
     */
    @Override
    public Results<ComicDetailVO> getComicDetail(HtpRquest request) {
        Integer id = request.getId();

        ComicConfig comicConfig = comicConfigDAO.selectByPrimaryKey(Constants.CONFIGID);
        //查询漫画
        Comic comic = comicDAO.selectByPrimaryKey(id);
        //推荐漫画
        ComicExample comicExample = new ComicExample();
        comicExample.setOffset(0L);
        comicExample.setLimit(10);
        comicExample.createCriteria().andStatusEqualTo(1);
        comicExample.setOrderByClause("comic.fire desc");
        List<Comic> comicList = comicDAO.selectByExample(comicExample);
        //List<Comic> comicList = comicDAO.selectHost(0, 10);

        //查询章节
        ChapterExample example = new ChapterExample();
        example.setOrderByClause("chapter.index ASC");
        example.createCriteria().andComicIdEqualTo(comic.getId()).andStatusEqualTo(1);
        List<Chapter> chapterList = chapterDAO.selectByExample(example);

        ComicDetailVO comicDetailVO = new ComicDetailVO();
        comicDetailVO.setChapterList(chapterList);
        comicDetailVO.setComic(comic);

        //查询玩家是否收藏该漫画
        Integer userId = request.getUserId();
        UserCollectionExample userCollectionExample = new UserCollectionExample();
        userCollectionExample.createCriteria().andUserIdEqualTo(userId).andTargetIdEqualTo(id).andTypeEqualTo(Constants.COLLECT_COMIC).andStatusEqualTo(Constants.VALID);
        List<UserCollection> userCollections = userCollectionDAO.selectByExample(userCollectionExample);
        if(userCollections.isEmpty())
        {
            comicDetailVO.setIsCollect(false);
        }else if(userCollections.get(0).getStatus() == Constants.INVALID)
        {
            comicDetailVO.setIsCollect(false);
        }
        else {
            comicDetailVO.setIsCollect(true);
        }

        List<ComicVO> comicVOList = new ArrayList<>();
        //详细下的推荐漫画
        for (Comic comic1 : comicList) {
            ComicVO dto = new ComicVO();
            Chapter chapter = chapterDAO.selectNew(comic1.getId());
            dto.setChapter(chapter);
            dto.setComic(comic1);
            dto.setHost(comicConfig.getHost());
            comicVOList.add(dto);
        }
        comicDetailVO.setComicVOList(comicVOList);
        comicDetailVO.setHost(comicConfig.getHost());
        return Results.success(comicDetailVO);
    }

    @Override
    public Results switchStat(Boolean status, Integer id) {
        ChapterExample chapterExample = new ChapterExample();
        ChapterExample.Criteria criteria = chapterExample.createCriteria().andComicIdEqualTo(id);
        List<Chapter> chapterList = chapterDAO.selectByExample(chapterExample);
        if(chapterList != null)
        {
            for (Chapter chapter : chapterList) {
                chapter.setStatus(!status? 0:1);
                chapterDAO.updateByPrimaryKeySelective(chapter);
            }
        }

        Comic comic = comicDAO.selectByPrimaryKey(id);
        comic.setStatus(status? 1:0);
        comicDAO.updateByPrimaryKey(comic);
        return Results.success();
    }

    @Override
    public Results<ComicDetailVO> getTagAndType() {
        TagExample tagExample = new TagExample();
        tagExample.createCriteria().andStatusEqualTo(1);
        List<Tag> tagList = tagDAO.selectByExample(tagExample);

        TypeExample typeExample = new TypeExample();
        typeExample.createCriteria().andStatusEqualTo(1);
        List<Type> typeList = typeDAO.selectByExample(typeExample);

        ComicDetailVO vo = new ComicDetailVO();
        vo.setTagList(tagList);
        vo.setTypeList(typeList);
        return Results.success(vo);
    }

    @Override
    public Results<LoginVO> login(SysUser user) {
        SysUser userByDB = userDao.getUserByUsername(user.getUsername());
        if(userByDB == null)
        {
            //用户不存在
            return Results.failure(ResponseCode.USERNAME_INVALID);
        }
        else if(userByDB.getStatus() == SysUser.Status.LOCKED){
            //用户被锁定
            return Results.failure(ResponseCode.USERNAME_LOCK);
        }
        else if(userByDB.getStatus() == SysUser.Status.VALID){
            if(passwordEncoder.matches(user.getPassword(),userByDB.getPassword()))
            {
                String token = TokenUtil.buildJWT(userByDB.getId().intValue());
                LoginVO loginVO = new LoginVO();
                loginVO.setToken(token);
                loginVO.setUser(userByDB);
                return Results.success("登录成功",loginVO);
            }else
            {
                return Results.failure(ResponseCode.USERNAME_ERROR_PASSWORD);
            }
        }
        return null;
    }

    @Override
    public Results verify(String token) {
        Integer res = TokenUtil.verifyToken(token);
        if(res == -1)
        {
            return new Results(ResponseCode.TOKEN_INVALID.getCode(),ResponseCode.TOKEN_INVALID.getMessage());
        }else if(res == -2)
        {
            return new Results(ResponseCode.TOKEN_EXPIRE.getCode(),ResponseCode.TOKEN_EXPIRE.getMessage());

        }else if(res > 0)
        {
            return Results.success("登录成功");
        }else {
            return Results.failure();
        }
    }

    @Override
    public Results addComment(HtpRquest request) {
        //获取目标id
        Integer targetId = request.getId();
        //获取发表人id
        Integer userId = request.getUserId();

        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setTargetId(targetId);
        comment.setText(request.getText());
        comment.setType(1);
        int i = commentDAO.insertSelective(comment);

        return i == 1 ? Results.success() : Results.failure();
    }

    @Override
    public Results<CommentVO> getCommentList(HtpRquest request) {
        request.countOffset();
        //得到目标id
        Integer targetId = request.getId();
        CommentExample example = new CommentExample();
        example.setOffset((long) request.getOffset());
        example.setLimit(request.getLimit());

        if(request.getType() == 1)
        {
            //时间排序最近
            example.setOrderByClause("comment.create_time desc");
        }else if(request.getType() == 2)
        {
            //时间排序最远
            //时间排序最近
            example.setOrderByClause("comment.create_time asc");
        }else if(request.getType() == 3){
            //最热排序降序
            example.setOrderByClause("comment.like_num desc");
        }else if(request.getType() == 4){
            //最热排序升序
            example.setOrderByClause("comment.like_num asc");
        }
        example.createCriteria().andTargetIdEqualTo(targetId).andTypeEqualTo(1).andStatusEqualTo(Constants.VALID);
        //得到评论
        List<Comment> commentList = commentDAO.selectByExample(example);

        List<CommentVO> commentVOList = new ArrayList<>();
        for (Comment comment : commentList) {
            SysUser user = userDao.getUserById(comment.getUserId().longValue());
            CommentVO commentVO = new CommentVO();
            commentVO.setUser(user);
            commentVO.setComment(comment);
            commentVOList.add(commentVO);
        }


        //得到评论总数
        example.clear();
        example.createCriteria().andTargetIdEqualTo(request.getId()).andStatusEqualTo(Constants.VALID);
        int count = (int) commentDAO.countByExample(example);

        return Results.success("success",count,commentVOList);
    }

    @Override
    public Results collect(HtpRquest request) {
        int targetId = request.getId();
        //获取当前用户
        int userId = request.getUserId();

        UserCollectionExample userCollectionExample = new UserCollectionExample();
        userCollectionExample.createCriteria().andUserIdEqualTo(userId).andTargetIdEqualTo(targetId);
        List<UserCollection> userCollections = userCollectionDAO.selectByExample(userCollectionExample);
        int i;
        if(userCollections.isEmpty())
        {
            UserCollection userCollection = new UserCollection();
            userCollection.setTargetId(targetId);
            userCollection.setUserId(userId);
            userCollection.setType(request.getType());
            i = userCollectionDAO.insertSelective(userCollection);
        }else {
            UserCollection userCollection = new UserCollection();
            userCollection.setId(userCollections.get(0).getId());
            userCollection.setStatus(userCollections.get(0).getStatus() == 0 ? Constants.VALID : Constants.INVALID);
            i = userCollectionDAO.updateByPrimaryKeySelective(userCollection);
        }
        return Results.success();
    }

    @Override
    public Results<CardVO> getCardList(HtpRquest request) {
        ComicConfig comicConfig = comicConfigDAO.selectByPrimaryKey(Constants.CONFIGID);
        Integer userId = request.getUserId();
        String host = comicConfig.getHost();
        request.countOffset();
        CardExample cardExample = new CardExample();
        cardExample.setOffset((long)request.getOffset());
        cardExample.setLimit(request.getLimit());
        cardExample.createCriteria().andStatusEqualTo(Constants.VALID);
        if(request.getType() == 0)
        {
            //帖子最新
            cardExample.setOrderByClause("card.create_time desc");
        }else if(request.getType() == 1)
        {
            //点赞最多
            cardExample.setOrderByClause("card.like_num desc");
        }
        List<Card> cardList = cardDAO.selectByExample(cardExample);
        List<CardVO> list = new ArrayList<>();
        if(cardList != null && cardList.size() >0)
        {
            for (Card card : cardList) {
                CardVO vo = new CardVO();

                SysUser user = userDao.getUserById(card.getUserId().longValue());
                //通过当前用户id和card用户id
                if(userId == user.getId().intValue())
                {
                    vo.setFollow(true);
                }
                ImageExample imageExample  = new ImageExample();
                imageExample.createCriteria().andTargetIdEqualTo(card.getId()).andTypeEqualTo(1);
                List<Image> imageList = imageDAO.selectByExample(imageExample);

                vo.setCard(card);
                vo.setImageList(imageList);
                vo.setUser(user);
                vo.setHost(host);
                list.add(vo);
            }
        }

        //count
        cardExample.clear();
        cardExample.createCriteria().andStatusEqualTo(Constants.VALID);
        long count = cardDAO.countByExample(cardExample);
        return Results.success("success",(int)count,list);
    }

    @Override
    public Results<CardVO> getCardDetail(HtpRquest request) {
        ComicConfig comicConfig = comicConfigDAO.selectByPrimaryKey(Constants.CONFIGID);
        Card card = cardDAO.selectByPrimaryKey(request.getId());
        ImageExample imageExample = new ImageExample();
        imageExample.createCriteria().andTargetIdEqualTo(card.getId()).andTypeEqualTo(1);
        List<Image> imageList = imageDAO.selectByExample(imageExample);

        SysUser user = userDao.getUserById(card.getUserId().longValue());

        CardVO cardVO = new CardVO();
        cardVO.setUser(user);
        cardVO.setCard(card);
        cardVO.setImageList(imageList);
        cardVO.setHost(comicConfig.getHost());

        return Results.success("success",cardVO);
    }

    @Override
    public Results getUserInfo(HtpRquest request) {
        Integer userId = request.getUserId();
        SysUser user = userDao.getUserById(userId.longValue());
        UserCenterVO userCenterVO = new UserCenterVO();
        userCenterVO.setSysUser(user);
        return Results.success(userCenterVO);
    }

    @Override
    public Results getSwiperImg(){
        SwiperImgExample example = new SwiperImgExample();
        example.createCriteria().andStatusEqualTo(Constants.VALID);
        List<SwiperImg> list = swiperImgDAO.selectByExample(example);
        return Results.success("success",list.size(),list);
    }
}

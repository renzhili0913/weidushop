package com.example.administrator.myapplication13.iamgesview;

import android.app.Activity;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

public class PictureSelectorConfig {
    /**
     * 初始化多图选择的配置
     *
     * @param activity
     * @param maxTotal
     */
    public static void initMultiConfig(Activity activity, int maxTotal) {
      /**进入相册 以下是例子：用不到的api可以不写*/
        PictureSelector.create(activity)
                //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .openGallery(PictureMimeType.ofImage())
                // 最大图片选择数量 int
                .maxSelectNum(maxTotal)
                // 最小选择数量 int
                .minSelectNum(0)
                // 每行显示个数 int
                .imageSpanCount(3)
                // 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .selectionMode(PictureConfig.MULTIPLE)
                // 是否可预览图片 true or false
                .previewImage(true)
                // 是否可预览视频 true or false
                .previewVideo(false)
                // 是否可播放音频 true or false
                .enablePreviewAudio(false)
                // 是否显示拍照按钮 true or false
                .isCamera(true)
//                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                // 是否裁剪 true or false
                .enableCrop(false)
                // 是否压缩 true or false
                .compress(true)
//                .compressMaxKB(1024)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
//                .compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效  int
                // int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .glideOverride(160, 160)
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
                // 是否显示gif图片 true or false
                .isGif(false)
//                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                // 是否圆形裁剪 true or false
                .circleDimmedLayer(false)
//                .showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
//                .showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                // 是否开启点击声音 true or false
                .openClickSound(false)
//                .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                // 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .previewEggs(true)
//                .cropCompressQuality()// 裁剪压缩质量 默认90 int
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
//                .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
//                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoSecond()// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
                //结果回调onActivityResult code
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 初始化单张图片选择的配置
     *
     * @param activity
     */
    public static void initSingleConfig(Activity activity) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(activity)
                //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .openGallery(PictureMimeType.ofImage())
                // 最大图片选择数量 int
                .maxSelectNum(1)
                // 最小选择数量 int
                .minSelectNum(0)
                // 每行显示个数 int
                .imageSpanCount(3)
                // 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .selectionMode(PictureConfig.SINGLE)
                // 是否可预览图片 true or false
                .previewImage(true)
                // 是否可预览视频 true or false
                .previewVideo(false)
                // 是否可播放音频 true or false
                .enablePreviewAudio(false)
                // 是否显示拍照按钮 true or false
                .isCamera(true)
//                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                // 是否裁剪 true or false
                .enableCrop(true)
                // 是否压缩 true or false
                .compress(true)

//                .compressMaxKB(500)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
//                .compressWH(7, 10) // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效  int
                // int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .glideOverride(130, 130)
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                // 是否显示uCrop工具栏，默认不显示 true or false
                .hideBottomControls(true)
                // 是否显示gif图片 true or false
                .isGif(false)
                // 裁剪框是否可拖拽 true or false
                .freeStyleCropEnabled(false)
                // 是否圆形裁剪 true or false
                .circleDimmedLayer(true)
                // 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropFrame(false)
                // 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .showCropGrid(false)
                // 是否开启点击声音 true or false
                .openClickSound(false)
//                .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                // 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .previewEggs(true)
//                .cropCompressQuality()// 裁剪压缩质量 默认90 int
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                // 裁剪是否可旋转图片 true or false
                .rotateEnabled(true)
                // 裁剪是否可放大缩小图片 true or false
                .scaleEnabled(true)
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoSecond()// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
                //结果回调onActivityResult code
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
}
